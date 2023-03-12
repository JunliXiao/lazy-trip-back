package member.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import member.dao.MemberDAO;
import member.dao.MemberDAOImpl;
import member.model.Member;

public class MemberServiceImpl implements MemberService{
	private MemberDAO dao;
	List<String> errorMsgs = new LinkedList<String>();

	public MemberServiceImpl() throws NamingException {
		dao = new MemberDAOImpl();
	}

	@Override
	public String register(Member member) {
		final String account = member.getAccount();
		final String password = member.getPassword();
		final String username = member.getUsername();
		final String gender = member.getGender();
		if((dao.selectByAccount(account)) == null) {
			if(account == null || account.isEmpty()) {
				errorMsgs.add("帳號不能為空");
			}
			if(password == null || password.isEmpty()) {
				errorMsgs.add("密碼不能為空");
			}
			if(gender == null || gender.isEmpty()) {
				errorMsgs.add("性別不能為空");
			}
			if(username == null || username.isEmpty()) {
				errorMsgs.add("暱稱不能為空");
			}
			
			if(errorMsgs.isEmpty()) {
				final int resultCount = dao.insert(member);
				return resultCount > 0 ? "註冊成功" : "註冊失敗";
			}else {
				return errorMsgs.toString();
			}
		}else {
			errorMsgs.add("此帳號已存在");
			return errorMsgs.toString();
		}
	}

	@Override
	public Member login(Member member) {
		final String account = member.getAccount();
		final String password = member.getPassword();
		Member temp = dao.selectByAccount(account);
		System.out.println(temp.getPassword() + " ; " + verifyPassword(password, temp.getPassword()) + " ; " + hashPassword(password));
		if(account == null || account.isEmpty() || password == null || password.isEmpty() || !(verifyPassword(password, temp.getPassword()))) {
			return null;
		}else {
			member.setPassword(hashPassword(password));
			member = dao.find(member);
			return member;
		}
			
		
	}

	@Override
	public String save(Member member) {
		final String username = member.getUsername();
		final String gender = member.getGender();
		final Date birth = member.getBirthday();
		if(gender == null || gender.isEmpty()) {
			errorMsgs.add("性別不能為空");
		}
//		if(username == null || username.isEmpty()) {
//			errorMsgs.add("暱稱不能為空");
//		}
		if(birth == null) {
			errorMsgs.add("生日不能為空");
		}
		if(errorMsgs.isEmpty()) {
			dao.updateById(member);
			return "修改成功";
		}else {
			return errorMsgs.toString();
		}
	}
	
	@Override
	public String saveintro(Member member) {
		final String username = member.getUsername();
		if(username == null || username.isEmpty()) {
			errorMsgs.add("暱稱不能為空");
		}
		if(errorMsgs.isEmpty()) {
			dao.updateintroById(member);
			return "修改成功";
		}else {
			return errorMsgs.toString();
		}
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
		final byte[] avatar = member.getImg();
		if (id == null || avatar == null || avatar.length == 0) {
			return null;
		}else {
			dao.updateImgById(member);
		}
		return member;
	}
	
	@Override
	public Member findByAccount(String account) {
		return account != null ? dao.selectByAccount(account) : null;
	}

	@Override
	public List<Member> findAll() {
		return dao.getAll();
	}
	
	@Override
	public String savePassword(Member member) {
		String ps = member.getPassword();
		if(ps == null) {
			return "密碼修改失敗(null)";
		}else {
			final int rs = dao.updatePasswordById(member);
			return rs > 0 ? "密碼修改成功" : "密碼修改失敗";
		}
	}
	
	private boolean verifyPassword(String password, String hashedPassword) {
        // Compare the hashed password with the hashed user input
        return hashedPassword.equals(hashPassword(password));
    }
	
	private String hashPassword(String password) {
        // Hash the password using SHA-256 algorithm
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


	

	


	

}
