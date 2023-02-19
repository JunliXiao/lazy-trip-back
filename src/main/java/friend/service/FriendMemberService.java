package friend.service;

import member.vo.Member;

import java.util.List;

public interface FriendMemberService {
    List<Member> getAllFriendMembers(Integer memberId);
}
