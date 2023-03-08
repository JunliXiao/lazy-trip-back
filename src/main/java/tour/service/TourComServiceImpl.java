package tour.service;

import java.util.List;

import tour.dao.TourComDao;
import tour.model.TourComVO;

public class TourComServiceImpl implements TourComService{
	
	private TourComDao dao;
	
	@Override
	public int tourComCreate(TourComVO tourComVO) {
		final int result = dao.insert(tourComVO);
        return result > 0 ? result : -1;
	}

	@Override
	public TourComVO tourComUpdate(TourComVO tourComVO) {
		final int result = dao.update(tourComVO);
        return result > 0 ? tourComVO : null;
	}

	@Override
	public String tourComDelete(Integer tourComId) {
		final int result = dao.delete(tourComId);
        return result > 0 ? "刪除成功" : null;
	}

	@Override
	public List<TourComVO> tourComQueryAll(Integer companyId) {
			final List<TourComVO> result = dao.getAll(companyId);
	        return result;
	}

	@Override
	public TourComVO getTourInfoByTourComId(Integer companyId) {
		final TourComVO result = dao.getTourByTourComId(companyId);
		return result;
	}
	
}
