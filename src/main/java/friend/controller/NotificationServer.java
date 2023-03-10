package friend.controller;

import friend.model.ChatMessageWrapper;
import friend.model.Chatroom;
import friend.model.MemberStatus;
import friend.model.MemberStatusBatch;
import friend.service.ChatMemberService;
import friend.service.ChatMemberServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ServerEndpoint(value = "/notification-ws/{memberId}",
        decoders = friend.util.MemberStatusDecoder.class,
        encoders = {friend.util.MemberStatusEncoder.class, friend.util.MemberStatusBatchEncoder.class})
public class NotificationServer {

    // 所有上線會員和他們的連線對話映射
    private static Map<Integer, Session> sessionsMap = new ConcurrentHashMap<>();

    ChatMemberService chatMemberService = new ChatMemberServiceImpl();

    Integer memberId;

    @OnOpen
    public void onOpen(@PathParam("memberId") Integer memberId, Session memberSession) throws IOException {
        // 會員上線
        this.memberId = memberId;
        sessionsMap.put(memberId, memberSession);
        MemberStatus onlineNotification = new MemberStatus(memberId, "online");
//        notifyAllOnlineMembers(onlineNotification, memberSession);
//        System.out.printf("%s: all online members = %s\n", this.getClass().getSimpleName(), sessionsMap.keySet());

        Map<String, Set<Integer>> chatroomsIdAndChatroomMembersId =
                chatMemberService.getChatroomsIdAndChatroomMembersId(memberId);

        // 會員的所有聊天室
        Set<Integer> chatroomsId = chatroomsIdAndChatroomMembersId.get("chatroomsId");

        // Key：會員所有聊天室的全部成員，上線者 Value：其和會員的共同聊天室
        Map<Integer, List<Integer>> membersIdAndChatroomsId =
                chatroomsIdAndChatroomMembersId.get("chatroomMembersId").stream()
                        .filter(chatMemberId -> !Objects.equals(chatMemberId, memberId) && sessionsMap.containsKey(chatMemberId))
                        .collect(Collectors.toMap(
                                id -> id,
                                id -> chatMemberService.getChatroomsByMember(id).stream()
                                        .map(Chatroom::getId)
                                        .filter(chatroomsId::contains)
                                        .toList()
                        ));

        // 將上線訊息通知所有上線中聊天室成員
        membersIdAndChatroomsId.forEach((id, commonChatroomsId) -> {
            onlineNotification.setCommonChatroomsId(commonChatroomsId);
            Session session = sessionsMap.get(id);
            if (session.isOpen()) session.getAsyncRemote().sendObject(onlineNotification);
        });

        memberSession.getAsyncRemote().sendObject(new MemberStatusBatch("online-batch", membersIdAndChatroomsId));
    }

    @OnError
    public void onError(Session memberSession, Throwable e) {
        //TODO 修改回傳格式
        if (memberSession.isOpen()) {
            memberSession.getAsyncRemote().sendObject(new ChatMessageWrapper("error", e));
        }
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session memberSession, CloseReason reason) {
        // 會員下線
        sessionsMap.remove(memberId, memberSession);

        MemberStatus offlineNotification = new MemberStatus(memberId, "offline");

        Map<String, Set<Integer>> chatroomsIdAndChatroomMembersId =
                chatMemberService.getChatroomsIdAndChatroomMembersId(memberId);

        // 會員的所有聊天室
        Set<Integer> chatroomsId = chatroomsIdAndChatroomMembersId.get("chatroomsId");

        // 會員所有聊天室的全部成員，上線者，其和會員的共同聊天室
        Map<Session, List<Integer>> membersSessionAndChatroomsId =
                chatroomsIdAndChatroomMembersId.get("chatroomMembersId").stream()
                        .filter(chatMemberId -> sessionsMap.containsKey(chatMemberId))
                        .collect(Collectors.toMap(
                                id -> sessionsMap.get(id),
                                id -> chatMemberService.getChatroomsByMember(id).stream()
                                        .map(Chatroom::getId)
                                        .filter(chatroomsId::contains)
                                        .toList()
                        ));
        // 將下線訊息通知所有上線中聊天室成員
        membersSessionAndChatroomsId.forEach((session, commonChatroomsId) -> {
            offlineNotification.setCommonChatroomsId(commonChatroomsId);
            if (session.isOpen()) session.getAsyncRemote().sendObject(offlineNotification);
        });
    }

    private static void notifyAllOnlineMembers(MemberStatus memberStatus, Session memberSession) {
        memberSession.getOpenSessions().forEach(session -> {
            if (session.isOpen()) session.getAsyncRemote().sendObject(memberStatus);
        });
    }

}
