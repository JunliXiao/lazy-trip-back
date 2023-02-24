package friend.service;

import java.util.List;

import member.model.Member;

public interface FriendMemberService {
	
	boolean createFriendRequest(Integer requesterId, Integer addresseeId);
	
	boolean acceptFriendRequest(Integer requesterId, Integer addresseeId);
	
	boolean blockFriendRequest(Integer requesterId, Integer addresseeId);
	
	boolean removeFriendRequest(Integer requesterId, Integer addresseeId);
	
    List<Member> getFriends(Integer memberId);
    
    List<Member> getSentRequests(Integer memberId);
    
    List<Member> getReceivedRequests(Integer memberId);
    
    List<Member> getBlockedMembers(Integer memberId);
    
    List<Member> getFriendSuggestions(Integer memberId);
}
