package tour.model;

import java.io.Serializable;

public class TourTagVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer tourTagId;
	private String tourTagTitle;

	public TourTagVO () {
		
	}
	
	public Integer getTourTagId() {
		return tourTagId;
	}
	public void setTourTagId(Integer tourTagId) {
		this.tourTagId = tourTagId;
	}
	public String getTourTagTitle() {
		return tourTagTitle;
	}
	public void setTourTagTitle(String tourTagTitle) {
		this.tourTagTitle = tourTagTitle;
	}
	
	@Override
	public String toString() {
		return "TourTagVO [tourTagId=" + tourTagId + ", tourTagTitle=" + tourTagTitle + "]";
	}
}
