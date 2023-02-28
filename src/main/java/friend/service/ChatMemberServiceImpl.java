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
    public boolean createChatroom(List<Integer> membersId) {
        return repository.addChatroom(membersId);
    }

    @Override
    public List<Chatroom> getChatroomsByOneMember(Integer memberId) {
        return repository.getChatrooms(memberId);
    }

    @Override
    public List<Chatroom> getChatroomsByMultipleMembers(List<Integer> memberId) {
        return null;
    }

    @Override
    public List<Member> getMembersByChatroom(Integer chatroomId) {
        return repository.getChatroomMembers(chatroomId);
    }
}
