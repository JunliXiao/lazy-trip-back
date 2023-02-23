package group.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
import tour.model.TourVO;

@WebServlet("/group")
public class GroupServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");

		if ("getAllGroups".equals(action)) {

			Gson gson = new Gson();
			GroupMemberService service = new GroupMemberService();
			//得到一位會員所加入的揪團
			// 因還未串接登入獲得memberid前端fetch先以1代替
			if (req.getParameter("memberid") != null) {
				Integer p1 = Integer.valueOf(req.getParameter("memberid"));
				List<GroupVO> list = service.GetAllGroup(p1);
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(list));
			}
		}
		if ("getOneGroup".equals(action)) {
			// 進入main頁面顯示揪團資訊
			GroupVO groupVO = new GroupVO();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			PrintWriter out = res.getWriter();
			GroupService service = new GroupService();
			groupVO = service.getOneGroupInfo(groupid);
			String jsonStr = new Gson().toJson(groupVO);
			out.println(jsonStr);
		}
		
		if ("addOneGroup".equals(action)) {
			  Gson gson = new Gson();
				GroupVO groupVO =new GroupVO();
				Group_memberVO groupmemberVO = new Group_memberVO();
				//groupVO:創建時新增揪團
				//groupmemberVO:創建時將創辦人加入groupmember
		        try {
		        	Integer gpCount = Integer.valueOf(req.getParameter("groupmembercount"));
		        	groupVO.setGroupmembercount(gpCount);
		        }catch(NumberFormatException e){
//		        	e.printStackTrace();
		        	res.sendRedirect(req.getRequestURL().toString());
		        }
		        Integer groupOwner =Integer.valueOf(req.getParameter("memberid"));
		        Integer howTojoin =Integer.valueOf(req.getParameter("how_2_join"));
		        if(req.getParameter("groupname").trim().length()!=0) {
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
		}
		
		//取得揪團當前的行程名稱
		if ("getTourInfo".equals(action)) {
			// 進入main頁面顯示揪團資訊
			Integer tourid = Integer.parseInt(req.getParameter("tourid"));
			PrintWriter out = res.getWriter();
			GroupService service = new GroupService();
			TourVO tourvo = new TourVO();
			tourvo = service.getOneTourInfo(tourid);
			String jsonStr = new Gson().toJson(tourvo);
			out.println(jsonStr);
		}
	}

	public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");

		// 修改揪團資訊
		if ("groupSetting".equals(action)) {
		GroupVO groupVO = new GroupVO();
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
		}
		
	}
}
