package order.service;

import order.model.CouponVO;
import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;

public class CouponService {
    private final OrderDAOInterface dao;

    public CouponService() {
       dao = new OrderJDBCDAOImpl();
    }

    public CouponVO confirmCoupon(Integer couponID){
        return dao.selectConfirmCoupon(couponID);
    }




}
