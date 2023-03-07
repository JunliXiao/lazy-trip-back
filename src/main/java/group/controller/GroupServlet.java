package group.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import group.model.GroupVO;
import group.model.Group_memberVO;
import group.service.GroupService;
import member.model.Member;
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
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");

		// Service
		GroupService service = new GroupService();

		if (member != null) {
			member.setPassword("***");
		}
		// 加入返回登入頁面或出錯頁面
//		else {
//			
//		}

		if ("getUsingMember".equals(action)) {
			PrintWriter out = res.getWriter();
			out.println(new Gson().toJson(member));
		}

		if ("getOneGroup".equals(action)) {
			// 進入main頁面顯示揪團資訊
			GroupVO groupVO = new GroupVO();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			PrintWriter out = res.getWriter();
			groupVO = service.getOneGroupInfo(groupid);
			List list = new ArrayList();
			list.add(groupVO);
			list.add(member);
//			String jsonStr1 = new Gson().toJson(groupVO);
			String jsonStr1 = new Gson().toJson(list);
//			System.out.println(jsonStr1);
			out.println(jsonStr1);

		}

		if ("addOneGroup".equals(action)) {
			Gson gson = new Gson();
			GroupVO groupVO = new GroupVO();
			Group_memberVO groupmemberVO = new Group_memberVO();
			// groupVO:創建時新增揪團
			// groupmemberVO:創建時將創辦人加入groupmember
			try {
				Integer gpCount = Integer.valueOf(req.getParameter("groupmembercount"));
				groupVO.setGroupmembercount(gpCount);
			} catch (NumberFormatException e) {
//		        	e.printStackTrace();
				res.sendRedirect(req.getRequestURL().toString());
			}
			Integer groupOwner = Integer.valueOf(member.getId());
			Integer howTojoin = Integer.valueOf(req.getParameter("how_2_join"));
			if (req.getParameter("groupname").trim().length() != 0) {
				String name = req.getParameter("groupname");
				groupVO.setGroupname(name);
			}
			groupVO.setMemberid(groupOwner);
			groupVO.setIfjoingroupdirectly(howTojoin);

			int pk = service.addGroup(groupVO);
			groupmemberVO.setMemberid(groupOwner);
			groupmemberVO.setGroupid(pk);
			service.InsertWhenCreate(groupmemberVO);
			groupVO.setGroupid(pk);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(groupVO));
		}

		// 取得揪團當前的行程名稱
		if ("getTourInfo".equals(action)) {
			// 進入main頁面顯示揪團資訊
			Integer tourid = Integer.parseInt(req.getParameter("tourid"));
			PrintWriter out = res.getWriter();
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
		GroupService service = new GroupService();
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
			service.updateGroupInfo(groupVO);
			PrintWriter out = res.getWriter();
			String jsonStr = new Gson().toJson(groupVO);
			out.println(jsonStr);
		}

	}

//	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
//		req.setCharacterEncoding("UTF-8");
//		res.setContentType("text/html;charset=UTF-8");
//		String action = req.getParameter("action");
//
//	}

}
