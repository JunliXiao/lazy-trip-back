package group.service;

import group.dao.GroupDAOImpl;
import group.dao.GroupDAO_interface;
import group.model.GroupVO;

public class GroupService {
	private GroupDAO_interface dao;
	
	public GroupService() {
		dao = new GroupDAOImpl();

	}
	
	public int addGroup(GroupVO groupVO) {

		int result = dao.insert(groupVO);
		return result;
	}
	
	public GroupVO getOneGroupInfo(Integer groupid) {
		GroupVO groupvo =  dao.findByPrimaryKey(groupid);
		return groupvo;
	}
	
	public void updateGroupInfo(GroupVO groupVO) {
		dao.update(groupVO);
	}

	
}
