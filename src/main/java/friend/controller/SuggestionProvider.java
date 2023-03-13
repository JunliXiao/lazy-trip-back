package friend.controller;

import com.google.gson.Gson;
import friend.json.ModelWrapper;
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

@WebServlet("/api/friend-suggestions")
public class SuggestionProvider extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 查詢沒有任何好友關係的會員，作為建議
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        FriendMemberService service = new FriendMemberServiceImpl();

        int id = Integer.parseInt(request.getParameter("member_id"));
        ModelWrapper wrapper = new ModelWrapper(service.getFriendSuggestions(id));
        out.println(gson.toJson(wrapper));
    }

}
