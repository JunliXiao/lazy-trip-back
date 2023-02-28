package friend.controller;

import com.google.gson.Gson;
import friend.service.FriendMemberService;
import friend.service.FriendMemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

@WebServlet("/api/friend")
public class FriendController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 按類型查詢會員：friend 好友、好友建議 suggestion、已封鎖會員 blocked
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        FriendMemberService service = new FriendMemberServiceImpl();
        int id = Integer.parseInt(request.getParameter("member_id"));
        String queryType = request.getParameter("query_type");

        output = switch (queryType) {
            case "friend" -> gson.toJson(service.getFriends(id));
            case "suggestion" -> gson.toJson(service.getFriendSuggestions(id));
            case "blocked" -> gson.toJson(service.getBlockedMembers(id));
            default -> "{error:Failed}";
        };

        out.println(output);
    }

}
