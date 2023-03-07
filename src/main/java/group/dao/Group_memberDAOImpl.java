package group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.DataSource;

import common.HikariDataSource;
import group.model.GroupVO;
import group.model.Group_memberVO;
import member.model.Member;
import tour.model.TourVO;

public class Group_memberDAOImpl implements Group_memberDAO_interface {

	private static DataSource ds = null;

	private static final String INSERT_STMT = "INSERT INTO lazy.group_member (  member_id , group_id , self_intro , special_need , g_m_status) values(?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE lazy.group_member set g_m_status = ? where group_member= ? ";
	private static final String GET_ALL_G_MEMBER_STMT = "SELECT  m.member_id, m.member_username , m.member_name , m.member_img "
			+ "FROM lazy.group_member g JOIN lazy.member m ON g.member_id = m.member_id "
			+ "WHERE g.group_id = ? AND g.g_m_status = 1;";
	private static final String GET_ALL_GROUP_STMT = "select  g.group_name , m.group_id, count(*) "
			+ "from lazy.group_member m join lazy.group g on g.group_id = m.group_id where m.group_id in"
			+ "(select group_id from lazy.group_member where member_id = ?  and g_m_status = 1 ) "
			+ "and m.g_m_status = 1  group by m.group_id ;";

	private static final String GET_ALL_INVITE = "SELECT m.group_member, IFNULL(tour.tour_title, '沒有行程') AS tour_title, g.group_member_count"
			+ ", g.group_name, g.if_join_group_directly ,IFNULL(m.self_intro, '無') as self_intro ,IFNULL(m.special_need, '無') as special_need "
			+ "FROM lazy.group_member m INNER JOIN lazy.group g ON m.group_id = g.group_id "
			+ "LEFT JOIN lazy.tour tour ON g.tour_id = tour.tour_id WHERE m.member_id = ? AND m.g_m_status <> 1;";

	@Override
	public void insertNeedApprove(Group_memberVO groupmemberVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertDirectly(Group_memberVO groupmemberVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Group_memberVO groupmemberVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(List<Integer> list) {
		// TODO Auto-generated method stub
		// 修正中
		String deleteStmt = "DELETE from lazy.group_member where group_id = ? and member_id in(";

		for (int i = 1; i < list.size(); i++) {
			if (i != list.size() - 1) {

				deleteStmt += list.get(i);
				deleteStmt += " , ";
			} else {
				deleteStmt += list.get(i);
			}
		}
		deleteStmt += ")";

		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(deleteStmt)) {

			pstmt.setInt(1, list.get(0));
//			for(int i = 1; i < list.size(); i++) {
//				pstmt.setInt(i+1,list.get(i));
//			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Group_memberVO> getAllInvite(Integer memberid) {
		// TODO Auto-generated method stub
		List list = new ArrayList<Group_memberVO>();
		Group_memberVO groupMemberVO = new Group_memberVO();
		GroupVO groupVO = new GroupVO();
		TourVO tourVO = new TourVO();
		ResultSet rs = null;
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_INVITE)) {

			pstmt.setInt(1, memberid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupMemberVO = new Group_memberVO();
				groupVO = new GroupVO();
				tourVO = new TourVO();

				groupMemberVO.setGroupmember(rs.getInt("group_member"));
				groupMemberVO.setSelfintro("self_intro");
				groupMemberVO.setSpecialneed("special_need");
				groupVO.setGroupmembercount(rs.getInt("group_member_count"));
				groupVO.setGroupname(rs.getString("group_name"));
				groupVO.setIfjoingroupdirectly(rs.getInt("if_join_group_directly"));
				tourVO.setTourTitle(rs.getString("tour_title"));
				groupVO.setTourVO(tourVO);
				groupMemberVO.setGroupvo(groupVO);
				list.add(groupMemberVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

//	@Override
//	public List<Group_memberVO> getAllDeleted(Integer groupid) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<GroupVO> getAllGroupByMemberid(Integer memberid) {
		// TODO Auto-generated method stub

		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = new GroupVO();
		ResultSet rs = null;

		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_GROUP_STMT)) {
			pstmt.setInt(1, memberid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGroupid(rs.getInt("m.group_id"));
//				groupVO.setTourid(rs.getInt("tour_id"));
				groupVO.setGroupmembercount(rs.getInt("count(*)"));
				groupVO.setGroupname(rs.getString("g.group_name"));
//				groupVO.setMemberid(rs.getInt("member_id"));
				list.add(groupVO);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Member> getAllMember(Integer groupid) {
		// TODO Auto-generated method stub
		List<Member> list = new ArrayList<Member>();
		Member member = new Member();
		ResultSet rs = null;
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_G_MEMBER_STMT)) {

			pstmt.setInt(1, groupid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new Member();

				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if (img != null && img.length != 0) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
//				member.setImg(rs.getBytes("member_img"));

				member.setId(rs.getInt("member_id"));
				member.setUsername(rs.getString("member_username"));
				member.setName(rs.getString("member_name"));
				list.add(member);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void inviteFriendtoGroup(List<Integer> list) {
		// TODO Auto-generated method stub
		String insertStmt = "INSERT INTO lazy.group_member (group_id , member_id , g_m_status) VALUES ";

		int groupId = list.get(0);
		int status = 3;

		for (int i = 1; i < list.size(); i++) {
			if (i != list.size() - 1) {

				insertStmt += "(" + groupId + " , " + list.get(i) + " , " + status + " ), ";

			} else {
				insertStmt += "(" + groupId + " , " + list.get(i) + " , " + status + " ) ";
			}
		}
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(insertStmt)) {

			System.out.println(insertStmt);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static final String GET_ALL_MEMS_STMT = "SELECT g.* , m.member_img ,m.member_username ,m.member_name "
			+ "FROM lazy.group_member g JOIN lazy.member m ON m.member_id = g.member_id " + "WHERE g.group_id =?;";

	@Override
	public List<Group_memberVO> getMemList(Integer groupid) {
		// TODO Auto-generated method stub
		List list = new ArrayList<Integer>();
		ResultSet rs = null;
		Group_memberVO mem = new Group_memberVO();
		Member member = new Member();
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_MEMS_STMT)) {

			pstmt.setInt(1, groupid);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				mem = new Group_memberVO();
				member = new Member();
				mem.setMemberid(rs.getInt("member_id"));
				mem.setGmstatus(rs.getInt("g_m_status"));

				member.setUsername(rs.getString("member_username"));
				member.setName(rs.getString("member_name"));
				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if (img != null && img.length != 0) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
				mem.setMember(member);
				list.add(mem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
