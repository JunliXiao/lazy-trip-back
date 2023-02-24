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

@WebServlet("/api/chatroom")
public class ChatroomController extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        ChatMemberService service = new ChatMemberServiceImpl();
        Integer id = Integer.parseInt(request.getParameter("chatroom_id"));

        output = gson.toJson(service.getMembersByChatroom(id));
        out.println(output);
    }
}
