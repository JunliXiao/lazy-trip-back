package friend.controller;

import friend.repository.FriendMemberRepositoryImpl;
import friend.service.FriendMemberService;
import friend.service.FriendMemberServiceImpl;
import member.model.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet("/friend-list")
public class FriendListPageController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FriendMemberService service = new FriendMemberServiceImpl(new FriendMemberRepositoryImpl());

        int id = Integer.parseInt(request.getParameter("member_id"));

        List<Member> friends = service.getAllFriendMembers(id);

        request.setAttribute("friends", friends);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/page/friend/friend_list.jsp");
        rd.forward(request, response);
    }
}
