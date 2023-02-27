package tour.dao;

import java.util.HashSet;
import java.util.Set;

import common.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tour.model.TourTagVO;

public class TourTagDaoImpl implements TourTagDao {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public int saveTourTag(TourTagVO tourTagVO) {
		Jedis jedis = null;
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
			String tagTitleKey = new StringBuilder("tag")
					.append(":")
					.append(tourTagVO.getTourTagTitle())
					.append(":")
					.append("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour").toString();
			jedis = pool.getResource();
			jedis.select(8);
			// 每個會員新增哪些標籤
			jedis.sadd(memberKey, tourTagVO.getTourTagTitle());
			// 每個會員在每個主行程，標記哪些標籤
			jedis.sadd(memberTourKey, tourTagVO.getTourTagTitle());
			// 每個tag在哪些主行程內
			jedis.sadd(tagTitleKey, tourTagVO.getTourId().toString());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			jedis.close();
		}
	}
	// 功能：用在渲染每個會員創立哪些tag
	public Set<String> getTourTagByMemberId(TourTagVO tourTagVO) {
		String key = null;
		Set<String> set = null;
		Set<String> allTourTag = new HashSet<>();
		Set<String> oneTourTag = null;
		Jedis jedis = null;
		set = tourTagVO.getTourIdSet();
		for(String tourId : set) {
			key = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour")
					.append(":")
					.append(tourId)
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(8);
			// 每個會員新增哪些標籤
			oneTourTag = jedis.smembers(key);
			allTourTag.addAll(oneTourTag);
		}

		jedis.close();

		return allTourTag;
	}

	@Override
	public Set<String> getTourByTourTagTitle(TourTagVO tourTagVO) {
		Jedis jedis = null;
		Set<String> allTour = new HashSet<>();
		try {
			String key = new StringBuilder("tag")
					.append(":")
					.append(tourTagVO.getTourTagTitle())
					.append(":")
					.append("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour").toString();
			jedis = pool.getResource();
			jedis.select(8);
			allTour = jedis.smembers(key);
			System.out.println(allTour.toString());
			return allTour;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	@Override
	public Set<String> getTourTagByMember(TourTagVO tourTagVO) {
		Jedis jedis = null;
		Set<String> allTourTag = new HashSet<>();
		try {
			String key = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(8);
			allTourTag = jedis.smembers(key);
			System.out.println(allTourTag.toString());
			return allTourTag;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public int deleteTourTagOnTour(TourTagVO tourTagVO) {
		Jedis jedis = null;
		try {
			String memberTourKey = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tour")
					.append(":")
					.append(tourTagVO.getTourId().toString())
					.append(":")
					.append("tag").toString();
			System.out.println(memberTourKey);
			jedis = pool.getResource();
			jedis.select(8);
			jedis.srem(memberTourKey, tourTagVO.getTourTagTitle());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
	        return 0;
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public int deleteTourTag(TourTagVO tourTagVO) {
		Jedis jedis = null;
		try {
			String memberKey = new StringBuilder("member")
					.append(":")
					.append(tourTagVO.getMemberId().toString())
					.append(":")
					.append("tag").toString();
			jedis = pool.getResource();
			jedis.select(8);
			jedis.srem(memberKey, tourTagVO.getTourTagTitle());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
	        return 0;
		} finally {
			jedis.close();
		}
	}
	

}
