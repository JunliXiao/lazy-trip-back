package group.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class DiscussionVO implements java.io.Serializable{
	private Integer discussion;
	private Integer memberid;
	private String discussioncontent;
	private Timestamp dicussiondate;
	private Integer groupid;

	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	public Integer getDiscussion() {
		return discussion;
	}
	public void setDiscussion(Integer discussion) {
		this.discussion = discussion;
	}
	
	public Integer getMemberid() {
		return memberid;
	}
	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	public String getDiscussionContent() {
		return discussioncontent;
	}
	public void setDiscussionContent(String discussioncontent) {
		this.discussioncontent = discussioncontent;
	}
	
	public String getDicussionDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS ");
		return sdf.format(dicussiondate);
	}
	public void setDicussionDate(Timestamp dicussiondate) {
		this.dicussiondate = dicussiondate;
	}
}
