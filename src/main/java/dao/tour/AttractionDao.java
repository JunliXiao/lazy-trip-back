package dao.tour;

import java.util.List;

import model.tour.AttractionVO;

public interface AttractionDao {
	public int insert(AttractionVO attractionVO);
	public int update(AttractionVO attractionVO);
	public int delete(Integer attractionId);
	List<AttractionVO> getAll();
	AttractionVO findByPrimaryKey(Integer attractionId);
}
