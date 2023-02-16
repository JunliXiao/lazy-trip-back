package dao.tour;

import java.util.List;

import model.tour.TourComVO;

public interface TourComDao {
	int insert(TourComVO tourComVO);
	int update(TourComVO tourComVO);
	int delete(Integer tourComId);
	List<TourComVO> getAll();
	TourComVO findByPrimaryKey(Integer tourComId);
}
