package service;

import java.util.List;
import java.util.Map;

public interface FriendshipService {

	Map<String, String> requestNewFriend(Integer requesterId, Integer addresseeId);
	
	Map<String, String> acceptFriendRequest(Integer requesterId, Integer addresseeId);
	
	Map<String, String> cancelFriendRequest(Integer requesterId, Integer addresseeId);
	
	Map<String, String> declineFriendRequest(Integer requesterId, Integer addresseeId);
	
	Map<String, String> blockFriendRequest(Integer specifierId, Integer otherId);
	
	Map<String, String> resetFriendRequest(Integer specifierId, Integer otherId);
	
	Map<String, String> unfriend(Integer specifierId, Integer otherId);
	
	List<Map<String, String>> getAllFriends(Integer memberId);
	
	List<Map<String, String>> getPendingRequests(Integer memberId);
	
	List<Map<String, String>> getReceivedPendingRequests(Integer memberId);
}
