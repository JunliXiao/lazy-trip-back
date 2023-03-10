package tour.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class TourScheduleComVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer tourScheduleComId;
	private Integer tourComId;
	private Integer attractionId;
	private String date;
	private String startTime;
	private Integer stayTime;
	private String endTime;
	private String status;
	private AttractionVO attractionVO;
	
	public TourScheduleComVO() {
	}
	public TourScheduleComVO(Integer tourScheduleComId, Integer tourComId, Integer attractionId, String date,
			String startTime, Integer stayTime, String endTime, AttractionVO attractionVO) {
		super();
		this.tourScheduleComId = tourScheduleComId;
		this.tourComId = tourComId;
		this.attractionId = attractionId;
		this.date = date;
		this.startTime = startTime;
		this.stayTime = stayTime;
		this.endTime = endTime;
		this.attractionVO = attractionVO;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public AttractionVO getAttractionVO() {
		return attractionVO;
	}

	public void setAttractionVO(AttractionVO attractionVO) {
		this.attractionVO = attractionVO;
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
	public Integer getStayTime() {
		return stayTime;
	}
	public void setStayTime(Integer stayTime) {
		this.stayTime = stayTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "TourScheduleComVO [tourScheduleComId=" + tourScheduleComId + ", tourComId=" + tourComId
				+ ", attractionId=" + attractionId + ", date=" + date + ", startTime=" + startTime + ", stayTime="
				+ stayTime + ", endTime=" + endTime + ", attractionVO=" + attractionVO + "]";
	}

}
