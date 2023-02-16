package tour.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tour_schedule_company")
public class TourScheduleComVO implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="c_tour_schedule_id")
	private Integer tourScheduleComId;
	@Column(name="c_tour_id")
	private Integer tourComId;
	@Column(name="attraction_id")
	private Integer attractionId;
	@Column(name="date")
	private String date;
	@Column(name="start_time")
	private String startTime;
	@Column(name="stay_time")
	private String stayTime;
	
	public TourScheduleComVO() {
	}
	
	public Integer getTourScheduleComId() {
		return tourScheduleComId;
	}
	public void setTourScheduleComId(Integer tourScheduleComId) {
		this.tourScheduleComId = tourScheduleComId;
	}
	public Integer getTourComId() {
		return tourComId;
	}
	public void setTourComId(Integer tourComId) {
		this.tourComId = tourComId;
	}
	public Integer getAttractionId() {
		return attractionId;
	}
	public void setAttractionId(Integer attractionId) {
		this.attractionId = attractionId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStayTime() {
		return stayTime;
	}
	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}
	
	public TourScheduleComVO(Integer tourScheduleComId, Integer tourComId, Integer attractionId, String date,
			String startTime, String stayTime) {
		super();
		this.tourScheduleComId = tourScheduleComId;
		this.tourComId = tourComId;
		this.attractionId = attractionId;
		this.date = date;
		this.startTime = startTime;
		this.stayTime = stayTime;
	}
	
	@Override
	public String toString() {
		return "TourScheduleComVO [tourScheduleComId=" + tourScheduleComId + ", tourComId=" + tourComId
				+ ", attractionId=" + attractionId + ", date=" + date + ", startTime=" + startTime + ", stayTime="
				+ stayTime + "]";
	}

}
