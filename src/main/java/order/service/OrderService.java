package order.service;

import order.dao.OrderDao_Interface;
import order.model.OrderDetailVO;
import order.model.OrderVO;
import order.dao.OrderJDBCDao;

import java.util.List;

public class OrderService {
    private OrderDao_Interface dao;

    public OrderService() {
        dao = new OrderJDBCDao();
    }

    public int addOrderAndOrderDetail(OrderVO orderVO, List<OrderDetailVO> orderDetailVOs) {
        boolean result = false;
        int resultNum = -1;
        result = dao.createOrderAndOrderDetail(orderVO, orderDetailVOs);
        if (result == false) {
            resultNum = 0;
        } else if (result == true) {
            resultNum = 1;
        }
        return resultNum;
    }


//    public OrderVO addOrder(Integer member_id, Integer company_id, LocalDate orderCheckInDate,
//                            LocalDate orderCheckOutDate, Integer coupon_id, Integer orderTotalPrice,
//                            String travelerName, String travelerIdNumber, String travelerEmail,
//                            String travelerPhone) {
//
//        OrderVO orderVO = new OrderVO();
//
//        orderVO.setMemberId(member_id);
//        orderVO.setCompanyId(company_id);
//        orderVO.setOrderCheckInDate(orderCheckInDate);
//        orderVO.setOrderCheckOutDate(orderCheckOutDate);
//        orderVO.setCouponId(coupon_id);
//        orderVO.setOrderTotalPrice(orderTotalPrice);
//        orderVO.setTravelerName(travelerName);
//        orderVO.setTravelerIdNumber(travelerIdNumber);
//        orderVO.setTravelerEmail(travelerEmail);
//        orderVO.setTravelerPhone(travelerPhone);
//
//        return orderVO;
//    }
//
//    public OrderDetailVO addOrderDetail(Integer roomTypeId, Integer orderDetailRoomPrice,
//                                  Byte orderDetailRoomQuantity, Integer orderDetailCouponDiscountPrice) {
//
//        OrderDetailVO orderDetailVO = new OrderDetailVO();
//
//        orderDetailVO.setRoomTypeId(roomTypeId);
//        orderDetailVO.setOrderDetailRoomPrice(orderDetailRoomPrice);
//        orderDetailVO.setOrderDetailRoomQuantity(orderDetailRoomQuantity);
//        orderDetailVO.setOrderDetailCouponDiscountPrice(orderDetailCouponDiscountPrice);
//
//        return orderDetailVO;
//    }


}
