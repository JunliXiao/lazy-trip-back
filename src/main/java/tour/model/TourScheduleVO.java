package tour.model;

public class TourScheduleVO {
    private Integer tourScheduleId;
	private Integer tourId;
    private Integer attractionId;
    private String date;
    private String startTime;
    private Integer stayTime;
    private AttractionVO attractionVO;

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
    public AttractionVO getAttractionVO() {
		return attractionVO;
	}

	public void setAttractionVO(AttractionVO attractionVO) {
		this.attractionVO = attractionVO;
	}
    @Override
	public String toString() {
		return "TourScheduleVO [tourScheduleId=" + tourScheduleId + ", tourId=" + tourId + ", attractionId="
				+ attractionId + ", date=" + date + ", startTime=" + startTime + ", stayTime=" + stayTime + "]";
	}
  

}

