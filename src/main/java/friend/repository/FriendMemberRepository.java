package friend.repository;

import java.util.List;

import member.model.Member;

public interface FriendMemberRepository {
    List<Member> getFriendMembers(Integer memberId, String statusCode);
}
