package controller.tour;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.tour.TourService;
import service.tour.impl.TourServiceImpl;

@WebServlet("/tourDelete")
public class TourDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tourId = req.getParameter("tourId");
		try {
			TourService service = new TourServiceImpl();
			final String resultStr = service.tourDelete(Integer.valueOf(tourId));
			resp.setContentType("application/json");
			Gson gson = new Gson();
			resp.getWriter().print(gson.toJson(resultStr));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}


