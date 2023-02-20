package friend.controller;

import com.google.gson.Gson;
import friend.service.FriendshipServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

@WebServlet("/api/friend-requests")
public class FriendRequestController extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    // 新增邀請：邀請方為 requester，接受方 為 addressee
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        FriendshipServiceImpl service = new FriendshipServiceImpl();
        int requester_id = Integer.parseInt(request.getParameter("requester_id"));
        int addressee_id = Integer.parseInt(request.getParameter("addressee_id"));

        out.println(gson.toJson(service.requestNewFriend(requester_id, addressee_id)));
    }

    // 更新邀請狀態：accept 接受、cancel 取消、decline 婉拒
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        FriendshipServiceImpl service = new FriendshipServiceImpl();
        int requester_id = Integer.parseInt(request.getParameter("requester_id"));
        int addressee_id = Integer.parseInt(request.getParameter("addressee_id"));
        String updateStatus = request.getParameter("update_status");

        out.println(gson.toJson(service.updateFriendRequestDirectional(requester_id, addressee_id, updateStatus)));
    }

    // 查詢邀請：direction 分為 sent 和 received 兩個方向
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        FriendshipServiceImpl service = new FriendshipServiceImpl();
        int id = Integer.parseInt(request.getParameter("member_id"));
        String direction = request.getParameter("direction");

        out.println(gson.toJson(service.getPendingRequests(id, direction)));
    }

}
