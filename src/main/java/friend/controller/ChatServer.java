package friend.controller;

import friend.model.ChatMessage;
import friend.model.ChatMessageWrapper;
import friend.model.Chatroom;
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


@ServerEndpoint(value = "/chat-ws/{memberId}",
        decoders = friend.util.ChatMessageDecoder.class,
        encoders = friend.util.ChatMessageEncoder.class)
public class ChatServer {

    // 所有上線會員和他們的連線對話映射
    private static Map<Integer, Session> sessionsMap = new ConcurrentHashMap<>();

    ChatMessageService chatMessageService = new ChatMessageServiceImpl();
    ChatMemberService chatMemberService = new ChatMemberServiceImpl();

    Integer memberId;

    @OnOpen
    public void onOpen(@PathParam("memberId") Integer memberId, Session memberSession) throws IOException {

        // 會員上線
        this.memberId = memberId;
        sessionsMap.put(memberId, memberSession);

        // 上線訊息
        ChatMessageWrapper onlineNotification = new ChatMessageWrapper("online", memberId);

        // 會員的所有聊天室
        List<Integer> chatroomsId =
                chatMemberService
                        .getChatroomsByOneMember(memberId)
                        .stream()
                        .map(Chatroom::getId)
                        .toList();

        // 會員所有聊天室的全部成員，上線者，其和會員的共同聊天室
        Map<Session, List<Integer>> membersSessionAndChatroomsId =
                chatroomsId
                        .stream()
                        .map(chatroomId -> chatMemberService.getMembersByChatroom(chatroomId))
                        .flatMap(List::stream)
                        .map(Member::getId)
                        .distinct()
                        .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                        .collect(Collectors.toMap(
                                id -> sessionsMap.get(id),
                                id -> chatMemberService
                                        .getChatroomsByOneMember(id)
                                        .stream()
                                        .map(Chatroom::getId)
                                        .filter(chatroomsId::contains)
                                        .toList()
                        ));

        // 將上線訊息通知所有上線中聊天室成員
        membersSessionAndChatroomsId.forEach((key, value) -> {
            onlineNotification.setMessageContent(value);
            if (key.isOpen()) key.getAsyncRemote().sendObject(onlineNotification);
        });

        System.out.println("All members online: " + sessionsMap.keySet());
    }

    @OnMessage
    public void onMessage(Session memberSession, ChatMessageWrapper wrapper) {
        String action = wrapper.getMessageType();
        Integer chatroomId = wrapper.getChatroomId();

        if (action.equals("retrieve-history")) {
            List<ChatMessage> chatHistory = chatMessageService.retrieveHistoryMessages(chatroomId);
            memberSession.getAsyncRemote().sendObject(new ChatMessageWrapper("reload-history", chatHistory));
        } else if (action.equals("new-message")) {
            Set<Integer> chatroomMembersId =
                    chatMemberService
                            .getMembersByChatroom(chatroomId)
                            .stream()
                            .map(Member::getId)
                            .collect(Collectors.toSet());
            chatMessageService.saveMessage((ChatMessage) wrapper.getMessageContent());
            broadcast(wrapper, chatroomMembersId);
        }

    }

    @OnError
    public void onError(Session memberSession, Throwable e) {
        if (memberSession.isOpen()) {
            memberSession.getAsyncRemote().sendObject(new ChatMessageWrapper("error", e));
        }
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session memberSession, CloseReason reason) {
        // 會員下線
        sessionsMap.remove(memberId, memberSession);
        System.out.println("Connection with member ID " + memberId + " closed");

        // 下線訊息
        ChatMessageWrapper offlineNotification = new ChatMessageWrapper("offline", memberId);

        // 會員的所有聊天室
        List<Integer> chatroomsId =
                chatMemberService
                        .getChatroomsByOneMember(memberId)
                        .stream()
                        .map(Chatroom::getId)
                        .toList();

        // 會員所有聊天室的全部成員，上線者，其和會員的共同聊天室
        Map<Session, List<Integer>> membersSessionAndChatroomsId =
                chatroomsId
                        .stream()
                        .map(chatroomId -> chatMemberService.getMembersByChatroom(chatroomId))
                        .flatMap(List::stream)
                        .map(Member::getId)
                        .distinct()
                        .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                        .collect(Collectors.toMap(
                                id -> sessionsMap.get(id),
                                id -> chatMemberService
                                        .getChatroomsByOneMember(id)
                                        .stream()
                                        .map(Chatroom::getId)
                                        .filter(chatroomsId::contains)
                                        .toList()
                        ));

        // 將下線訊息通知所有上線中聊天室成員
        membersSessionAndChatroomsId.forEach((key, value) -> {
            offlineNotification.setMessageContent(value);
            if (key.isOpen()) key.getAsyncRemote().sendObject(offlineNotification);
        });

    }

    private static void broadcast(ChatMessageWrapper wrapper, Set<Integer> receiversId) {
        receiversId
                .stream()
                .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                .map(chatMemberId -> sessionsMap.get(chatMemberId))
                .forEach(chatMemberSession -> {
                    if (chatMemberSession.isOpen()) chatMemberSession.getAsyncRemote().sendObject(wrapper);
                });
    }

}