package com.article.model;

import java.sql.Timestamp;
import java.util.List;

public class ArticleService {

	private ArticleDAO_interface dao;

	public ArticleService() {
		dao = new ArticleDAO();
	}

	public ArticleVO addArticle(String articleContent, Timestamp articleDate, String status, Integer adminId,
			Integer memberId, Integer companyId, Integer tourId) {
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticleContent(articleContent);
		articleVO.setArticleDate(articleDate);
		articleVO.setStatus(status);
		articleVO.setAdminId(adminId);
		articleVO.setMemberId(memberId);
		articleVO.setCompanyId(companyId);
		articleVO.setTourId(tourId);
		dao.insert(articleVO);
		
		return articleVO;
	}
	
	public ArticleVO updateArticle(Integer articleId, String articleContent, Timestamp articleDate, String status, Integer adminId,
			Integer memberId, Integer companyId, Integer tourId) {
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticleId(articleId);
		articleVO.setArticleContent(articleContent);
		articleVO.setArticleDate(articleDate);
		articleVO.setStatus(status);
		articleVO.setAdminId(adminId);
		articleVO.setMemberId(memberId);
		articleVO.setCompanyId(companyId);
		articleVO.setTourId(tourId);
		dao.update(articleVO);
		
		return articleVO;
	}
	
	public void deleteArticle(Integer artilcleId) {
		dao.delete(artilcleId);
	}
	
	public ArticleVO getOneEmp(Integer artilcleId) {
		return dao.findByPrimaryKey(artilcleId);
	}

	public List<ArticleVO> getAll() {
		return dao.getAll();
	}
	
}
