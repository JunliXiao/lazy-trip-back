package tour.model;

import java.io.Serializable;

public class TourScheduleComVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer tourScheduleComId;
    private Integer tourComId;
    private Integer attractionId;
    private String date;
    private String startTime;
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
