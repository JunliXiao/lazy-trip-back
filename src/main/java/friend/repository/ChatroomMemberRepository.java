package friend.repository;

import friend.model.Chatroom;
import member.model.Member;

import java.util.List;

public interface ChatroomMemberRepository {

    boolean addChatroom(List<Integer> membersId);

    boolean addMembersToChatroom(List<Integer> membersId, Integer chatroomId);

    boolean deleteMemberFromChatroom(Integer memberId, Integer chatroomId);

    List<Chatroom> getChatrooms(Integer memberId);

    List<Member> getChatroomMembers(Integer chatroomId);

    Member getMemberByNameOrUsername(String searchText);

}
