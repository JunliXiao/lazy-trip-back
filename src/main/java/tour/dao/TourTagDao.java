package tour.dao;

import java.util.Set;

import tour.model.TourTagVO;

public interface TourTagDao {
	int saveTourTag(TourTagVO tourTagVO);
	TourTagVO updateTourTag(TourTagVO tourTagVO);
	int deleteTourTag(TourTagVO tourTagVO);
    Set<String> getTourTagByMemberId(TourTagVO tourTagVO);
    Set<String> getTourByTourTagTitle(TourTagVO tourTagVO);
}
