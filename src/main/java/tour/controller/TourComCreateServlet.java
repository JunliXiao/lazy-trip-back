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

import tour.model.TourComVO;
import tour.service.TourComService;
import tour.service.TourComServiceImpl;

@WebServlet("/tourComCreate")
@MultipartConfig
public class TourComCreateServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
		req.setCharacterEncoding("UTF-8");
		TourComVO tourComVO = gson.fromJson(req.getReader(), TourComVO.class);
		TourComService service;
		try {
			service = new TourComServiceImpl();
			int result = service.tourComCreate(tourComVO);
			System.out.println(result);
			tourComVO.setTourComId(result);
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().print(gson.toJson(tourComVO));	
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
}
