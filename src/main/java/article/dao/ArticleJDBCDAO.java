package com.article.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleJDBCDAO implements ArticleDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/lazytrip?serverTimezone=Asia/Taipei";
	String username = "root";
	String password = "password";
	
	private static final String INSERT_STMT =
			"insert into article(articleContent,articleDate,status,memberID,tourID) values(?,?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE article set articleContent=?, articleDate=?, status=? where articleId = ?";
	private static final String DELETE = 
			"DELETE FROM article where articleId = ?";
	private static final String GET_ONE_STMT = 
			"SELECT articleId,articleContent,articleDate,status,adminId,memberId,companyId,tourId FROM article where articleId = ?";
	private static final String GET_ALL_STMT = 
			"SELECT articleId,articleContent,articleDate,status,adminId,memberId,companyId,tourId FROM article order by articleId";
	
	@Override
	public void insert(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, articleVO.getArticleContent());
			pstmt.setTimestamp(2, articleVO.getArticleDate());
			pstmt.setString(3, articleVO.getStatus());
//			pstmt.setInt(4, articleVO.getAdminId());
			pstmt.setInt(4, articleVO.getMemberId());
//			pstmt.setInt(4, articleVO.getCompanyId());
			pstmt.setInt(5, articleVO.getTourId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, articleVO.getArticleContent());
			pstmt.setTimestamp(2, articleVO.getArticleDate());
			pstmt.setString(3, articleVO.getStatus());
//			pstmt.setInt(4, articleVO.getAdminId());
//			pstmt.setInt(5, articleVO.getTourId());
			pstmt.setInt(4, articleVO.getArticleId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(Integer articleId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, articleId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public ArticleVO findByPrimaryKey(Integer articleId) {
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, articleId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("articleId"));
				articleVO.setArticleContent(rs.getString("articleContent"));
				articleVO.setArticleDate(rs.getTimestamp("articleDate"));
				articleVO.setStatus(rs.getString("status"));
				articleVO.setAdminId(rs.getInt("adminId"));
				articleVO.setMemberId(rs.getInt("memberId"));
				articleVO.setCompanyId(rs.getInt("companyId"));
				articleVO.setTourId(rs.getInt("tourId"));
				articleVO.setArticleId(rs.getInt("articleId"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return articleVO;
	}

	@Override
	public List<ArticleVO> getAll() {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("articleId"));
				articleVO.setArticleContent(rs.getString("articleContent"));
				articleVO.setArticleDate(rs.getTimestamp("articleDate"));
				articleVO.setStatus(rs.getString("status"));
				articleVO.setAdminId(rs.getInt("adminId"));
				articleVO.setMemberId(rs.getInt("memberId"));
				articleVO.setCompanyId(rs.getInt("companyId"));
				articleVO.setTourId(rs.getInt("tourId"));
				articleVO.setArticleId(rs.getInt("articleId"));
				list.add(articleVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	public static void main(String[] args) {

		ArticleJDBCDAO dao = new ArticleJDBCDAO();
		// 新增
//		ArticleVO articleVO1 = new ArticleVO();
//		articleVO1.setArticleContent("JDBC基本測試3");
//		articleVO1.setArticleDate(java.sql.Timestamp.valueOf("2023-02-22 14:35:30"));
//		articleVO1.setStatus("T");
////		articleVO1.setAdminId(new Integer(1));
//		articleVO1.setMemberId(new Integer(1));
////		articleVO1.setCompanyId(new Integer(1));
//		articleVO1.setTourId(new Integer(2));
//		dao.insert(articleVO1);
//		System.out.println("新增成功");
		
		
		// 修改
//		ArticleVO articleVO2 = new ArticleVO();
//		articleVO2.setArticleContent("測試修改JDBC");
//		articleVO2.setArticleDate(java.sql.Timestamp.valueOf("2002-01-01 22:22:22"));
//		articleVO2.setStatus("F");
////		articleVO2.setAdminId(2000);
////		articleVO2.setTourId(new Integer(2000));
//		articleVO2.setArticleId(new Integer(2));
//		dao.update(articleVO2);
//		System.out.println("修改成功");
		
		// 刪除
//		dao.delete(3);
//		System.out.println("刪除成功");
		
		// 查詢
//		ArticleVO articleVO3 = dao.findByPrimaryKey(1);
//		System.out.print(articleVO3.getAdminId() + ",");
//		System.out.print(articleVO3.getArticleContent() + ",");
//		System.out.print(articleVO3.getArticleDate() + ",");
//		System.out.print(articleVO3.getStatus() + ",");
//		System.out.print(articleVO3.getAdminId() + ",");
//		System.out.print(articleVO3.getMemberId() + ",");
//		System.out.print(articleVO3.getCompanyId() + ",");
//		System.out.print(articleVO3.getTourId() + ",");
//		System.out.println(articleVO3.getAdminId());
//		System.out.println("單一查詢成功");
		
		// 查詢多筆
		List<ArticleVO> list = dao.getAll();
		for (ArticleVO articleVO4 : list) {
			System.out.print("ArticleId:" + articleVO4.getArticleId() + ",");
			System.out.print("ArticleContent:" + articleVO4.getArticleContent() + ",");
			System.out.print("ArticleDate:" + articleVO4.getArticleDate() + ",");
			System.out.print("Status:" + articleVO4.getStatus() + ",");
			System.out.print("AdminId:" + articleVO4.getAdminId() + ",");
			System.out.print("MemberId:" + articleVO4.getMemberId() + ",");
			System.out.print("CompanyId:" + articleVO4.getCompanyId() + ",");
			System.out.print("TourId:" + articleVO4.getTourId());
			System.out.println("");
//			System.out.println(articleVO4.getAdminId());
		}
		System.out.println("多筆查詢成功");
		
		
		
	}

}
