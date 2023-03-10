package friend.model;

import java.util.List;

public class MemberStatus {

    private int memberId;
    private String status; // online, offline
    private List<Integer> commonChatroomsId;

    public MemberStatus(int memberId, String status) {
        this.memberId = memberId;
        this.status = status;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getCommonChatroomsId() {
        return commonChatroomsId;
    }

    public void setCommonChatroomsId(List<Integer> commonChatroomsId) {
        this.commonChatroomsId = commonChatroomsId;
    }
}
