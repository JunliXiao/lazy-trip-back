package member.dao;

import java.util.List;

import member.model.CommentImg;

public interface CommentImgDAO {
	public int insert(CommentImg commentImg);
	public int delete(CommentImg commentImg);
	public CommentImg selectById(CommentImg commentImg);
	public List<CommentImg> getAllImg();
	public List<CommentImg> getAll();

}
