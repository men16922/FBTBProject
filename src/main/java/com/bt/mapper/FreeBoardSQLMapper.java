package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.FreeBoardVo;


public interface FreeBoardSQLMapper {

   //글쓰기
   public void insertContent(FreeBoardVo freeBoardVo);
   
   //글수정
   public void updateContent(FreeBoardVo freeBoardVo);
   
   //글삭제
   public void deleteContent(
		   @Param("freeboard_no") int freeboard_no,
		   @Param("emp_code") int emp_code
		   );
   
   //글 상세보기
   public FreeBoardVo selectByBoardNo(int freeboard_no);
   
   // 수정 글보기
   public FreeBoardVo selectByBoardNoAndCode( 
		   @Param("freeboard_no") int freeboard_no,
		   @Param("emp_code") int emp_code);
   
   //검색없는 글목록
   public List<FreeBoardVo> selectAllList(int currPage);
   
   //검색있는 글목록
   public List<FreeBoardVo> selectSearchList(
         @Param("freeboard_title") String freeboard_title,
         @Param("freeboard_content") String freeboard_content,
         @Param("freeboard_writer") String freeboard_writer,
         @Param("branch_name") String branch_name,
         @Param("currPage") int currPage
         );
   
   //프리보드 게시글 수 
   public int selectCntFreeList(
         @Param("freeboard_title") String freeboard_title,
         @Param("freeboard_content") String freeboard_content,
         @Param("freeboard_writer") String freeboard_writer,
         @Param("branch_name") String branch_name
         );
   
   
   //조회수 올리기
   public void updateViewCount(int freeboard_no);
   
   //글번호 생성
   public int createKeyFreeBoard();
   
      
   
   
   
}