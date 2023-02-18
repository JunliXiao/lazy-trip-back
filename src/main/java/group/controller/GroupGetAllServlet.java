package group.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import group.model.GroupVO;
import group.service.GroupMemberService;
import group.service.GroupService;

@WebServlet("/GroupGetAll")
public class GroupGetAllServlet extends HttpServlet {
	public void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");

		
        Gson gson = new Gson();
        
        GroupMemberService service = new GroupMemberService();
		//因還未串接登入獲得memberid前端fetch先以1代替
        Integer p1 = Integer.valueOf(req.getParameter("memberid"));

        List<GroupVO> list = service.GetAllGroup(p1);
		
        res.setContentType("application/json");
        res.getWriter().print(gson.toJson(list));
        
		}
}
