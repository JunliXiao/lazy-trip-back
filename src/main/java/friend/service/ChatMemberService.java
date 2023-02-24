package friend.service;

import friend.model.Chatroom;
import member.model.Member;

import java.util.List;

public interface ChatMemberService {


    List<Chatroom> getChatroomsByMember(Integer memberId);

    List<Member> getMembersByChatroom(Integer chatroomId);

}
