package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DeptVo;

public interface DepartmentBoardSQLMapper {

	public int createKey();

	public List<DepartmentBoardVo> selectAll(@Param("currPage") int currPage, @Param("dept_type_no") int dept_type_no);

	public List<DepartmentBoardVo> selectnotice(@Param("currPage") int currPage, @Param("dept_type_no") int dept_type_no);

	public void insert(DepartmentBoardVo departmentBoardVo);

	public DepartmentBoardVo selectByNo(@Param("dept_board_no") int dept_board_no, @Param("dept_type_no") int dept_type_no);

	public void update(
			@Param("dept_board_no") int dept_board_no, 
			@Param("dept_board_title") String dept_board_title,
			@Param("emp_code") int emp_code,
			@Param("dept_board_content") String dept_board_content
			);

	public void delete(
			@Param("dept_board_no") int dept_board_no, 
			@Param("emp_code") int emp_code);
	
	public DepartmentBoardVo selectByNoAndCode(
			@Param("dept_board_no") int dept_board_no,
			@Param("dept_type_no") int dept_type_no,
			@Param("emp_code") int emp_code);
	
	public DeptVo selectByDeptNo(int dept_no);

	public List<DeptVo> selectByLogisticsName();

	public DeptVo selectByDeptNo_All();

	public DeptVo selectByBranchNo(int branchNo);

	public void updateCheck(
			@Param("dept_board_no") int dept_board_no, 
			@Param("emp_code") int emp_code);

	public void updateView(int dept_board_no);

	public List<DepartmentBoardVo> selectByTitle(
			@Param("dept_board_title") String dept_board_title,
			@Param("dept_content") String dept_content,
			@Param("dept_writer") String dept_writer,
			@Param("currPage") int currPage, 
			@Param("dept_type_no") int dept_type_no);


	public List<DepartmentBoardVo> selectnoticeByTitle(
			@Param("dept_board_title") String dept_board_title,
			@Param("dept_content") String dept_content, 
			@Param("dept_writer") String dept_writer, 
			@Param("currPage") int currPage, 
			@Param("dept_type_no") int dept_type_no);

	public int selectAllCount(int dept_type_no);

	public int selectByTitleCount(
			@Param("dept_board_title") String dept_board_title, 
			@Param("dept_content") String dept_content, 
			@Param("dept_writer") String dept_writer, 
			@Param("dept_type_no") int dept_type_no);


}