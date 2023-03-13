package tour.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.naming.NamingException;

import tour.dao.TourComDao;
import tour.dao.TourComDaoImpl;
import tour.dao.TourDao;
import tour.dao.TourDaoImpl;
import tour.model.TourComVO;
import tour.model.TourVO;



public class TourComServiceImpl implements TourComService {
	private TourComDao tourComDao;
	private TourDao tourDao;

	public TourComServiceImpl() throws NamingException {
		tourComDao = new TourComDaoImpl();
		tourDao = new TourDaoImpl();
	}

	@Override
	public int tourComCreate(TourComVO tourComVO) {
		final int result = tourComDao.insert(tourComVO);
		return result > 0 ? result : -1;
	}

	@Override
	public TourComVO tourComUpdate(TourComVO tourComVO) {
		final int result = tourComDao.update(tourComVO);
		return result > 0 ? tourComVO : null;
	}

	@Override
	public String tourComDelete(Integer tourComId) {
		final int result = tourComDao.delete(tourComId);
		return result > 0 ? "刪除成功" : null;
	}

	@Override
	public List<TourComVO> tourComQueryAll(Integer companyId) {
		final List<TourComVO> result = tourComDao.getAllByCompany(companyId);
		return result;
	}

	@Override
	public TourComVO getTourInfoByTourComId(Integer companyId) {
		final TourComVO result = tourComDao.getTourByTourComId(companyId);
		return result;
	}

	@Override
	public List<String> queryAll(Integer memberId) {
		final List<TourComVO> resultTourCom = tourComDao.getAll();
		final List<TourVO> resultTour = tourDao.getAll(memberId);
		final List<String> strList;
		strList = resultTour.stream()
							.map(TourVO::getTourTitle)
							.collect(Collectors.toList());
		List<String> featureList = resultTourCom.stream()
									.map(TourComVO::getFeature)
									.filter(strList::contains)
									.collect(Collectors.toList());
		System.out.println(resultTourCom.toString());
		 if(featureList.size() <= 0) {
			 List<String> top3TourRecommend = resultTourCom.stream()
			 			.limit(3)
			 			.map(TourComVO::getTourTitle)
			 			.collect(Collectors.toList());
			 return top3TourRecommend;
		 }
		return featureList;
	}
}
