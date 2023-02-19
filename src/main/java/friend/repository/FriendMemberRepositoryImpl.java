package friend.repository;

import common.HikariDataSource;
import member.vo.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendMemberRepositoryImpl implements FriendMemberRepository {
    @Override
    public List<Member> getFriendMembers(Integer memberId, String statusCode) {
        List<Member> friends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id FROM friendship WHERE status_code = ? AND requester_id = ?\r
                	UNION\r
                SELECT requester_id FROM friendship WHERE status_code = ? AND addressee_id = ?);
                """;
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, statusCode);
            ps.setInt(2, memberId);
            ps.setString(3, statusCode);
            ps.setInt(4, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member friend = new Member();
                friend.setId(Integer.parseInt(rs.getString("member_id")));
                friend.setAccount(rs.getString("member_account"));
                friend.setName(rs.getString("member_name"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }
}
