package member.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import member.model.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

@WebServlet("/page/member/upload")
@MultipartConfig
public class MemberUploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Part part = req.getPart("avatar");
		System.out.println(id +", " + part);
		
		if(id != null && !id.isEmpty() && part.getSize() != 0) {
			InputStream is =  part.getInputStream();
			final byte[] avatar = is.readAllBytes();
			try {
				MemberService service = new MemberServiceImpl();
				Member member = new Member();
				member.setId(Integer.parseInt(id));
				member.setImg(avatar);
				service.changeImgById(member);
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
