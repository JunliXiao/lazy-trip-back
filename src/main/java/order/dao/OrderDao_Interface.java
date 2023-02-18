package order.dao;

import order.model.OrderDetailVO;
import order.model.OrderVO;

import java.util.List;

public interface OrderDao_Interface {

    public int updateOrderOverTimeForPay();
    public boolean OrderPay(OrderVO orderVO);
    public boolean createOrderAndOrderDetail(OrderVO orderVO, List<OrderDetailVO> orderDetailVOs);

}
