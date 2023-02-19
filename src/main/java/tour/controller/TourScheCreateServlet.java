package tour.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tour.model.TourScheduleVO;
import tour.service.TourScheduleService;
import tour.service.TourScheduleServiceImpl;

@WebServlet("/tourScheCreate")
@MultipartConfig
public class TourScheCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Gson gson = new Gson();
            req.setCharacterEncoding("UTF-8");
            TourScheduleVO tourScheduleVO = gson.fromJson(req.getReader(), TourScheduleVO.class);
            TourScheduleService service = new TourScheduleServiceImpl();
            int result = service.tourScheCreate(tourScheduleVO);
            tourScheduleVO.setTourId(result);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(gson.toJson(tourScheduleVO));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
