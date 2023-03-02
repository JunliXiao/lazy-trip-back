package company.model;

import java.sql.Date;

public class CouponVO implements java.io.Serializable{

	private Integer couponID;
	private Integer companyID;
	private String couponText;
	private Date couponEndTime;
	private Date couponStartTime;
	private Boolean couponStatus;
	private Float couponDiscount;
	private Integer couponQuantity;
	private Integer couponUsedQuantity;
	public Integer getCouponID() {
		return couponID;
	}
	public void setCouponID(Integer couponID) {
		this.couponID = couponID;
	}
	public Integer getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}
	public String getCouponText() {
		return couponText;
	}
	public void setCouponText(String couponText) {
		this.couponText = couponText;
	}
	public Date getCouponEndTime() {
		return couponEndTime;
	}
	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
	public Date getCouponStartTime() {
		return couponStartTime;
	}
	public void setCouponStartTime(Date couponStartTime) {
		this.couponStartTime = couponStartTime;
	}
	public Boolean getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(Boolean couponStatus) {
		this.couponStatus = couponStatus;
	}
	public Float getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(Float couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public Integer getCouponQuantity() {
		return couponQuantity;
	}
	public void setCouponQuantity(Integer couponQuantity) {
		this.couponQuantity = couponQuantity;
	}
	public Integer getCouponUsedQuantity() {
		return couponUsedQuantity;
	}
	public void setCouponUsedQuantity(Integer couponUsedQuantity) {
		this.couponUsedQuantity = couponUsedQuantity;
	}
	
	
}
