package tour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import common.HikariDataSource;
import tour.model.TourComVO;

public class TourComDaoImpl implements TourComDao {
//    private static DataSource dataSource;
//
//    public TourComDaoImpl() throws NamingException {
//        dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/example");
//    }

    private static final String INSERT_SQL = "insert into tour_company (tour_title, start_date, end_date, tour_img, cost, tour_person, company_id) values (?,?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "update tour_company set tour_title=?, start_date=?, end_date=?, tour_img=?, cost=?, tour_person=? where c_tour_id=? and company_id=?;";
    private static final String DELETE_SQL = "delete from tour_company where c_tour_id = ?";
    private static final String GET_ALL_SQL = "select c_tour_id, tour_title, start_date, end_date, tour_img, cost, tour_person, company_id from tour_company order by c_tour_id;";
    private static final String GET_ONE_SQL = "select tour_title, start_date, end_date, tour_img, cost, tour_person, company_id form tour_company where c_tour_id=?;";
    private static final String SQL = "select * from tour_company where";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int insert(TourComVO tourComVO) {
        byte[] decodedBytes = null;
        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, tourComVO.getTourTitle());
            Date StartDate = dateFormat.parse(tourComVO.getStartDate());
            Date endDate = dateFormat.parse(tourComVO.getEndDate());
            ps.setObject(2, StartDate);
            ps.setObject(3, endDate);

            decodedBytes = Base64.getMimeDecoder().decode(tourComVO.getTourImg());
            ps.setBytes(4, decodedBytes);
            ps.setInt(5, tourComVO.getCompanyId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int update(TourComVO tourComVO) {
        byte[] decodedBytes = null;
        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, tourComVO.getTourTitle());
            Date StartDate = dateFormat.parse(tourComVO.getStartDate());
            Date endDate = dateFormat.parse(tourComVO.getEndDate());
            ps.setObject(2, StartDate);
            ps.setObject(3, endDate);

            decodedBytes = Base64.getMimeDecoder().decode(tourComVO.getTourImg());
            ps.setBytes(4, decodedBytes);
            ps.setInt(5, tourComVO.getTourComId());
            ps.setInt(6, tourComVO.getCompanyId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public int delete(Integer tourComId) {
        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, tourComId);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<TourComVO> getAll() {
        List<TourComVO> list = new ArrayList<TourComVO>();
        TourComVO tourComVO = null;
        ResultSet rs = null;

        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {

            rs = ps.executeQuery();

            while (rs.next()) {
                tourComVO = new TourComVO();
                tourComVO.setTourComId(rs.getInt("c_tour_id"));
                tourComVO.setTourTitle(rs.getString("tour_title"));
                tourComVO.setStartDate(String.valueOf(rs.getDate("start_date")));
                tourComVO.setEndDate(String.valueOf(rs.getDate("end_date")));
                tourComVO.setTourImgByte(rs.getBytes("tour_img"));
                tourComVO.setCompanyId(rs.getInt("company_id"));
                list.add(tourComVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public TourComVO findByPrimaryKey(Integer tourComId) {
        TourComVO tourComVO = null;
        ResultSet rs = null;

        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

            ps.setInt(1, tourComId);

            rs = ps.executeQuery();

            while (rs.next()) {
                tourComVO = new TourComVO();
                tourComVO.setTourTitle(rs.getString("tour_title"));
                tourComVO.setStartDate(String.valueOf(rs.getDate("start_date")));
                tourComVO.setEndDate(String.valueOf(rs.getDate("end_date")));
                tourComVO.setTourImgByte(rs.getBytes("tour_img"));
                tourComVO.setCompanyId(rs.getInt("company_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tourComVO;

    }
    
    public int query(TourComVO tourComVO) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select * from user where");
		return 0;
    	
    }
}
