package member.dao;

import java.util.List;

import member.model.Comment;

public interface CommentDAO {
	public int insert(Comment comment);
	public int updatById(Comment comment);
	public Comment selectById(Integer id);
	public List<Comment> getAllComment();
	public List<Comment> getAll();
}
