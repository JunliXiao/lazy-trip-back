package friend.service;

import friend.repository.FriendMemberRepository;
import friend.repository.FriendMemberRepositoryImpl;
import member.model.Member;

import java.util.List;
import java.util.Map;

public class FriendMemberServiceImpl implements FriendMemberService {

    private FriendMemberRepository repository;

    public FriendMemberServiceImpl() {
        this.repository = new FriendMemberRepositoryImpl();
    }
    
	@Override
	public boolean createFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.addFriendship(requesterId, addresseeId);
	}

	@Override
	public boolean acceptFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.updateFriendship(requesterId, addresseeId, "A");
	}

	@Override
	public boolean blockFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.updateFriendship(requesterId, addresseeId, "B");
	}

	@Override
	public boolean removeFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.deleteFriendship(requesterId, addresseeId);
	}

	@Override
	public Map<String, String> checkFriendshipBetween(Integer specifierId, Integer otherId) {
		return repository.getFriendshipBetween(specifierId, otherId);
	}

	@Override
	public List<Member> getFriends(Integer memberId) {
		return repository.getMembersByFriendship(memberId, "A");
	}

	@Override
	public List<Member> getBlockedMembers(Integer memberId) {
		return repository.getMembersByFriendship(memberId, "B");
	}

	@Override
	public List<Member> getSentRequests(Integer memberId) {
		return repository.getMembersByRequest(memberId, "sent");
	}
	
	@Override
	public List<Member> getReceivedRequests(Integer memberId) {
		return repository.getMembersByRequest(memberId, "received");
	}

	@Override
	public List<Member> getFriendSuggestions(Integer memberId) {
		return repository.getMembersByNonFriendship(memberId);
	}
    
}
