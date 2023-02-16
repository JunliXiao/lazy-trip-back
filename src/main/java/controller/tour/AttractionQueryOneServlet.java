package controller.tour;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.tour.AttractionVO;
import service.tour.AttractionService;
import service.tour.impl.AttractionServiceImpl;

@WebServlet("/attractionQueryOne")
public class AttractionQueryOneServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		String attractionId = req.getParameter("attractionId");
		try {
			AttractionService service = new AttractionServiceImpl();
			final AttractionVO resultLists = service.attrQueryOne(Integer.valueOf(attractionId));
			System.out.println(resultLists.toString());
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(resultLists));
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
}
