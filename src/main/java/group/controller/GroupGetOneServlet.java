package group.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import group.model.GroupVO;
import group.service.GroupService;

@WebServlet("/onegroup")
public class GroupGetOneServlet extends HttpServlet {
	public void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		
		GroupVO groupVO =new GroupVO();
        Integer groupid = Integer.parseInt(req.getParameter("groupid"));
		PrintWriter out = res.getWriter();
        GroupService service = new GroupService();
        groupVO = service.getOneGroupInfo(groupid);
        String jsonStr = new Gson().toJson(groupVO);
//		out.println("Hello from ServletGEEEET");
//        out.println(p1);
		out.println(jsonStr);
	}
	
	public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		GroupVO groupVO =new GroupVO();
     
		Integer groupId = Integer.parseInt(req.getParameter("groupid"));
        String groupName = req.getParameter("groupname");
        Integer groupMemberCount = Integer.parseInt(req.getParameter("groupmembercount"));
        Integer ifjoingroupdirectly = Integer.parseInt(req.getParameter("ifjoingroupdirectly"));

        groupVO.setGroupid(groupId);
        groupVO.setGroupmembercount(groupMemberCount);
        groupVO.setGroupname(groupName);
        groupVO.setIfjoingroupdirectly(ifjoingroupdirectly);
        GroupService service = new GroupService();
        service.updateGroupInfo(groupVO);
		PrintWriter out = res.getWriter();
        String jsonStr = new Gson().toJson(groupVO);
		out.println(jsonStr);

		
//		out.println("hahaha");
	}
}
