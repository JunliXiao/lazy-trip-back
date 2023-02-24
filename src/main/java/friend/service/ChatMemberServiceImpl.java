package friend.service;

import friend.model.Chatroom;
import friend.repository.ChatroomMemberRepository;
import friend.repository.ChatroomMemberRepositoryImpl;
import member.model.Member;

import java.util.List;

public class ChatMemberServiceImpl implements ChatMemberService {

    private ChatroomMemberRepository repository;

    public ChatMemberServiceImpl() {
        this.repository = new ChatroomMemberRepositoryImpl();
    }

    @Override
    public List<Chatroom> getChatroomsByMember(Integer memberId) {
        return null;
    }

    @Override
    public List<Member> getMembersByChatroom(Integer chatroomId) {
        return repository.getChatroomMembers(chatroomId);
    }
}
