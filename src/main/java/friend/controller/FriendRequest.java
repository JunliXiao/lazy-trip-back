package friend.controller;

import com.google.gson.Gson;
import friend.service.FriendshipService_Impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

@WebServlet("/friends/request")
public class FriendRequest extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    Gson _gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        FriendshipService_Impl service = new FriendshipService_Impl();
        int requester_id = Integer.parseInt(request.getParameter("requester_id"));
        int addressee_id = Integer.parseInt(request.getParameter("addressee_id"));
        out.println(_gson.toJson(service.requestNewFriend(requester_id, addressee_id)));
    }

}
