package order.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import order.model.LinePayVO;
import order.model.OrderVO;
import order.service.OrderService;
import order.service.PayService;
import order.utils.GsonLocalDateAndTimeUtils;

@WebServlet("/Pay.do")
public class PayServlet extends HttpServlet {

	LinePayVO linePayVO;

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String channelId = "1660748179";
		String channelSecret = "45b294aab39d564db301042a8f8df979";
		String url = "https://sandbox-api-pay.line.me/v2/payments/request";

		Gson gson = new Gson();

		req.setCharacterEncoding("UTF-8");

		linePayVO = gson.fromJson(req.getReader(), new TypeToken<LinePayVO>() {
		}.getType());

		JSONObject payJson = new JSONObject();

		// 將需要的屬性添加到JSONObject中
		payJson.put("productName", linePayVO.getProductName());
		payJson.put("amount", linePayVO.getAmount());
		payJson.put("currency", linePayVO.getCurrency());
		payJson.put("confirmUrl", linePayVO.getConfirmUrl());
		payJson.put("orderId", linePayVO.getLinePayOrderID());

		// 將JSONObject轉換為字串
		String requestBody = payJson.toString();

		// 設定request header
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-LINE-ChannelId", channelId);
		conn.setRequestProperty("X-LINE-ChannelSecret", channelSecret);
		conn.setDoOutput(true);

		// 設定request body
		OutputStream os = conn.getOutputStream();
		os.write(requestBody.getBytes());
		os.flush();

		// 讀取回應
		int statusCode = conn.getResponseCode();
		if (statusCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			String responseBody = responseBuffer.toString();
			res.getWriter().append(responseBody);
		} else {
			// 回應非200 OK，處理錯誤
			String errorResponse = "API Error: " + conn.getResponseMessage();
			res.getWriter().append(errorResponse);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String channelId = "1660748179";
		String channelSecret = "45b294aab39d564db301042a8f8df979";
		PayService paySvc  = new PayService();

		req.setCharacterEncoding("UTF-8");
		String transactionId = req.getParameter("transactionId");
		System.out.println("transactionId: " + transactionId);

		String url = "https://sandbox-api-pay.line.me/v2/payments/" + transactionId + "/confirm";

		// 將需要的屬性添加到JSONObject
		JSONObject payConfirmJson = new JSONObject();
		payConfirmJson.put("amount", linePayVO.getAmount());
		payConfirmJson.put("currency", linePayVO.getCurrency());

		// 將JSONObject轉換為字串
		String requestBody = payConfirmJson.toString();

		// 設定request header
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-LINE-ChannelId", channelId);
		conn.setRequestProperty("X-LINE-ChannelSecret", channelSecret);
		conn.setDoOutput(true);

		// 設定request body
		OutputStream os = conn.getOutputStream();
		os.write(requestBody.getBytes());
		os.flush();
		
		// 創建一個JSONObject
		JSONObject returnResult; 
		

		// 讀取回應
		int statusCode = conn.getResponseCode();
		if (statusCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			String responseBody = responseBuffer.toString();
            //responseBody轉成JSONObject
			returnResult = new JSONObject(responseBody);

			String contextPath = req.getContextPath();
			
			// 從JSONObject中獲取returnMessage的值
			String returnMessage = returnResult.getString("returnMessage");
			
			//值是Success.就導向付款成功網址
			if(returnMessage.equals("Success.")) {
				
				int result = paySvc.orderPay(linePayVO.getOrderID());
				if(result == 1) {
					String successUrl = contextPath + "/page/order/order_pay_success.html";
					res.sendRedirect(successUrl);
				}else if(result == 0){
					String failUrl = contextPath + "/page/order/order_pay_fail.html";
					res.sendRedirect(failUrl);
				}
		    //否則就導向付款失敗網址
			}else {
				String failUrl = contextPath + "/page/order/order_pay_fail.html";
				res.sendRedirect(failUrl);
			}
		} else {
			// 回應非200 OK，處理錯誤
			String errorResponse = "API Error: " + conn.getResponseMessage();
			System.out.println("付款失敗");
		}

	}

}