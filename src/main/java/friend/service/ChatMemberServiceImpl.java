package friend.service;

import friend.model.Chatroom;
import friend.repository.ChatroomMemberRepository;
import friend.repository.ChatroomMemberRepositoryImpl;
import member.model.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<Chatroom> getChatroomsByMember(Integer memberId) {
        return repository.getChatrooms(memberId);
    }

    @Override
    public Map<String, Set<Integer>> getChatroomsIdAndChatroomMembersId(Integer memberId) {
        Map<String, Set<Integer>> resultMap = new HashMap<>();

        Set<Integer> chatroomsId = repository.getChatrooms(memberId).stream()
                .map(Chatroom::getId)
                .collect(Collectors.toSet());
        Set<Integer> chatroomMembersId = chatroomsId.stream()
                .map(chatroomId -> repository.getChatroomMembers(chatroomId))
                .flatMap(List::stream)
                .map(Member::getId)
                .collect(Collectors.toSet());

        resultMap.put("chatroomsId", chatroomsId);
        resultMap.put("chatroomMembersId", chatroomMembersId);
        return resultMap;
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
