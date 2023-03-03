package order.dao;

import company.model.CompanyVO;
import company.model.CouponVO;
import company.model.RoomTypeImgVO;
import company.model.RoomTypeVO;
import order.model.*;

import java.util.List;

public interface OrderDAOInterface {

    public List<CompanyVO> selectFindCompanyAndRoomTypePriceByPosition(String addressCounty);
    public CompanyVO selectFindCompanyByCompanyID(Integer companyID);
    public CompanyVO selectFindCompanyNameByCompanyID(CompanyVO companyVO);
    public List<RoomTypeVO> selectFindAllRoomTypeByCompanyID(Integer companyID);
    public RoomTypeVO selectFindRoomTypeByRoomTypeID(RoomTypeVO roomTypeVO);
    public RoomTypeVO selectFindRoomTypeNameByRoomTypeID(RoomTypeVO roomTypeVO);
    public List<RoomTypeImgVO> selectFindAllRoomTypeImgByRoomTypeID(Integer roomTypeID);
    public List<CompanyVO> selectShowSearchKeyWordByCompanyNameOrAddress(CompanyVO companyVO);
    public CouponVO selectConfirmCoupon(Integer couponID);
    public int createOrderAndOrderDetail(OrderVO orderVO, List<OrderDetailVO> orderDetailVOs);
    public int orderPay(OrderVO orderVO);
    public int updateOrderOverTimeForPay();
    public List<OrderVO> selectFindOrderByMemberID(OrderVO orderVO);
    public List<OrderDetailVO> selectFindOrderDetailByOrderID(OrderDetailVO orderDetailVO);

}
