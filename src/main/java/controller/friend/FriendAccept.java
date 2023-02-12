package controller.friend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.friend.FriendshipService_Impl;

@WebServlet("/friends/update/accept")
public class FriendAccept extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson _gson = new Gson();
       
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		FriendshipService_Impl service = new FriendshipService_Impl();
		int requester_id = Integer.parseInt(request.getParameter("requester_id"));
		int addressee_id = Integer.parseInt(request.getParameter("addressee_id"));
		out.println(_gson.toJson(service.acceptFriendRequest(requester_id, addressee_id)));
	}

}
