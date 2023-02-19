package group.service;

import java.util.List;

import group.dao.GroupDAO;
import group.dao.Group_memberDAO;
import group.dao.Group_memberDAO_interface;
import group.model.GroupVO;

public class GroupMemberService {
	private Group_memberDAO_interface dao;
	
	public GroupMemberService() {
		dao = new Group_memberDAO();

	}
	
	public List<GroupVO> GetAllGroup(Integer memberid){
		
		List<GroupVO> list = dao.getAllGroupByMemberid(memberid);
		
		return list;
	}
}
