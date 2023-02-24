package friend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;

@WebServlet("/api/chat")
public class ChatroomController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;		

        ChatMemberService service = new ChatMemberServiceImpl();
        Integer id = Integer.parseInt(request.getParameter("member_id"));
        
        output = gson.toJson(service.getChatroomsByMember(id));
		out.println(output);
	}

}
