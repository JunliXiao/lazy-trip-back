package controller.tour;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/tourQueryAll")
public class TourQueryAllServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
//		TourVO tourVO = gson.fromJson(req.getReader(), TourVO.class);
		try {
			TourService service = new TourServiceImpl();
			final List<TourVO> resultLists = service.tourQueryAll(); 
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(resultLists));
			System.out.println(gson.toJson(resultLists));
			} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
}
