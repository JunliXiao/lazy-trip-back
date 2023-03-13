package friend.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import friend.json.ModelWrapper;
import friend.model.Chatroom;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/api/chat")
public class ChatroomController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        ChatMemberService service = new ChatMemberServiceImpl();
        Integer id = Integer.parseInt(request.getParameter("member_id"));
        List<Chatroom> dataList = service.getChatroomsByMember(id);

        output = gson.toJson(new ModelWrapper(dataList));
        out.println(output);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        ChatMemberService service = new ChatMemberServiceImpl();
        // Custom type for JSON processing
        Type typeOfMemberIdList = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> memberIds = gson.fromJson(request.getReader(), typeOfMemberIdList);

        output = gson.toJson(service.createChatroom(memberIds));
        out.println(output);
    }

}
