package order.model;

public class OrderDetailVO implements java.io.Serializable {
    private Integer orderDetailId;
    private Integer orderId;
    private Integer roomTypeId;
    private Integer orderDetailRoomPrice;
    private Byte orderDetailRoomQuantity;
    private Integer orderDetailCouponDiscountPrice;

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getOrderDetailRoomPrice() {
        return orderDetailRoomPrice;
    }

    public void setOrderDetailRoomPrice(Integer orderDetailRoomPrice) {
        this.orderDetailRoomPrice = orderDetailRoomPrice;
    }

    public Byte getOrderDetailRoomQuantity() {
        return orderDetailRoomQuantity;
    }

    public void setOrderDetailRoomQuantity(Byte orderDetailRoomQuantity) {
        this.orderDetailRoomQuantity = orderDetailRoomQuantity;
    }

    public Integer getOrderDetailCouponDiscountPrice() {
        return orderDetailCouponDiscountPrice;
    }

    public void setOrderDetailCouponDiscountPrice(Integer orderDetailCouponDiscountPrice) {
        this.orderDetailCouponDiscountPrice = orderDetailCouponDiscountPrice;
    }
}