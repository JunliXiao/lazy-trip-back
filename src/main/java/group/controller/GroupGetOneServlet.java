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

@WebServlet("/GroupGetOne")
public class GroupGetOneServlet extends HttpServlet {
	public void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		
		GroupVO groupVO =new GroupVO();
        Integer p1 = Integer.parseInt(req.getParameter("groupid"));
		PrintWriter out = res.getWriter();
        GroupService service = new GroupService();
        groupVO = service.GetOneGroupInfo(p1);
        String jsonStr = new Gson().toJson(groupVO);
//		out.println("Hello from ServletGEEEET");
//        out.println(p1);
		out.println(jsonStr);
	}
}
