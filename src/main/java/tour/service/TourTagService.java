package tour.service;

import java.util.Set;

import tour.model.TourTagVO;

public interface TourTagService {
    int tourTagCreate(TourTagVO tourTagVO);

//    TourVO tourTagUpdate(TourTagVO tourTagVO);
//
//    String tourTagDelete(Integer tourTagId);

    Set<String> tourTagQueryAll(TourTagVO tourTagVO);

//    TourTagVO tourTagQueryOne(Integer tourTagId);
}

