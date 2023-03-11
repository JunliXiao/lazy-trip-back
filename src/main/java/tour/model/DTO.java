package tour.model;

import java.io.Serializable;
import java.util.List;

import member.model.Member;

public class DTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer tourComId;
	private String tourTitle;
	private String startDate;
	private String endDate;
	private String tourImg;
	private Integer cost;
	private Integer tourPerson;
	private Integer companyId;
	private String status;
	private String feature;
	private List<Member> memberVO;
	private List<TourVO> tourVO;
	
	public DTO() {
	}
	@Override
	public String toString() {
		return "DTO [tourComId=" + tourComId + ", tourTitle=" + tourTitle + ", startDate=" + startDate + ", endDate="
				+ endDate + ", tourImg=" + tourImg + ", cost=" + cost + ", tourPerson=" + tourPerson + ", companyId="
				+ companyId + ", status=" + status + ", feature=" + feature + ", memberVO=" + memberVO + ", tourVO="
				+ tourVO + "]";
	}
	public Integer getTourComId() {
		return tourComId;
	}
	public void setTourComId(Integer tourComId) {
		this.tourComId = tourComId;
	}
	public String getTourTitle() {
		return tourTitle;
	}
	public void setTourTitle(String tourTitle) {
		this.tourTitle = tourTitle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTourImg() {
		return tourImg;
	}
	public void setTourImg(String tourImg) {
		this.tourImg = tourImg;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Integer getTourPerson() {
		return tourPerson;
	}
	public void setTourPerson(Integer tourPerson) {
		this.tourPerson = tourPerson;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public List<Member> getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(List<Member> memberVO) {
		this.memberVO = memberVO;
	}
	public List<TourVO> getTourVO() {
		return tourVO;
	}
	public void setTourVO(List<TourVO> tourVO) {
		this.tourVO = tourVO;
	}
	
	
	public DTO(Integer tourComId, String tourTitle, String startDate, String endDate, String tourImg, Integer cost,
			Integer tourPerson, Integer companyId, String status, String feature, List<Member> memberVO, List<TourVO> tourVO) {
		super();
		this.tourComId = tourComId;
		this.tourTitle = tourTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tourImg = tourImg;
		this.cost = cost;
		this.tourPerson = tourPerson;
		this.companyId = companyId;
		this.status = status;
		this.feature = feature;
		this.memberVO = memberVO;
		this.tourVO = tourVO;
	}
}
