package article.dao;

import java.util.*;

public interface ArticleDAO_interface {

	public void insert(ArticleVO articleVO);
    public void update(ArticleVO articleVO);
    public void delete(Integer articleId);
    public ArticleVO findByPrimaryKey(Integer articleId);
    public List<ArticleVO> getAll();
	
}
