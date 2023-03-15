package admin.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import admin.dao.AdminDAO;
import admin.dao.AdminDAOImpl;
import admin.model.Admin;
import member.controller.HashedPassword;
import member.dao.MemberDAOImpl;
import member.model.Member;
import member.controller.HashedPassword;

public class AdminService {
	private AdminDAO dao;
	
	public AdminService() {
		dao = new AdminDAOImpl();
	}
	
	public String insert(Admin admin) {
		if(dao.selectByAccount(admin.getAccount()) == null) {
			final int resultCount = dao.insert(admin);
			return resultCount > 0 ? "新增成功" : "新增失敗";
		}else {
			return "此管理員帳號已存在";
		}
		
	};
	public String updateById(Admin admin){
		final int resultCount = dao.updateById(admin);
		return resultCount > 0 ? "修改成功" : "修改失敗";
		
	};
	
	public String updateMemberById(Member member) {
//		String secret = hashPassword(member.getPassword());
		String secret = HashedPassword.hashPassword(member.getPassword());
		member.setPassword(secret);
		final int resultCount = dao.updateMemberById(member);
		return resultCount > 0 ? "會員資料修改成功":"會員資料修改失敗";
	}
	
	public Admin login(Admin admin) {
		final String account = admin.getAccount();
		final String password = admin.getPassword();
		Admin temp = dao.selectByAccount(account);
		if (temp != null) {
			System.out.println(temp.getPassword() + " ; " + HashedPassword.verifyPassword(password, temp.getPassword())
					+ " ; " + HashedPassword.hashPassword(password));
			if (account == null || account.isEmpty() || password == null || password.isEmpty()
					|| !(verifyPassword(password, temp.getPassword()))) {
				return null;
			} else {
				admin.setPassword(hashPassword(password));
				admin = dao.login(admin);
				return admin;
			}
		} else {
			return null;
		}

	};
	public List<Admin> getAll(){
		return dao.getAll();
	};
	
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
