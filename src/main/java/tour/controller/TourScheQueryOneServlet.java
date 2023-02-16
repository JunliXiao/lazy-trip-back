package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.model.TourScheduleVO;
import tour.service.TourScheduleService;
import tour.service.TourScheduleServiceImpl;

@WebServlet("/tourScheQueryOne")
public class TourScheQueryOneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String tourScheduleId = req.getParameter("tourScheduleId");
        try {
            TourScheduleService service = new TourScheduleServiceImpl();
            final TourScheduleVO resultLists = service.tourScheQueryOne(Integer.valueOf(tourScheduleId));
            System.out.println(resultLists.toString());
            resp.setContentType("application/json");
            resp.getWriter().print(gson.toJson(resultLists));
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
