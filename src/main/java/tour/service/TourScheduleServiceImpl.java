package tour.service;

import java.util.List;

import javax.naming.NamingException;

import tour.dao.TourScheduleDao;
import tour.dao.TourScheduleDaoImpl;
import tour.model.TourScheduleVO;

public class TourScheduleServiceImpl implements TourScheduleService {
    private TourScheduleDao dao;

    public TourScheduleServiceImpl() throws NamingException {
        dao = new TourScheduleDaoImpl();
    }

    @Override
    public int tourScheCreate(TourScheduleVO tourScheduleVO) {
        final int result = dao.insert(tourScheduleVO);
        return result > 0 ? result : -1;
    }

    @Override
    public TourScheduleVO tourScheUpdate(TourScheduleVO tourScheduleVO) {
        final int result = dao.update(tourScheduleVO);
        return result > 0 ? tourScheduleVO : null;
    }

    @Override
    public String tourScheDelete(Integer tourScheduleId) {
        final int result = dao.delete(tourScheduleId);
        return result > 0 ? "刪除成功" : null;
    }

    @Override
    public List<TourScheduleVO> tourScheQueryAll() {
        final List<TourScheduleVO> resultLists = dao.getAll();
        return resultLists;
    }

    @Override
    public TourScheduleVO tourScheQueryOne(Integer tourScheduleId) {
        final TourScheduleVO result = dao.findByPrimaryKey(tourScheduleId);
        return result;
    }

}
