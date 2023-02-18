package group.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import group.model.GroupVO;
import group.service.GroupService;

@WebServlet("/GroupAdd")
public class GroupAddServlet extends HttpServlet {
	public void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		
        Gson gson = new Gson();

		GroupVO groupVO =new GroupVO();
        String p1 = req.getParameter("groupname");
        Integer p2 = Integer.valueOf(req.getParameter("groupmembercount"));
        Integer p3 =Integer.valueOf(req.getParameter("memberid"));
        Integer p4 =Integer.valueOf(req.getParameter("how_2_join"));
        groupVO.setGroupname(p1);
        groupVO.setGroupmembercount(p2);
        groupVO.setMemberid(p3);
        groupVO.setIfjoingroupdirectly(p4);
        
        GroupService service = new GroupService();
        int pk = service.addGroup(groupVO);
        groupVO.setGroupid(pk);
        res.setContentType("application/json");
        res.getWriter().print(gson.toJson(groupVO));
//        res.getWriter().print(pk);
//		PrintWriter out = res.getWriter();
//		out.println("Hello from Servlet");
//		out.println(p1+" "+p2+p3 +"++"+pk );	
		
		}
}
