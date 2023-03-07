package group.service;

import java.util.List;

import group.dao.Group_memberDAOImpl;
import group.dao.Group_memberDAO_interface;
import group.model.GroupVO;
import group.model.Group_memberVO;
import member.model.Member;

public class GroupMemberService {
	private Group_memberDAO_interface dao;

	public GroupMemberService() {
		dao = new Group_memberDAOImpl();

	}

	public List<GroupVO> GetAllGroup(Integer memberid) {
		return dao.getAllGroupByMemberid(memberid);
	}

	public List<Member> getAllMember(Integer groupid) {
		return dao.getAllMember(groupid);
	}

	public List<Group_memberVO> getAllMemberList(Integer groupid) {
		return dao.getMemList(groupid);
	}

	public void delGroupMem(List<Integer> list) {
		dao.delete(list);
	}

	public void inviteFriend(List<Integer> list) {
		dao.inviteFriendtoGroup(list);
	}

	public List<Group_memberVO> getAllInvite(Integer memberid) {
		return dao.getAllInvite(memberid);
	}
}
