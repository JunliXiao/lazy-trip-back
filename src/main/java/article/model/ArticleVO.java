package com.article.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ArticleVO implements Serializable{

	private Integer articleId;
	private String articleContent;
	private Timestamp articleDate;
	private String status;
	private Integer adminId;
	private Integer memberId;
	private Integer companyId;
	private Integer tourId;
	
	public ArticleVO() {
	}
	
	public ArticleVO(Integer articleId, String articleContent, Timestamp articleDate, String status, Integer adminId,
			Integer memberId, Integer companyId, Integer tourId) {
		super();
		this.articleId = articleId;
		this.articleContent = articleContent;
		this.articleDate = articleDate;
		this.status = status;
		this.adminId = adminId;
		this.memberId = memberId;
		this.companyId = companyId;
		this.tourId = tourId;
	}


	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public Timestamp getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Timestamp articleDate) {
		this.articleDate = articleDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
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
	public Integer getTourId() {
		return tourId;
	}
	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}
	
	
}
