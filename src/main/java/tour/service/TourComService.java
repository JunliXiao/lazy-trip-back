package tour.service;

import java.util.List;

import tour.model.TourComVO;

public interface TourComService {
	int tourComCreate(TourComVO tourComVO);

	TourComVO tourComUpdate(TourComVO tourComVO);

    String tourComDelete(Integer tourComId);

    List<TourComVO> tourComQueryAll(Integer companyId);
    
    TourComVO getTourInfoByTourComId(Integer tourComId);
}
