package service.tour;

import java.util.List;

import model.tour.TourVO;

public interface TourService {
	TourVO tourCreate(TourVO tourVO);
	TourVO tourUpdate(TourVO tourVO);
	String tourDelete(Integer tourId);
	List<TourVO> tourQueryAll();
	TourVO tourQueryOne(Integer tourId);
}

