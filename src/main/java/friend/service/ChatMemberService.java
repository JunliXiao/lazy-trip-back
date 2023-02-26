package friend.service;

import friend.model.Chatroom;
import member.model.Member;

import java.util.List;

public interface ChatMemberService {


    List<Chatroom> getChatroomsByOneMember(Integer memberId);

    List<Chatroom> getChatroomsByMultipleMembers(List<Integer> memberId);

    List<Member> getMembersByChatroom(Integer chatroomId);

}
