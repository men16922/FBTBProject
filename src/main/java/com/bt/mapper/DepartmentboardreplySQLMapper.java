package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.DepartmentboardreplyVo;

public interface DepartmentboardreplySQLMapper {
	public int replyCreatekey();

	public void insert(DepartmentboardreplyVo departmentboardreplyVo);

	public List<DepartmentboardreplyVo> selectByBoardNo(int board_no);

	public void delete(int dept_board_reply_no);

	public void update(@Param("dept_board_reply_no") int dept_board_reply_no,
			@Param("dept_board_reply_content") String dept_board_reply_content);

	public void deletebyboardno(int dept_board_no);

	public int getempcodebyreplyno(int dept_board_reply_no);
}
