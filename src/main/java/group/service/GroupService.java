package group.service;

import group.dao.GroupDAO;
import group.dao.GroupDAO_interface;
import group.model.GroupVO;

public class GroupService {
	private GroupDAO_interface dao;
	
	public GroupService() {
		dao = new GroupDAO();

	}
	
	public int addGroup(GroupVO groupVO) {

		int result = dao.insert(groupVO);
		return result;
	}
	
	public GroupVO GetOneGroupInfo(Integer groupid) {
		GroupVO groupvo =  dao.findByPrimaryKey(groupid);
		return groupvo;
	}
	

	
}
