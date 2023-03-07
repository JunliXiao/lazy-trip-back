package order.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import order.model.OrderDetailVO;
import order.model.OrderVO;
import order.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Order.do")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            JsonObject sendOrder = gson.fromJson(req.getReader(), JsonObject.class);

            JsonObject orderJson = sendOrder.getAsJsonObject("order");
            JsonArray orderDetailJson = sendOrder.getAsJsonArray("orderDetail");

            OrderVO orderVO = new OrderVO();
            orderVO.setMemberID(orderJson.get("memberID").getAsInt());
            orderVO.setCompanyID(orderJson.get("companyID").getAsInt());
            orderVO.setOrderCheckInDate(LocalDate.parse(orderJson.get("orderCheckInDate").getAsString()));
            orderVO.setOrderCheckOutDate(LocalDate.parse(orderJson.get("orderCheckOutDate").getAsString()));
            orderVO.setCouponID(orderJson.get("couponID").getAsInt());
            orderVO.setOrderTotalPrice(orderJson.get("orderTotalPrice").getAsInt());
            orderVO.setTravelerName(orderJson.get("travelerName").getAsString());
            orderVO.setTravelerIDNumber(orderJson.get("travelerIDNumber").getAsString());
            orderVO.setTravelerEmail(orderJson.get("travelerEmail").getAsString());
            orderVO.setTravelerPhone(orderJson.get("travelerPhone").getAsString());


            List<OrderDetailVO> orderDetailVOs = new ArrayList<>();
            for (JsonElement odj : orderDetailJson) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                orderDetailVO.setRoomTypeID(odj.getAsJsonObject().get("roomTypeID").getAsInt());
                orderDetailVO.setOrderDetailRoomPrice(odj.getAsJsonObject().get("orderDetailRoomPrice").getAsInt());
                orderDetailVO.setOrderDetailRoomQuantity(odj.getAsJsonObject().get("orderDetailRoomQuantity").getAsByte());
                orderDetailVO.setOrderDetailCouponDiscountPrice(odj.getAsJsonObject().get("orderDetailCouponDiscountPrice").getAsInt());
                orderDetailVOs.add(orderDetailVO);
            }

            OrderService orderSvc = new OrderService();
            int result = orderSvc.addOrderAndOrderDetail(orderVO, orderDetailVOs);

            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.print(gson.toJson(result));
        }catch (Exception e){
            System.out.println("Create order failed: "+e.getMessage());
            e.printStackTrace();
        }



    }


}
