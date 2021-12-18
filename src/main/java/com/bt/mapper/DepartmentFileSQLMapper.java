package com.bt.mapper;

import java.util.List;

import com.bt.vo.DepartmentFileVo;


public interface DepartmentFileSQLMapper {
	
	public int createKey();
	
	public void insert(DepartmentFileVo departmentFileVo);
	
	public List<DepartmentFileVo> selectByBoardNo(int dept_board_no);
	
	public void deletebyboardno(int dept_board_no);
}
