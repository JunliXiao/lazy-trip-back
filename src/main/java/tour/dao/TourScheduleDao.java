package tour.dao;

import java.util.List;

import tour.model.TourScheduleVO;

public interface TourScheduleDao {
    public int insert(TourScheduleVO tourScheduleVO);

    public int update(TourScheduleVO tourScheduleVO);

    public int delete(Integer tourScheduleId);

    public List<TourScheduleVO> getAll();

    public TourScheduleVO findByPrimaryKey(Integer tourScheduleId);
}
