package com.bt.production.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bt.production.service.ProductionServiceImpl;
import com.bt.production.vo.ComponentStockVo;
import com.bt.production.vo.DeliveryVo;
import com.bt.production.vo.FactoryInItemVo;
import com.bt.production.vo.FactoryOrderDetailVo;
import com.bt.production.vo.FactoryOrderVo;
import com.bt.production.vo.FactoryOutItemVo;
import com.bt.vo.FactoryVo;
import com.bt.production.vo.SupplierVo;
import com.bt.vo.ProcessStatusVo;
import com.bt.vo.StoreOrderDetailVo;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.EmployeeVo;

@Controller
@RequestMapping("/production/*")
public class ProductionController {

	@Autowired
	private ProductionServiceImpl productionService;
	
	// 부서별 접근권한 막기
	public boolean deptcheck(int dept_no) {
		if (dept_no == 40) {
			return true;
		} else
			return false;

	}

	// 메인페이지
	@RequestMapping("/main_page.do")
	public String main_page(Model model, String searchWord, HttpSession session,
			@RequestParam(value = "search_type", defaultValue = "0", required = false) int search_type,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		int dept_type_no = employeeVo.getDept_no() / 10;
		// System.out.println("부서유형 : "+dept_type_no);

		String dept_title = "";
		String dept_content = "";
		String dept_writer = "";

		if (search_type == 0 && searchWord != null) {
			dept_title = null;
			dept_content = null;
			dept_writer = null;

		}

		if (search_type == 1) {
			dept_title = searchWord;
		}

		if (search_type == 2) {
			dept_content = searchWord;
		}

		if (search_type == 3) {
			dept_writer = searchWord;
		}

		List<Map<String, Object>> list = productionService.getBoardList(dept_title, dept_content, dept_writer, currPage,
				dept_type_no);
		List<Map<String, Object>> noticelist = productionService.getnoticeBoardList(dept_title, dept_content,
				dept_writer, currPage, dept_type_no);

		int totalCount = productionService.getBoardCount(dept_title, dept_content, dept_writer, dept_type_no);

		// 숫자 5개씩 나오게
		int beginPage = ((currPage - 1) / 5) * 5 + 1;
		int endPage = ((currPage - 1) / 5 + 1) * (5);
		if (endPage > ((totalCount - 1) / 10) + 1) {
			endPage = ((totalCount - 1) / 10) + 1;
		}

		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currPage", currPage);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("dataList", list);
		model.addAttribute("noticelist", noticelist);

		return "production/main_page";
	}

	// 부서 게시판 글쓰기 페이지
	@RequestMapping("/board_write_page.do")
	public String board_write_page(HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}
		return "production/board_write_page";
	}

	// 부서 게시판 글쓰기 프로세스
	@RequestMapping("/board_write_process.do")
	public String board_write_process(HttpSession session, DepartmentBoardVo departmentBoardVo,
			MultipartFile[] board_upload_files, String check_notice) {

		String uploadRootFolderName = "C:/upload/";

		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String todayFolder = df.format(today);

		String saveFolderName = uploadRootFolderName + todayFolder;
		File saveFolder = new File(saveFolderName);

		// 폴더가 존재하는지 확인하는 부분
		if (!saveFolder.exists()) {
			saveFolder.mkdirs();
		}

		List<DepartmentFileVo> fileVoList = new ArrayList<DepartmentFileVo>();
		if (board_upload_files == null)
			System.out.println("비었음");
		// 파일 업로드 부분
		if (board_upload_files != null) {
			for (MultipartFile file : board_upload_files) {

				if (file.getSize() <= 0) {
					continue;
				}

				String oriFileName = file.getOriginalFilename();

				String saveFileName = UUID.randomUUID().toString();
				saveFileName += "_" + System.currentTimeMillis();
				saveFileName += oriFileName.substring(oriFileName.lastIndexOf("."));

				String saveRealPath = saveFolderName + "/" + saveFileName;
				System.out.println(saveRealPath);
				try {
					file.transferTo(new File(saveRealPath));
					System.out.println("test getSize: " + file.getSize());
					System.out.println("test getOriginalFilename: " + file.getOriginalFilename());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// DB를 위한 FileVo 객체 생성
				DepartmentFileVo fileVo = new DepartmentFileVo();

				fileVo.setDept_file_link_path(todayFolder + "/" + saveFileName);
				fileVo.setDept_file_real_path(saveRealPath);

				System.out.println(fileVo.getDept_file_link_path());
				fileVoList.add(fileVo);
			}
		}

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		departmentBoardVo.setEmp_code(employeeVo.getEmp_code());
		int dept_type_no = employeeVo.getDept_no() / 10;
		departmentBoardVo.setDept_type_no(dept_type_no);

		int boardNo = productionService.createKey(departmentBoardVo);
		System.out.println("공지 게시글번호 : " + boardNo);

		if (check_notice == null) {
			// 아무것도 안날라온 경우
			productionService.writeContent(departmentBoardVo, fileVoList);
		} else {
			// 라디오체크로 넘어온 값이 Y인 경우 공지사항 등록
			productionService.writeContent(departmentBoardVo, fileVoList);
			productionService.updateCheck(boardNo, employeeVo.getEmp_code());
		}

		return "redirect:/production/main_page.do";
	}

	// 부서 게시글 수정 프로세스
	@RequestMapping("/update_content_process.do")
	public String board_update_process(DepartmentBoardVo departmentBoardVo,
			@RequestParam(value = "emp_code", required = false, defaultValue = "0") int emp_code,
			@RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no,
			String dept_board_title, String dept_board_content, String check_notice, MultipartFile[] board_upload_files,
			HttpSession session) {

		// 세션처리하기!(부서,로그아웃)
		Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		if (dept_board_title != null && dept_board_title.trim().equals("")) {
			dept_board_title = null;
		}
		if (dept_board_content != null && dept_board_content.trim().equals("")) {
			dept_board_content = null;
		}

		// CSRF
		dept_board_title = dept_board_title.replaceAll("<", "&lt");
		dept_board_title = dept_board_title.replaceAll(">", "&gt");

		dept_board_content = dept_board_content.replaceAll("<", "&lt");
		dept_board_content = dept_board_content.replaceAll(">", "&gt");

		// DB의 엔터 값을 <br>로 리플레이스
		dept_board_title = dept_board_title.replaceAll("\n", "<br>");
		dept_board_content = dept_board_content.replaceAll("\n", "<br>");

		String uploadRootFolderName = "C:/upload/";

		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String todayFolder = df.format(today);

		String saveFolderName = uploadRootFolderName + todayFolder;
		File saveFolder = new File(saveFolderName);

		// 폴더가 존재하는지 확인하는 부분
		if (!saveFolder.exists()) {
			saveFolder.mkdirs();
		}

		List<DepartmentFileVo> fileVoList = new ArrayList<DepartmentFileVo>();
		if (board_upload_files == null)
			System.out.println("비었음");
		// 파일 업로드 부분
		if (board_upload_files != null) {
			for (MultipartFile file : board_upload_files) {

				if (file.getSize() <= 0) {
					continue;
				}

				String oriFileName = file.getOriginalFilename();

				String saveFileName = UUID.randomUUID().toString();
				saveFileName += "_" + System.currentTimeMillis();
				saveFileName += oriFileName.substring(oriFileName.lastIndexOf("."));

				String saveRealPath = saveFolderName + "/" + saveFileName;
				System.out.println(saveRealPath);
				try {
					file.transferTo(new File(saveRealPath));
					System.out.println("test getSize: " + file.getSize());
					System.out.println("test getOriginalFilename: " + file.getOriginalFilename());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// DB를 위한 FileVo 객체 생성
				DepartmentFileVo fileVo = new DepartmentFileVo();

				fileVo.setDept_file_link_path(todayFolder + "/" + saveFileName);
				fileVo.setDept_file_real_path(saveRealPath);

				System.out.println(fileVo.getDept_file_link_path());
				fileVoList.add(fileVo);
			}
		}

		if (check_notice.equals("Y")) {
			productionService.update(dept_board_no, dept_board_title, dept_board_content, employeeVo.getEmp_code());
			productionService.updateCheck(dept_board_no, employeeVo.getEmp_code());
		} else {
			productionService.update(dept_board_no, dept_board_title, dept_board_content, employeeVo.getEmp_code());
		}

		productionService.updatefile(departmentBoardVo, fileVoList);

		return "redirect:/production/main_page.do";

	}

	// 부서 게시판 읽기
	@RequestMapping("/board_read_page.do")
	public String board_read_page(int dept_board_no, Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");
		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}
		int dept_type_no = employeeVo.getDept_no() / 10;
		int emp_code = employeeVo.getEmp_code();

		Cookie[] cookies = request.getCookies();
		// 비교하기 위해 새로운 쿠키
		Cookie viewCookie = null;
		// 쿠키가 있을 경우
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("cookie" + dept_board_no)) {
					System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
					viewCookie = cookies[i];
				}
			}
		}
		if (viewCookie == null) {
			System.out.println("cookie 없음");

			// 쿠키 생성(이름, 값)
			Cookie newCookie = new Cookie("cookie" + dept_board_no, "|" + dept_board_no + "|");

			// 쿠키 추가
			response.addCookie(newCookie);

			// 쿠키를 추가 시키고 조회수 증가시킴
			productionService.updateRead(dept_board_no, emp_code, dept_type_no);
		}
		// viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
		else {
			System.out.println("cookie 있음");

			// 쿠키 값 받아옴.
			String value = viewCookie.getValue();

			System.out.println("cookie 값 : " + value);

		}

		Map<String, Object> map = productionService.readContent(dept_board_no, dept_type_no);
		model.addAttribute("map", map);

		// 조회수 업데이트

		return "production/board_read_page";
	}

	// 부서 게시글 수정페이지
	@RequestMapping("/board_update_page.do")
	public String board_update_page(int dept_board_no, Model model, HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		int dept_type_no = employeeVo.getDept_no() / 10;

		Map<String, Object> map = productionService.readContent(dept_board_no, dept_type_no);
		EmployeeVo checkEmp = (EmployeeVo) map.get("employeeVo");

		if (checkEmp.getEmp_code() == employeeVo.getEmp_code()) {
			// 정상적으로 로그인한 사원과 게시글 쓴 사원이 같을 때 작동
			model.addAttribute("data",
					productionService.updateReadContent(dept_board_no, dept_type_no, employeeVo.getEmp_code()));
			return "production/board_update_page";

		} else {
			return "commons/incorrect_access_page";
		}

	}

	// 부서 게시글 삭제 프로세스
	@RequestMapping("/delete_process.do")
	public String board_delete_process(HttpSession session,
			@RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		productionService.delete(dept_board_no, employeeVo.getEmp_code());

		return "redirect:/production/main_page.do";
	}

	// 댓글 작성하기
	@RequestMapping("/write_reply_process.do")
	@ResponseBody
	public String writeReply(DepartmentboardreplyVo departmentboardreplyVo, HttpSession session) {

		Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = null;

		employeeVo = (EmployeeVo) dataMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}
		// System.out.println("컨"+employeeVo.getEmp_code());
		departmentboardreplyVo.setEmp_code(employeeVo.getEmp_code());

		productionService.writeReply(departmentboardreplyVo); // board_no와 글 내용

		return "true";
	}

	// 댓글 삭제
	@RequestMapping("/delete_reply_process.do")
	public String deleteReply(int dept_board_reply_no, int dept_board_no, HttpSession session) {

		Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		int emp_code = productionService.getempcodebyreplyno(dept_board_reply_no);
		if (employeeVo.getEmp_code() == emp_code) {

			productionService.deleteReply(dept_board_reply_no); // board_no와 글 내용
		} else {
			return "commons/incorrect_access_page";
		}

		return "redirect:/production/board_read_page.do?dept_board_no=" + dept_board_no;
	}

	// 댓글 목록 가져오기
	@RequestMapping("/get_reply_list.do")
	@ResponseBody
	public List<Map<String, Object>> getReplyList(int board_no) {

		List<Map<String, Object>> list = productionService.getReplyList(board_no);

		return list;
	}

	@RequestMapping("/update_reply_process.do")
	public String updatereplyprocess(HttpSession session,
			@RequestParam(value = "dept_board_reply_no", required = false, defaultValue = "0") int dept_board_reply_no,
			@Param("dept_board_reply_content") String dept_board_reply_content,
			@RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no) {

		EmployeeVo employeeVo = new EmployeeVo();
		Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
		/*
		 * if(!check(dataMap)) { return "commons/fail_page"; }
		 */
		employeeVo = (EmployeeVo) dataMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		int emp_code = productionService.getempcodebyreplyno(dept_board_reply_no);

		if (employeeVo.getEmp_code() == emp_code) {

			productionService.updatereply(dept_board_reply_no, dept_board_reply_content);
		} else {
			return "commons/incorrect_access_page";
		}

		return "redirect:./board_read_page.do?dept_board_no=" + dept_board_no;

	}

	// 부품조회 페이지
	@RequestMapping("/component_page.do")
	public String component_page(Model model, HttpSession session, ComponentStockVo componentStockVo) {
		// 로그인한 사용자의 공장에서 취급하는 부품들만 조회 가능
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		componentStockVo.setBranch_no(employeeVo.getBranch_no());
		System.out.println("로그인branchno : " + employeeVo.getBranch_no());

		List<Map<String, Object>> list = productionService.getComponentList(employeeVo.getBranch_no());
		model.addAttribute("dataList", list);

		return "production/component_page";
	}

	// 신규 부품 등록 페이지
	@RequestMapping("/component_enroll_page.do")
	public String component_enroll_page(HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		return "production/component_enroll_page";
	}

	// 신규 부품등록 프로세스
	@RequestMapping("/component_enroll_process.do")
	@ResponseBody
	public String component_enroll_process(HttpSession session, String comp_name, String comp_price, String comp_qty,
			Model model) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		int qty = 0;

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}
		if (comp_name != null && comp_name.trim().equals("")) {
			comp_name = null;
		}
		if (comp_price != null && comp_price.trim().equals("")) {
			comp_price = null;
		}
		if (comp_qty != null && comp_qty.trim().equals("")) {
			comp_qty = null;
		} else {
			qty = Integer.parseInt(comp_qty);
		}

		productionService.insertComponent(comp_name, comp_price, qty, employeeVo.getBranch_no());

		System.out.println("부품 수량 넘어오는지 " + comp_qty);

		return "redirect:/production/component_enroll_page.do";
	}

	// 입고조회 페이지
	@RequestMapping("/in_item_page.do")
	public String in_item_page(Model model, HttpSession session,
			@RequestParam(value = "factory_in_item_code", required = false, defaultValue = "0") int factory_in_item_code,
			@RequestParam(value = "comp_no", required = false, defaultValue = "0") int comp_no, String start_date,
			String end_date, @RequestParam(value = "emp_code", required = false, defaultValue = "0") int emp_code,
			@RequestParam(value = "supplier_no", required = false, defaultValue = "0") int supplier_no,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, String sort) {

		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}
		if (sort != null && sort.trim().equals("")) {
			sort = null;
		}

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		List<Map<String, Object>> componentList = productionService.getComponentList(employeeVo.getBranch_no());
		List<Map<String, Object>> empList = productionService.empList(employeeVo.getBranch_no());
		List<Map<String, Object>> supplierList = productionService.allSupplier();
		List<Map<String, Object>> inItemList = productionService.getInItem(employeeVo.getBranch_no(),
				factory_in_item_code, comp_no, start_date, end_date, emp_code, supplier_no, currPage, sort);

		// 페이징처리
		int totalCount = productionService.getInItemListCount(factory_in_item_code, comp_no, start_date, end_date,
				emp_code, supplier_no, employeeVo.getBranch_no());
		int beginPage = ((currPage - 1) / 5) * 5 + 1;
		int endPage = ((currPage - 1) / 5 + 1) * (5);
		if (endPage > ((totalCount - 1) / 5) + 1) {
			endPage = ((totalCount - 1) / 5) + 1;
		}

		model.addAttribute("inItemList", inItemList);
		model.addAttribute("componentList", componentList);
		model.addAttribute("empList", empList);
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalCount", totalCount);

		return "production/in_item_page";
	}

	// 입고조회 리스트 보내기
	@RequestMapping("/in_item_process.do")
	@ResponseBody
	public List<Map<String, Object>> inItemProcess(HttpSession session,
			@RequestParam(value = "factory_in_item_code", required = false, defaultValue = "0") int factory_in_item_code,
			@RequestParam(value = "comp_no", required = false, defaultValue = "0") int comp_no, String start_date,
			String end_date, @RequestParam(value = "emp_code", required = false, defaultValue = "0") int emp_code,
			@RequestParam(value = "supplier_no", required = false, defaultValue = "0") int supplier_no,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage, String sort) {

		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}
		if (sort != null && sort.trim().equals("")) {
			sort = null;
		}
		System.out.println("리스트 : " + comp_no);

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		// 리스트 받아오기
		List<Map<String, Object>> inItemList = productionService.getInItem(employeeVo.getBranch_no(),
				factory_in_item_code, comp_no, start_date, end_date, emp_code, supplier_no, currPage, sort);

		return inItemList;
	}

	// 공급업체 선택하기
	@RequestMapping("/sendCompNo.do")
	@ResponseBody
	public List<SupplierVo> sendCompNo(int comp_no) {

		List<SupplierVo> dataList = productionService.supplierList(comp_no);

		if (dataList != null) {
			return dataList;
		} else {
			return null;
		}

	}

	// 미입고 발주 불러서 입고등록하는 페이지
	@RequestMapping("/none_in_item_page.do")
	public String none_in_item_page(HttpSession session, Model model) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		List<Map<String, Object>> list = productionService.getNoneOrder(employeeVo.getBranch_no());
		model.addAttribute("list", list);

		return "production/none_in_item_page";

	}

	// 입고등록 프로세스
	@RequestMapping("/in_item_enroll_process.do")
	public String in_item_process(Model model, HttpSession session, FactoryInItemVo factoryInItemVo,
			@RequestParam(value = "factory_order_detail_no", required = false, defaultValue = "0") int[] factory_order_detail_no,
			@RequestParam(value = "factory_in_item_qty", required = false, defaultValue = "0") int[] factory_in_item_qty,
			@RequestParam(value = "comp_no", required = false, defaultValue = "0") int[] comp_no,
			String check_approval) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (check_approval != null && check_approval.trim().equals("")) {
			check_approval = null;
		}

		for (int chk : factory_order_detail_no) {
			System.out.println("체크박스 몇번: " + chk);
			if (chk != 0) {
				if (factory_order_detail_no != null && factory_in_item_qty != null && comp_no != null
						&& check_approval != null) {
					productionService.addComponent(factory_order_detail_no, factory_in_item_qty,
							employeeVo.getEmp_code());
					// 승인여부 Y로 업데이트하기
					productionService.updateCheckApproval(factory_order_detail_no);
					// 부품재고 수량 업데이트
					productionService.updateComponent(factory_in_item_qty, comp_no);
				}
			}
		}

		return "redirect:/production/none_in_item_page.do";
	}

	// 출고 조회 페이지
	@RequestMapping("/out_item_page.do")
	public String out_item_page(Model model, HttpSession session,
			@RequestParam(value = "factory_out_item_no", required = false, defaultValue = "0") int factory_out_item_no,
			@RequestParam(value = "product_no", required = false, defaultValue = "0") int product_no,
			@RequestParam(value = "branch_no", required = false, defaultValue = "0") int branch_no, String start_date,
			String end_date, @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			String sort) {

		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}
		if (sort != null && sort.trim().equals("")) {
			sort = null;
		}

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		List<Map<String, Object>> productList = productionService.productList(employeeVo.getEmp_code());
		List<Map<String, Object>> branchList = productionService.branchList();

		List<Map<String, Object>> searchList = productionService.getOutItem(factory_out_item_no, product_no, branch_no,
				start_date, end_date, employeeVo.getBranch_no(), currPage, sort);

		// 페이징처리
		int totalCount = productionService.getOutItemListCount(factory_out_item_no, product_no, branch_no, start_date,
				end_date, employeeVo.getBranch_no());
		int beginPage = ((currPage - 1) / 5) * 5 + 1;
		int endPage = ((currPage - 1) / 5 + 1) * (5);
		if (endPage > ((totalCount - 1) / 5) + 1) {
			endPage = ((totalCount - 1) / 5) + 1;
		}

		model.addAttribute("productList", productList);
		model.addAttribute("branchList", branchList);
		model.addAttribute("searchList", searchList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);

		return "production/out_item_page";
	}

	// 출고조회 리스트 불러오기
	@RequestMapping("/out_item_process.do")
	@ResponseBody
	public List<Map<String, Object>> out_item_process(HttpSession session,
			@RequestParam(value = "factory_out_item_no", required = false, defaultValue = "0") int factory_out_item_no,
			@RequestParam(value = "product_no", required = false, defaultValue = "0") int product_no,
			@RequestParam(value = "branch_no", required = false, defaultValue = "0") int branch_no, String start_date,
			String end_date, @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			String sort) {

		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}
		if (sort != null && sort.trim().equals("")) {
			sort = null;
		}

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		List<Map<String, Object>> searchList = productionService.getOutItem(factory_out_item_no, product_no, branch_no,
				start_date, end_date, employeeVo.getBranch_no(), currPage, sort);

		return searchList;
	}

	// 출고등록 페이지
	@RequestMapping("/out_item_enroll_page.do")
	public String out_item_enroll_page(Model model, HttpSession session, FactoryOutItemVo factoryOutItemVo) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		List<Map<String, Object>> productList = productionService.productList(employeeVo.getEmp_code());
		List<Map<String, Object>> branchList = productionService.branchList();

		model.addAttribute("productList", productList);
		model.addAttribute("branchList", branchList);

		return "production/out_item_enroll_page";

	}

	// 출고등록 프로세스
	@RequestMapping("/out_item_enroll_process.do")
	public String out_item_enroll_process(HttpSession session, String factory_out_item_no, String product_no,
			String factory_out_item_qty, String branch_no, String factory_out_item_date) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		// String null 처리
		if (factory_out_item_date != null && factory_out_item_date.trim().equals("")) {
			factory_out_item_date = null;
		}

		// String으로 넘어온거 int로 바꾸기
		int outitemNo = Integer.parseInt(factory_out_item_no);
		System.out.println("outitem " + outitemNo);
		int productNo = Integer.parseInt(product_no);
		int outitemQty = Integer.parseInt(factory_out_item_qty);
		int branchNo = Integer.parseInt(branch_no);

		Map<String, Object> map = productionService.getFactoryNo(employeeVo.getEmp_code());
		FactoryVo factoryVo = (FactoryVo) map.get("factoryVo");

		// 출고등록하기
		productionService.addoutProduct(outitemNo, productNo, outitemQty, employeeVo.getEmp_code(),
				factory_out_item_date, branchNo);
		// 부품재고 마이너스하기
		System.out.println("제품 몇개 출고하나 : " + outitemQty);
		productionService.updateCompStock(outitemQty);

		return "redirect:/production/out_item_enroll_page.do";
	}

	// 발주등록 페이지
	@RequestMapping("/order_enroll_page.do")
	public String order_enroll_page(FactoryOrderVo factoryOrderVo, Model model, HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		List<Map<String, Object>> componentList = productionService.compList();
		List<Map<String, Object>> supplierList = productionService.allSupplier();

		int maxSeq = productionService.getSequence();
		System.out.println("팩토리오더 맥스시퀀스 값 : " + maxSeq);

		model.addAttribute("componentList", componentList);
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("maxSeq", maxSeq);

		return "production/order_enroll_page";

	}

	// 발주등록 프로세스
	@RequestMapping("/order_enroll_process.do")
	public String order_enroll_process(FactoryOrderDetailVo factoryOrderDetailVo, Model model, HttpSession session,
			FactoryOrderVo factoryOrderVo, String factory_order_res_no, String[] comp_no, String[] supplier_no,
			String[] factory_order_qty, String factory_order_note) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		System.out.println("넘어온 발주번호 : " + factory_order_res_no);

		if (factory_order_note != null) {
			// CSRF 방어
			factory_order_note = factory_order_note.replaceAll("<", "&lt");
			factory_order_note = factory_order_note.replaceAll(">", "&gt");

			// DB의 엔터 값을 <br>로 리플레이스
			factory_order_note = factory_order_note.replaceAll("\n", "<br>");
		}

		int[] comp = Arrays.stream(comp_no).mapToInt(Integer::parseInt).toArray();
		int[] supplier = Arrays.stream(supplier_no).mapToInt(Integer::parseInt).toArray();
		int[] qty = Arrays.stream(factory_order_qty).mapToInt(Integer::parseInt).toArray();

		if (comp_no != null && supplier_no != null && factory_order_qty != null) {

			productionService.componentOrderDetail(factoryOrderDetailVo, factoryOrderVo, factory_order_res_no,
					employeeVo.getEmp_code(), comp, supplier, qty, factory_order_note);

		}

		return "redirect:/production/order_enroll_page.do";
	}

	// 발주 조회 페이지
	@RequestMapping("/order_list_page.do")
	public String order_list_page(HttpSession session, Model model, String factory_order_res_no,
			@RequestParam(value = "emp_code", required = false, defaultValue = "0") int emp_code, String start_date,
			String end_date, @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			String sort) {

		if (factory_order_res_no != null && factory_order_res_no.trim().equals("")) {
			factory_order_res_no = null;
		}
		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}
		if (sort != null && sort.trim().equals("")) {
			sort = null;
		}

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		System.out.println("발주branch : " + employeeVo.getBranch_no());
		List<Map<String, Object>> empList = productionService.empList(employeeVo.getBranch_no());

		List<Map<String, Object>> orderList = productionService.getOrderList(employeeVo.getBranch_no(),
				factory_order_res_no, emp_code, start_date, end_date, currPage, sort);

		// 페이징처리
		int totalCount = productionService.getOrderListCount(employeeVo.getBranch_no(), factory_order_res_no, emp_code,
				start_date, end_date);
		int beginPage = ((currPage - 1) / 5) * 5 + 1;
		int endPage = ((currPage - 1) / 5 + 1) * (5);
		if (endPage > ((totalCount - 1) / 5) + 1) {
			endPage = ((totalCount - 1) / 5) + 1;
		}

		model.addAttribute("empList", empList);
		model.addAttribute("orderList", orderList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);

		return "production/order_list_page";
	}

	// 발주 리스트 불러오기
	@RequestMapping("/order_list_process.do")
	@ResponseBody
	public List<Map<String, Object>> order_list_process(HttpSession session, String factory_order_res_no,
			@RequestParam(value = "emp_code", required = false, defaultValue = "0") int emp_code, String start_date,
			String end_date, @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			String sort) {

		if (factory_order_res_no != null && factory_order_res_no.trim().equals("")) {
			factory_order_res_no = null;
		}
		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}
		if (sort != null && sort.trim().equals("")) {
			sort = null;
		}

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		List<Map<String, Object>> orderList = productionService.getOrderList(employeeVo.getBranch_no(),
				factory_order_res_no, emp_code, start_date, end_date, currPage, sort);

		return orderList;

	}

	// 발주 상세조회 페이지
	@RequestMapping("/order_list_read_page.do")
	public String order_list_read_page(Model model, int factory_order_code, HttpSession session) {

		List<Map<String, Object>> order = productionService.getOrder(factory_order_code);

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		for (Map<String, Object> map : order) {
			FactoryOrderDetailVo factoryOrderDetailVo = (FactoryOrderDetailVo) map.get("factoryOrderDetailVo");
			String note = factoryOrderDetailVo.getFactory_order_note();

			if (note == null) {
				factoryOrderDetailVo.setFactory_order_note("비고사항 없음");
			}
		}

		model.addAttribute("order", order);

		return "production/order_list_read_page";
	}

	// 배송 요청조회 페이지
	@RequestMapping("/delivery_request_page.do")
	public String delivery_request_page(Model model, ProcessStatusVo processStatusVo,
			StoreOrderDetailVo storeOrderDetailVo, HttpSession session) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		// 3, Y인거 뽑아서 리스트로 출력하기
		List<Map<String, Object>> list = productionService.reqDelivery(employeeVo.getBranch_no());

		model.addAttribute("list", list);

		return "production/delivery_request_page";

	}

	// 배송 요청조회 페이지에서 승인버튼 눌렀을때
	@RequestMapping("/delivery_request_process.do")
	public String delivery_request_process(ProcessStatusVo processStatusVo) {

		// 승인버튼 누르면 insert됨
		productionService.changeState(processStatusVo);

		return "redirect:/production/delivery_request_page.do";
	}

	@RequestMapping("/delivery_reject_process.do")
	public String delivery_reject_process(ProcessStatusVo processStatusVo) {

		// 거절버튼 누르면 요청거절됨
		productionService.rejectState(processStatusVo);

		return "redirect:/production/delivery_request_page.do";
	}

	// 배송 등록 목록 조회페이지
	@RequestMapping("/delivery_ready_page.do")
	public String delivery_ready_page(Model model, HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		List<Map<String, Object>> list = productionService.getReadyDelivery(employeeVo.getBranch_no());
		model.addAttribute("list", list);

		return "production/delivery_ready_page";

	}

	// 배송 등록 페이지
	@RequestMapping("/delivery_enroll_page.do")
	public String delivery_enroll_page(int store_order_detail_no, Model model, DeliveryVo deliveryVo,
			HttpSession session) {

		Map<String, Object> map = productionService.getEnroll(store_order_detail_no);
		model.addAttribute("map", map);

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		return "production/delivery_enroll_page";
	}

	// 배송 등록 프로세스
	@RequestMapping("/delivery_enroll_process.do")
	public String delivery_enroll_process(DeliveryVo deliveryVo, ProcessStatusVo processStatusVo, HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		deliveryVo.setEmp_code(employeeVo.getEmp_code());

		productionService.enrollDelivery(deliveryVo, processStatusVo);

		return "redirect:/production/delivery_ready_page.do";
	}

	// 배송 조회 페이지
	@RequestMapping("/delivery_list_page.do")
	public String delivery_list_page(Model model, HttpSession session,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "store_order_detail_no", required = false, defaultValue = "0") int store_order_detail_no,
			@RequestParam(value = "invoice_no", required = false, defaultValue = "0") int invoice_no,
			String start_date, String end_date,
			@RequestParam(value = "branch_no", required = false, defaultValue = "0") int branch_no) {

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}

		List<Map<String, Object>> list = productionService.getDeliveryList(store_order_detail_no, invoice_no,
				start_date ,end_date, currPage, employeeVo.getBranch_no(), branch_no);
		List<Map<String, Object>> branchList = productionService.branchList();

		// 페이징처리
		int totalCount = productionService.getDeliveryListCount(store_order_detail_no, invoice_no, start_date, end_date,
				currPage, employeeVo.getBranch_no(), branch_no);
		int beginPage = ((currPage - 1) / 5) * 5 + 1;
		int endPage = ((currPage - 1) / 5 + 1) * (5);
		if (endPage > ((totalCount - 1) / 5) + 1) {
			endPage = ((totalCount - 1) / 5) + 1;
		}

		model.addAttribute("list", list);
		model.addAttribute("branchList", branchList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);

		return "production/delivery_list_page";
	}

	// 배송 상세조회 페이지
	@RequestMapping("/delivery_list_read_page.do")
	public String delivery_list_read_page(int store_order_detail_no, Model model, HttpSession session) {

		List<Map<String, Object>> list = productionService.readDeliveryList(store_order_detail_no);
		model.addAttribute("list", list);

		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		return "production/delivery_list_read_page";
	}

	// 상품 발주조회 페이지
	@RequestMapping("/product_order_list_page.do")
	public String product_order_list_page(HttpSession session, Model model,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			String store_order_res_no, String branch_name, String start_date, String end_date) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		if (!deptcheck(employeeVo.getDept_no())) {
			return "commons/fail_page";
		}

		if (store_order_res_no != null && store_order_res_no.trim().equals("")) {
			store_order_res_no = null;
		}
		if (branch_name != null && branch_name.trim().equals("")) {
			branch_name = null;
		}
		if (start_date != null && start_date.trim().equals("")) {
			start_date = null;
		}
		if (end_date != null && end_date.trim().equals("")) {
			end_date = null;
		}

		List<Map<String, Object>> list = productionService.productOrderList(currPage, employeeVo.getBranch_no(),
				store_order_res_no, branch_name, start_date, end_date);
		List<Map<String, Object>> branchList = productionService.branchList();

		// 페이징처리
		int totalCount = productionService.getListCount(employeeVo.getBranch_no(), store_order_res_no, branch_name,
				start_date, end_date);
		int beginPage = ((currPage - 1) / 5) * 5 + 1;
		int endPage = ((currPage - 1) / 5 + 1) * (5);
		if (endPage > ((totalCount - 1) / 5) + 1) {
			endPage = ((totalCount - 1) / 5) + 1;
		}

		model.addAttribute("list", list);
		model.addAttribute("branchList", branchList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("beginPage", beginPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currPage", currPage);

		return "production/product_order_list_page";
	}

	@RequestMapping("/sendResCode.do")
	@ResponseBody
	public List<Map<String, Object>> sendResCode(
			@RequestParam(value = "store_order_res_code", required = false, defaultValue = "0") int store_order_res_code,
			HttpSession session) {
		Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
		EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

		List<Map<String, Object>> list = productionService.productOrderDetailList(employeeVo.getBranch_no(),
				store_order_res_code);
		System.out.println("발주코드 무엇 : " + store_order_res_code);

		if (list != null) {
			System.out.println("리스트 넘겼다");
			return list;
		} else {
			return null;
		}

	}
}