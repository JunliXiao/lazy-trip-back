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

    private ChatroomMemberRepository chatroomMemberRepository;

    public ChatMemberServiceImpl() {
        this.chatroomMemberRepository = new ChatroomMemberRepositoryImpl();
    }

    @Override
    public boolean createChatroom(List<Integer> membersId) {
        boolean canCreateNewChatroom = false;

        // 檢查是否輸入的會員已有共同的聊天室，而不能新增
        List<List<Integer>> listOfCurrentChatroomsId = membersId
                .stream()
                .map(id -> chatroomMemberRepository.getChatrooms(id))
                .map(list -> list.stream()
                        .map(Chatroom::getId)
                        .collect(Collectors.toList()))
                .toList();
        List<Integer> refList = listOfCurrentChatroomsId.get(0);

        for (int i = 1; i < listOfCurrentChatroomsId.size(); i++) {
            System.out.printf("refList = %s, check if it intersects %s\n", refList, listOfCurrentChatroomsId.get(i));
            refList.retainAll(listOfCurrentChatroomsId.get(i));
            if (refList.size() == 0) {
                canCreateNewChatroom = true;
                break;
            }
        }

        if (canCreateNewChatroom) {
//            return repository.addChatroom(membersId);
        }
        return canCreateNewChatroom;
    }

    @Override
    public List<Chatroom> getChatroomsByMember(Integer memberId) {
        return chatroomMemberRepository.getChatrooms(memberId);
    }

    @Override
    public Map<String, Set<Integer>> getChatroomsIdAndChatroomMembersId(Integer memberId) {
        Map<String, Set<Integer>> resultMap = new HashMap<>();

        Set<Integer> chatroomsId = chatroomMemberRepository.getChatrooms(memberId).stream()
                .map(Chatroom::getId)
                .collect(Collectors.toSet());
        Set<Integer> chatroomMembersId = chatroomsId.stream()
                .map(chatroomId -> chatroomMemberRepository.getChatroomMembers(chatroomId))
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
        return chatroomMemberRepository.getChatroomMembers(chatroomId);
    }

    @Override
    public Member findMember(String searchText) {
        return chatroomMemberRepository.getMemberByNameOrUsername(searchText);
    }
}
