package company.model;

public class RoomTypeImgVO implements java.io.Serializable {
	private Integer roomTypeImgID;
	private Integer roomTypeID;
	private byte[] roomTypeImg;

	public byte[] getRoomTypeImg() {
		return roomTypeImg;
	}

	public void setRoomTypeImg(byte[] roomTypeImg) {
		this.roomTypeImg = roomTypeImg;
	}

	public Integer getRoomTypeImgID() {
		return roomTypeImgID;
	}

	public void setRoomTypeImgID(Integer roomTypeImgID) {
		this.roomTypeImgID = roomTypeImgID;
	}

	public Integer getRoomTypeID() {
		return roomTypeID;
	}

	public void setRoomTypeID(Integer roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	

	
}
