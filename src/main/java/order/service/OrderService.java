package order.service;

import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;
import order.model.OrderDetailVO;
import order.model.OrderVO;

import java.util.List;

public class OrderService {
    private OrderDAOInterface dao;

    public OrderService() {
        dao = new OrderJDBCDAOImpl();
    }

    public int addOrderAndOrderDetail(OrderVO orderVO, List<OrderDetailVO> orderDetailVOs) {
        return dao.createOrderAndOrderDetail(orderVO,orderDetailVOs);
    }
}
