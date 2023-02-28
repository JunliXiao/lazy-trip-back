package friend.repository;

import member.model.Member;

import java.util.List;
import java.util.Map;

public interface FriendMemberRepository {

    boolean addFriendship(Integer requesterId, Integer addresseeId);

    boolean updateFriendship(Integer requesterId, Integer addresseeId, String statusCode);

    boolean deleteFriendship(Integer requesterId, Integer addresseeId);

    Map<String, String> getFriendshipBetween(Integer specifierId, Integer otherId);

    List<Member> getMembersByFriendship(Integer memberId, String statusCode);

    List<Member> getMembersByRequest(Integer memberId, String direction);

    List<Member> getMembersByNonFriendship(Integer memberId);

}
