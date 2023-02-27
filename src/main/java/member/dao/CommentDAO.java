package member.dao;

import java.util.List;

import member.model.Comment;

public interface CommentDAO {
	public int insert(Comment comment);
	public int updatById(Comment comment);
	public int updateImgById(Comment comment);
	public Comment selectById(Comment comment);
	public List<Comment> getAllComment();
	public List<Comment> getAll();
}
