package friend.controller;

import friend.model.ChatMessage;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;
import friend.service.ChatMessageService;
import friend.service.ChatMessageServiceImpl;
import member.model.Member;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@ServerEndpoint(value = "/socket/{memberId}",
        decoders = friend.util.ChatMessageDecoder.class,
        encoders = friend.util.ChatMessageEncoder.class)
public class ChatServer {

    // 所有上線會員和他們的連線對話映射
    private static Map<Integer, Session> sessionsMap = new ConcurrentHashMap<>();

    ChatMessageService chatMessageService = new ChatMessageServiceImpl();
    ChatMemberService chatMemberService = new ChatMemberServiceImpl();

    Integer memberId;
    Integer chatroomId;
    Set<Integer> chatroomMembersId;

    @OnOpen
    public void onOpen(@PathParam("memberId") Integer memberId, Session memberSession) throws IOException {
        // 初始化連線對話資訊
        this.memberId = memberId;
        this.chatroomId = Integer.parseInt(memberSession.getRequestParameterMap().get("chatroom_id").get(0));
        this.chatroomMembersId =
                chatMemberService
                        .getMembersByChatroom(chatroomId)
                        .stream()
                        .map(Member::getId)
                        .collect(Collectors.toSet());
        // 排除非此聊天室的會員
        if (!chatroomMembersId.contains(memberId)) throw new RuntimeException("會員不屬於此聊天室！");
        // 新增為上線會員及其連線對話
        sessionsMap.put(memberId, memberSession);

        // 回傳歷史訊息
        List<ChatMessage> chatHistory = chatMessageService.retrieveHistoryMessages(chatroomId);
        memberSession.getAsyncRemote().sendObject(chatHistory);

        System.out.println("All members online: " + sessionsMap.keySet());
    }

    @OnMessage
    public void onMessage(Session memberSession, ChatMessage message) {
//            chatMessageService.saveMessage(message);
        broadcast(message, chatroomMembersId);
    }

    @OnError
    public void onError(Session memberSession, Throwable e) {
        if (memberSession.isOpen()) memberSession.getAsyncRemote().sendObject(e);
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session memberSession, CloseReason reason) {
        sessionsMap.remove(memberId, memberSession);
        System.out.println("Connection with member ID " + memberId + " closed");
    }

    private static void broadcast(ChatMessage message, Set<Integer> chatroomMembersId) {
        chatroomMembersId
                .stream()
                .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                .map(chatMemberId -> sessionsMap.get(chatMemberId))
                .forEach(chatMemberSession -> chatMemberSession.getAsyncRemote().sendObject(message));
    }
}