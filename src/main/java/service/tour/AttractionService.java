package service.tour;

import java.util.List;

import model.tour.AttractionVO;

public interface AttractionService {
	AttractionVO attrCreate(AttractionVO attractionVO);
	AttractionVO attrUpdate(AttractionVO attractionVO);
	String attrDelete(Integer attractionId);
	List<AttractionVO> attrQueryAll();
	AttractionVO attrQueryOne(Integer attractionId);
}
