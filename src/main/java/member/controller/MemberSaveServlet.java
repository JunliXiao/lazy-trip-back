package member.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.service.MemberServiceImpl;
import member.service.MemberService_interface;
import member.vo.Member;


@WebServlet("/lazy/save")
public class MemberSaveServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		Member member = gson.fromJson(req.getReader(), Member.class);
		try {
			resp.setContentType("application/json");
			JsonObject jsonObj = new JsonObject();
//			Member seMember = (Member) req.getSession().getAttribute("member");
//			Member seMember = new Member();
			final int id = Integer.parseInt(req.getParameter("id")) ;
//			seMember.setId(5);
			if(id == 0) {
				jsonObj.addProperty("message", "修改失敗");
			}else {
				MemberService_interface service = new MemberServiceImpl();
//				member.setId(seMember.getId());
				member.setId(id);
				service.save(member);
				jsonObj.addProperty("message", "修改成功");
			}
			resp.getWriter().append(jsonObj.toString());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
}
