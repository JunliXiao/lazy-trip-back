package order.dao;


import company.model.CompanyVO;
import company.model.CouponVO;
import company.model.RoomTypeImgVO;
import company.model.RoomTypeVO;
import order.model.OrderDetailVO;
import order.model.OrderVO;
import order.practice.BorisDataBaseJDBC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import common.HikariDataSource;

public class OrderJDBCDAOImpl implements OrderDAOInterface {


    //====================================================================================
    BorisDataBaseJDBC bd = new BorisDataBaseJDBC(); // 連線Boris的資料庫(JDBC)
    //====================================================================================




    //從定位找到該飯店的所有資料(之後再做限制筆數功能，俗稱分頁) 1?
    //方法已做
    private static final String SELECT_FIND_COMPANY_AND_ROOMTYPE_PRICE_BY_POSITION =
    "SELECT cm.company_id, cm.company_name, cm.introduction, cm.address_county, cm.address_area, cm.address_street, cm.company_img, MIN(rt.roomtype_price) FROM lazy.company cm JOIN lazy.roomtype rt ON cm.company_id = rt.company_id  WHERE cm.address_county = ? GROUP BY cm.company_id LIMIT 50;";

    //====================================================================================

    //從廠商編號 找到 廠商資料 1?
    //方法已做
    private static final String SELECT_FIND_COMPANY_BY_COMPANY_ID =
            "SELECT company_id, company_name, introduction, address_county, address_area, address_street, latitude, longitude, company_img FROM lazy.company WHERE company_id = ?;";


    //從 廠商編號 找到 廠商名稱 1?
    //方法已做
    private static final String SELECT_FIND_COMPANY_NAME_BY_COMPANY_ID =
            "SELECT company_name FROM lazy.company WHERE company_id = ?;";

    //從 廠商編號 找到所有房型資料 1?
    //方法已做
    private static final String SELECT_FIND_ALL_ROOMTYPE_BY_COMPANY_ID =
            "SELECT company_id, roomtype_id, roomtype_name, roomtype_person, roomtype_quantity, roomtype_price FROM lazy.roomtype WHERE company_id = ?;";


    //從 房型編號找到房型資料 1?
    //方法已做
    private static final String SELECT_FIND_ROOMTYPE_BY_ROOMTYPE_ID =
            "SELECT company_id, roomtype_name, roomtype_person, roomtype_quantity, roomtype_price FROM lazy.roomtype WHERE roomtype_id = ?;";


    //從 房型編號找到房型名稱 1?
    //方法已做
    private static final String SELECT_FIND_ROOMTYPE_NAME_BY_ROOMTYPE_ID =
            "SELECT roomtype_name FROM lazy.roomtype WHERE roomtype_id = ?;";


    //從房型id找到所有房型照片資料 1?
    //方法已做
    private static final String SELECT_FIND_All_ROOMTYPE_IMG_BY_ROOMTYPE_ID =
            "SELECT roomtype_id, roomtype_img_id, roomtype_img FROM lazy.roomtype_img WHERE roomtype_id = ?;";


    //====================================================================================


    //透過文字搜尋框 下關鍵字顯示飯店名稱、縣市、區域、其餘地址 4?
    //方法已做
    private static final String SELECT_SHOW_SEARCH_KEY_WORD_BY_COMPANY_NAME_OR_ADDRESS =
            "SELECT company_id, company_name, address_county, address_area, address_street FROM lazy.company WHERE company_name LIKE '%?%' OR address_county LIKE '%?%' OR address_area LIKE '%?%' OR address_street LIKE '%?%' ;";

    //====================================================================================

    //優惠碼套用(如果狀態為1 才找得到資料) 1?  先不要用!!!!
    //方法已做
//    private static final String SELECT_CONFIRM_COUPON_CAN_USED =
//            "SELECT coupon_id, coupon_discount, coupon_status FROM lazy.coupon WHERE coupon_id = ? AND coupon_status = 1;";

    //新的優惠券方法 1?
    private static final String SELECT_CONFIRM_COUPON =
            "SELECT coupon_id, company_id, coupon_text, coupon_starttime, coupon_endtime, coupon_status, coupon_discount, coupon_quantity, coupon_used_quantity FROM lazy.coupon WHERE coupon_id = ?;";
//            "SELECT coupon_id, company_id, coupon_text, coupon_starttime, coupon_endtime, coupon_img, coupon_status, coupon_discount, coupon_quantity, coupon_used_quantity FROM lazy.coupon WHERE coupon_id = ?;";

    //====================================================================================

    //送出訂單與訂單明細  10?
    //方法已做
    private static final String CREATE_ORDER =
            "INSERT INTO lazy.order (member_id, company_id, order_check_in_date, order_check_out_date, coupon_id, order_total_price, order_pay_deadline, traveler_name, traveler_id_number, traveler_email, traveler_phone)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL '23:59:59' HOUR_SECOND), ?, ?, ?, ?);";

    //5? 方法已做
    private static final String CREATE_ORDER_DETAIL =
            "INSERT INTO lazy.order_detail (order_id, roomtype_id, order_detail_room_price, order_detail_room_quantity, order_detail_coupon_discount_price)\n" +
                    "VALUES (?, ?, ?, ?, ?);";

    //====================================================================================

    //付款(更改付款狀態為"已付款") 6?
    //方法已做
    private static final String UPDATE_ORDER_PAY =
            "UPDATE lazy.order SET order_status = ?, order_pay_datetime = NOW(), order_pay_card_name = ?, order_pay_card_number = ?, order_pay_card_year = ?, order_pay_card_month = ? WHERE order_id = ? ;";

    //====================================================================================

    //付款期限到了(更改付款狀態為"已過付款期限")(要設定排程器) 0?
    //方法已做
    private static final String UPDATE_ORDER_OVER_TIME_FOR_PAY =
            "UPDATE lazy.order SET order_status = '超過付款時間' WHERE order_status = '未付款' AND order_pay_deadline < NOW() ;";

    //====================================================================================

    //透過會員編號找到所有訂單狀態的訂單基本資料(之後後端再依訂單狀態分類給使用者看，其中未付款要有訂單付款截止時間，已付款要有訂單付款時間) 1?
    //方法已做
    private static final String SELECT_FIND_ORDER_BY_MEMBER_ID =
            "SELECT order_id, order_check_in_date, order_check_out_date, company_id, order_total_price, order_status, coupon_id, order_create_datetime, order_pay_deadline, order_pay_datetime  FROM lazy.order WHERE member_id = ? ;";


    //在訂單基本資料上按下展開更多，查詢該筆訂單詳細資料 1?
    ////方法已做
    private static final String SELECT_FIND_ORDER_DETAIL_BY_ORDER_ID =
            "SELECT order_detail_id, roomtype_id, order_detail_room_price, order_detail_room_quantity, order_detail_coupon_discount_price FROM lazy.order_detail WHERE order_id = ?;";
    //====================================================================================



    //從定位找到該飯店的所有資料(之後再做限制筆數功能，俗稱分頁) 1?
    public List<CompanyVO> selectFindCompanyAndRoomTypePriceByPosition(String addressCounty){

        List<CompanyVO> companyVOs = new ArrayList<>();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_AND_ROOMTYPE_PRICE_BY_POSITION);) {

            ps.setString(1,addressCounty);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                CompanyVO companyVO = new CompanyVO();
                companyVO.setCompanyID(rs.getInt("cm.company_id"));
                companyVO.setCompanyName(rs.getString("cm.company_name"));
                companyVO.setIntroduction(rs.getString("cm.introduction"));
                companyVO.setAddressCounty(rs.getString("cm.address_county"));
                companyVO.setAddressArea(rs.getString("cm.address_area"));
//                companyVO.setAddressStreet(rs.getString("cm.address_street"));
                companyVO.setCompanyImg(rs.getString("cm.company_img"));
                RoomTypeVO roomTypeVO = new RoomTypeVO();
                roomTypeVO.setRoomTypePrice(rs.getInt("MIN(rt.roomtype_price)"));
                companyVO.setRoomTypeVO(roomTypeVO);
                companyVOs.add(companyVO);
            }
        }catch (SQLException e){
            System.out.println("SelectFindCompanyAndRoomTypePriceByPosition: "+e.getMessage());
        }
        return companyVOs;
        }






    //從廠商編號 找到 廠商資料 1?
    public CompanyVO selectFindCompanyByCompanyID(Integer companyID) {

        CompanyVO companyVO = new CompanyVO();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_BY_COMPANY_ID);) {

            ps.setInt(1, companyID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                companyVO.setCompanyID(rs.getInt("company_id"));
                companyVO.setCompanyName(rs.getString("company_name"));
                companyVO.setIntroduction(rs.getString("introduction"));
                companyVO.setAddressCounty(rs.getString("address_county"));
                companyVO.setAddressArea(rs.getString("address_area"));
                companyVO.setAddressStreet(rs.getString("address_street"));
                companyVO.setLatitude(rs.getDouble("latitude"));
                companyVO.setLongitude(rs.getDouble("longitude"));
                companyVO.setCompanyImg(rs.getString("company_img"));
            }
        } catch (SQLException e) {
            System.out.println("SelectFindCompanyByCompanyID :"+e.getMessage());
        }
        return companyVO;
    }


    //從 廠商編號 找到 廠商名稱 1?
    public CompanyVO selectFindCompanyNameByCompanyID(CompanyVO companyVO) {

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_COMPANY_NAME_BY_COMPANY_ID);) {

            ps.setInt(1, companyVO.getCompanyID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                companyVO.setCompanyName(rs.getString("company_name"));
            }
        } catch (SQLException e) {
            System.out.println("selectFindCompanyNameByCompanyID: "+e.getMessage());
        }
        return companyVO;
    }

    //從 廠商編號 找到所有房型資料 1?
    public List<RoomTypeVO> selectFindAllRoomTypeByCompanyID(Integer companyID) {

        List<RoomTypeVO> roomTypeVOs = new ArrayList<>();
        RoomTypeVO roomTypeVO = new RoomTypeVO();
//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_ALL_ROOMTYPE_BY_COMPANY_ID);) {

            ps.setInt(1, companyID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                roomTypeVO.setCompanyID(rs.getInt("company_id"));
                roomTypeVO.setRoomTypeID(rs.getInt("roomtype_id"));
                roomTypeVO.setRoomTypeName(rs.getString("roomtype_name"));
                roomTypeVO.setRoomTypePerson(rs.getInt("roomtype_person"));
                roomTypeVO.setRoomTypeQuantity(rs.getInt("roomtype_quantity"));
                roomTypeVO.setRoomTypePrice(rs.getInt("roomtype_price"));
                roomTypeVOs.add(roomTypeVO);
            }
        } catch (SQLException e) {
            System.out.println("SelectFindAllRoomTypeByCompanyID: "+e.getMessage());
        }
        return roomTypeVOs;
    }

    //從 房型編號找到房型資料 1?
    public RoomTypeVO selectFindRoomTypeByRoomTypeID(RoomTypeVO roomTypeVO) {
//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_ROOMTYPE_BY_ROOMTYPE_ID);) {

            ps.setInt(1, roomTypeVO.getRoomTypeID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roomTypeVO.setCompanyID(rs.getInt("company_id"));
                roomTypeVO.setRoomTypeName(rs.getString("roomtype_name"));
                roomTypeVO.setRoomTypePerson(rs.getInt("roomtype_person"));
                roomTypeVO.setRoomTypeQuantity(rs.getInt("roomtype_quantity"));
                roomTypeVO.setRoomTypePrice(rs.getInt("roomtype_price"));
            }
        } catch (SQLException e) {
            System.out.println("SelectFindRoomTypeByRoomTypeID: "+e.getMessage());
        }
        return roomTypeVO;
    }

    //從 房型編號找到房型名稱 1?
    public RoomTypeVO selectFindRoomTypeNameByRoomTypeID(RoomTypeVO roomTypeVO) {

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_ROOMTYPE_NAME_BY_ROOMTYPE_ID);) {

            ps.setInt(1, roomTypeVO.getRoomTypeID());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roomTypeVO.setRoomTypeName(rs.getString("roomtype_name"));
            }
        } catch (SQLException e) {
            System.out.println("selectFindRoomTypeNameByRoomTypeID: "+e.getMessage());
        }
        return roomTypeVO;
    }

    //從房型id找到所有房型照片資料 1?
    public List<RoomTypeImgVO> selectFindAllRoomTypeImgByRoomTypeID(Integer roomTypeID) {
        List<RoomTypeImgVO> roomTypeImgVOs = new ArrayList<>();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_All_ROOMTYPE_IMG_BY_ROOMTYPE_ID);) {

            ps.setInt(1, roomTypeID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();
                roomTypeImgVO.setRoomTypeID(rs.getInt("roomtype_id"));
                roomTypeImgVO.setRoomTypeImgID(rs.getInt("roomtype_img_id"));
                roomTypeImgVO.setRoomTypeImg(rs.getString("roomtype_img"));
                roomTypeImgVOs.add(roomTypeImgVO);
            }
        } catch (SQLException e) {
            System.out.println("selectFindAllRoomTypeImgByRoomTypeID: "+e.getMessage());
        }
        return roomTypeImgVOs;
    }


    //
    public List<CompanyVO> selectShowSearchKeyWordByCompanyNameOrAddress(CompanyVO companyVO) {

        List<CompanyVO> companyVOs = new ArrayList<>();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try(Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_SHOW_SEARCH_KEY_WORD_BY_COMPANY_NAME_OR_ADDRESS);) {
            ps.setString(1, companyVO.getCompanyName());
            ps.setString(2, companyVO.getAddressCounty());
            ps.setString(3, companyVO.getAddressArea());
            ps.setString(4, companyVO.getAddressStreet());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                companyVO = new CompanyVO();
                companyVO.setCompanyID(rs.getInt("company_id"));
                companyVO.setCompanyName(rs.getString("company_name"));
                companyVO.setAddressCounty(rs.getString("address_county"));
                companyVO.setAddressArea(rs.getString("address_area"));
                companyVO.setAddressStreet(rs.getString("address_street"));
                companyVOs.add(companyVO);
            }
        } catch (SQLException e) {
            System.out.println("selectShowSearchKeyWordByCompanyNameOrAddress: "+e.getMessage());
        }
        return companyVOs;
    }


    //優惠碼套用(如果狀態為1 才找得到資料) 1?  先不要用!!!
//    public CouponVO SelectConfirmCouponCanBeUsed(Integer couponID) {
//        CouponVO couponVO = new CouponVO();
//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
////        try(Connection con = HikariDataSource.getConnection();
//             PreparedStatement ps = con.prepareStatement(SELECT_CONFIRM_COUPON_CAN_USED);) {
//
//            ps.setInt(1, couponID);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                couponVO.setCouponID((rs.getInt("coupon_id")));
//                couponVO.setCouponDiscount(rs.getDouble("coupon_discount"));
//                couponVO.setCouponStatus(rs.getBoolean("coupon_status"));
//            }
//        } catch (SQLException e) {
//            System.out.println("SelectUseCoupon: "+e.getMessage());
//        }
//        return couponVO;
//    }


    //新的優惠券方法 1?
    public CouponVO selectConfirmCoupon(Integer couponID) {
        CouponVO couponVO = new CouponVO();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try(Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_CONFIRM_COUPON);) {

            ps.setInt(1,couponID);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                couponVO.setCouponID(rs.getInt("coupon_id"));
                couponVO.setCompanyID(rs.getInt("company_id"));
                couponVO.setCouponText(rs.getString("coupon_text"));
                couponVO.setCouponStartTime(rs.getObject("coupon_starttime",LocalDateTime.class));
                couponVO.setCouponEndTime(rs.getObject("coupon_endtime",LocalDateTime.class));
//                couponVO.setCouponImg(rs.getString("coupon_img"));
                couponVO.setCouponStatus(rs.getBoolean("coupon_status"));
                couponVO.setCouponDiscount(rs.getDouble("coupon_discount"));
                couponVO.setCouponQuantity(rs.getInt("coupon_quantity"));
                couponVO.setCouponUsedQuantity(rs.getInt("coupon_used_quantity"));
            }
        }catch (SQLException e){
            System.out.println("SelectConfirmCoupon: "+e.getMessage());
        }
        return couponVO;
    }









    //      新增訂單與訂單明細時，在JDBCDao做交易，一個方法直接做訂單與明細的新增，一旦出錯就還原，但搞不太清楚service裡要怎麼做，
//      於是寫了別的方法，分別在OrderDao和OrderDetailDao分別做新增，然後在Service裡面設立一個方法一次新增
    public int createOrderAndOrderDetail(OrderVO orderVO, List<OrderDetailVO> orderDetailVOs) {
        int result = -1;
        Connection conn = null;
        PreparedStatement ps = null;

//        String orderColumns[] = {"order_ID"};
//        String orderDetailColumns[] = {"`order_detail_ID`"};
        int order_ID = -1;

        try {
//            conn = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
            conn = HikariDataSource.getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, orderVO.getMemberID());
            ps.setInt(2, orderVO.getCompanyID());
            ps.setObject(3, orderVO.getOrderCheckInDate());
            ps.setObject(4, orderVO.getOrderCheckOutDate());
            ps.setInt(5, orderVO.getCouponID());
            ps.setInt(6, orderVO.getOrderTotalPrice());
            ps.setString(7, orderVO.getTravelerName());
            ps.setString(8, orderVO.getTravelerIDNumber());
            ps.setString(9, orderVO.getTravelerEmail());
            ps.setString(10, orderVO.getTravelerPhone());

            int rowCount = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order_ID = rs.getInt(1);
                if (order_ID < 0) {
                    System.out.println("order insert failed.");
                }
            }
            //拿到編號就繼續新增明細


            ps = conn.prepareStatement(CREATE_ORDER_DETAIL, Statement.RETURN_GENERATED_KEYS);

            for (OrderDetailVO orderDetailVO : orderDetailVOs) {
                ps.setInt(1, order_ID);
                ps.setInt(2, orderDetailVO.getRoomTypeID());
                ps.setInt(3, orderDetailVO.getOrderDetailRoomPrice());
                ps.setByte(4, orderDetailVO.getOrderDetailRoomQuantity());
                ps.setInt(5, orderDetailVO.getOrderDetailCouponDiscountPrice());
                ps.addBatch();
            }

            int[] rowCount2 = ps.executeBatch();
            int sum = 0;
            for (int rc2 : rowCount2) {
                sum += rc2;
            }
            rs = ps.getGeneratedKeys();

            while (rs.next()) {
                int orderDetailID = rs.getInt(1);
                System.out.println("orderID: " + order_ID + " orderDetailID: " + orderDetailID + " inserted.");
            }

            conn.commit();
            conn.setAutoCommit(true);
            result = 1;
        } catch (SQLException se) {
            result = 0;
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    System.out.println("rollback failed. " + e.getMessage());
                }

            }
            System.out.println("createOrderAndOrderDetail: "+ se.getMessage());
            System.out.println("insert failed.");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    //付款(更改付款狀態為"已付款") 6?
    public int orderPay(OrderVO orderVO) {

        int result = -1;

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_PAY);) {

            ps.setString(1, "已付款");
            ps.setString(2, orderVO.getOrderPayCardName());
            ps.setString(3, orderVO.getOrderPayCardNumber());
            ps.setString(4, orderVO.getOrderPayCardYear());
            ps.setString(5, orderVO.getOrderPayCardMonth());
            ps.setInt(6, orderVO.getOrderID());
            ps.executeUpdate();

            System.out.println("orderID: " + orderVO.getOrderID() + " update success");
            result = 1;

        } catch (SQLException e) {
            result = 0;
            System.out.println("OrderPay: "+e.getMessage());
            System.out.print("Update the pay with order failed.");

        }
        return result;
    }


    //付款期限到了(更改付款狀態為"已過付款期限")(要設定排程器) 0?
    public int updateOrderOverTimeForPay() {

        int result = -1;

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_OVER_TIME_FOR_PAY);) {

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                int orderID = rs.getInt(1);
                System.out.println("orderID :" + orderID + " over pay for deadline and update success.");
            }

            result = 1;

        } catch (SQLException e) {
            System.out.println("updateOrderOverTimeForPay: "+e.getMessage());
            System.out.println("Update over time for pay failed.");
            result = 0;
        }
        return result;
    }


    //透過會員編號找到所有訂單狀態的訂單基本資料(之後後端再依訂單狀態分類給使用者看，其中未付款要有訂單付款截止時間，已付款要有訂單付款時間) 1?
    public List<OrderVO> selectFindOrderByMemberID(OrderVO orderVO) {

        List<OrderVO> orderVOs = new ArrayList<>();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_BY_MEMBER_ID);) {

            ps.setInt(1, orderVO.getMemberID());
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                orderVO = new OrderVO();
                orderVO.setOrderID(rs.getInt("order_id"));
                orderVO.setOrderCheckInDate(rs.getObject("order_check_in_date", LocalDate.class));
                orderVO.setOrderCheckOutDate(rs.getObject("order_check_out_date", LocalDate.class));
                orderVO.setCompanyID(rs.getInt("company_id"));
                orderVO.setOrderTotalPrice(rs.getInt("order_total_price"));
                orderVO.setOrderStatus(rs.getString("order_status"));
                orderVO.setCouponID(rs.getInt("coupon_id"));
                orderVO.setOrderCreateDatetime(rs.getObject("order_create_datetime", LocalDateTime.class));
                orderVO.setOrderPayDeadline(rs.getObject("order_pay_deadline", LocalDateTime.class));
                orderVO.setOrderPayDatetime(rs.getObject("order_pay_datetime", LocalDateTime.class));
                orderVOs.add(orderVO);
            }

        } catch (SQLException e) {
            System.out.println("SelectFindOrderByMemberID: "+e.getMessage());
        }
        return orderVOs;

    }

    //在訂單基本資料上按下展開更多，查詢該筆訂單詳細資料 1?
    public List<OrderDetailVO> selectFindOrderDetailByOrderID(OrderDetailVO orderDetailVO) {

        List<OrderDetailVO> orderDetailVOs = new ArrayList<>();

//        try (Connection con = DriverManager.getConnection(bd.URL, bd.USER, bd.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FIND_ORDER_DETAIL_BY_ORDER_ID);) {

            ps.setInt(1, orderDetailVO.getOrderID());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orderDetailVO = new OrderDetailVO();
                orderDetailVO.setOrderDetailID(rs.getInt("order_detail_id"));
                orderDetailVO.setRoomTypeID(rs.getInt("roomtype_id"));
                orderDetailVO.setOrderDetailRoomPrice(rs.getInt("order_detail_room_price"));
                orderDetailVO.setOrderDetailRoomQuantity(rs.getByte("order_detail_room_quantity"));
                orderDetailVO.setOrderDetailCouponDiscountPrice(rs.getInt("order_detail_coupon_discount_price"));
                orderDetailVOs.add(orderDetailVO);
            }


        } catch (SQLException e) {
            System.out.println("SelectFindOrderDetailByOrderID: "+e.getMessage());
        }

        return orderDetailVOs;
    }


}




