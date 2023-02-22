package friend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import friend.service.FriendMemberService;
import friend.service.FriendMemberServiceImpl;
import friend.service.FriendshipService;
import friend.service.FriendshipServiceImpl;

@WebServlet("/api/friend-suggestions")
public class SuggestionController extends HttpServlet {
	
    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();	

    // 查詢沒有任何好友關係的會員，作為建議
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	PrintWriter out = response.getWriter();
    	FriendMemberService service = new FriendMemberServiceImpl();
    	
    	int id = Integer.parseInt(request.getParameter("member_id"));
    	
    	out.println(gson.toJson(service.getFriendSuggestions(id)));
    }

}
