package group.service;

import java.util.List;

import group.dao.GroupDAOImpl;
import group.dao.Group_memberDAOImpl;
import group.dao.Group_memberDAO_interface;
import group.model.GroupVO;
import group.model.Group_memberVO;

public class GroupMemberService {
	private Group_memberDAO_interface dao;
	
	public GroupMemberService() {
		dao = new Group_memberDAOImpl();

	}
	
	public void InsertWhenCreate(Group_memberVO groupmemberVO) {
		dao.insertWhenCreate(groupmemberVO);
	}
	
	public List<GroupVO> GetAllGroup(Integer memberid){
		
		List<GroupVO> list = dao.getAllGroupByMemberid(memberid);
		
		return list;
	}
}
