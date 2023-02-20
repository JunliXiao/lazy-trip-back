package friend.service;

import java.util.List;

import member.model.Member;

public interface FriendMemberService {
    List<Member> getAllFriendMembers(Integer memberId);
}
