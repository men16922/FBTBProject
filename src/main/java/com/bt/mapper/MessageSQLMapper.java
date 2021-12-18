package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.MessageVo;

public interface MessageSQLMapper {

   public int createKeyMessage();
   
   public void insertContent(
         @Param("msg_no") int msg_no,
         @Param("sender") int sender,
         @Param("receiver") int receiver,
         @Param("msg_content") String msg_content,
         @Param("msg_title") String msg_title);
   
   public void deleteContent(int msg_no);
   
   public void updateCheck(int msg_no);
   
   public MessageVo selectByMsgNo(int msg_no);
   
   //검색없는 리스트 조회
   public List<MessageVo> selectByReceiverNo(
         @Param("receiver") int receiver,
         @Param("currPage") int currPage
         );
   
   //검색있는 리스트 조회
   public List<MessageVo> selectSearchByReceiverNo(
         @Param("receiver") int receiver,
         @Param("msg_title") String msg_title,
         @Param("emp_name") String emp_name,
         @Param("branch_name") String branch_name,
         @Param("currPage") int currPage
         );
  
   
   //메시지 글 수 
   public int selectCntMessage(
         @Param("receiver") int receiver,
         @Param("msg_title") String msg_title,
         @Param("emp_name") String emp_name,
         @Param("branch_name") String branch_name
         );
   
   
   
   
   
   //검색없는 보낸 메시지 조회
   public List<MessageVo> selectBySenderNo(
         @Param("sender") int sender,
         @Param("currPage") int currPage
         );
   
   //검색있는 보낸 메시지  조회
   public List<MessageVo> selectSearchBySenderNo(
         @Param("sender") int sender,
         @Param("msg_title") String msg_title,
         @Param("emp_name") String emp_name,
         @Param("branch_name") String branch_name,
         @Param("currPage") int currPage
         );
  
   
   //보낸 메시지 글 수 
   public int selectCntSendedMessage(
         @Param("sender") int sender,
         @Param("msg_title") String msg_title,
         @Param("emp_name") String emp_name,
         @Param("branch_name") String branch_name
         );
   

   public int selectCntCheckN(int receiver);

   
   
}