package friend.repository;

import common.HikariDataSource;
import friend.model.Chatroom;
import member.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatroomMemberRepositoryImpl implements ChatroomMemberRepository {

    @Override
    public boolean addChatroom(List<Integer> membersId) {
        boolean hasAdded = false;
        String sqlInsertChatroom = "INSERT INTO `chatroom` (`chatroom_name`) VALUES (' ');";
        String sqlInsertChatroomMember = "INSERT INTO `chatroom_member` (`chatroom_id`, `member_id`) VALUES (?, ?);";
        String[] generatedColumns = {"chatroom_id"};
        int chatroomId;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(sqlInsertChatroom, generatedColumns);
             PreparedStatement ps2 = connection.prepareStatement(sqlInsertChatroomMember)) {
            hasAdded = ps1.executeUpdate() != 0;
            ResultSet rs = ps1.getGeneratedKeys();

            if (rs.next()) {
                chatroomId = rs.getInt(1);
                ps2.setInt(1, chatroomId);
            } else {
                throw new RuntimeException("Failed to create a chatroom");
            }

            for (Integer m : membersId) {
                ps2.setInt(2, m);
                ps2.addBatch();
            }

            int[] insertResults = ps2.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasAdded;
    }

    @Override
    public boolean addMembersToChatroom(List<Integer> membersId, Integer chatroomId) {
        boolean hasAdded = false;
        String sql = "INSERT INTO `chatroom_member` (`chatroom_id`, `member_id`) VALUES (?, ?);";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);

            for (Integer m : membersId) {
                ps.setInt(2, m);
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean deleteMemberFromChatroom(Integer memberId, Integer chatroomId) {
        boolean hasDeleted = false;
        String sql = "DELETE FROM `chatroom_member` WHERE chatroom_id = ? and member_id = ?;";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            ps.setInt(2, memberId);
            hasDeleted = ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasDeleted;
    }

    @Override
    public List<Chatroom> getChatrooms(Integer memberId) {
        List<Chatroom> chatrooms = new ArrayList<>();
        String sql = """
                SELECT ch.chatroom_id, ch.chatroom_name, UNIX_TIMESTAMP(ch.created_at) created_at_unix FROM chatroom_member cm
                \tJOIN chatroom ch ON ch.chatroom_id = cm.chatroom_id
                WHERE member_id = ?;
                """;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chatroom chatroom = new Chatroom();
                chatroom.setId(rs.getInt("chatroom_id"));
                chatroom.setName(rs.getString("chatroom_name"));
//                System.out.println(rs.getLong("created_at_unix"));
                chatroom.setCreatedAtUnix(rs.getLong("created_at_unix"));
                chatrooms.add(chatroom);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chatrooms;
    }

    @Override
    public List<Member> getChatroomMembers(Integer chatroomId) {
        List<Member> members = new ArrayList<>();
        String sql = """
                SELECT m.member_id, m.member_name, m.member_account, m.member_username FROM chatroom_member cm
                \tJOIN member m ON cm.member_id = m.member_id
                WHERE cm.chatroom_id = ?;
                """;
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(Integer.parseInt(rs.getString("member_id")));
                member.setAccount(rs.getString("member_account"));
                member.setName(rs.getString("member_name"));
                member.setUsername(rs.getString("member_username"));
                members.add(member);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return members;
    }
}
