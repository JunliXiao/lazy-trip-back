package service.friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.friend.FriendshipDao;
import dao.friend.FriendshipDao_Impl;
import model.friend.Friendship;

public class FriendshipService_Impl implements FriendshipService {
	
	private FriendshipDao<Friendship> dao;
	
	public FriendshipService_Impl() {
		dao = new FriendshipDao_Impl();
	}
	
	@Override
	public Map<String, String> requestNewFriend(Integer requesterId, Integer addresseeId) {
		String str = "failure";
		
		
		
		if(dao.addFriendship(requesterId, addresseeId)) {
			str = "success";
		}
		
		Map<String, String> result = new HashMap<>();
		result.put("result", str);
		return result;
	}
	
	@Override
	public Map<String, String> acceptFriendRequest(Integer requesterId, Integer addresseeId) {
		Map<String, String> result = new HashMap<>();
		String str = "failure";
		
		if(dao.getFriendshipStatus(requesterId, addresseeId).compareTo("R") != 0) {
			result.put("result", str);
			return result;
		}
		
		if(dao.updateFriendship(requesterId, addresseeId, "A", addresseeId)) {
			str = "success";
		}

		result.put("result", str);
		return result;
	}
	
	@Override
	public Map<String, String> cancelFriendRequest(Integer requesterId, Integer addresseeId) {
		Map<String, String> result = new HashMap<>();
		String str = "failure";
		
		if(dao.getFriendshipStatus(requesterId, addresseeId).compareTo("R") != 0) {
			result.put("result", str);
			return result;
		}
		
		if(dao.updateFriendship(requesterId, addresseeId, "C", requesterId)) {
			str = "success";
		}

		result.put("result", str);
		return result;
	}
	
	@Override
	public Map<String, String> declineFriendRequest(Integer requesterId, Integer addresseeId) {
		Map<String, String> result = new HashMap<>();
		String str = "failure";
		
		if(dao.getFriendshipStatus(requesterId, addresseeId).compareTo("R") != 0) {
			result.put("result", str);
			return result;
		}
		
		if(dao.updateFriendship(requesterId, addresseeId, "D", addresseeId)) {
			str = "success";
		}

		result.put("result", str);
		return result;
	}
	
	@Override
	public Map<String, String> blockFriendRequest(Integer specifierId, Integer otherId) {
		Map<String, String> result = new HashMap<>();
		String str = "failure";
		
		if(dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 0) {
			result.put("result", str);
			return result;
		}
		
		if(dao.updateFriendship(specifierId, otherId, "B", specifierId)) {
			str = "success";
		}

		result.put("result", str);
		return result;
	}

	@Override
	public Map<String, String> resetFriendRequest(Integer specifierId, Integer otherId) {
		Map<String, String> result = new HashMap<>();
		String str = "failure";
		
//		if(dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 1) {
//			result.put("result", str);
//			return result;
//		}
		
		if(dao.updateFriendship(specifierId, otherId, "N", specifierId)) {
			str = "success";
		}

		result.put("result", str);
		return result;
	}
	
	@Override
	public Map<String, String> unfriend(Integer specifierId, Integer otherId) {
		Map<String, String> result = new HashMap<>();
		String str = "failure";
		
		if(dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 0) {
			result.put("result", str);
			return result;
		}
		
		if(dao.updateFriendship(specifierId, otherId, "N", specifierId)) {
			str = "success";
		}

		result.put("result", str);
		return result;
	}
	
	@Override
	public List<Map<String, String>> getAllFriends(Integer memberId) {
		return dao.getFriendships(memberId, "A");
	}
	
	@Override
	public List<Map<String, String>> getPendingRequests(Integer memberId) {
		return dao.getFriendshipsAsRequesterSpecifier(memberId, "R", memberId);
	}
	
	@Override
	public List<Map<String, String>> getReceivedPendingRequests(Integer memberId) {
		return dao.getFriendshipsAsAddresseeSpecifier(memberId, "R", memberId);
	}	

}
