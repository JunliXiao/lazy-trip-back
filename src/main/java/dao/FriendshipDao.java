package dao;

import java.util.List;

public interface FriendshipDao<Friendship> {

	List<Friendship> getByRequester(Integer requester_id);
	
}
