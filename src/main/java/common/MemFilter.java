package common;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.Member;

@WebFilter(urlPatterns={ "/page/member/main.html"})
public class MemFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		if(member == null) {
			session.setAttribute("location", req.getRequestURI());
			resp.sendRedirect(req.getContextPath() + "/page/login.html");
			return;
		}else {
			System.out.println(member.getUsername() + ", " + req.getRequestURI());
			chain.doFilter(req, resp);
			
		}
		
		
	}

}
