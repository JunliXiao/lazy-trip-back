package group.dao;


import group.model.GroupVO;

public interface GroupDAO_interface {
		public int insert(GroupVO groupVo);
        public void update(GroupVO groupVo);
        public void delete(Integer groupid);
        public GroupVO findByPrimaryKey(Integer groupid);
//        public List<GroupVO> getAllbyMember_id(Integer member_id);
        
        //getAll:列出該member_id發起人的所有group
        //findByPk:使用者點入一個群組是使用該group_id查詢


}