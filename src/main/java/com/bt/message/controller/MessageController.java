package com.bt.message.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.message.service.MessageServiceImpl;
import com.bt.vo.BranchVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.MessageVo;

@Controller
@RequestMapping("/message/*")
public class MessageController {

	@Autowired
	private MessageServiceImpl messageServiceImpl;

	// 받은 메시지 조회 리스트
	@RequestMapping("/list_message_page.do")
	public String readListMessagePage(HttpSession session, Model model,
			@RequestParam(value = "search_type", defaultValue = "0", required = false) int search_type,
			@Param("search_word") String search_word,
			@RequestParam(value = "currPage", defaultValue = "0", required = false) int currPage) {

		System.out.println("searchType : " + search_type);
		System.out.println("searchWord : " + search_word);

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (currPage == 0) {
			currPage = 1;
		}

		String msg_title = "";
		String emp_name = "";
		String branch_name = "";

		if (search_type == 0 && search_word != null) {
			msg_title = null;
			emp_name = null;
			branch_name = null;

		}

		if (search_type == 1) {
			branch_name = search_word;
		}

		if (search_type == 2) {
			emp_name = search_word;
		}

		if (search_type == 3) {
			msg_title = search_word;
		}

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
			System.out.println("세션정보이름: " + employeeVo.getEmp_name());

			int receiver = employeeVo.getEmp_code();

			List<Map<String, Object>> dataList = messageServiceImpl.getListAll(receiver, branch_name, emp_name,
					msg_title, currPage);

			int totalCount = messageServiceImpl.countMessageList(receiver, msg_title, emp_name, branch_name);

			// 전체 페이지수
			int totalPage = totalCount / 10;

			if (totalCount % 10 > 0) {
				totalPage++;
			}

			if (totalPage < currPage) {
				currPage = totalPage;
			}

			int beginPage = ((currPage - 1) / 5) * 5 + 1;
			int endPage = ((currPage - 1) / 5 + 1) * (5);
			System.out.println("엔드페이지" + endPage);

			if (endPage > (totalCount - 1) / 10 + 1) {
				endPage = ((totalCount - 1) / 10 + 1);
			}

			model.addAttribute("currPage", currPage);
			model.addAttribute("beginPage", beginPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("messageData", dataList);

			return "/commons/list_message_page";

		}

	}

	// 보낸 메시지 조회 리스트
	@RequestMapping("/list_sended_message_page.do")
	public String readListSendedMessagePage(HttpSession session, Model model,
			@RequestParam(value = "search_type", defaultValue = "0", required = false) int search_type,
			@Param("search_word") String search_word,
			@RequestParam(value = "currPage", defaultValue = "0", required = false) int currPage) {

		System.out.println("searchType : " + search_type);
		System.out.println("searchWord : " + search_word);

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (currPage == 0) {
			currPage = 1;
		}

		String msg_title = "";
		String emp_name = "";
		String branch_name = "";

		if (search_type == 0 && search_word != null) {
			msg_title = null;
			emp_name = null;
			branch_name = null;

		}

		if (search_type == 1) {
			branch_name = search_word;
		}

		if (search_type == 2) {
			emp_name = search_word;
		}

		if (search_type == 3) {
			msg_title = search_word;
		}

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
			System.out.println("세션정보이름: " + employeeVo.getEmp_name());

			int sender = employeeVo.getEmp_code();

			List<Map<String, Object>> dataList = messageServiceImpl.getSendedListAll(sender, branch_name, emp_name, msg_title, currPage);
			int totalCount = messageServiceImpl.countSendedMessageList(sender, msg_title, emp_name, branch_name);

			// 전체 페이지수
			int totalPage = totalCount / 10;

			if (totalCount % 10 > 0) {
				totalPage++;
			}

			if (totalPage < currPage) {
				currPage = totalPage;
			}

			int beginPage = ((currPage - 1) / 5) * 5 + 1;
			int endPage = ((currPage - 1) / 5 + 1) * (5);
			System.out.println("엔드페이지" + endPage);

			if (endPage > (totalCount - 1) / 10 + 1) {
				endPage = ((totalCount - 1) / 10 + 1);
			}

			model.addAttribute("currPage", currPage);
			model.addAttribute("beginPage", beginPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("messageData", dataList);

			return "/commons/list_sended_message_page";

		}

	}

	@RequestMapping("/read_message_page.do")
	public String readMessagePage(int msg_no, Model model, HttpSession session) {

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			Map<String, Object> dataMap = messageServiceImpl.getContentByMsgNo(msg_no);

			model.addAttribute("contentData", dataMap);

			return "/commons/read_message_page";

		}

	}

	@RequestMapping("/delete_message_process.do")
	public String deleteMessageProcess(int msg_no, HttpSession session) {

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

			MessageVo messageVo = messageServiceImpl.getMsgByMsgNo(msg_no);

			if (employeeVo.getEmp_code() == messageVo.getReceiver()) {

				messageServiceImpl.deleteContentByMsgNo(msg_no);

				return "redirect:/message/list_message_page.do";
			} else {

				return "commons/fail_page";
			}

		}

	}

	@RequestMapping("/write_message_page.do")
	public String writeMessagePage(Model model, HttpSession session) {

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			List<DeptVo> deptList = messageServiceImpl.getListDept();
			model.addAttribute("deptList", deptList);

			return "/commons/write_message_page";

		}
	}

	@RequestMapping("/write_message_process.do")
	public String writeMessageProcess(HttpSession session, int receiver, String msg_title, String msg_content) {
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

			int sender = employeeVo.getEmp_code();

			System.out.println("받는 사람 emp_code : " + receiver);

			System.out.println("sender" + sender + " receiver" + receiver + " msg_title" + msg_title + " msg_content"
					+ msg_content);
			messageServiceImpl.insertMessage(sender, receiver, msg_title, msg_content);

			return "redirect:/message/list_message_page.do";
		}

	}

	// 답장하기
	@RequestMapping("/reply_message_page.do")
	public String replyMessagePage(Model model, HttpSession session, int msg_no) {

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (sessionData == null) {

			return "redirect:/employee/login_page.do";

		} else {

			Map<String, Object> dataMap = messageServiceImpl.getContentByMsgNo(msg_no);
			model.addAttribute("contentData", dataMap);

			return "/commons/reply_message_page";

		}
	}

	@RequestMapping("/sendDeptNo.do")
	@ResponseBody
	public List<EmployeeVo> sendDeptNo(int dept_no) {

		System.out.println("넘어온 값(dept_no): " + dept_no);

		List<EmployeeVo> dataList = messageServiceImpl.getEmpByDeptNo(dept_no);

		if (dataList != null) {

			return dataList;

		} else {
			return null;
		}

	}

	@RequestMapping("/sendBranchTypeNo.do")
	@ResponseBody
	public List<BranchVo> sendBranchTypeNo(int branch_type_no) {

		System.out.println("넘어온 값(branch_type_no): " + branch_type_no);

		List<BranchVo> dataList = messageServiceImpl.getBranchByBranchType(branch_type_no);

		if (dataList != null) {

			return dataList;

		} else {
			return null;
		}

	}

	@RequestMapping("/sendBranchNo.do")
	@ResponseBody
	public List<EmployeeVo> sendBranchNo(int branch_no) {

		System.out.println("넘어온 값(branch_no): " + branch_no);

		List<EmployeeVo> dataList = messageServiceImpl.getEmpByBranchNo(branch_no);

		if (dataList != null) {

			return dataList;

		} else {
			return null;
		}

	}

}