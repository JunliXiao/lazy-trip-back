package member.dao;

import java.util.List;

import member.vo.Member;

public interface MemberDao_interface {

	int insert(Member member);
	int updateById(Member member);
	Member find(Member member);
	int updateImgById(Member member);
	byte[] selectAvatarById(Integer id);
	Member selectById(Integer id);
	public List<Member> getAll();
}
