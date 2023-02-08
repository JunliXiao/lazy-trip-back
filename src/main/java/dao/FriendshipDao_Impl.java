package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Friendship;

public class FriendshipDao_Impl implements FriendshipDao<Friendship>{
	
	DataSource dataSource;
	
	public FriendshipDao_Impl() {
		dataSource = ServiceLocator.getInstance().getDataSource();
	}

	@Override
	public List<Friendship> getByRequester(Integer requester_id) {
		List<Friendship> friendships = new ArrayList<>();
		String sql = "SELECT friendship_id, requester_id, addressee_id, status, created_at FROM friendship WHERE requester_id = ?;";
		
		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, requester_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer fr_id = rs.getInt(1);
				Integer rq_id = rs.getInt(2);
				Integer ad_id = rs.getInt(3);
				String status = rs.getString(4);
				Timestamp timestamp = rs.getTimestamp(5);
				Friendship freindship = new Friendship(fr_id, rq_id, ad_id, status, timestamp);
				friendships.add(freindship);
			}
			return friendships;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return friendships;
	}

}
