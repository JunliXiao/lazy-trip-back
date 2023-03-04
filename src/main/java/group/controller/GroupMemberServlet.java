package group.controller;

import java.io.IOException;
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
import group.service.GroupMemberService;
import member.model.Member;

@WebServlet("/groupmembers")
public class GroupMemberServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		GroupMemberService service = new GroupMemberService();

		if (member != null) {
			member.setPassword("***");
		}

		// 取得當前揪團所有隸屬成員
		if ("getGroupMems".equals(action)) {
			Gson gson = new Gson();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			List<Member> list = service.getAllMember(groupid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));

		}

		// 取得當前揪團所有任何狀態成員ID
		if ("getGroupMemsInList".equals(action)) {
			Gson gson = new Gson();
			Integer groupid = Integer.parseInt(req.getParameter("groupid"));
			List<Group_memberVO> list = service.getAllMemberList(groupid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));
		}

		// 得到一位會員所加入的揪團
		if ("getAllGroups".equals(action)) {
			Gson gson = new Gson();
			if (member.getId() != null) {
				List<GroupVO> list = service.GetAllGroup(member.getId());
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(list));
			}
		}

		// 刪除揪團成員
		if ("delGroupMember".equals(action)) {
			Enumeration<String> e = req.getParameterNames();
			List list = new ArrayList<Integer>();
			while (e.hasMoreElements()) {
				String param = e.nextElement();
				String value = req.getParameter(param);
				if (param.equals("groupid")) {
					list.add(0, Integer.parseInt(value));
				} else if (!param.equals("action") && !param.equals("groupid")) {
					list.add(Integer.parseInt(value));
				}
			}
			service.delGroupMem(list);
		}
		// 查詢邀請
		if ("getAllInvite".equals(action)) {
			Gson gson = new Gson();
			Integer memberid = Integer.valueOf(member.getId());
			List list = service.getAllInvite(memberid);
			res.setContentType("application/json");
			res.getWriter().print(gson.toJson(list));
		}
	}
}
