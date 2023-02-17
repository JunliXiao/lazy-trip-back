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

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import common.HikariDataSource;
import tour.model.TourVO;

public class TourDaoImpl implements TourDao {
	private static DataSource dataSource;
	public TourDaoImpl() throws NamingException {
		dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/example");
	}


    private static final String INSERT_SQL = "insert into tour (tour_title, start_date, end_date, tour_img, member_id) values (?,?,?,?,?);";
    private static final String UPDATE_SQL = "update tour set tour_title=?, start_date=?, end_date=?, tour_img=? where tour_id=? and member_id=?;";
    private static final String DELETE_SQL = "DELETE FROM tour where tour_id = ?";
    private static final String GET_ALL_SQL = "select tour_id, tour_title, start_date, end_date, tour_img, member_id from tour order by tour_id;";
    private static final String GET_ONE_SQL = "select tour_id, tour_title, start_date, end_date, tour_img, member_id from tour where tour_id=?;";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int insert(TourVO tourVO) {
        byte[] decodedBytes = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, tourVO.getTourTitle());
            Date StartDate = dateFormat.parse(tourVO.getStartDate());
            Date endDate = dateFormat.parse(tourVO.getEndDate());
            ps.setObject(2, StartDate);
            ps.setObject(3, endDate);

            decodedBytes = Base64.getMimeDecoder().decode(tourVO.getTourImg());
            ps.setBytes(4, decodedBytes);
            ps.setInt(5, tourVO.getMemberId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int update(TourVO tourVO) {
        byte[] decodedBytes = null;
        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, tourVO.getTourTitle());
            Date StartDate = dateFormat.parse(tourVO.getStartDate());
            Date endDate = dateFormat.parse(tourVO.getEndDate());
            ps.setObject(2, StartDate);
            ps.setObject(3, endDate);

            decodedBytes = Base64.getMimeDecoder().decode(tourVO.getTourImg());
            ps.setBytes(4, decodedBytes);
            ps.setInt(5, tourVO.getTourId());
            ps.setInt(6, tourVO.getMemberId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public int delete(Integer tourId) {
        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, tourId);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<TourVO> getAll() {
        List<TourVO> list = new ArrayList<TourVO>();
        TourVO tourVO = null;
        ResultSet rs = null;

        try (Connection conn = HikariDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {

            rs = ps.executeQuery();

            while (rs.next()) {
                tourVO = new TourVO();
                tourVO.setTourId(rs.getInt("tour_id"));
                tourVO.setTourTitle(rs.getString("tour_title"));
                tourVO.setStartDate(String.valueOf(rs.getDate("start_date")));
                tourVO.setEndDate(String.valueOf(rs.getDate("end_date")));
                byte[] imageBytes = rs.getBytes("tour_img");
                if (imageBytes != null) {
                    String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                    tourVO.setTourImg(encodedImage);
                }
                tourVO.setMemberId(rs.getInt("member_id"));
                list.add(tourVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public TourVO findByPrimaryKey(Integer tourId) {
        TourVO tourVO = null;
        ResultSet rs = null;

        try (Connection conn = HikariDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

            ps.setInt(1, tourId);

            rs = ps.executeQuery();

            while (rs.next()) {
                tourVO = new TourVO();
                tourVO.setTourId(rs.getInt("tour_id"));
                tourVO.setTourTitle(rs.getString("tour_title"));
                tourVO.setStartDate(String.valueOf(rs.getDate("start_date")));
                tourVO.setEndDate(String.valueOf(rs.getDate("end_date")));
                byte[] imageBytes = rs.getBytes("tour_img");
                if (imageBytes != null) {
                    String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                    tourVO.setTourImg(encodedImage);
                }
                tourVO.setMemberId(rs.getInt("member_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tourVO;

    }
}