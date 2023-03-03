package company.service;

import java.util.List;

import company.dao.RoomTypeDAO;
import company.model.RoomTypeVO;
import company.dao.RoomTypeDAO_interface;

public class RoomTypeService {

	private RoomTypeDAO_interface dao;
	public RoomTypeService() {
		dao = new RoomTypeDAO();
	}
	
	public RoomTypeVO addRoomType(Integer roomTypeID,Integer companyID, String roomTypeName,Integer roomTypePerson, 
			Integer roomTypeQuantity, Integer roomTypePrice
			) {

		RoomTypeVO roomTypeVO = new RoomTypeVO();

		roomTypeVO.setCompanyID(companyID);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypePerson(roomTypePerson);
		roomTypeVO.setRoomTypeQuantity(roomTypeQuantity);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		dao.insert(roomTypeVO);

		return roomTypeVO;
	}

	public RoomTypeVO updateRoomType(Integer roomTypeID,Integer companyID, String roomTypeName,Integer roomTypePerson, 
			Integer roomTypeQuantity, Integer roomTypePrice
			) {

		RoomTypeVO roomTypeVO = new RoomTypeVO();

		roomTypeVO.setCompanyID(companyID);
		roomTypeVO.setRoomTypeName(roomTypeName);
		roomTypeVO.setRoomTypePerson(roomTypePerson);
		roomTypeVO.setRoomTypeQuantity(roomTypeQuantity);
		roomTypeVO.setRoomTypePrice(roomTypePrice);
		dao.insert(roomTypeVO);

		return roomTypeVO;
	}

	public void deleteRoomType(Integer roomTypeID) {
		dao.delete(roomTypeID);
	}

	public RoomTypeVO getOneRoomType(Integer roomTypeID) {
		return dao.findByPrimaryKey(roomTypeID);
	}

	
	public List<RoomTypeVO> getAllByCompanyID(Integer companyID) {
		return dao.getAllByCompanyID(companyID);
	}
	
	
}
