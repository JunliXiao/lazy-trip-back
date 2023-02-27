package tour.dao;

import java.util.Set;

import common.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tour.model.TourTagVO;

public class TourTagDaoImplRedis implements TourTagDao {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public int saveTourTag(TourTagVO tourTagVO) {
		try {
			String memberKey = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tag").toString();
			String memberTourKey = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour")
					.append(":")
					.append(tourTagVO.getTourId().toString())
					.append(":")
					.append("tag").toString();
			Jedis jedis = pool.getResource();
			jedis.select(8);
			// 每個會員新增哪些標籤
			jedis.sadd(memberKey, tourTagVO.getTourTagTitle());
			// 每個會員在每個主行程，標記哪些標籤
			jedis.sadd(memberTourKey, tourTagVO.getTourTagTitle());
			jedis.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Set<String> getTourTagByMemberId(TourTagVO tourTagVO) {
		String key = new StringBuilder("member")
				.append(":")
				.append(tourTagVO.getMemberId())
				.append(":")
				.append("tag").toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.select(8);
		// 每個會員新增哪些標籤
		Set<String> allTourTag = jedis.smembers(key);
		jedis.close();

		return allTourTag;
	}

	@Override
	public TourTagVO updateTourTag(TourTagVO tourTagVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteTourTag(TourTagVO tourTagVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> getTourByTourTagTitle(TourTagVO tourTagVO) {
		// TODO Auto-generated method stub
		return null;
	}
}
