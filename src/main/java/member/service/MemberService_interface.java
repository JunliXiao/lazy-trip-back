package member.service;

import java.util.List;

import member.vo.Member;

public interface MemberService_interface {
	String register(Member member);
	Member login(Member member);
	Member save(Member member);
	byte[] findAvatrById(Integer id);
	Member findById(Integer id);
	Member changeImgById(Member member);
	public List<Member> findAll();
}
