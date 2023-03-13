package order.controller;

import order.model.CouponVO;
import order.utils.GsonLocalDateAndTimeUtils;
import order.service.CouponService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/Coupon.do")
public class CouponServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            GsonLocalDateAndTimeUtils gson = new GsonLocalDateAndTimeUtils();
            Integer couponID = Integer.valueOf(req.getParameter("couponID"));

            String result = null;
            CouponService couponSvc = new CouponService();
            CouponVO couponVO = couponSvc.confirmCoupon(couponID);

            if(couponVO.getCouponID() == null){
                result = "該優惠碼不存在";
            }else if(!couponVO.getCouponStatus()){
                result = "該優惠碼已失效";
            }else if(couponVO.getCouponUsedQuantity() >= couponVO.getCouponQuantity()){
                result = "使用已達數量上限";
            }else if(couponVO.getCouponID() != null){
                if(couponVO.getCouponStatus() && couponVO.getCouponUsedQuantity() < couponVO.getCouponQuantity()){
                    result = "OK";
                }
            }else{
                //優惠碼有問題才會跳到這行
                result = "該優惠碼不能使用";
            }

            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();

            if(result != null){
                if(result.equals("OK")){
                    out.print(gson.toJson(couponVO));
                }else{
                    out.print(gson.toJson(result));
                }
            }
        }catch (Exception e){
            System.out.println("Coupon.do: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
