package member.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.model.Member;
import member.service.MemberServiceImpl;

@WebServlet("/page/member/resetps")
public class MemberResetPS extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			MemberServiceImpl service = new MemberServiceImpl();
			Gson gson = new Gson();
			JsonObject resObject = new JsonObject();
			
			Member member = gson.fromJson(req.getReader(), Member.class);
			String hashedPassword = hashPassword(member.getPassword());
			member.setPassword(hashedPassword);
			
			if(	req.getSession().getAttribute("verify") == null || 
				req.getSession().getAttribute("forgetMemId") == null) {
				Member memberlogin = (Member) req.getSession().getAttribute("member");
				member.setId(memberlogin.getId());
				
			}else {
				Integer id = (Integer) req.getSession().getAttribute("forgetMemId");
				member.setId(id);
			}
			final String resultStr = service.savePassword(member);
			if(resultStr.equals("密碼修改成功")) {
				resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + 
						req.getContextPath() + "/page/" + "login.html");
			}else {
				resp.setContentType("application/json;charset=UTF-8");
				resObject.addProperty("msg", resultStr);
				resp.getWriter().append(resObject.toString());
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private String hashPassword(String password) {
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
