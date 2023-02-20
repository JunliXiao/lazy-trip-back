package group.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import group.model.GroupVO;
import group.model.Group_memberVO;
import group.service.GroupMemberService;
import group.service.GroupService;

@WebServlet("/newgroup")
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
		Group_memberVO groupmemberVO = new Group_memberVO();
		//groupVO:創建時新增揪團
		//groupmemberVO:創建時將創辦人加入groupmember
        try {
        	Integer gpCount = Integer.valueOf(req.getParameter("groupmembercount"));
        	groupVO.setGroupmembercount(gpCount);
        }catch(NumberFormatException e){
//        	e.printStackTrace();
        	res.sendRedirect(req.getRequestURL().toString());
        }
        Integer groupOwner =Integer.valueOf(req.getParameter("memberid"));
        Integer howTojoin =Integer.valueOf(req.getParameter("how_2_join"));
        if(req.getParameter("groupname").length()!=0) {
        	String name = req.getParameter("groupname");
        	groupVO.setGroupname(name);
        }
        groupVO.setMemberid(groupOwner);
        groupVO.setIfjoingroupdirectly(howTojoin);
        
        
        GroupService groupService = new GroupService();
        GroupMemberService groupMemberService = new GroupMemberService();
        int pk = groupService.addGroup(groupVO);
        groupmemberVO.setMemberid(groupOwner);
        groupmemberVO.setGroupid(pk);
        groupMemberService.InsertWhenCreate(groupmemberVO);
        groupVO.setGroupid(pk);
        res.setContentType("application/json");
        res.getWriter().print(gson.toJson(groupVO));
//        res.getWriter().print(pk);
//		PrintWriter out = res.getWriter();
//		out.println("Hello from Servlet");
//		out.println(p1+" "+p2+p3 +"++"+pk );	
		
		}
}
