package company.service;

import java.util.List;

import company.dao.RoomTypeImgDAO_interface;
import company.dao.RoomTypeImgDAO;
import company.model.RoomTypeImgVO;

public class RoomTypeImgService {

	private RoomTypeImgDAO_interface dao;

	public RoomTypeImgService() {
		dao = new RoomTypeImgDAO();
	}

	public RoomTypeImgVO addRoomTypeImg(Integer roomTypeImgID,Integer roomTypeID, byte[] roomTypeImg
			) {

		RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();

		roomTypeImgVO.setRoomTypeImgID(roomTypeImgID);
		roomTypeImgVO.setRoomTypeID(roomTypeID);
		roomTypeImgVO.setRoomTypeImg(roomTypeImg);
		dao.insert(roomTypeImgVO);

		return roomTypeImgVO;
	}

	public RoomTypeImgVO updateRoomTypeImg(Integer roomTypeImgID,Integer roomTypeID, byte[] roomTypeImg
) {

		RoomTypeImgVO roomTypeImgVO = new RoomTypeImgVO();

		roomTypeImgVO.setRoomTypeImgID(roomTypeImgID);
		roomTypeImgVO.setRoomTypeID(roomTypeID);
		roomTypeImgVO.setRoomTypeImg(roomTypeImg);
		dao.update(roomTypeImgVO);

		return roomTypeImgVO;
	}

	public void deleteroomTypeImg(Integer roomTypeImgID) {
		dao.delete(roomTypeImgID);
	}

	public RoomTypeImgVO getOneRoomTypeImg(Integer roomTypeID) {
		return dao.findByRoomTypeID(roomTypeID);
	}

	public List<RoomTypeImgVO> getAll() {
		return dao.getAll();
	}
	
	
	
}
