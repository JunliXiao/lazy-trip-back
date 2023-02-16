package tour.service;

import java.util.List;

import javax.naming.NamingException;

import tour.dao.TourDao;
import tour.dao.TourDaoImpl;
import tour.model.TourVO;
import tour.service.TourService;

public class TourServiceImpl implements TourService {
    private TourDao dao;

    public TourServiceImpl() throws NamingException {
        dao = new TourDaoImpl();
    }

    @Override
    public TourVO tourCreate(TourVO tourVO) {
        final int result = dao.insert(tourVO);
        return result > 0 ? tourVO : null;
    }

    @Override
    public TourVO tourUpdate(TourVO tourVO) {
        final int result = dao.update(tourVO);
        return result > 0 ? tourVO : null;
    }

    @Override
    public String tourDelete(Integer tourId) {
        final int result = dao.delete(tourId);
        return result > 0 ? "刪除成功" : null;
    }

    @Override
    public List<TourVO> tourQueryAll() {
        final List<TourVO> resultLists = dao.getAll();
        return resultLists;
    }

    @Override
    public TourVO tourQueryOne(Integer tourId) {
        final TourVO result = dao.findByPrimaryKey(tourId);
        return result;
    }
}
