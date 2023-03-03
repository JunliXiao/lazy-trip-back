package order.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import order.service.BookingSearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/Company.do")
public class CompanyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");



        if(req.getParameter("type").equals("chooseCompany")){
            try {
                Gson gson = new Gson();
                Integer companyID = Integer.valueOf(req.getParameter("companyID"));

                BookingSearchService bsSvc = new BookingSearchService();
                List<JsonObject> result = bsSvc.showCompanyInformationByCompanyID(companyID);

                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();
                out.print(gson.toJson(result));
            }catch (Exception e){
                System.out.println("BookingSearch.do_showCompanyInformationByCompanyID: "+e.getMessage());
            }

        }






    }
}
