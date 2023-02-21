package tour.dao;

import java.util.List;

import tour.model.TourTagVO;
import tour.model.TourVO;

public interface TourTagDao {
	int insert(TourTagVO tourTagVO);

    int update(TourTagVO tourTagVO);

    int delete(Integer tourTagId);

    List<TourTagVO> getAll();

    TourVO findByPrimaryKey(Integer tourTagId);
}
