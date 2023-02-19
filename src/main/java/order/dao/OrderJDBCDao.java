package order.dao;

import common.HikariDataSource;
import order.model.OrderDetailVO;
import order.model.OrderVO;
import order.practice.SqlConnection;

import java.sql.*;
import java.util.List;

public class OrderJDBCDao implements OrderDao_Interface {
    

    //文字搜尋飯店或飯店縣市、飯店區域、飯店街道的關鍵字顯示符合條件的飯店(顯示在下拉選單) 先不要做
    //4?
//    private static final String select_search_keyword =
//            "select company_name, address_county, address_area, address_street from lazy.company where company_name like '%?%' or address_county like '%?%' or address_area like '%?%' or address_street like '%?%';";

    //====================================================================================

    //透過文字搜尋框 下關鍵字顯示飯店名稱、縣市、區域、其餘地址
    // 4? OK 需要companyVO檔
    private static final String SELECT_SEARCH_KEYWORD_COMPANY_NAME_AND_ADDRESS =
            "SELECT company_id, company_name, address_county, address_area, address_street FROM lazy.company WHERE company_name LIKE '%?%' OR address_county LIKE '%?%' OR address_area LIKE '%?%' OR address_street LIKE '%?%' ;";

    //====================================================================================

    //選擇飯店後顯示飯店資料和所有房型資料
    //1? OK 需要companyVO、roomtypeVO、roomtype_imgVO檔
    private static final String SELECT_THE_COMPANY_ROOM_TYPE =
            "SELECT cp.company_id, cp.company_name, cp.introduction, cp.address_county, cp.address_area, cp.address_street, \n" +
                    "cp.latitude, cp.longitude, cp.company_img, rt.roomtype_id, rt.roomtype_name, rt.roomtype_person, rt.roomtype_quantity, \n" +
                    "rt.roomtype_price, rti.roomtype_img FROM lazy.company cp JOIN lazy.roomtype rt ON cp.company_id  = rt.company_id \n" +
                    "JOIN lazy.roomtype_img rti on rt.roomtype_id = rti.roomtype_id WHERE cp.company_id  = ?;";

    //====================================================================================
    //優惠碼套用(如果狀態為1 才找得到資料)
    // 1? OK 需要couponVO檔
    private static final String SELECT_USE_COUPON =
            "SELECT coupon_id, coupon_discount, coupon_status FROM lazy.coupon WHERE coupon_id = ? AND coupon_status = 1;";

    //====================================================================================

    //送出訂單與訂單明細
    //10? OK void createOrderAndOrderDetail()
    private static final String CREATE_ORDER =
            "INSERT INTO lazy.order (member_id, company_id, order_check_in_date, order_check_out_date, coupon_id, order_total_price, order_pay_deadline, traveler_name, traveler_id_number, traveler_email, traveler_phone)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL '23:59:59' HOUR_SECOND), ?, ?, ?, ?);";

    //5? OK void createOrderAndOrderDetail()
    private static final String CREATE_ORDER_DETAIL =
            "INSERT INTO lazy.order_detail (order_id, roomtype_id, order_detail_room_price, order_detail_room_quantity, order_detail_coupon_discount_price)\n" +
                    "VALUES (?, ?, ?, ?, ?);";
    //====================================================================================

    //付款(更改付款狀態為"已付款")
    //6? OK  boolean OrderPay
    private static final String UPDATE_ORDER_PAY =
            "UPDATE lazy.order SET order_status = ?, order_pay_datetime = NOW(), order_pay_card_name = ?, order_pay_card_number = ?, order_pay_card_year = ?, order_pay_card_month = ? WHERE order_id = ? ;";
    //====================================================================================

    //付款期限到了(更改付款狀態為"已過付款期限")(要設定排程器)
    //0? OK
    private static final String UPDATE_ORDER_OVER_TIME_FOR_PAY =
            "UPDATE lazy.order SET order_status = '超過付款時間' WHERE order_status = '未付款' AND order_pay_deadline < NOW() ;";
    //====================================================================================

    //透過會員編號找到所有訂單狀態的訂單基本資料(之後後端再依訂單狀態分類給使用者看，其中未付款要有訂單付款截止時間，已付款要有訂單付款時間)
    //1?  OK 需要companyVO檔
    private static final String SELECT_ALL_ORDER =
            "SELECT o.order_id, o.order_check_in_date, o.order_check_out_date, cp.company_name, o.order_total_price, o.order_status, o.coupon_id, o.order_create_datetime, o.order_pay_deadline, o.order_pay_datetime  FROM lazy.order o JOIN lazy.company cp ON o.company_id = cp.company_id WHERE o.member_id = ? ;";

    //在訂單基本資料上按下展開更多，查詢該筆訂單詳細資料
    //1?  OK 需要roomtypeVO檔
    private static final String SELECT_THE_ORDER_DETAIL =
            "SELECT od.order_detail_id, rt.roomtype_name, od.order_detail_room_price, od.order_detail_room_quantity FROM lazy.order_detail od JOIN lazy.roomtype rt ON od.roomtype_id = rt.roomtype_id WHERE od.order_id = ?;";


    //====================================================================================
    SqlConnection sc = new SqlConnection();
    //====================================================================================



    public int updateOrderOverTimeForPay() {
        int rowCount = -1;
//        try (Connection con = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_OVER_TIME_FOR_PAY);) {
            rowCount = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update over time for pay failed.");
        } finally {
            return rowCount;
        }
    }


    public boolean OrderPay(OrderVO orderVO) {
        int rowCount = -1;
//        try (Connection con = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
        try (Connection con = HikariDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ORDER_PAY);) {

            ps.setString(1, "已付款");
            ps.setString(2, orderVO.getOrderPayCardName());
            ps.setString(3, orderVO.getOrderPayCardNumber());
            ps.setString(4, orderVO.getOrderPayCardYear());
            ps.setString(5, orderVO.getOrderPayCardMonth());
            ps.setInt(6, orderVO.getOrderId());
            rowCount = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.print("Update the pay with order failed.");
        } finally {
            if (rowCount == 1) {
                return true;
            } else {
                return false;
            }
        }
    }


    //      新增訂單與訂單明細時，在JDBCDao做交易，一個方法直接做訂單與明細的新增，一旦出錯就還原，但搞不太清楚service裡要怎麼做，
//      於是寫了別的方法，分別在OrderDao和OrderDetailDao分別做新增，然後在Service裡面設立一個方法一次新增
    public boolean createOrderAndOrderDetail(OrderVO orderVO, List<OrderDetailVO> orderDetailVOs) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = true;
        String columns[] = {"order_id"};
        int order_id = -1;

        try {
//            conn = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
            conn = HikariDataSource.getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(CREATE_ORDER, columns);

            ps.setInt(1, orderVO.getMemberId());
            ps.setInt(2, orderVO.getCompanyId());
            ps.setObject(3, orderVO.getOrderCheckInDate());
            ps.setObject(4, orderVO.getOrderCheckOutDate());
            ps.setInt(5, orderVO.getCouponId());
            ps.setInt(6, orderVO.getOrderTotalPrice());
            ps.setString(7, orderVO.getTravelerName());
            ps.setString(8, orderVO.getTravelerIdNumber());
            ps.setString(9, orderVO.getTravelerEmail());
            ps.setString(10, orderVO.getTravelerPhone());
            int rowCount = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order_id = rs.getInt(1);
                if (order_id < 0) {
                    System.out.println("order insert failed.");
                }
            }
            System.out.println("order: " + rowCount + " row inserted");

            //拿到編號就繼續新增明細

            ps = conn.prepareStatement(CREATE_ORDER_DETAIL);

            for (OrderDetailVO orderDetailVO : orderDetailVOs) {
                ps.setInt(1, order_id);
                ps.setInt(2, orderDetailVO.getRoomTypeId());
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
            if (sum == 0) {
                System.out.println("orderDetail insert failed.");
            }
            System.out.println("orderDetail: " + sum + " row(s) inserted");


            conn.commit();
        } catch (SQLException se) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                result = false;
                System.out.println("rollback failed. " + e.getMessage());
            }
            result = false;
            throw new RuntimeException(se.getMessage());

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("setAutoCommit true failed. " + e.getMessage());
            }
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
}




