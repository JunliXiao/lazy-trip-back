package article.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import article.dao.ArticleDAO;
import article.model.ArticleVO;
import article.service.ArticleService;

@WebServlet("/article/ArticleServlet2")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ArticleServlet2 extends HttpServlet {
	private static final long serialVersionUID = 77777L;
       
	public static byte[] getImageByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
		
	}
	
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer articleId = Integer.parseInt(request.getParameter("articleId"));
		ArticleDAO articleDAO = new ArticleDAO();
		
		try {
			ArticleVO articleVO = articleDAO.getImageByArticleId(articleId);
			response.setContentType("image/png , image/jpg");
			OutputStream out = response.getOutputStream();
			out.write(articleVO.getArticleImage());
			out.close();
		}catch (SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
if ("getOne_For_Display".equals(action)) { // ??????select_page.jsp?????????//??????????????????


				/***************************1.?????????????????? - ???????????????????????????**********************/
				Integer articleId = Integer.valueOf(request.getParameter("articleId").trim());
				
				/***************************2.??????????????????*****************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(articleId);
				
				/***************************3.????????????,????????????(Send the Success view)*************/
				request.setAttribute("articleVO", articleVO); // ??????????????????empVO??????,??????req
				String url = "/article/oneArticle.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // ???????????? listOneEmp.jsp
				successView.forward(request, response);
		}
		
if ("getMember_For_Display".equals(action)) { // ??????select_page.jsp?????????//????????????


			/***************************1.?????????????????? - ???????????????????????????**********************/
			Integer memberId = Integer.valueOf(request.getParameter("memberId").trim());
			

			
			/***************************2.??????????????????*****************************************/
			ArticleService articleSvc = new ArticleService();
			List<ArticleVO> articleVO = articleSvc.getOneByMember(memberId);
			
			/***************************3.????????????,????????????(Send the Success view)*************/
			request.setAttribute("articleVO", articleVO); // ??????????????????empVO??????,??????req
			String url = "/article/myArticle.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url); // ???????????? listOneEmp.jsp
			successView.forward(request, response);
	}
		
		
if ("getOne_For_Update".equals(action)) { // ??????listAllEmp.jsp?????????

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.??????????????????****************************************/
				Integer articleId = Integer.valueOf(request.getParameter("articleId"));
				
				/***************************2.??????????????????****************************************/
				ArticleService articleSvc = new ArticleService();
				ArticleVO articleVO = articleSvc.getOneArticle(articleId);
								
				/***************************3.????????????,????????????(Send the Success view)************/
				request.setAttribute("articleVO", articleVO);         // ??????????????????empVO??????,??????req
				String url = "/article/updateArticle.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);// ???????????? update_emp_input.jsp
				successView.forward(request, response);
		}
		
		
if ("update".equals(action)) { // ??????update_emp_input.jsp?????????
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.?????????????????? - ???????????????????????????**********************/
				Integer articleId = Integer.valueOf(request.getParameter("articleId").trim());
				
				
				String articleTitle = request.getParameter("articleTitle");
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("????????????????????????");
				}	


				String articleContent = request.getParameter("articleContent").trim();
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("????????????????????????");
				}	
				
				Timestamp articleDate = Timestamp.valueOf(request.getParameter("articleDate").trim());
				Timestamp articleDateChange = Timestamp.valueOf(request.getParameter("articleDateChange").trim());
				
//				java.sql.Timestamp articleDateChange = null;
//				try {
//					articleDateChange = java.sql.Timestamp.valueOf(request.getParameter("articleDateChange").trim());
//				} catch (IllegalArgumentException e) {
//					articleDateChange=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("???????????????xyz!");
//				}
				
				
				//??????
				String saveDirectory = "/DB-image";
				String savepath = request.getServletContext().getRealPath(saveDirectory);
				File imfolderPath = new File(savepath);
				if(!imfolderPath.exists()) {
					imfolderPath.mkdirs();
				}
				Part part = request.getPart("articleImage");
				String filename = part.getSubmittedFileName();
				byte[] articleImage = null;
				
				
				
				System.out.println(imfolderPath);
				System.out.println(filename);
				
				if(filename != null && filename.length() != 0 && part.getContentType() != null) {
					String imPath = imfolderPath + "/" + filename;
					part.write(imPath);
					articleImage = getImageByteArray(imPath);
				}
				else {
					errorMsgs.add("???????????????");
				}
				

				Integer adminId = 1;
				Integer memberId = 1;
				Integer tourId = 1;
				
				ArticleVO articleVO = new ArticleVO();
				articleVO.setArticleId(articleId);
				articleVO.setArticleContent(articleTitle);
				articleVO.setArticleContent(articleContent);
				articleVO.setArticleDate(articleDate);
				articleVO.setArticleDateChange(articleDateChange);
				articleVO.setArticleImage(articleImage);
				articleVO.setAdminId(adminId);
				articleVO.setMemberId(memberId);
				articleVO.setTourId(tourId);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					request.setAttribute("articleVO", articleVO); // ???????????????????????????empVO??????,?????????req
					RequestDispatcher failureView = request
							.getRequestDispatcher("/article/updateArticle.jsp");
					failureView.forward(request, response);
					return; //????????????
				}
				
				/***************************2.??????????????????*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.updateArticle(articleId, articleTitle,articleContent, articleDateChange,articleImage, adminId,memberId,tourId);
				
				/***************************3.????????????,????????????(Send the Success view)*************/
				request.setAttribute("articleVO", articleVO); // ?????????update?????????,????????????empVO??????,??????req
				String url = "/article/myArticle.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url); // ???????????????,??????listOneEmp.jsp
				successView.forward(request, response);
		}

		
if ("insert".equals(action)) { // ??????addEmp.jsp?????????  
	
		List<String> errorMsgs = new LinkedList<String>();
		request.setAttribute("errorMsgs", errorMsgs);

		String articleTitle = request.getParameter("articleTitle");
		if (articleTitle == null || articleTitle.trim().length() == 0) {
			errorMsgs.add("?????????????????????");
		}	
		
		String articleContent = request.getParameter("articleContent");
		if (articleContent == null || articleContent.trim().length() == 0) {
			errorMsgs.add("?????????????????????");
		}	
		
		Timestamp articleDate = Timestamp.valueOf(request.getParameter("articleDate").trim());
		
		
		
		//??????
		String saveDirectory = "/DB-image";
		String savepath = request.getServletContext().getRealPath(saveDirectory);
		File imfolderPath = new File(savepath);
		if(!imfolderPath.exists()) {
			imfolderPath.mkdirs();
		}
		Part part = request.getPart("articleImage");
		String filename = part.getSubmittedFileName();
		byte[] articleImage = null;
		
		
		
		System.out.println(imfolderPath);
		System.out.println(filename);
		
		if(filename != null && filename.length() != 0 && part.getContentType() != null) {
			String imPath = imfolderPath + "/" + filename;
			part.write(imPath);
			articleImage = getImageByteArray(imPath);
		}
		else {
			errorMsgs.add("???????????????");
		}
		
		Integer adminId = 1;
		Integer memberId = 1;
		Integer tourId = 1;
//		Integer tourId = Integer.valueOf(request.getParameter("tourId").trim());
//		if (tourId == null || tourId == 0) {
//			errorMsgs.add("?????????????????????");
//		}	
		
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticleTitle(articleTitle);
		articleVO.setArticleContent(articleContent);
		articleVO.setArticleDate(articleDate);
		articleVO.setArticleDateChange(null);
		articleVO.setArticleImage(articleImage);
		articleVO.setAdminId(adminId);
		articleVO.setMemberId(memberId);
		articleVO.setTourId(tourId);

		// Send the use back to the form, if there were errors
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("articleVO", articleVO); // ???????????????????????????empVO??????,?????????req
			RequestDispatcher failureView = request
					.getRequestDispatcher("/article/addArticle.jsp");
			failureView.forward(request, response);
			return; //????????????
		}

		
		/***************************2.??????????????????*****************************************/
		ArticleService articleSvc = new ArticleService();
		articleVO = articleSvc.addArticle(articleTitle,articleContent, articleDate, null, articleImage, adminId,memberId,tourId);
		
		/***************************3.????????????,????????????(Send the Success view)*************/
		request.setAttribute("articleVO", articleVO); // ?????????update?????????,????????????empVO??????,??????req
		String url = "/article/allArticle.jsp";
		RequestDispatcher successView = request.getRequestDispatcher(url); // ???????????????,??????listOneEmp.jsp
		successView.forward(request, response);
		}


if ("delete".equals(action)) { // ??????listAllEmp.jsp


		/***************************1.??????????????????***************************************/
		Integer articleId = Integer.valueOf(request.getParameter("articleId"));
		
		/***************************2.??????????????????***************************************/
		ArticleService articleSvc = new ArticleService();
		articleSvc.deleteArticle(articleId);
		
		/***************************3.????????????,????????????(Send the Success view)***********/								
		String url = "/article/myArticle.jsp";
		RequestDispatcher successView = request.getRequestDispatcher(url);// ???????????????,????????????????????????????????????
		successView.forward(request, response);
		}

if("search".equals(action)) {
	String select = request.getParameter("select");
	
	ArticleDAO articleDAO = new ArticleDAO();
	List<ArticleVO> sum = articleDAO.findByWords(select);
	request.setAttribute("Msgs", sum);
	
	RequestDispatcher successView = request.getRequestDispatcher("/article/searchAllArticle.jsp");
	successView.forward(request, response);
	
	
	
}






	}

}
