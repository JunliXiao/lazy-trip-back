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

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;


@WebServlet("/lazy/login")
public class MemberLoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		Member member = gson.fromJson(req.getReader(), Member.class);

		try {
			MemberService service = new MemberServiceImpl();
			member = service.login(member);

			if (member == null) {
				JsonObject error = new JsonObject();
			    error.addProperty("errorMessage", "�Τ�W�αK�X���~");
			    resp.setContentType("application/json");
			    resp.setCharacterEncoding("UTF-8");
			    resp.getWriter().write(error.toString());
			} else {
				if (req.getSession(false) != null) {
					req.changeSessionId(); // �����ͷs��Session ID
				}
				req.getSession().setAttribute("member", member);
				req.getSession().setAttribute("login", true);
				resp.sendRedirect("main.jsp");
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
