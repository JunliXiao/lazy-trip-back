package group.dao;


import java.util.List;

import group.model.GroupVO;
import group.model.Group_memberVO;

public interface Group_memberDAO_interface {
	public void insertNeedApprove(Group_memberVO groupmemberVO);
	public void insertDirectly(Group_memberVO groupmemberVO);
    public void update(Group_memberVO groupmemberVO);
    public void delete(Integer groupmember);
    public List<Group_memberVO> getAlltoAccept(Integer groupid);
    public List<Group_memberVO> getAllDeleted(Integer groupid);
    public List<GroupVO> getAllGroupByMemberid(Integer memberid);
//    public List<MemeberVO> getAllMember(Group_memberVO groupmemberVO);
    
    //insertNeedApprove為審核加入申請時
    //insert直接加入時新加入一筆
    //為保留過往申請紀錄，update(刪除按鈕)是將g_m_status(審核狀態)更改為3(已拒絕)
    //成員狀態: 1:審核中成員; 2:是該群組成員 ; 3:被拒絕成員
    //getAll:每位使用者加入的所有群組
    //delete是成員被移出群組時刪除一筆
    //getAllisMember:成員清單 用 group_id=?與g_m_status=2 join member列出圖與暱稱
}	
