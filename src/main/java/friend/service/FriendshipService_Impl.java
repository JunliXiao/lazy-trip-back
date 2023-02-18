package friend.service;

import friend.dao.FriendshipDao;
import friend.dao.FriendshipDao_Impl;
import friend.model.Friendship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendshipService_Impl implements FriendshipService {

    private FriendshipDao<Friendship> dao;

    public FriendshipService_Impl() {
        dao = new FriendshipDao_Impl();
    }

    @Override
    public Map<String, String> requestNewFriend(Integer requesterId, Integer addresseeId) {
        String str = "failure";


        if (dao.addFriendship(requesterId, addresseeId)) {
            str = "success";
        }

        Map<String, String> result = new HashMap<>();
        result.put("result", str);
        return result;
    }

    @Override
    public Map<String, String> updateFriendRequestDirectional(Integer requesterId, Integer addresseeId, String updateStatus) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";
        String statusCode = switch (updateStatus) {
            case "accept" -> "A";
            case "cancel" -> "C";
            case "decline" -> "D";
            default -> throw new IllegalStateException("Unexpected value");
        };

        if (dao.getFriendshipStatus(requesterId, addresseeId).compareTo("R") != 0) {
            result.put("result", str);
            return result;
        }

        if (statusCode.equals("A") || statusCode.equals("C")) {
            str = dao.updateFriendship(requesterId, addresseeId, statusCode, requesterId) ? "success" : "failure";
        } else {
            str = dao.updateFriendship(requesterId, addresseeId, statusCode, addresseeId) ? "success" : "failure";
        }

        result.put("result", str);
        return result;
    }


    @Override
    public Map<String, String> blockFriendRequest(Integer specifierId, Integer otherId) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";

        if (dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 0) {
            result.put("result", str);
            return result;
        }

        if (dao.updateFriendship(specifierId, otherId, "B", specifierId)) {
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

        if (dao.updateFriendship(specifierId, otherId, "N", specifierId)) {
            str = "success";
        }

        result.put("result", str);
        return result;
    }

    @Override
    public Map<String, String> unfriend(Integer specifierId, Integer otherId) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";

        if (dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 0) {
            result.put("result", str);
            return result;
        }

        if (dao.updateFriendship(specifierId, otherId, "N", specifierId)) {
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
    public List<Map<String, String>> getPendingRequests(Integer memberId, String direction) {

        if (direction.equals("sent")) {
            return dao.getFriendshipsAsRequester(memberId, "R");
        } else {
            return dao.getFriendshipsAsAddressee(memberId, "R");
        }

    }

}
