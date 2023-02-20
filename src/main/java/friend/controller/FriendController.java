package friend.controller;

import com.google.gson.Gson;

import friend.service.FriendshipService;
import friend.service.FriendshipServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.List;
import java.util.Map;

@WebServlet("/api/friends")
public class FriendController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 查詢好友
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        FriendshipService service = new FriendshipServiceImpl();
//        FriendMemberService service = new FriendMemberServiceImpl(new FriendMemberRepositoryImpl());

        int id = Integer.parseInt(request.getParameter("member_id"));
        List<Map<String, String>> friends = service.getAllFriends(id);
//        List<Member> friends = service.getAllFriendMembers(id);

        out.println(gson.toJson(friends));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
