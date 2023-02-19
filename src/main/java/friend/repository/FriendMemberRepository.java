package friend.repository;

import member.vo.Member;

import java.util.List;

public interface FriendMemberRepository {
    List<Member> getFriendMembers(Integer memberId, String statusCode);
}
