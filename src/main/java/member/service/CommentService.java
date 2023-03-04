package member.service;

import member.dao.CommentDAO;
import member.dao.CommentDAOImpl;
import member.model.Comment;

public class CommentService {
	private CommentDAO dao = new CommentDAOImpl();
	
	public String post(Comment comment) {
		final int resultCount = dao.insert(comment);
		return resultCount > 0 ? "發佈成功" : "發佈失敗";
	}
	public Comment find(Comment comment) {
		
		comment = dao.selectById(comment.getMemberId());
		return comment;
	}
}
