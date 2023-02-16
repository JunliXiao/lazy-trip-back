package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import member.service.MemberServiceImpl;
import member.vo.Member;



@WebServlet("/lazy/register")
public class MemberRegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public MemberRegisterServlet() {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Member member = gson.fromJson(req.getReader(), Member.class);
			MemberServiceImpl service = new MemberServiceImpl();
			final String resultStr = service.register(member);
			
//			System.out.println(resultStr);
			
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json;charset=UTF-8");
			JsonObject respBody = new JsonObject();
			respBody.addProperty("successful", resultStr.equals("註冊成功"));
			respBody.addProperty("message", resultStr);
			resp.getWriter().append(respBody.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
