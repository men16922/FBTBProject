package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.FreeBoardReplyVo;





public interface FreeBoardReplySQLMapper {
	public int replyCreatekey();
	
	public int getempcodebyreplyno(int freeboard_reply_no);
	
	public void insert(FreeBoardReplyVo freeBoardReplyVo);

	public List<FreeBoardReplyVo> selectByBoardNo(int freeboard_no);

	public void delete(int freeboard_reply_no);

	public void update(@Param("freeboard_reply_no") int freeboard_reply_no,
			@Param("freeboard_reply_content") String freeboard_reply_content);

	public void deletebyboardno(int freeboard_no);
}
