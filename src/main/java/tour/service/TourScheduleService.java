package tour.service;

import java.util.List;

import tour.model.TourScheduleVO;

public interface TourScheduleService {
    TourScheduleVO tourScheCreate(TourScheduleVO tourScheduleVO);

    TourScheduleVO tourScheUpdate(TourScheduleVO tourScheduleVO);

    String tourScheDelete(Integer tourScheduleId);

    List<TourScheduleVO> tourScheQueryAll();

    TourScheduleVO tourScheQueryOne(Integer tourScheduleId);
}
