package friend.controller;

import com.google.gson.Gson;
import friend.json.ModelWrapper;
import friend.service.FriendMemberService;
import friend.service.FriendMemberServiceImpl;
import member.model.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/friend")
public class FriendController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 按類型查詢會員：friend 好友、好友建議 suggestion、已封鎖會員 blocklist
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String output;

        FriendMemberService service = new FriendMemberServiceImpl();
        int id = Integer.parseInt(request.getParameter("member_id"));
        String queryType = request.getParameter("query_type");

        List<Member> dataList = switch (queryType) {
            case "friend" -> service.getFriends(id);
            case "suggestion" -> service.getFriendSuggestions(id);
            case "blocklist" -> service.getBlockedMembers(id);
            default -> new ArrayList<>();
        };
//        if (dataList.size() == 0) {
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        } else {
//            response.setStatus(HttpServletResponse.SC_OK);
//        }

        output = gson.toJson(new ModelWrapper(dataList));
        out.println(output);
    }

}
