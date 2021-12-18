package com.bt.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.BranchSQLMapper;
import com.bt.mapper.DepartmentBoardSQLMapper;
import com.bt.mapper.DepartmentFileSQLMapper;
import com.bt.mapper.DepartmentboardreplySQLMapper;
import com.bt.mapper.EmployeeSQLMapper;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.BranchVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;

@Service
public class ManagementServiceImpl {

	@Autowired
	private EmployeeSQLMapper employeeSQLMapper;

	@Autowired
	private DepartmentBoardSQLMapper departmentBoardSQLMapper;

	@Autowired
	private DepartmentFileSQLMapper departmentFileSQLMapper;

	@Autowired
	private DepartmentboardreplySQLMapper departmentboardreplySQLMapper;

	@Autowired
	private BranchSQLMapper branchSQLMapper;

// 부서게시판 시작!
	public List<Map<String, Object>> getBoardList(String dept_board_title, String dept_content, String dept_writer,
			int currPage, int dept_type_no) {
		// 게시물 가져오기
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<DepartmentBoardVo> boardList = null;

		if (dept_board_title == null && dept_content == null && dept_writer == null) {

			boardList = departmentBoardSQLMapper.selectAll(currPage, dept_type_no);
		} else {
			// 검색하는 경우
			boardList = departmentBoardSQLMapper.selectByTitle(dept_board_title, dept_content, dept_writer, currPage,
					dept_type_no);
		}

		for (DepartmentBoardVo departmentBoardVo : boardList) {
			Map<String, Object> map = new HashMap<String, Object>();

			EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
			BranchVo branchVo = employeeSQLMapper.selectByBranchNo(employeeVo.getBranch_no());

			map.put("branchVo", branchVo);
			map.put("employeeVo", employeeVo);
			map.put("departmentBoardVo", departmentBoardVo);

			list.add(map);
		}

		return list;

	}

	// 게시물 갯수 세기
	public int getBoardCount(String dept_board_title, String dept_content, String dept_writer, int dept_type_no) {

		if (dept_board_title == null && dept_content == null && dept_writer == null) {
			// 디폴트
			return departmentBoardSQLMapper.selectAllCount(dept_type_no);
		} else {
			// 검색했을때
			return departmentBoardSQLMapper.selectByTitleCount(dept_board_title, dept_content, dept_writer,
					dept_type_no);

		}
	}

	public List<Map<String, Object>> getnoticeBoardList(String dept_board_title, String dept_content,
			String dept_writer, int currPage, int dept_type_no) {
		// 게시물 가져오기
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<DepartmentBoardVo> boardList = null;

		if (dept_board_title == null && dept_content == null && dept_writer == null) {
			boardList = departmentBoardSQLMapper.selectnotice(currPage, dept_type_no);
		} else {
			// 검색하는 경우
			boardList = departmentBoardSQLMapper.selectnoticeByTitle(dept_board_title, dept_content, dept_writer,
					currPage, dept_type_no);

		}

		for (DepartmentBoardVo departmentBoardVo : boardList) {

			Map<String, Object> map = new HashMap<String, Object>();

			EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
			BranchVo branchVo = employeeSQLMapper.selectByBranchNo(employeeVo.getBranch_no());

			map.put("branchVo", branchVo);
			map.put("employeeVo", employeeVo);
			map.put("departmentBoardVo", departmentBoardVo);

			list.add(map);
		}

		return list;

	}

	public int createKey(DepartmentBoardVo departmentBoardVo) {
		int boardKey = departmentBoardSQLMapper.createKey();
		departmentBoardVo.setDept_board_no(boardKey);
		// System.out.println("게시글 번호 : "+boardKey);
		return boardKey;
	}

	public void writeContent(DepartmentBoardVo departmentBoardVo, List<DepartmentFileVo> fileVoList) {
		// 게시글 작성
		departmentBoardSQLMapper.insert(departmentBoardVo);

		// System.out.println("게시글 ~ : "+departmentBoardVo.getDept_board_no());
		for (DepartmentFileVo departmentFileVo : fileVoList) {

			int dept_file_no = departmentFileSQLMapper.createKey();
			departmentFileVo.setDept_file_no(dept_file_no);
			departmentFileVo.setDept_board_no(departmentBoardVo.getDept_board_no());

			departmentFileSQLMapper.insert(departmentFileVo);
		}
	}

	// 파일 수정하기
	public void updatefile(DepartmentBoardVo departmentBoardVo, List<DepartmentFileVo> fileVoList) {

		// System.out.println("게시글 ~ : "+departmentBoardVo.getDept_board_no());
		for (DepartmentFileVo departmentFileVo : fileVoList) {
			int dept_file_no = departmentFileSQLMapper.createKey();
			departmentFileVo.setDept_file_no(dept_file_no);
			departmentFileVo.setDept_board_no(departmentBoardVo.getDept_board_no());

			departmentFileSQLMapper.insert(departmentFileVo);
		}
	}

	// 공지사항 등록하기
	public void updateCheck(int dept_board_no, int emp_code) {
		// 공지사항 등록하기
		departmentBoardSQLMapper.updateCheck(dept_board_no, emp_code);
	}

	// 게시글 읽기
	public Map<String, Object> updateReadContent(int dept_board_no, int dept_type_no, int emp_code) {
		// 수정할 때 게시글 읽기
		Map<String, Object> map = new HashMap<String, Object>();

		DepartmentBoardVo departmentBoardVo = departmentBoardSQLMapper.selectByNoAndCode(dept_board_no, dept_type_no,
				emp_code);
		EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
		EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(employeeVo.getRank_no());
		BranchVo branchVo = branchSQLMapper.selectByBNO(employeeVo.getBranch_no());
		List<DepartmentFileVo> departmentFileVolist = departmentFileSQLMapper.selectByBoardNo(dept_board_no);

		map.put("departmentBoardVo", departmentBoardVo);
		map.put("employeeVo", employeeVo);
		map.put("employeeRankVo", employeeRankVo);
		map.put("branchVo", branchVo);
		map.put("departmentFileVolist", departmentFileVolist);

		return map;
	}

	public Map<String, Object> readContent(int dept_board_no, int dept_no) {
		// 게시글 읽기
		Map<String, Object> map = new HashMap<String, Object>();

		DepartmentBoardVo departmentBoardVo = departmentBoardSQLMapper.selectByNo(dept_board_no, dept_no);
		EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
		EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(employeeVo.getRank_no());
		BranchVo branchVo = branchSQLMapper.selectByBNO(employeeVo.getBranch_no());
		List<DepartmentFileVo> departmentFileVolist = departmentFileSQLMapper.selectByBoardNo(dept_board_no);
		map.put("departmentBoardVo", departmentBoardVo);
		map.put("employeeVo", employeeVo);
		map.put("employeeRankVo", employeeRankVo);
		map.put("branchVo", branchVo);
		map.put("departmentFileVolist", departmentFileVolist);

		return map;
	}

	public void updateRead(int dept_board_no) {
		// 조회수 업데이트
		departmentBoardSQLMapper.updateView(dept_board_no);
	}

	public void update(int dept_board_no, String dept_board_title, String dept_board_content, int emp_code) {
		// 게시글 수정
		departmentBoardSQLMapper.update(dept_board_no, dept_board_title, emp_code, dept_board_content);
	}

	public void delete(int dept_board_no, int emp_code) {
		departmentBoardSQLMapper.delete(dept_board_no, emp_code);
		departmentboardreplySQLMapper.deletebyboardno(dept_board_no);
		departmentFileSQLMapper.deletebyboardno(dept_board_no);
	}

	public void writeReply(DepartmentboardreplyVo departmentboardreplyVo) {

		int dept_board_reply_no = departmentboardreplySQLMapper.replyCreatekey();
		departmentboardreplyVo.setDept_board_reply_no(dept_board_reply_no);
		departmentboardreplySQLMapper.insert(departmentboardreplyVo);

	}

	public void deleteReply(int dept_board_reply_no) {

		departmentboardreplySQLMapper.delete(dept_board_reply_no);

	}

	public List<Map<String, Object>> getReplyList(int board_no) {

		List<Map<String, Object>> list = new ArrayList<>();

		List<DepartmentboardreplyVo> replyVoList = departmentboardreplySQLMapper.selectByBoardNo(board_no);

		for (DepartmentboardreplyVo departmentboardreplyVo : replyVoList) {

			EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentboardreplyVo.getEmp_code());

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("employeeVo", employeeVo);
			map.put("departmentboardreplyVo", departmentboardreplyVo);

			list.add(map);
		}

		return list;
	}

	public void updatereply(int dept_board_reply_no, String dept_board_reply_content) {

		departmentboardreplySQLMapper.update(dept_board_reply_no, dept_board_reply_content);
	}

	public int getempcodebyreplyno(int dept_board_reply_no) {

		return departmentboardreplySQLMapper.getempcodebyreplyno(dept_board_reply_no);
	}
	// 부서게시판 끝
	// 부서게시판 끝

}