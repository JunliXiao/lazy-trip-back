package member.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

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
import redis.clients.jedis.Jedis;

@WebServlet("/page/member/forgetps")
public class MemberForgetPS extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Jedis jedis = new Jedis("localhost", 6379);
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
//		String testString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//		System.out.println(testString);
		Member member = gson.fromJson(req.getReader(), Member.class);
		
		JsonObject errorMsgs = new JsonObject();
		resp.setContentType("application/json;charset=UTF-8");
		try {
			MemberServiceImpl service = new MemberServiceImpl();
			member = service.findByAccount(member.getAccount());
			if(member == null) {
				errorMsgs.addProperty("msg", "此帳號不存在");
				resp.getWriter().append(errorMsgs.toString());
			}else {
				
				StringBuilder db = new StringBuilder("Member:").append(member.getId());
				String code = returnAuthCode();
				jedis.set(db.toString(), code);
				jedis.expire(db.toString(), 1800);
				req.getSession().setAttribute("forgetMemId", member.getId());
				
				resp.sendRedirect("verify.html");
				
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String returnAuthCode() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 8; i++) {
			int condition = (int) (Math.random() * 3) + 1;
			switch (condition) {
			case 1:
				char c1 = (char)((int)(Math.random() * 26) + 65);
				sb.append(c1);
				break;
			case 2:
				char c2 = (char)((int)(Math.random() * 26) + 97);
				sb.append(c2);
				break;
			case 3:
				sb.append((int)(Math.random() * 10));
			}
		}
		return sb.toString();
	}
	
	
}
