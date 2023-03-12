package order.service;

import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;
import order.model.OrderDetailVO;
import order.model.OrderVO;

import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderDAOInterface dao;

    public OrderService() {
        dao = new OrderJDBCDAOImpl();
    }

    public int addOrderAndOrderDetail(List<OrderVO> orderVOs) {
        return dao.createOrderAndOrderDetail(orderVOs);
    }
//    public List<OrderVO> showOrderAllByMemberID(Integer memberID){
//        return dao.selectFindOrderAllByMemberID(memberID);
//    }
    public Map<Integer,OrderVO> showOrderAllByMemberID(Integer memberID){
        return dao.selectFindOrderAllByMemberID(memberID);
    }
    public Map<Integer, OrderVO> showOrderAllAndAlreadyPayByCompanyID(Integer companyID){
        return dao.selectFindOrderAllAndAlreadyPayByCompanyID(companyID);
    }
    public List<OrderVO> showOrderAllAndStatusWaitPayByOrderID(Integer orderID){
        return dao.selectFindOrderAllAndStatusWaitPayByOrderID(orderID);
    }
}
