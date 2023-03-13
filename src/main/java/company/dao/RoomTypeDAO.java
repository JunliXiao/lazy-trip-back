package company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.HikariDataSource;
import company.model.CompanyVO;
import company.model.RoomTypeImgVO;
import company.model.RoomTypeVO;


public class RoomTypeDAO implements RoomTypeDAO_interface {
	private static final String INSERT_STMT = """
	INSERT INTO roomtype (company_id,roomtype_name,roomtype_person,roomtype_quantity,roomtype_price) 
	VALUES ( ?, ?, ?, ?, ?)
	""";
	private static final String GET_ALL_STMT = """
	SELECT roomtype_id as roomTypeID,company_id as companyID,roomtype_name as roomTypeName, 
	roomtype_person as roomTypePerson,roomtype_quantity as roomTypeQuantity,roomtype_price as roomTypePrice 
	FROM roomtype order by roomtype_id;
	""";
	private static final String GET_ONE_STMT = """
	SELECT roomtype_id as roomTypeID,company_id as companyID,roomtype_name as roomTypeName, 
	roomtype_person as roomTypePerson,roomtype_quantity as roomTypeQuantity,roomtype_price as roomTypePrice FROM roomtype 
	where roomtype_id = ?;
	""";
	private static final String DELETE = "DELETE FROM roomtype where roomtype_id = ?";
	private static final String UPDATE = """
	UPDATE roomtype set roomtype_name=?,roomtype_person=?,
	roomtype_quantity=?,roomtype_price=? where roomtype_id = ?
	""";
	private static final String GET_ALL_BY_COMPANYID = """
			SELECT a.roomtype_id as aRoomTypeID,company_id as companyID,roomtype_name as roomTypeName, 
			roomtype_person as roomTypePerson,roomtype_quantity as roomTypeQuantity,roomtype_price as roomTypePrice,
			roomtype_img_id as roomTypeImgID,b.roomtype_id as bRoomTypeID,roomtype_img as roomTypeImg 
			FROM roomtype a left join roomtype_img b on a.roomtype_id = b.roomtype_id where company_id = ? order by a.roomtype_id;
			""";
	
	
	@Override
	public void insert(RoomTypeVO roomTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, roomTypeVO.getCompanyID());
			pstmt.setString(2, roomTypeVO.getRoomTypeName());
			pstmt.setInt(3, roomTypeVO.getRoomTypePerson());
			pstmt.setInt(4, roomTypeVO.getRoomTypeQuantity());
			pstmt.setInt(5, roomTypeVO.getRoomTypePrice());
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(RoomTypeVO roomTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, roomTypeVO.getRoomTypeName());
			pstmt.setInt(2, roomTypeVO.getRoomTypeQuantity());
			pstmt.setInt(3, roomTypeVO.getRoomTypePerson());
			pstmt.setInt(4, roomTypeVO.getRoomTypePrice());
			pstmt.setInt(5, roomTypeVO.getRoomTypeID());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(Integer roomTypeID) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, roomTypeID);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public RoomTypeVO findByPrimaryKey(Integer roomTypeID) {

		RoomTypeVO roomTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);//取得sql語法
			

			pstmt.setInt(1, roomTypeID); //把問號取代現在的key

			rs = pstmt.executeQuery();//執行sql語法

			while (rs.next()) {
				// roomtypeVO 也稱為 Domain objects

				roomTypeVO.setRoomTypeID(rs.getInt("roomTypeID"));
				roomTypeVO.setCompanyID(rs.getInt("companyID"));
				roomTypeVO.setRoomTypePerson(rs.getInt("roomTypePerson"));
				roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
				roomTypeVO.setRoomTypeQuantity(rs.getInt("roomTypeQuantity"));
				roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
				

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return roomTypeVO;
	}

	@Override
	public List<RoomTypeVO> getAll() {
		List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
		RoomTypeVO roomTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EquipmentVO 也稱為 Domain objects

				roomTypeVO.setRoomTypeID(rs.getInt("roomTypeID"));
				roomTypeVO.setCompanyID(rs.getInt("companyID"));
				roomTypeVO.setRoomTypePerson(rs.getInt("roomTypePerson"));
				roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
				roomTypeVO.setRoomTypeQuantity(rs.getInt("roomTypeQuantity"));
				roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));

				list.add(roomTypeVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	
	
	@Override
	public List<RoomTypeVO> getAllByCompanyID(Integer companyID) {
		List<RoomTypeVO> list = new ArrayList<RoomTypeVO>();
//		RoomTypeVO roomTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_COMPANYID);
			pstmt.setInt(1, companyID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// EquipmentVO 也稱為 Domain objects

				RoomTypeVO roomTypeVO = new RoomTypeVO();
				roomTypeVO.setRoomTypeID(rs.getInt("aRoomTypeID"));
				roomTypeVO.setCompanyID(rs.getInt("companyID"));
				roomTypeVO.setRoomTypePerson(rs.getInt("roomTypePerson"));
				roomTypeVO.setRoomTypeName(rs.getString("roomTypeName"));
				roomTypeVO.setRoomTypeQuantity(rs.getInt("roomTypeQuantity"));
				roomTypeVO.setRoomTypePrice(rs.getInt("roomTypePrice"));
				
				RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();
				roomTypeImgVO.setRoomTypeImgID(rs.getInt("roomTypeImgID"));
				roomTypeImgVO.setRoomTypeID(rs.getInt("bRoomTypeID"));
				roomTypeImgVO.setRoomTypeImg(rs.getString("roomTypeImg"));
				roomTypeVO.setRoomTypeImgVO(roomTypeImgVO);

				list.add(roomTypeVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	
}
