package order.controller;

import com.google.gson.Gson;
import order.model.OrderVO;
import order.service.OrderService;
import order.utils.GsonLocalDateAndTimeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/Order.do")
public class OrderGetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        GsonLocalDateAndTimeUtils gsonUtils = new GsonLocalDateAndTimeUtils();
        OrderService orderSvc = new OrderService();


       if (req.getParameter("type").equals("showOrderAllByMemberID")) {
            System.out.println("memberID: " + req.getParameter("memberID"));
            try {
                Integer memberID = Integer.valueOf(req.getParameter("memberID"));
//                List<OrderVO> result = orderSvc.showOrderAllByMemberID(memberID);
                Map<Integer,OrderVO> result = orderSvc.showOrderAllByMemberID(memberID);
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();
                out.print(gsonUtils.toJson(result));
            } catch (Exception e) {
                System.out.println("Order.do_showOrderAllByMemberID: " + e.getMessage());
                e.printStackTrace();
            }


        }else if (req.getParameter("type").equals("showOrderAllAndAlreadyPayByCompanyID")) {
            System.out.println("companyID: " + req.getParameter("companyID"));
            try {
                Integer CompanyID = Integer.valueOf(req.getParameter("companyID"));

                Map<Integer, OrderVO> result = orderSvc.showOrderAllAndAlreadyPayByCompanyID(CompanyID);
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();
                out.print(gsonUtils.toJson(result));

            } catch (Exception e) {
                System.out.println("Order.do_showOrderAllAndAlreadyPayByCompanyID: " + e.getMessage());
                e.printStackTrace();
            }


        }else if(req.getParameter("type").equals("showPayTableFromTheOrderID")){
           System.out.println("orderID: " + req.getParameter("payTableFromTheOrderID"));

           try {
               Integer orderID = Integer.valueOf(req.getParameter("orderID"));

               List<OrderVO> result = orderSvc.showOrderAllAndStatusWaitPayByOrderID(orderID);
               res.setCharacterEncoding("UTF-8");
               res.setContentType("application/json");
               PrintWriter out = res.getWriter();
               out.print(gsonUtils.toJson(result));

           } catch (Exception e) {
               System.out.println("Order.do_showPayTableFromTheOrderID: " + e.getMessage());
               e.printStackTrace();
           }


       }
    }
}
