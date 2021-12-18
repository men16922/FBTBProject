package com.bt.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.employee.service.EmployeeServiceImpl;
import com.bt.freeboard.service.FreeBoardServiceImpl;
import com.bt.logistics.service.LogisticsServiceImpl;
import com.bt.production.service.ProductionServiceImpl;
import com.bt.production.vo.FactoryInItemVo;
import com.bt.sales.service.SalesServiceImpl;
import com.bt.vo.EmployeeVo;

@Controller
@RequestMapping("/employee/*")
public class EmployeeController {

	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	@Autowired
	private FreeBoardServiceImpl freeBoardServiceImpl;
	@Autowired
	private SalesServiceImpl salesService;
	@Autowired
	private ProductionServiceImpl productionService;
	@Autowired
	private LogisticsServiceImpl logisticsServiceImpl;
	@Autowired // 메일 발송 용
	private JavaMailSender mailSender;
	
	@RequestMapping("/no_access_page.do")
	public String no_access_page() {
		return "commons/no_access_page";
	}
	
	@RequestMapping("/fail_page.do")
	public String fail_page() {
		return "commons/fail_page";
	}
	
	@RequestMapping("/homepage.do")
	public String home_page(HttpSession session, Model model) {

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

		int currPage = 1;

		List<Map<String, Object>> dataList = freeBoardServiceImpl.getListAll(null, null, null, null, currPage);

		int totalCount = freeBoardServiceImpl.countFreeboardList(null, null, null, null);

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
		model.addAttribute("dataList", dataList);

		// 관리부 차트
		int mon = 0;
		mon = 1;
		Integer employeeVo1 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo1 == null) {
			employeeVo1 = 0;
		}
		model.addAttribute("count1", employeeVo1);

		mon = 2;
		Integer employeeVo2 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo2 == null) {
			employeeVo2 = 0;
		}
		model.addAttribute("count2", employeeVo2);

		mon = 3;
		Integer employeeVo3 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo3 == null) {
			employeeVo3 = 0;
		}
		model.addAttribute("count3", employeeVo3);

		mon = 4;
		Integer employeeVo4 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo4 == null) {
			employeeVo4 = 0;
		}
		model.addAttribute("count4", employeeVo4);

		mon = 5;
		Integer employeeVo5 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo5 == null) {
			employeeVo5 = 0;
		}
		model.addAttribute("count5", employeeVo5);

		mon = 6;
		Integer employeeVo6 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo6 == null) {
			employeeVo6 = 0;
		}
		model.addAttribute("count6", employeeVo6);
		mon = 7;
		Integer employeeVo7 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo7 == null) {
			employeeVo7 = 0;
		}
		model.addAttribute("count7", employeeVo7);
		mon = 8;
		Integer employeeVo8 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo8 == null) {
			employeeVo8 = 0;
		}
		model.addAttribute("count8", employeeVo8);
		mon = 9;
		Integer employeeVo9 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo9 == null) {
			employeeVo9 = 0;
		}
		model.addAttribute("count9", employeeVo9);

		mon = 10;
		Integer employeeVo10 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo10 == null) {
			employeeVo10 = 0;
		}
		model.addAttribute("count10", employeeVo10);

		mon = 11;
		Integer employeeVo11 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo11 == null) {
			employeeVo11 = 0;
		}
		model.addAttribute("count11", employeeVo11);

		mon = 12;
		Integer employeeVo12 = employeeServiceImpl.getCountByMonth(mon);
		if (employeeVo12 == null) {
			employeeVo12 = 0;
		}
		model.addAttribute("count12", employeeVo12);
		// 관리부 차트 끝

		// 회사소식 시작
		int hp_notice_type_no = 1;
		List<Map<String, Object>> homepage_list_Company = employeeServiceImpl.getHomepageNotice(hp_notice_type_no);
		hp_notice_type_no = 2;
		List<Map<String, Object>> homepage_list_Chairman = employeeServiceImpl.getHomepageNotice(hp_notice_type_no);
		model.addAttribute("homepage_list_Company", homepage_list_Company);
		model.addAttribute("homepage_list_Chairman", homepage_list_Chairman);
		// 회사소식 끝

		// 물류부 해야할 일
		int dept_no = employeeVo.getDept_no();

		int firstCheckCntToDo = logisticsServiceImpl.countFirstCheckToDo(dept_no);
		int lastCheckCntToDo = logisticsServiceImpl.countLastCheckToDo(dept_no);
		int reselectCntToDo = logisticsServiceImpl.countReselectToDo(dept_no);

		model.addAttribute("firstCheckCntToDo", firstCheckCntToDo);
		model.addAttribute("lastCheckCntToDo", lastCheckCntToDo);
		model.addAttribute("reselectCntToDo", reselectCntToDo);
		// 물류부 해야할 일 끝

		// 영업부 해야할일
		int customerreservationcount = salesService.countreservation();
		int storeincount = salesService.storeincount(Integer.toString(employeeVo.getBranch_no()));
		model.addAttribute("customerreservationcount", customerreservationcount);
		model.addAttribute("storeincount", storeincount);
		// 영업부 해야할일 끝

		// 생산부 해야할일
		int factoryOrderCount = productionService.countDoList(employeeVo.getBranch_no());
		int productReqCount = productionService.CountProductList(employeeVo.getBranch_no());
		int productReadyCount = productionService.CountReadyList(employeeVo.getBranch_no());

		model.addAttribute("factoryOrderCount", factoryOrderCount);
		model.addAttribute("productReqCount", productReqCount);
		model.addAttribute("productReadyCount", productReadyCount);
		// 생산부 해야할일 끝

		return "/commons/homepage";
	}

	@RequestMapping("/homepageNotice_modify_process.do")
	public String homepageNotice_modify_process(int hp_notice_type_no, String hp_notice_title,
			String hp_notice_content) {
		employeeServiceImpl.homepageNotice_modify_process(hp_notice_type_no, hp_notice_title, hp_notice_content);

		return "redirect:/employee/homepage.do";
	}

	// 로그인 페이지
	@RequestMapping("/login_page.do")
	public String loginPage(HttpSession session) {

		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

		if (sessionData != null) {

			return "commons/fail_page";

		} else {

			return "/employee/login_page";
		}
	}

	// 로그인 프로세스
	@RequestMapping("/login_process.do")
	public String loginProcess(EmployeeVo employeevo, HttpSession session) {

		System.out.println("비밀번호 값 들어오는지 테스트 " + employeevo.getEmp_password());

		Map<String, Object> dataMap = employeeServiceImpl.getIdPw(employeevo);

		EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

		if (employeeVo == null) {
			return "commons/fail_page";
		} else {
			session.setAttribute("sessionUser", dataMap);
			return "redirect:/employee/homepage.do";
		}

	}

	// 로그아웃
	@RequestMapping("/logout_process.do")
	public String logoutProcess(HttpSession session) {
		session.invalidate();

		return "redirect:/employee/login_page.do";
	}

	// 비밀번호 수정 페이지
	@RequestMapping("/update_password_page.do")
	public String updatePasswordPage() {

		return "/commons/update_password_page";
	}

	// 비밀번호 수정 프로세스
	@RequestMapping("/update_password_process.do")
	public String updatePasswordProcess(String new_emp_password, HttpSession session) {

		Map<String, Object> map = (Map<String, Object>) session.getAttribute("sessionUser");

		if (map == null) {

			return "redirect:/employee/login_page.do";

		} else {

			// ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

			// CSRF 방어
			new_emp_password = new_emp_password.replaceAll("<", "&lt");
			new_emp_password = new_emp_password.replaceAll(">", "&gt");

			// DB의 엔터 값을 <br>로 리플레이스
			new_emp_password = new_emp_password.replaceAll("\n", "<br>");

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			EmployeeVo employeeVo = (EmployeeVo) map.get("employeeVo");

			employeeServiceImpl.updateEmpPassword(employeeVo.getEmp_code(), new_emp_password);

			return "redirect:/commons/homepage.do";

		}

	}

	// 기본 비밀번호 확인
	@RequestMapping("/confirmPw.do")
	@ResponseBody
	public String confirmPw(String emp_password, HttpSession session) {

		System.out.println("넘어온 값22(emp_password): " + emp_password);
		Map<String, Object> map = (Map<String, Object>) session.getAttribute("sessionUser");

		// ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

		// CSRF 방어
		emp_password = emp_password.replaceAll("<", "&lt");
		emp_password = emp_password.replaceAll(">", "&gt");

		// DB의 엔터 값을 <br>로 리플레이스
		emp_password = emp_password.replaceAll("\n", "<br>");

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		EmployeeVo employeeVo = (EmployeeVo) map.get("employeeVo");
		EmployeeVo empData = employeeServiceImpl.getEmpCodeAndPassword(employeeVo.getEmp_code(), emp_password);

		if (empData != null) {
			System.out.println("비밀번호 데이터 있는 경우 이름 " + empData.getEmp_name());
			return "true";

		} else {

			return "false";
		}

	}

	// 아이디랑 비밀번호 확인
	@RequestMapping("/confirmIdPw.do")
	@ResponseBody
	public String confirmIdPw(@Param("emp_no") int emp_no, @Param("emp_password") String emp_password) {

		// ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

		// CSRF 방어
		emp_password = emp_password.replaceAll("<", "&lt");
		emp_password = emp_password.replaceAll(">", "&gt");

		// DB의 엔터 값을 <br>로 리플레이스
		emp_password = emp_password.replaceAll("\n", "<br>");

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		EmployeeVo empData = employeeServiceImpl.getIdPwConfirm(emp_no, emp_password);

		if (empData == null) {
			System.out.println("비었음");
		}

		if (empData != null) {

			return "true";
		} else {
			return "false";
		}

	}

	// 비밀번호 찾기
	@RequestMapping("/find_password.do")
	@ResponseBody
	public String Findpassword(@Param("emp_name") String emp_name, @Param("emp_no") String emp_no,
			@Param("emp_email") String emp_email) {

		EmployeeVo empData = employeeServiceImpl.Findpassword(emp_name, emp_no, emp_email);

		if (empData == null) {
			System.out.println("비었음");
		}

		if (empData != null) {

			return "true";
		} else {
			return "false";
		}

	}

	// 메일
	@ResponseBody
	@RequestMapping("/employee_find_mailing_process.do")
	public String employee_find_mailing_process(String emp_no, String emp_email, HttpSession session) {

		String rannum = numberGen(6, 1);
		FBMailSenderThread thread = new FBMailSenderThread(mailSender, emp_no, emp_email, rannum);
		// ID, 키값 넘겨주고
		thread.start(); // 메일링 시작
		String emp_code = employeeServiceImpl.getempcodebyempno(emp_no);
		employeeServiceImpl.updateEmpPassword(Integer.parseInt(emp_code), rannum);
		return "true";
	}

	public String numberGen(int len, int dupCd) {

		Random rand = new Random();
		String numStr = ""; // 난수가 저장될 변수

		for (int i = 0; i < len; i++) {

			// 0~9 까지 난수 생성
			String ran = Integer.toString(rand.nextInt(10));

			if (dupCd == 1) {
				// 중복 허용시 numStr에 append
				numStr += ran;
			} else if (dupCd == 2) {
				// 중복을 허용하지 않을시 중복된 값이 있는지 검사한다
				if (!numStr.contains(ran)) {
					// 중복된 값이 없으면 numStr에 append
					numStr += ran;
				} else {
					// 생성된 난수가 중복되면 루틴을 다시 실행한다
					i -= 1;
				}
			}
		}
		return numStr;
	}


}
//========================== 메일링 용 ==================================

class FBMailSenderThread extends Thread { // 메일링을 위한 Thread 처리

	private String to;
	private JavaMailSender mailSender;
	private String emp_no;
	private String rannum;

	public FBMailSenderThread(JavaMailSender mailSender, String emp_no, String emp_email, String rannum) {

		this.mailSender = mailSender;
		this.to = emp_email;
		this.emp_no = emp_no;
		this.rannum = rannum;
	}

	@Override
	public void run() {
		// 메일발송....
		/*
		 * --발송용 계정이 따로 필요하다. 지메일 설정에서 IMAP '사용'으로 설정
		 * https://myaccount.google.com/lesssecureapps?pli=1 가서 보안수준이 낮은 앱의 액세스 '허용'
		 */
		try {
			MimeMessage message = null;
			MimeMessageHelper messageHelper = null;
			message = mailSender.createMimeMessage();
			messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setSubject("[WEB 발송] BT 전자 회원가입을 축하드립니다.");

			String link = "http://localhost:8181/bt/employee/login_page.do";

			String text = "";
			text += "BT 전자 회원 가입을 축하 드립니다.<br>";
			text += "사원번호 : ";
			text += emp_no;
			text += "<br>";
			text += "비밀번호 : ";
			text += rannum;
			text += "<br>";
			text += "비밀 번호는 6자리 랜덤입니다.";
			text += "<a href='" + link + "'>";

			messageHelper.setText(text, true);
			messageHelper.setFrom("111", "BT전자관리자"); // 발송자 이름
			messageHelper.setTo(to);// 보내는 메일을 받을 메일 주소
			// messageHelper.addInline(contentId, dataSource);
			mailSender.send(message);

			System.out.println("완료");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
// ========================== 메일링 용 ==================================