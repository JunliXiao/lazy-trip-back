package controller.friend;

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
import service.friend.FriendshipService_Impl;

@WebServlet("/friends")
public class FriendList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		FriendshipService_Impl service = new FriendshipService_Impl();
		int id = Integer.parseInt(request.getParameter("member_id"));
		List<Map<String, String>> friends = service.getAllFriends(id); 
		
		out.println(gson.toJson(friends));

	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
