package service;

import java.util.List;

import dao.FriendshipDao;
import dao.FriendshipDao_Impl;
import model.Friendship;

public class FriendshipService {
	
	private FriendshipDao<Friendship> dao;
	
	public FriendshipService() {
		dao = new FriendshipDao_Impl();
	}
	
	public List<Friendship> getFriendshipBy(Integer requester_id) {
		return dao.getByRequester(requester_id);
	}

}
