package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.Friendship;
import service.FriendshipService;

@WebServlet("/friendship")
public class FriendshipController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Gson _gson = new Gson();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		FriendshipService service = new FriendshipService();
		List<Friendship> friendships = service.getFriendshipBy(5); // hard-coded
		
		for (Friendship friendship : friendships) {
			out.print(_gson.toJson(friendship));
		}

	}
	
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}
}
