package controller.tour;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.tour.TourVO;
import service.tour.TourService;
import service.tour.impl.TourServiceImpl;

@WebServlet("/tourQueryOne")
public class TourQueryOneServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String tourId = req.getParameter("tourId");
		try {
			TourService service = new TourServiceImpl();
			final TourVO resultLists = service.tourQueryOne(Integer.valueOf(tourId));
			System.out.println(resultLists.toString());
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(resultLists));
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
}
