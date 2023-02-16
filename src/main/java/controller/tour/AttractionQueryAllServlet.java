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

import model.tour.AttractionVO;
import service.tour.AttractionService;
import service.tour.impl.AttractionServiceImpl;

@WebServlet("/attractionQueryAll")
public class AttractionQueryAllServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		try {
			AttractionService service = new AttractionServiceImpl();
			final List<AttractionVO> result = service.attrQueryAll(); 
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(result));
			System.out.println(gson.toJson(result));
			} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
}
