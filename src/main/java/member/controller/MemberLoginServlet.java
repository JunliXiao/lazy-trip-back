package member.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;


@WebServlet("/page/login")
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
			    error.addProperty("errorMessage", "用戶名或密碼錯誤");
			    resp.setContentType("application/json");
			    resp.setCharacterEncoding("UTF-8");
			    resp.getWriter().write(error.toString());
			} else {
				if (req.getSession(false) != null) {
					req.changeSessionId(); // ←產生新的Session ID
				}
				
				req.getSession().setAttribute("member", member);
				req.getSession().setAttribute("login", true);
				
				Cookie cookie = new Cookie("memId", member.getId().toString());
				Cookie cookie2 = new Cookie("memUsername", member.getUsername());
				cookie.setMaxAge(7 * 24 * 60 * 60);
				cookie2.setMaxAge(7 * 24 * 60 * 60);
				cookie.setPath("/");
				cookie2.setPath("/");
				resp.addCookie(cookie);
				resp.addCookie(cookie2);
				
				
				resp.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()  + req.getContextPath() + "/" + "index.html");
//				resp.sendRedirect("member");
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}