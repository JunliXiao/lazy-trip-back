package group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.DataSource;

import common.HikariDataSource;
import group.model.GroupVO;
import group.model.Group_memberVO;
import member.model.Member;

public class Group_memberDAOImpl implements Group_memberDAO_interface {
	
	private static DataSource ds = null;
	
	private static final String INSERT_STMT = "INSERT INTO lazy.group_member (  member_id , group_id , self_intro , special_need , g_m_status) values(?,?,?,?,?)";
	private static final String INSERT_WHEN_CREATE_STMT ="INSERT INTO lazy.group_member (member_id , group_id , g_m_status ) values(?,?,?)";
	private static final String UPDATE_STMT = "UPDATE lazy.group_member set g_m_status = ? where group_member= ? ";
	private static final String GET_ALL_G_MEMBER_STMT = "SELECT m.member_id, m.member_username , m.member_name , m.member_img "
			+ "FROM lazy.group_member g JOIN lazy.member m ON g.member_id = m.member_id "
			+ "WHERE g.group_id = ? AND g.g_m_status = 1;";
	private static final String GET_ALL_GROUP_STMT = "select g.group_name , m.group_id, count(*) "
			+ "from group_member m join lazy.group g on g.group_id = m.group_id "
			+ "where m.group_id in(select group_id from lazy.group_member where member_id = ? ) and m.g_m_status =1 "
			+ "group by m.group_id ";
	
	@Override
	public void insertNeedApprove(Group_memberVO groupmemberVO) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void insertDirectly(Group_memberVO groupmemberVO) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void insertWhenCreate(Group_memberVO groupmemberVO) {
		// 創辦人創群組時使用此方法
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_WHEN_CREATE_STMT)) {
			pstmt.setInt(1, groupmemberVO.getMemberid());
			pstmt.setInt(2, groupmemberVO.getGroupid());
			pstmt.setInt(3, 1);
			
			pstmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
		}

	
	}

	@Override
	public void update(Group_memberVO groupmemberVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(List<Integer> list) {
		// TODO Auto-generated method stub
		//修正中
		String DELETE_STMT = "DELETE from lazy.group_member where group_id = ? and member_id in(";

		for (int i = 1; i < list.size(); i++) {
			if (i != list.size() - 1) {

				DELETE_STMT += list.get(i);
				DELETE_STMT += " , ";
			} else {
				DELETE_STMT += list.get(i);
			}
		}
		DELETE_STMT += ")";
		
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_STMT)) {
			
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
	public List<Group_memberVO> getAlltoAccept(Integer groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group_memberVO> getAllDeleted(Integer groupid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupVO> getAllGroupByMemberid(Integer memberid) {
		// TODO Auto-generated method stub
		
		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = new GroupVO();
		ResultSet rs = null;
		
		try (Connection connection = HikariDataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_GROUP_STMT)){
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
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_G_MEMBER_STMT)){
			
			pstmt.setInt(1, groupid);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new Member();
				
				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if(img !=null && img.length !=0 ) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
//				member.setImg(rs.getBytes("member_img"));
				
				member.setId(rs.getInt("member_id"));
				member.setUsername(rs.getString("member_username"));
				member.setName(rs.getString("member_name"));
				list.add(member);

			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}




}
