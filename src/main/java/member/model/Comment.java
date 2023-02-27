package member.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer memberId;
	private Integer companyId;
	private Integer point;
	private Timestamp time;
	private String text;
	private String staus;
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", memberId=" + memberId + ", companyId=" + companyId + ", point=" + point
				+ ", time=" + time + ", text=" + text + ", staus=" + staus + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getStaus() {
		return staus;
	}
	public void setStaus(String staus) {
		this.staus = staus;
	}
	
	
	

}
