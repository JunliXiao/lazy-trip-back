package tour.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tour_company")
public class TourComVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "c_tour_id")
	private Integer tourComId;
	@Column(name="company_id")
	private String tourTitle;
	@Column(name="tour_title")
	private String startDate;
	@Column(name="start_date")
	private String endDate;
	@Column(name="end_date")
	private String tourImg;
	@Column(name="cost")
	private Integer cost;
	@Column(name="tour_person")
	private Integer tourPerson;
	@Column(name="company_id")
	private Integer companyId;
	private byte[] tourImgByte;
	

	public TourComVO() {
	}
	
	public TourComVO(Integer tourComId, String tourTitle, String startDate, String endDate, String tourImg,
			Integer cost, Integer tourPerson, Integer companyId, byte[] tourImgByte) {
		this.tourComId = tourComId;
		this.tourTitle = tourTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tourImg = tourImg;
		this.cost = cost;
		this.tourPerson = tourPerson;
		this.companyId = companyId;
		this.tourImgByte= tourImgByte;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public byte[] getTourImgByte() {
		return tourImgByte;
	}
	public void setTourImgByte(byte[] tourImgByte) {
		this.tourImgByte = tourImgByte;
	}
	
	@Override
	public String toString() {
		return "TourComVO [tourComId=" + tourComId + ", tourTitle=" + tourTitle + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", tourImg=" + tourImg + ", cost=" + cost + ", tourPerson=" + tourPerson
				+ ", companyId=" + companyId + "]";
	}


}

