package group.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import common.HikariDataSource;
import group.model.GroupVO;
import group.model.Group_memberVO;

public class Group_memberDAO implements Group_memberDAO_interface {
	
	private static DataSource ds = null;
	
	private static final String INSERT_STMT = "INSERT INTO lazy.group_member (  member_id , group_id , self_intro , special_need , g_m_status) values(?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE lazy.group_member set g_m_status = ? where group_member= ? ";
	private static final String DELETE_STMT = "DELETE from lazy.group_member where group_id = ? and member_id = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM lazy.group_member where group_id = ? and g_m_status = ?";
	private static final String GET_ALL_GROUP_STMT = "SELECT g.group_name , g.group_member_count , g.group_id FROM lazy.group_member m right join lazy.group g on g.group_id =m.group_id "
			+ "where m.member_id = ?";
	
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
	public void delete(Integer groupmember) {
		// TODO Auto-generated method stub
		
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
				groupVO.setGroupid(rs.getInt("g.group_id"));
//				groupVO.setTourid(rs.getInt("tour_id"));
				groupVO.setGroupmembercount(rs.getInt("g.group_member_count"));
				groupVO.setGroupname(rs.getString("g.group_name"));
//				groupVO.setMemberid(rs.getInt("member_id"));
				list.add(groupVO);

			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
