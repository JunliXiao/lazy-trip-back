package model;

import java.sql.Timestamp;

public class Friendship {
	
	private Integer friendshipId;
	private Integer requesterId;
	private Integer addresseeId;
	private String status;
	private Timestamp createdAt;
	
	public Friendship() {
		
	}
	
	public Friendship(Integer friendshipId, Integer requesterId, Integer addresseeId, String status) {
		super();
		this.friendshipId = friendshipId;
		this.requesterId = requesterId;
		this.addresseeId = addresseeId;
		this.status = status;
	}	

	public Friendship(Integer friendshipId, Integer requesterId, Integer addresseeId, String status,
			Timestamp createdAt) {
		super();
		this.friendshipId = friendshipId;
		this.requesterId = requesterId;
		this.addresseeId = addresseeId;
		this.status = status;
		this.createdAt = createdAt;
	}



	public Integer getFriendshipId() {
		return friendshipId;
	}

	public void setFriendshipId(Integer friendshipId) {
		this.friendshipId = friendshipId;
	}

	public Integer getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(Integer requesterId) {
		this.requesterId = requesterId;
	}

	public Integer getAddresseeId() {
		return addresseeId;
	}

	public void setAddresseeId(Integer addresseeId) {
		this.addresseeId = addresseeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}