package controller.tour;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.tour.AttractionVO;
import service.tour.AttractionService;
import service.tour.impl.AttractionServiceImpl;

@WebServlet("/attractionCreate")
@MultipartConfig
public class AttractionCreateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			Gson gson = new Gson();
			
			req.setCharacterEncoding("UTF-8");
			AttractionVO attractionVO = gson.fromJson(req.getReader(), AttractionVO.class);
			AttractionService service = new AttractionServiceImpl();
			service.attrCreate(attractionVO);
			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(attractionVO));
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
}
