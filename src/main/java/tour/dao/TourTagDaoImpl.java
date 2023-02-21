package tour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import common.HikariDataSource;
import tour.model.TourTagVO;
import tour.model.TourVO;

public class TourTagDaoImpl implements TourTagDao{

	private static final String INSERT_SQL = "insert into tour_tag (tour_tag_title) values (?);";
	private static final String UPDATE_SQL = "update tour_tag set tour_tag_title=? where tour_id=? and member_id=?;";
	private static final String DELETE_SQL = "delete from tour_tag where tour_tag_id = ?";
	private static final String GET_ALL_SQL = "select tour_tag_title from tour_tag order by tour_tag_id;";
	private static final String GET_ONE_SQL = "select tour_id, tour_title, start_date, end_date, tour_img from tour where tour_id=?;";
	@Override
	public int insert(TourTagVO tourTagVO) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, tourTagVO.getTourTagTitle());
			int insertRow = ps.executeUpdate();
			if (insertRow > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int generatedKey = rs.getInt(1);
					return generatedKey;
				}
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int update(TourTagVO tourTagVO) {
		try (Connection conn = HikariDataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(Integer tourTagId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TourTagVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TourVO findByPrimaryKey(Integer tourTagId) {
		// TODO Auto-generated method stub
		return null;
	}

}
