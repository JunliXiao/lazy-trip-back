package model.tour;

public class TourScheduleVO {
	private Integer tourScheduleId;
	private Integer tourId;
	private Integer attractionId;
	private String date;
	private String startTime;
	private Integer stayTime;
	private String memorandum;
	private String alias;
	
	public TourScheduleVO() {
	}
	
	public Integer getTourScheduleId() {
		return tourScheduleId;
	}
	public void setTourScheduleId(Integer tourScheduleId) {
		this.tourScheduleId = tourScheduleId;
	}
	public Integer getTourId() {
		return tourId;
	}
	public void setTourId(Integer tourId) {
		this.tourId = tourId;
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
	public String getMemorandum() {
		return memorandum;
	}
	public void setMemorandum(String memorandum) {
		this.memorandum = memorandum;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}

