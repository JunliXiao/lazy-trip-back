package dao.tour;

import java.util.List;

import model.tour.TourVO;

public interface TourDao {
	int insert(TourVO tourVO);
	int update(TourVO tourVO);
	int delete(Integer tourId);
	List<TourVO> getAll();
	TourVO findByPrimaryKey(Integer tourId);
}
