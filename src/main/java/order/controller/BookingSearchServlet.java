package order.controller;

import com.google.gson.Gson;
import company.model.CompanyVO;
import order.service.BookingSearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BookingSearch.do")
public class BookingSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        System.out.println(req.getParameter("type"));



       if(req.getParameter("type").equals("showCompanyByPosition")){
           System.out.println("addressCounty"+req.getParameter("addressCounty"));

           try {
               String addressCounty = req.getParameter("addressCounty");

               Gson gson = new Gson();
               List<CompanyVO> companyVOs = new ArrayList<>();
               BookingSearchService bsSvc = new BookingSearchService();

               companyVOs = bsSvc.showCompanyAndRoomTypePriceByPosition(addressCounty);

               res.setCharacterEncoding("UTF-8");
               res.setContentType("application/json");
               PrintWriter out = res.getWriter();
               out.print(gson.toJson(companyVOs));
           }catch (Exception e){
               System.out.println("BookingSearch.do_showCompanyAndRoomTypePriceByPosition: "+e.getMessage());
           }





       }





    }
}
