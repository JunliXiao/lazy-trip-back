package order.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import company.model.CompanyVO;
import order.service.BookingSearchService;
import order.service.CompanyService;

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
        Gson gson = new Gson();
        CompanyService companySvc = new CompanyService();

        if(req.getParameter("type").equals("showCompanyInformation")){
            try {
                Integer companyID = Integer.valueOf(req.getParameter("companyID"));
                List<CompanyVO> result = companySvc.showCompanyAllByCompanyID(companyID);

                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();
                out.print(gson.toJson(result));
                System.out.println("開啟飯店頁面 companyID: "+companyID);
                System.out.println(result.get(0).toString());
                System.out.println(result.get(1).toString());
            }catch (Exception e){
                System.out.println("Company.do_showCompanyInformation: "+e.getMessage());
            }

        }






    }
}
