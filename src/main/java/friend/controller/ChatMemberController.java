package friend.controller;

import com.google.gson.Gson;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/chat/member")
public class ChatMemberController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output = "false";

        ChatMemberService service = new ChatMemberServiceImpl();
        String target = request.getParameter("action");

        if (target.equals("member")) {
            String searchText = request.getParameter("search_text");
            output = gson.toJson(service.findMember(searchText));
        } else if (target.equals("chatroom_member")) {
            Integer id = Integer.parseInt(request.getParameter("chatroom_id"));
            output = gson.toJson(service.getMembersByChatroom(id));
        }
        out.println(output);
    }
}
