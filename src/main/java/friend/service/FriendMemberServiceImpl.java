package friend.service;

import friend.repository.FriendMemberRepository;
import member.model.Member;

import java.util.List;

public class FriendMemberServiceImpl implements FriendMemberService {

    private FriendMemberRepository repository;

    public FriendMemberServiceImpl(FriendMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Member> getAllFriendMembers(Integer memberId) {
        return repository.getFriendMembers(memberId, "A");
    }
}
