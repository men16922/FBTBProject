package com.bt.freeboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.EmployeeSQLMapper;
import com.bt.mapper.FreeBoardFileSQLMapper;
import com.bt.mapper.FreeBoardReplySQLMapper;
import com.bt.mapper.FreeBoardSQLMapper;
import com.bt.vo.BranchVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.FreeBoardFileVo;
import com.bt.vo.FreeBoardReplyVo;
import com.bt.vo.FreeBoardVo;

@Service
public class FreeBoardServiceImpl {

	@Autowired
	private FreeBoardSQLMapper freeBoardSQLMapper;

	@Autowired
	private EmployeeSQLMapper employeeSQLMapper;

	@Autowired
	private FreeBoardFileSQLMapper freeBoardFileSQLMapper;

	@Autowired
	private FreeBoardReplySQLMapper freeBoardReplySQLMapper;

	public void updateViewCount(int freeboard_no, int emp_code) {
		FreeBoardVo freeBoardVo = freeBoardSQLMapper.selectByBoardNo(freeboard_no);
		EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(freeBoardVo.getEmp_code());

		if (!(emp_code == employeeVo.getEmp_code())) {
			freeBoardSQLMapper.updateViewCount(freeboard_no);
		}

	}

	public Map<String, Object> getContentByBoardNo(int freeboard_no) {

		Map<String, Object> map = new HashMap<String, Object>();

		FreeBoardVo freeBoardVo = freeBoardSQLMapper.selectByBoardNo(freeboard_no);
		EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(freeBoardVo.getEmp_code());
		DeptVo deptVo = employeeSQLMapper.selectByDeptNo(employeeVo.getDept_no());

		int writer_branch_no = employeeVo.getBranch_no();
		BranchVo branchVo = employeeSQLMapper.selectByBranchNo(writer_branch_no);

		int writer_rank_no = employeeVo.getRank_no();
		EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(writer_rank_no);

		List<FreeBoardFileVo> freeBoardFileVoList = freeBoardFileSQLMapper.selectByFreeBoardNo(freeboard_no);

		map.put("freeBoardVo", freeBoardVo);
		map.put("employeeVo", employeeVo);
		map.put("deptVo", deptVo);
		map.put("branchVo", branchVo);
		map.put("employeeRankVo", employeeRankVo);
		map.put("freeBoardFileVoList", freeBoardFileVoList);

		return map;
	}
	 // 댓글의 emp_code 찾기
	   public int getempcodebyreplyno(int freeboard_reply_no){
		   return freeBoardReplySQLMapper.getempcodebyreplyno(freeboard_reply_no);
	   }

	// 게시글 전체조회
	public List<Map<String, Object>> getListAll(String freeboard_title, String freeboard_content,
			String freeboard_writer, String branch_name, int currPage) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<FreeBoardVo> freeboardlist = null;

		// 검색한 값이 없을 때
		if (freeboard_title == null && freeboard_content == null && freeboard_writer == null && branch_name == null) {
			freeboardlist = freeBoardSQLMapper.selectAllList(currPage);

		} else {
			freeboardlist = freeBoardSQLMapper.selectSearchList(freeboard_title, freeboard_content, freeboard_writer,
					branch_name, currPage);
		}

		for (FreeBoardVo freeBoardVo : freeboardlist) {

			Map<String, Object> map = null;
			map = new HashMap<String, Object>();

			System.out.println("사원코드 넘어오는지: " + freeBoardVo.getEmp_code());
			System.out.println("제목 넘어오는지: " + freeBoardVo.getFreeboard_title());

			EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(freeBoardVo.getEmp_code());
			DeptVo deptVo = employeeSQLMapper.selectByDeptNo(employeeVo.getDept_no());

			int branch_no = employeeVo.getBranch_no();
			BranchVo branchVo = employeeSQLMapper.selectByBranchNo(branch_no);

			map.put("freeBoardVo", freeBoardVo);
			map.put("employeeVo", employeeVo);
			map.put("deptVo", deptVo);
			map.put("branchVo", branchVo);

			list.add(map);
		}

		return list;
	}

	// 게시글 수
	public int countFreeboardList(String freeboard_title, String freeboard_content, String freeboard_writer,
			String branch_name) {

		int cnt = freeBoardSQLMapper.selectCntFreeList(freeboard_title, freeboard_content, freeboard_writer,
				branch_name);
		return cnt;
	}

	// 글쓰기
	public void insertContentFreeboard(FreeBoardVo freeBoardvo, List<FreeBoardFileVo> fileVoList) {

		int freeboard_no = freeBoardSQLMapper.createKeyFreeBoard();
		freeBoardvo.setFreeboard_no(freeboard_no);
		freeBoardSQLMapper.insertContent(freeBoardvo);

		for (FreeBoardFileVo freeBoardFileVo : fileVoList) {
			int freeboard_file_no = freeBoardFileSQLMapper.createFreeBoardFileKey();
			freeBoardFileVo.setFreeboard_file_no(freeboard_file_no);
			freeBoardFileVo.setFreeboard_no(freeboard_no);
			freeBoardFileSQLMapper.insertFreeBoardFile(freeBoardFileVo);
		}

	}

	public Map<String, Object> updateContentRead(int freeboard_no, int emp_code) {
		// 수정 글 읽기
		Map<String, Object> map = new HashMap<String, Object>();

		freeBoardSQLMapper.selectByBoardNoAndCode(freeboard_no, emp_code);

		FreeBoardVo freeBoardVo = freeBoardSQLMapper.selectByBoardNo(freeboard_no);
		EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(freeBoardVo.getEmp_code());
		DeptVo deptVo = employeeSQLMapper.selectByDeptNo(employeeVo.getDept_no());

		int writer_branch_no = employeeVo.getBranch_no();
		BranchVo branchVo = employeeSQLMapper.selectByBranchNo(writer_branch_no);

		int writer_rank_no = employeeVo.getRank_no();
		EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(writer_rank_no);

		List<FreeBoardFileVo> freeBoardFileVoList = freeBoardFileSQLMapper.selectByFreeBoardNo(freeboard_no);

		map.put("freeBoardVo", freeBoardVo);
		map.put("employeeVo", employeeVo);
		map.put("deptVo", deptVo);
		map.put("branchVo", branchVo);
		map.put("employeeRankVo", employeeRankVo);
		map.put("freeBoardFileVoList", freeBoardFileVoList);

		return map;
	}

	public void updateContentByBoardNo(FreeBoardVo freeBoardvo) {
		// 게시글 수정
		freeBoardSQLMapper.updateContent(freeBoardvo);

	}

	public void updatefile(FreeBoardVo freeBoardVo, List<FreeBoardFileVo> fileVoList) {
		freeBoardFileSQLMapper.deletebyboardno(freeBoardVo.getFreeboard_no());
		// System.out.println("게시글 ~ : "+departmentBoardVo.getDept_board_no());
		for (FreeBoardFileVo freeBoardFileVo : fileVoList) {
			int freeboard_file_no = freeBoardFileSQLMapper.createFreeBoardFileKey();
			freeBoardFileVo.setFreeboard_file_no(freeboard_file_no);
			freeBoardFileVo.setFreeboard_no(freeBoardVo.getFreeboard_no());

			freeBoardFileSQLMapper.insertFreeBoardFile(freeBoardFileVo);
		}
	}

	public void deleteContentByBoardNo(int freeboard_no, int emp_code) {
		freeBoardSQLMapper.deleteContent(freeboard_no, emp_code);

	}

	public List<Map<String, Object>> getReplyList(int freeboard_no) {

		List<Map<String, Object>> list = new ArrayList<>();

		List<FreeBoardReplyVo> replyVoList = freeBoardReplySQLMapper.selectByBoardNo(freeboard_no);

		for (FreeBoardReplyVo freeBoardReplyVo : replyVoList) {

			EmployeeVo employeeVo = employeeSQLMapper.selectByCode(freeBoardReplyVo.getEmp_code());

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("employeeVo", employeeVo);
			map.put("freeBoardReplyVo", freeBoardReplyVo);

			list.add(map);
		}

		return list;
	}

	public void writeReply(FreeBoardReplyVo freeBoardReplyVo) {

		int freeboardreply_no = freeBoardReplySQLMapper.replyCreatekey();

		freeBoardReplyVo.setFreeboard_reply_no(freeboardreply_no);
		System.out.println(freeBoardReplyVo.getFreeboard_reply_no() + "," + freeBoardReplyVo.getFreeboard_no()
				+ freeBoardReplyVo.getFreeboard_reply_content() + "," + freeBoardReplyVo.getEmp_code() + ","
				+ freeBoardReplyVo.getFreeboard_reply_date());

		freeBoardReplySQLMapper.insert(freeBoardReplyVo);

	}

	public void deleteReply(int freeboard_reply_no) {

		freeBoardReplySQLMapper.delete(freeboard_reply_no);

	}

	public void updatereply(int freeboard_reply_no, String freeboard_reply_content) {

		freeBoardReplySQLMapper.update(freeboard_reply_no, freeboard_reply_content);
	}

}