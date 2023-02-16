package member.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.dao.MemberDao_interface;
import member.vo.Member;

public class MemberDaoImpl implements MemberDao_interface{
	private DataSource ds;

	public MemberDaoImpl() throws NamingException {
		ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/lazy");
	}
	
	private static final String INSERT = "insert into member(member_account, member_password, member_username, member_gender, member_birth, member_accessnum) values(?, ?, ?, ?, ?, '1')";
	@Override
	public int insert(Member member) {
		int generatedKey = -1;
		try(
				Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
		){
			pstmt.setString(1, member.getAccount());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getUsername());
			pstmt.setString(4, member.getGender());
			pstmt.setDate(5, member.getBirthday());
			pstmt.executeUpdate();
			
			try (ResultSet rs = pstmt.getGeneratedKeys()){
				if(rs.next()) {
					generatedKey = rs.getInt(1);
					System.out.println("GeneratedKey: " + generatedKey);
				}
			}
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return generatedKey;
	}

	@Override
	public int updateById(Member member) {
		StringBuilder sql = new StringBuilder("update member set member_id= ?");
		final String ps = member.getPassword();
		final String un = member.getUsername();
		final String gender = member.getGender();
		final Date birth = member.getBirthday();
		if(ps != null && !ps.isEmpty()) {
			sql.append(", member_password=?");
		}
		if(un != null && !un.isEmpty()) {
			sql.append(", member_username=?");
		}
		if(gender != null && !gender.isEmpty()) {
			sql.append(", member_gender=?");
		}
		if(birth != null ) {
			sql.append(", member_birthday=?");
		}
		sql.append(" where member_id = ?");
		
		try (	Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, member.getId());	
			int nxtSeq = 2;
			if(ps != null && !ps.isEmpty()) {
				pstmt.setString(nxtSeq++, ps);
//				nxtSeq++;
			}
			if(un != null && !un.isEmpty()) {
				pstmt.setString(nxtSeq++, un);
			}
			if(gender != null && !gender.isEmpty()) {
				pstmt.setString(nxtSeq++, gender);
			}
			if(birth != null ) {
				pstmt.setString(nxtSeq++, un);
			}
			pstmt.setInt(nxtSeq, member.getId());
			System.out.println(sql);
			return pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static final String LOGIN = "select * from member where member_account = ? and member_password = ?";
	@Override
	public Member find(Member member) {
		try (
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(LOGIN);
		){
			pstmt.setString(1, member.getAccount());
			pstmt.setString(2, member.getPassword());
			try (ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					member.setId(rs.getInt("member_id"));
					member.setAccount(rs.getString("member_account"));
					member.setPassword(rs.getString("member_password"));
					member.setName(rs.getString("member_name"));
					member.setGender(rs.getString("member_gender"));
					member.setUsername(rs.getString("member_username"));
					member.setPhone(rs.getString("member_phone"));;
					member.setBirthday(rs.getDate("member_birth"));
					member.setReg_date(rs.getTimestamp("member_reg_date"));
					member.setAddress(rs.getString("member_address"));
					member.setIntro(rs.getString("member_intro"));
					member.setImg(rs.getBytes("member_img"));
					member.setBanner_img(rs.getBytes("member_banner_img"));
					member.setAccessnum(rs.getString("member_accessnum"));
					return member;
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	final static String UPDATE_IMG_BYID = "update member set img = ? where member_id = ?;";
	@Override
	public int updateImgById(Member member) {
		try (	Connection conn = ds.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_IMG_BYID);
		) {
			pstmt.setBytes(1, member.getImg());
			pstmt.setInt(2, member.getId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public byte[] selectAvatarById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final String SELECT_ONE = "select * from member where member_id = ?";
	@Override
	public Member selectById(Integer id) {
		try(	Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ONE);
		) {
			pstmt.setInt(1, id);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Member member = new Member();
					member.setId(rs.getInt("member_id"));
					member.setAccount(rs.getString("member_account"));
					member.setPassword(rs.getString("member_password"));
					member.setName(rs.getString("member_name"));
					member.setGender(rs.getString("member_gender"));
					member.setUsername(rs.getString("member_username"));
					member.setPhone(rs.getString("member_phone"));;
					member.setBirthday(rs.getDate("member_birth"));
					member.setReg_date(rs.getTimestamp("member_reg_date"));
					member.setAddress(rs.getString("member_address"));
					member.setIntro(rs.getString("member_intro"));
					member.setImg(rs.getBytes("member_img"));
					member.setBanner_img(rs.getBytes("member_banner_img"));
					member.setAccessnum(rs.getString("member_accessnum"));
					
					return member;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	final static String GET_ALL_STMT = "select * from member order by member_id";
	@Override
	public List<Member> getAll() {
		List<Member> resultList = new ArrayList<Member>();
		Member member;
		try( 	Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
				ResultSet rs = pstmt.executeQuery();){
			while(rs.next()) {
				member = new Member();

				member.setImg(rs.getBytes("member_img"));
				final byte[] img = member.getImg();
				if(img !=null && img.length !=0 ) {
					member.setImgBase64Str(Base64.getEncoder().encodeToString(img));
					member.setImg(null);
				}
				
				member.setId(rs.getInt("member_id"));
				member.setAccount(rs.getString("member_account"));
				member.setPassword(rs.getString("member_password"));
				member.setName(rs.getString("member_name"));
				member.setGender(rs.getString("member_gender"));
				member.setUsername(rs.getString("member_username"));
				member.setPhone(rs.getString("member_phone"));;
				member.setBirthday(rs.getDate("member_birth"));
				member.setReg_date(rs.getTimestamp("member_reg_date"));
				member.setAddress(rs.getString("member_address"));
				member.setIntro(rs.getString("member_intro"));
				member.setBanner_img(rs.getBytes("member_banner_img"));
				member.setAccessnum(rs.getString("member_accessnum"));
				resultList.add(member);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}


}
