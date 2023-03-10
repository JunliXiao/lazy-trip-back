package friend.service;

import friend.model.Chatroom;
import member.model.Member;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChatMemberService {

    boolean createChatroom(List<Integer> membersId);

    List<Chatroom> getChatroomsByMember(Integer memberId);

    Map<String, Set<Integer>> getChatroomsIdAndChatroomMembersId(Integer memberId);

    List<Chatroom> getChatroomsByMultipleMembers(List<Integer> memberId);

    List<Member> getMembersByChatroom(Integer chatroomId);

}
