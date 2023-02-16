package member.service;

import java.sql.Date;
import java.util.List;

import javax.naming.NamingException;

import member.dao.MemberDao_interface;
import member.dao.impl.MemberDaoImpl;
import member.vo.Member;

public class MemberServiceImpl implements MemberService_interface{
	private MemberDao_interface dao;

	public MemberServiceImpl() throws NamingException {
		dao = new MemberDaoImpl();
	}

	@Override
	public String register(Member member) {
		final int resultCount = dao.insert(member);
		return resultCount > 0 ? "註冊成功" : "註冊失敗";
	}

	@Override
	public Member login(Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member save(Member member) {
		final int id = member.getId();
		final String username = member.getUsername();
		final String gender = member.getGender();
		final Date birth = member.getBirthday();
		if(username == null || username.isEmpty() || gender == null || gender.isEmpty() ) {
			return null;
		}
		dao.updateById(member);
		return member;
	}

	@Override
	public byte[] findAvatrById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member findById(Integer id) {
		
		return id != null ? dao.selectById(id) : null;
	}

	@Override
	public Member changeImgById(Member member) {
		final Integer id = member.getId();
		final byte[] img = member.getImg();
		if (id == null || img == null || img.length == 0) {
			return null;
		}else {
			dao.updateImgById(member);
		}
		return member;
	}

	@Override
	public List<Member> findAll() {
		return dao.getAll();
	}


	

}
