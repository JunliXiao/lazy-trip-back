package tour.service;

import java.util.Set;

import tour.model.TourTagVO;

public interface TourTagService {
    int tourTagCreate(TourTagVO tourTagVO);

//    TourVO tourTagUpdate(TourTagVO tourTagVO);
//
    String tourTagOnTourDelete(TourTagVO tourTagVO);

    String tourTagDelete(TourTagVO tourTagVO);
    
    Set<String> tourQueryByMemberId(TourTagVO tourTagVO);

//    TourTagVO tourTagQueryOne(Integer tourTagId);
}

