package friend.repository;

import java.util.List;

import member.model.Member;

public interface FriendMemberRepository {
	
	boolean addFriendship(Integer requesterId, Integer addresseeId);
	
	boolean updateFriendship(Integer requesterId, Integer addresseeId, String statusCode);
	
	boolean deleteFriendship(Integer requesterId, Integer addresseeId);
	
    List<Member> getMembersByFriendship(Integer memberId, String statusCode);
    
    List<Member> getMembersByRequest(Integer memberId, String direction);
    
    List<Member> getMembersByNonFriendship(Integer memberId);
    
}
