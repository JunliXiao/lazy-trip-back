package order.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderVO implements java.io.Serializable {

    private Integer orderId;
    private Integer memberId;
    private Integer companyId;
    private Integer couponId;
    private LocalDate orderCheckInDate;
    private LocalDate orderCheckOutDate;
    private Integer orderTotalPrice;
    private String orderStatus;
    private LocalDateTime orderCreateDatetime;
    private LocalDateTime orderPayDeadline;
    private LocalDateTime orderPayDatetime;
    private String orderPayCardName;
    private String orderPayCardNumber;
    private String orderPayCardYear;
    private String orderPayCardMonth;
    private String travelerName;
    private String travelerIdNumber;
    private String travelerEmail;
    private String travelerPhone;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public LocalDate getOrderCheckInDate() {
        return orderCheckInDate;
    }

    public void setOrderCheckInDate(LocalDate orderCheckInDate) {
        this.orderCheckInDate = orderCheckInDate;
    }

    public LocalDate getOrderCheckOutDate() {
        return orderCheckOutDate;
    }

    public void setOrderCheckOutDate(LocalDate orderCheckOutDate) {
        this.orderCheckOutDate = orderCheckOutDate;
    }

    public Integer getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Integer orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderCreateDatetime() {
        return orderCreateDatetime;
    }

    public void setOrderCreateDatetime(LocalDateTime orderCreateDatetime) {
        this.orderCreateDatetime = orderCreateDatetime;
    }

    public LocalDateTime getOrderPayDeadline() {
        return orderPayDeadline;
    }

    public void setOrderPayDeadline(LocalDateTime orderPayDeadline) {
        this.orderPayDeadline = orderPayDeadline;
    }

    public LocalDateTime getOrderPayDatetime() {
        return orderPayDatetime;
    }

    public void setOrderPayDatetime(LocalDateTime orderPayDatetime) {
        this.orderPayDatetime = orderPayDatetime;
    }

    public String getOrderPayCardName() {
        return orderPayCardName;
    }

    public void setOrderPayCardName(String orderPayCardName) {
        this.orderPayCardName = orderPayCardName;
    }

    public String getOrderPayCardNumber() {
        return orderPayCardNumber;
    }

    public void setOrderPayCardNumber(String orderPayCardNumber) {
        this.orderPayCardNumber = orderPayCardNumber;
    }

    public String getOrderPayCardYear() {
        return orderPayCardYear;
    }

    public void setOrderPayCardYear(String orderPayCardYear) {
        this.orderPayCardYear = orderPayCardYear;
    }

    public String getOrderPayCardMonth() {
        return orderPayCardMonth;
    }

    public void setOrderPayCardMonth(String orderPayCardMonth) {
        this.orderPayCardMonth = orderPayCardMonth;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getTravelerIdNumber() {
        return travelerIdNumber;
    }

    public void setTravelerIdNumber(String travelerIdNumber) {
        this.travelerIdNumber = travelerIdNumber;
    }

    public String getTravelerEmail() {
        return travelerEmail;
    }

    public void setTravelerEmail(String travelerEmail) {
        this.travelerEmail = travelerEmail;
    }

    public String getTravelerPhone() {
        return travelerPhone;
    }

    public void setTravelerPhone(String travelerPhone) {
        this.travelerPhone = travelerPhone;
    }
}