package order.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import company.model.CompanyVO;
import company.model.RoomTypeImgVO;
import company.model.RoomTypeVO;
import order.dao.OrderDAOInterface;
import order.dao.OrderJDBCDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class BookingSearchService {
    private final OrderDAOInterface dao;
    Gson gson;

    public BookingSearchService() {
        dao = new OrderJDBCDAOImpl();
        gson = new Gson();
    }


    public List<CompanyVO> showCompanyAndRoomTypePriceByPosition(String addressCounty){
        return dao.selectFindCompanyAndRoomTypePriceByPosition(addressCounty);
    }


    public List<CompanyVO> showCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(String keyword){
        return dao.SelectFindCompanyAndRoomTypePriceByCompanyNameOrCountyOrArea(keyword);
    }
}
