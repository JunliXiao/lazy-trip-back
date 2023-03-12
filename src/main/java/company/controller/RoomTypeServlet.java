package company.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import company.model.RoomTypeVO;
import company.service.RoomTypeService;

@WebServlet("/roomtypeservlet") 
public class RoomTypeServlet extends HttpServlet{


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
				String str = req.getParameter("roomTypeID");

//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入房型編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
	//
				Integer roomTypeID = null;

				try {
					roomTypeID = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("房型編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();

				RoomTypeVO roomTypeVO = roomTypeService.getOneRoomType(roomTypeID);

				if (roomTypeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/table roomType test.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("roomTypeVO", roomTypeVO); // 資料庫取出的empVO物件,存入req
				String url = "/roomType/table roomType test.jsp";
				Gson gson = new Gson();
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(roomTypeID));
			}

			if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				/*************************** 1.接收請求參數 ****************************************/
				Integer roomTypeID = Integer.valueOf(req.getParameter("roomTypeID"));

				/*************************** 2.開始查詢資料 ****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				RoomTypeVO roomTypeVO = roomTypeService.getOneRoomType(roomTypeID);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("roomTypeVO", roomTypeVO); // 資料庫取出的empVO物件,存入req
				String url = "/roomType/table roomType test.jsp";
				Gson gson = new Gson();
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(roomTypeVO));
			}

			if ("update".equals(action)) { // 來自update_comapny_input.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer roomTypeID = Integer.parseInt(req.getParameter("roomTypeID"));
				Integer companyID = Integer.parseInt(req.getParameter("companyID"));
				String roomTypeName = req.getParameter("roomTypeName");
				Integer roomTypePerson = Integer.parseInt(req.getParameter("roomTypePerson"));
				Integer roomTypeQuantity = Integer.parseInt(req.getParameter("roomTypeQuantity"));
				Integer roomTypePrice = Integer.parseInt(req.getParameter("roomTypePrice"));
				
				String str = req.getParameter("");

				String roomTypeNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
				if (roomTypeName == null || roomTypeName.trim().length() == 0) {
					errorMsgs.add("設備名稱: 請勿空白");
				} else if (!roomTypeName.trim().matches(roomTypeNameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("設備名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到10之間");
				}
				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypeID(roomTypeID);
				roomTypeVO.setCompanyID(companyID);
				roomTypeVO.setRoomTypeName(roomTypeName);
				roomTypeVO.setRoomTypePerson(roomTypePerson);
				roomTypeVO.setRoomTypeQuantity(roomTypeQuantity);
				roomTypeVO.setRoomTypePrice(roomTypePrice);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roomTypeVO", roomTypeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/table roomType test.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeVO = roomTypeService.updateRoomType(roomTypeID, companyID, roomTypeName, roomTypePerson, roomTypeQuantity,roomTypePrice);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("roomTypeVO", roomTypeVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/roomType/table roomType test.jsp";
				Gson gson = new Gson();
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(roomTypeVO));
			}

			if ("insert".equals(action)) { // 來自addEmp.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer roomTypeID = Integer.valueOf(req.getParameter("roomTypeID"));

//				String equipmentName = req.getParameter("roomTypeName").trim();
//				if (equipmentName == null || equipmentName.trim().length() == 0) {
//					errorMsgs.add("請勿空白");
//				}
	//
//				String equipmentDesc = req.getParameter("equipmentDesc").trim();
//				if (equipmentDesc == null || equipmentDesc.trim().length() == 0) {
//					errorMsgs.add("請勿空白");
//				}

				RoomTypeVO roomTypeVO = new RoomTypeVO();
//				equipmentVO.setroomTypeID(123);
//				equipmentVO.setroomTypeName("安安");
//				equipmentVO.setEquipmentDesc("你好");

				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("equipmentVO", equipmentVO); // 含有輸入格式錯誤的empVO物件,也存入req
//					RequestDispatcher failureView = req.getRequestDispatcher("/equipment/addequipment.jsp");
//					failureView.forward(req, res);
//					return;
//				}

				/*************************** 2.開始新增資料 ***************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				
				Integer companyID = Integer.parseInt(req.getParameter("companyID"));
				String roomTypeName = req.getParameter("roomTypeName");
				Integer roomTypePerson = Integer.parseInt(req.getParameter("roomTypePerson"));
				Integer roomTypeQuantity = Integer.parseInt(req.getParameter("roomTypeQuantity"));
				Integer roomTypePrice = Integer.parseInt(req.getParameter("roomTypePrice"));

				roomTypeVO = roomTypeService.addRoomType(roomTypeID, companyID, roomTypeName, roomTypePerson, roomTypeQuantity,
						roomTypePrice);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/roomType/addroomType.jsp";
				Gson gson = new Gson();
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(roomTypeVO));
			}

			if ("delete".equals(action)) { // 來自listAllEmp.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				/*************************** 1.接收請求參數 ***************************************/
				Integer roomTypeID = Integer.valueOf(req.getParameter("roomTypeID"));

				/*************************** 2.開始刪除資料 ***************************************/
				RoomTypeService roomTypeService = new RoomTypeService();
				roomTypeService.deleteRoomType(roomTypeID);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/roomType/table roomType test.jsp";
				Gson gson = new Gson();
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(roomTypeID));
			}
			
			
			if ("getAllByCompanyID".equals(action)) { // 來自前端的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("companyID");

//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入房型編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/select_page.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
	//
				Integer companyID = null;

				try {
					companyID = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("房型編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				RoomTypeService roomTypeService = new RoomTypeService();

				List<RoomTypeVO> roomTypeVOList = roomTypeService.getAllByCompanyID(companyID);

				if (roomTypeVOList == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/roomType/table roomType test.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("RoomTypeVOList", roomTypeVOList); // 資料庫取出的roomTypeVOList物件,存入req
				String url = "/roomType/table roomType test.jsp";
				Gson gson = new Gson();
				res.setContentType("application/json");
				res.getWriter().print(gson.toJson(roomTypeVOList));
			}
		
		}
}

	
	
	
	

