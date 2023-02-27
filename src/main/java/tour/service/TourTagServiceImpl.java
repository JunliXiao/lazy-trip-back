package tour.service;

import java.util.Set;

import javax.naming.NamingException;

import tour.dao.TourTagDao;
import tour.dao.TourTagDaoImplRedis;
import tour.model.TourTagVO;

public class TourTagServiceImpl implements TourTagService {
    private TourTagDao dao;

    public TourTagServiceImpl() throws NamingException {
        dao = new TourTagDaoImplRedis();
    }

    @Override
    public int tourTagCreate(TourTagVO tourTagVO) {
        final int result = dao.saveTourTag(tourTagVO);
        return result > 0 ? result : -99;
    }

//    @Override
//    public TourVO tourUpdate(TourVO tourVO) {
//        final int result = dao.update(tourVO);
//        return result > 0 ? tourVO : null;
//    }
//
//    @Override
//    public String tourDelete(Integer tourId) {
//        final int result = dao.delete(tourId);
//        return result > 0 ? "刪除成功" : null;
//    }

    @Override
    public Set<String> tourTagQueryAll(TourTagVO tourTagVO) {
        final Set<String> resultLists = dao.getTourTagByMemberId(tourTagVO);
        return resultLists;
    }

//    @Override
//    public TourVO tourQueryOne(Integer tourId) {
//        final TourVO result = dao.findByPrimaryKey(tourId);
//        return result;
//    }
}
