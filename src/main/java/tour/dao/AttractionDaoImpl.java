package tour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tour.model.AttractionVO;

public class AttractionDaoImpl implements AttractionDao {
    private static DataSource dataSource;

    public AttractionDaoImpl() throws NamingException {
        dataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/example");
    }

    private static final String INSERT_SQL = "insert into attraction (attraction_title, location, introduction, attraction_img, latitude, longitude) values (?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "update attraction set attraction_title=?, location=?, introduction=?, attraction_img=?, latitude=?, longitude=? where attraction_id=?;";
    private static final String DELETE_SQL = "DELETE FROM attraction where attraction_id = ?";
    private static final String GET_ALL_SQL = "select attraction_id, attraction_title, location, introduction, attraction_img, latitude, longitude from attraction order by attraction_id;";
    private static final String GET_ONE_SQL = "select attraction_title, location, introduction, attraction_img, latitude, longitude from attraction where attraction_id=?;";

    @Override
    public int insert(AttractionVO attractionVO) {
        byte[] decodedBytes = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, attractionVO.getAttractionTitle());
            ps.setString(2, attractionVO.getLocation());
            ps.setString(3, attractionVO.getIntroduction());

            decodedBytes = Base64.getMimeDecoder().decode(attractionVO.getAttractionImg());
            ps.setBytes(4, decodedBytes);
            ps.setDouble(5, attractionVO.getLatitude());
            ps.setDouble(6, attractionVO.getLongitude());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int update(AttractionVO attractionVO) {
        byte[] decodedBytes = null;
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, attractionVO.getAttractionTitle());
            ps.setString(2, attractionVO.getLocation());
            ps.setString(3, attractionVO.getIntroduction());

            decodedBytes = Base64.getMimeDecoder().decode(attractionVO.getAttractionImg());
            ps.setBytes(4, decodedBytes);
            ps.setDouble(5, attractionVO.getLatitude());
            ps.setDouble(6, attractionVO.getLongitude());
            ps.setInt(7, attractionVO.getAttractionId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int delete(Integer attractionId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, attractionId);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<AttractionVO> getAll() {
        List<AttractionVO> list = new ArrayList<AttractionVO>();
        AttractionVO attractionVO = null;
        ResultSet rs = null;

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ALL_SQL)) {

            rs = ps.executeQuery();

            while (rs.next()) {
                attractionVO = new AttractionVO();
                attractionVO.setAttractionId(rs.getInt("attraction_id"));
                attractionVO.setAttractionTitle(rs.getString("attraction_title"));
                attractionVO.setLocation(rs.getString("location"));
                attractionVO.setIntroduction(rs.getString("introduction"));
                byte[] imageBytes = rs.getBytes("tour_img");
                if (imageBytes != null) {
                    String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                    attractionVO.setAttractionImg(encodedImage);
                }
                attractionVO.setLatitude(rs.getDouble("latitude"));
                attractionVO.setLongitude(rs.getDouble("longitude"));
                list.add(attractionVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public AttractionVO findByPrimaryKey(Integer attractionId) {
        AttractionVO attractionVO = null;
        ResultSet rs = null;

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_ONE_SQL)) {

            ps.setInt(1, attractionId);

            rs = ps.executeQuery();

            while (rs.next()) {
                attractionVO = new AttractionVO();
                attractionVO.setAttractionId(rs.getInt("attraction_id"));
                attractionVO.setAttractionTitle(rs.getString("attraction_title"));
                attractionVO.setLocation(rs.getString("location"));
                attractionVO.setIntroduction(rs.getString("introduction"));
                byte[] imageBytes = rs.getBytes("tour_img");
                if (imageBytes != null) {
                    String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                    attractionVO.setAttractionImg(encodedImage);
                }
                attractionVO.setLatitude(rs.getDouble("latitude"));
                attractionVO.setLongitude(rs.getDouble("longitude"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attractionVO;

    }
}