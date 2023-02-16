package tour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tour.model.TourScheduleVO;

public class TourScheduleDaoImpl implements TourScheduleDao {
    private static DataSource dataSource;

    public TourScheduleDaoImpl() throws NamingException {
        dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/example");
    }

    private static final String INSERT_SQL = "insert into tour_schedule (date, start_time, stay_time, memorandum, alias, tour_id) values (?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "update tour_schedule set date=?, start_time=?, stay_time=?, memorandum=?, alias=? where tour_schedule_id=? and tour_id=?;";
    private static final String DELETE_SQL = "delete from tour_schedule where tour_schedule_id = ?";
    private static final String GET_ALL_SQL = "select tour_schedule_id, tour_id, date, start_time, stay_time, memorandum, alias from tour_schedule order by tour_schedule_id;";
    private static final String GET_ONE_SQL = "select tour_id, date, start_time, stay_time, memorandum, alias from tour_schedule where tour_schedule_id=?;";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Override
    public int insert(TourScheduleVO tourScheduleVO) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            Date Date = dateFormat.parse(tourScheduleVO.getDate());
            Time startTime = new Time(timeFormat.parse(tourScheduleVO.getStartTime()).getTime());

            ps.setObject(1, Date);
            ps.setObject(2, startTime);
            ps.setInt(3, tourScheduleVO.getStayTime());
            ps.setString(4, tourScheduleVO.getMemorandum());
            ps.setString(5, tourScheduleVO.getAlias());
            ps.setInt(6, tourScheduleVO.getTourId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int update(TourScheduleVO tourScheduleVO) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            Date Date = dateFormat.parse(tourScheduleVO.getDate());
            Time startTime = new Time(timeFormat.parse(tourScheduleVO.getStartTime()).getTime());
            ps.setObject(1, Date);
            ps.setObject(2, startTime);
            ps.setObject(3, tourScheduleVO.getStayTime());
            ps.setString(4, tourScheduleVO.getMemorandum());
            ps.setString(5, tourScheduleVO.getAlias());
            ps.setInt(6, tourScheduleVO.getTourScheduleId());
            ps.setInt(7, tourScheduleVO.getTourId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public int delete(Integer tourScheduleId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, tourScheduleId);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<TourScheduleVO> getAll() {
        List<TourScheduleVO> list = new ArrayList<TourScheduleVO>();
        TourScheduleVO tourScheduleVO = null;
        ResultSet rs = null;

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {

            rs = ps.executeQuery();
            while (rs.next()) {
                tourScheduleVO = new TourScheduleVO();
                tourScheduleVO.setTourScheduleId(rs.getInt("tour_schedule_id"));
                tourScheduleVO.setTourId(rs.getInt("tour_id"));
                tourScheduleVO.setDate(String.valueOf(rs.getDate("date")));
                String strTime = timeFormat.format(rs.getTime("start_time"));
                tourScheduleVO.setStartTime(strTime);
                tourScheduleVO.setStayTime(rs.getInt("stay_time"));
                tourScheduleVO.setMemorandum(rs.getString("memorandum"));
                tourScheduleVO.setAlias(rs.getString("alias"));
                list.add(tourScheduleVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public TourScheduleVO findByPrimaryKey(Integer tourScheduleId) {
        TourScheduleVO tourScheduleVO = null;
        ResultSet rs = null;

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {
            rs = ps.executeQuery();
            while (rs.next()) {
                tourScheduleVO = new TourScheduleVO();
                tourScheduleVO.setTourId(rs.getInt("tour_id"));
                tourScheduleVO.setDate(String.valueOf(rs.getDate("date")));
                String strTime = timeFormat.format(rs.getTime("start_time"));
                tourScheduleVO.setStartTime(strTime);
                tourScheduleVO.setStayTime(rs.getInt("stay_time"));
                tourScheduleVO.setMemorandum(rs.getString("memorandum"));
                tourScheduleVO.setAlias(rs.getString("alias"));
                tourScheduleVO.setTourScheduleId(rs.getInt("tour_schedule_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tourScheduleVO;
    }


}
