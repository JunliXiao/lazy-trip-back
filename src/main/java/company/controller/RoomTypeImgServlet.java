package company.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import company.model.RoomTypeImgVO;
import company.service.RoomTypeImgService;

@WebServlet("/RoomtypeImgServlet")
public class RoomTypeImgServlet extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("roomTypeImgID");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入房型圖片編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/company/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer roomTypeImgID = null;
			try {
				roomTypeImgID = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("房型圖片編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/company/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			RoomTypeImgService roomTypeImgService = new RoomTypeImgService();
			RoomTypeImgVO roomtypeImgVO = roomTypeImgService.getOneRoomTypeImg(roomTypeImgID);
			if (roomtypeImgVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/company/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("roomTypeImgVO", roomtypeImgVO); // 資料庫取出的empVO物件,存入req
			String url = "/company/listOneCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer roomTypeID = Integer.valueOf(req.getParameter("roomtypeID"));

			/*************************** 2.開始查詢資料 ****************************************/
			RoomTypeImgService roomTypeImgService = new RoomTypeImgService();
			
			RoomTypeImgVO roomTypeImgVO = roomTypeImgService.getOneRoomTypeImg(roomTypeID);
			
						

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("roomTypeImgVO", roomTypeImgVO); // 資料庫取出的empVO物件,存入req
			String url = "/company/update_company_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_comapny_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer roomTypeImgID = Integer.valueOf(req.getParameter("roomTypeImgID"));

			Integer roomTypeID = Integer.valueOf(req.getParameter("roomTypeID"));
			
			String roomTypeImgString = req.getParameter("roomTypeImg");
					
		    // Open an input stream to the image file
		    InputStream in = getServletContext().getResourceAsStream("/images/" + roomTypeImgString);
		    
		    // Copy the contents of the input stream to the output stream
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    byte[] buffer = new byte[4096];
		    int bytesRead = -1;
		    while ((bytesRead = in.read(buffer)) != -1) {
		      out.write(buffer, 0, bytesRead);
		    }
		    byte[] roomTypeImg = out.toByteArray();
		    		    
		    in.close();
		    out.close();
		  
			
//			String RoomtypeImgNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
//			if (RoomtypeImgID == null || RoomTypeImgID.trim().length() == 0) {
//				errorMsgs.add("設備名稱: 請勿空白");
//			} else if (!RoomTypeImgID.trim().matches(RoomTypeImgIDReg)) { // 以下練習正則(規)表示式(regular-expression)
//				errorMsgs.add("設備名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
//			}
//
//			String RoomTypeImg = req.getParameter("RoomTypeImg").trim();
//			if (RoomTypeImg == null || RoomTypeImg.trim().length() == 0) {
//				errorMsgs.add("職位請勿空白");
//			}

			
			RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();
			roomTypeImgVO.setRoomTypeID(roomTypeID);
			roomTypeImgVO.setRoomTypeImg(roomTypeImg);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("RoomTypeImgVO", roomTypeImgVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/company/update_company_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			RoomTypeImgService RoomtypeImgService = new RoomTypeImgService();
			roomTypeImgVO = RoomtypeImgService.updateRoomTypeImg(roomTypeImgID, roomTypeID, roomTypeImg);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("RoomtypeImgVO", roomTypeImgVO); // 資料庫update成功後,正確的的empVO物件,存入req
			String url = "/company/listOneCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer roomTypeID = Integer.valueOf(req.getParameter("RoomTypeID").trim());

			Integer roomTypeImgID = Integer.valueOf(req.getParameter("RoomTypeImgID").trim());
			if (roomTypeImgID == null || roomTypeImgID == 0) {
				errorMsgs.add("請勿空白");
			}

			String roomTypeImgString = req.getParameter("roomTypeImg");
			
		    // Open an input stream to the image file
		    InputStream in = getServletContext().getResourceAsStream("/images/" + roomTypeImgString);
		    
		    // Copy the contents of the input stream to the output stream
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    byte[] buffer = new byte[4096];
		    int bytesRead = -1;
		    while ((bytesRead = in.read(buffer)) != -1) {
		      out.write(buffer, 0, bytesRead);
		    }
		    byte[] roomTypeImg = out.toByteArray();
		    		    
		    in.close();
		    out.close();
		    
			RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();
			
			roomTypeImgVO.setRoomTypeID(roomTypeID);
			roomTypeImgVO.setRoomTypeImg(roomTypeImg);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("RoomTypeImgVO", roomTypeImgVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/RoomtypeImg/addRoomtypeImg.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			RoomTypeImgService roomTypeImgService = new RoomTypeImgService();
			roomTypeImgVO = roomTypeImgService.addRoomTypeImg(roomTypeImgID, roomTypeID, roomTypeImg);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/company/addCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer RoomTypeImgID = Integer.valueOf(req.getParameter("RoomTypeImgID"));

			/*************************** 2.開始刪除資料 ***************************************/
			RoomTypeImgService RoomTypeImgService = new RoomTypeImgService();
			RoomTypeImgService.deleteroomTypeImg(RoomTypeImgID);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/company/listAllCompany.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}

		if ("1".equals(action)) {

			Gson gson = new Gson();
			RoomTypeImgVO jsonObject = gson.fromJson(req.getReader(), RoomTypeImgVO.class);
			System.out.println(jsonObject.getRoomTypeImgID());
			RoomTypeImgService service = new RoomTypeImgService();
			List<RoomTypeImgVO> list = service.getAll();
			res.setContentType("application/json");
		    res.getWriter().write(gson.toJson(list));
		}
	}
}
