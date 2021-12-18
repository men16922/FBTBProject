package com.bt.management.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bt.employee.service.EmployeeServiceImpl;
import com.bt.management.service.AreaServiceImpl;
import com.bt.management.service.BranchService;
import com.bt.management.service.ManagementServiceImpl;
import com.bt.management.service.ProductServiceImpl;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.BranchVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.ProductFileVo;
import com.bt.vo.ProductVo;

@Controller
@RequestMapping("/management/*")
public class ManagementController {

   @Autowired // 메일 발송 용
   private JavaMailSender mailSender;

   @Autowired
   private ProductServiceImpl productService;
   @Autowired
   private BranchService branchService; // 다형성 적용 (의존성의 분리 적용)
   @Autowired
   private AreaServiceImpl areaService;
   @Autowired
   private EmployeeServiceImpl employeeService;
   @Autowired
   private ManagementServiceImpl managementService;

   @RequestMapping("/fail_page.do")
   public String failpage() {

      return "management/fail_page";
   }

   public boolean deptcheck(int dept_no) {
      if (dept_no / 10 == 1) {
         return true;
      } else
         return false;

   }

   // --------------------------------------------------------------------------------------------------부서게시판

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

      List<Map<String, Object>> list = managementService.getBoardList(dept_title, dept_content, dept_writer, currPage,
            dept_type_no);
      List<Map<String, Object>> noticelist = managementService.getnoticeBoardList(dept_title, dept_content,
            dept_writer, currPage, dept_type_no);

      int totalCount = managementService.getBoardCount(dept_title, dept_content, dept_writer, dept_type_no);
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

      return "management/main_page";
   }

   // 부서 게시판 글쓰기 페이지
   @RequestMapping("/board_write_page.do")
   public String board_write_page(HttpSession session) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      return "management/board_write_page";

   }

   // 부서 게시판 글쓰기 프로세스
   @RequestMapping("/board_write_process.do")
   public String board_write_process(HttpSession session, DepartmentBoardVo departmentBoardVo,
         MultipartFile[] board_upload_files, String check_notice) {
      // ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~
      String board_content = departmentBoardVo.getDept_board_content();
      // CSRF 방어
      board_content = board_content.replaceAll("<", "&lt");
      board_content = board_content.replaceAll(">", "&gt");

      // DB의 엔터 값을 <br>로 리플레이스
      board_content = board_content.replaceAll("\n", "<br>");

      departmentBoardVo.setDept_board_content(board_content);

      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

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

      int dept_type_no = employeeVo.getDept_no() / 10;

      departmentBoardVo.setEmp_code(employeeVo.getEmp_code());
      departmentBoardVo.setDept_type_no(dept_type_no);

      int boardNo = managementService.createKey(departmentBoardVo);
      System.out.println("공지 게시글번호 : " + boardNo);

      if (check_notice == null) {
         // 아무것도 안날라온 경우
         managementService.writeContent(departmentBoardVo, fileVoList);
      } else {
         // 라디오체크로 넘어온 값이 Y인 경우 공지사항 등록
         managementService.writeContent(departmentBoardVo, fileVoList);
         managementService.updateCheck(boardNo, employeeVo.getEmp_code());
      }

      return "redirect:/management/main_page.do";
   }

   // 부서 게시판 읽기
   @RequestMapping("/board_read_page.do")
   public String board_read_page(int dept_board_no, Model model, HttpSession session, HttpServletRequest request,
         HttpServletResponse response) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      int dept_type_no = employeeVo.getDept_no() / 10;

      Map<String, Object> map = managementService.readContent(dept_board_no, dept_type_no);
      model.addAttribute("map", map);
      // ++++++++++++++++++++++++조휘수 쿠키처리++++++++++++++++++++++++++++++
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
         // 조회수 업데이트
         managementService.updateRead(dept_board_no);
      }
      // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
      else {
         System.out.println("cookie 있음");

         // 쿠키 값 받아옴.
         String value = viewCookie.getValue();

         System.out.println("cookie 값 : " + value);

      }
      // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

      return "management/board_read_page";

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
      Map<String, Object> map = managementService.readContent(dept_board_no, dept_type_no);
      EmployeeVo checkEmp = (EmployeeVo) map.get("employeeVo");

      if (checkEmp.getEmp_code() == employeeVo.getEmp_code() && checkEmp.getDept_no() / 10 == dept_type_no) {
         // 정상적으로 로그인한 사원과 게시글 쓴 사원이 같을 때 작동
         model.addAttribute("data",
               managementService.updateReadContent(dept_board_no, dept_type_no, employeeVo.getEmp_code()));
         return "management/board_update_page";
      } else {
         return "commons/incorrect_access_page";
      }

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
         managementService.update(dept_board_no, dept_board_title, dept_board_content, employeeVo.getEmp_code());
         managementService.updateCheck(dept_board_no, employeeVo.getEmp_code());
      } else {
         managementService.update(dept_board_no, dept_board_title, dept_board_content, employeeVo.getEmp_code());
      }

      managementService.updatefile(departmentBoardVo, fileVoList);

      return "redirect:/management/main_page.do";

   }

   // 부서 게시글 삭제 프로세스
   // 부서 게시글 삭제 프로세스
   @RequestMapping("/delete_process.do")
   public String board_delete_process(HttpSession session,
         @RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no) {
      Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

      managementService.delete(dept_board_no, employeeVo.getEmp_code());

      return "redirect:/management/main_page.do";
   }

   // 댓글 작성하기 프로세스
   @RequestMapping("/write_reply_process.do")
   @ResponseBody
   public String writeReply(DepartmentboardreplyVo departmentboardreplyVo, HttpSession session) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      departmentboardreplyVo.setEmp_code(employeeVo.getEmp_code());

      managementService.writeReply(departmentboardreplyVo); // board_no와 글 내용

      return "true";

   }

   // 댓글 삭제하기 프로세스
   @RequestMapping("/delete_reply_process.do")
   public String deleteReply(int dept_board_reply_no, int dept_board_no, HttpSession session) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      int emp_code = managementService.getempcodebyreplyno(dept_board_reply_no);
      if (employeeVo.getEmp_code() == emp_code) {

         managementService.deleteReply(dept_board_reply_no); // board_no와 글 내용
      } else {
         return "commons/incorrect_access_page";
      }

      return "redirect:/management/board_read_page.do?dept_board_no=" + dept_board_no;

   }

   // 댓글 목록 가져오기
   @RequestMapping("/get_reply_list.do")
   @ResponseBody
   public List<Map<String, Object>> getReplyList(int board_no) {

      List<Map<String, Object>> list = managementService.getReplyList(board_no);

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

      int emp_code = managementService.getempcodebyreplyno(dept_board_reply_no);

      if (employeeVo.getEmp_code() == emp_code) {

         managementService.updatereply(dept_board_reply_no, dept_board_reply_content);
      } else {
         return "commons/incorrect_access_page";
      }

      return "redirect:./board_read_page.do?dept_board_no=" + dept_board_no;

   }

   @ResponseBody
   @RequestMapping("/test5.do")
   public List<Map<String, Object>> test5(
         @RequestParam(value = "dept_no", defaultValue = "1", required = false) int dept_no) {
      List<Map<String, Object>> list = branchService.getBranchListByDeptNo(dept_no);

      return list;

   }

   @ResponseBody
   @RequestMapping("/test6.do")
   public List<Map<String, Object>> test6(
         @RequestParam(value = "dept_no", defaultValue = "0", required = false) int dept_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no,
         @RequestParam(value = "rank_no", defaultValue = "0", required = false) int rank_no, String emp_name,
         String start_date, @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page,
         @RequestParam(value = "orderby", required = false, defaultValue = "1") int orderby) {
      if (emp_name == null) {
         emp_name = "";
      }
      if (start_date == null) {
         start_date = "";
      }

      List<Map<String, Object>> list = branchService.getBoardList_employee(dept_no, branch_no, emp_no, rank_no,
            emp_name, start_date, curr_page, orderby);

      return list;

   }

   @ResponseBody
   @RequestMapping("/employee_view_process.do")
   public List<Map<String, Object>> employee_view_process(
         @RequestParam(value = "dept_no", defaultValue = "0", required = false) int dept_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no,
         @RequestParam(value = "rank_no", defaultValue = "0", required = false) int rank_no, String emp_name,
         String start_date, Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page,
         @RequestParam(value = "orderby", required = false, defaultValue = "1") int orderby) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (start_date == null) {
         start_date = "";
      }

      List<Map<String, Object>> list = branchService.getBoardList_employee(dept_no, branch_no, emp_no, rank_no,
            emp_name, start_date, curr_page, orderby);

      return list;
   }

   @RequestMapping("/employee_view.do")
   public String employee_view(@RequestParam(value = "dept_no", defaultValue = "0", required = false) int dept_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no,
         @RequestParam(value = "rank_no", defaultValue = "0", required = false) int rank_no, String emp_name,
         String start_date, Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page,
         @RequestParam(value = "orderby", required = false, defaultValue = "1") int orderby, HttpSession session) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (start_date == null) {
         start_date = "";
      }
      System.out.println("부서:" + dept_no + "지점:" + branch_no + "직급:" + rank_no + "직원명:" + emp_name + "emp_no :"
            + emp_no + "입사일:" + start_date);
      if (session.getAttribute("sessionUser") != null) {

         List<Map<String, Object>> list = branchService.getBoardList_employee(dept_no, branch_no, emp_no, rank_no,
               emp_name, start_date, curr_page, orderby);
         List<Map<String, Object>> list_for_branchList = branchService.select_ShowAllBranch();

         System.out.println("qqq : " + list.size());

         // ++++++++++++++++++++++++++페이징++++++++++++++++++++++++++++++++++++++
         int totalCount = branchService.getBoardList_employee_paging(dept_no, branch_no, emp_no, rank_no, emp_name,
               start_date, orderby);
         // 페이지바가 5씩 나오도록 (1~5, 6~10, 11~15)

         // System.out.println("토탈 카운트"+totalCount);
         int beginPage = ((curr_page - 1) / 5) * 5 + 1;
         int endPage = ((curr_page - 1) / 5 + 1) * 5;

         // 글 개수에 따라 마지막 페이지 숫자가 달라짐
         if (endPage > ((totalCount - 1) / 10 + 1)) {
            endPage = (totalCount - 1) / 10 + 1;
         }

         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("curr_page", curr_page);

         model.addAttribute("totalCount", totalCount);
         // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         model.addAttribute("boardList_employee_main", list);
         model.addAttribute("BranchListForEmployee", list_for_branchList);
      }
      return "management/employee_view"; // 사원 조회

   }

   /*
    * @ResponseBody
    * 
    * @RequestMapping("/test4.do") public List<Map<String,Object>>
    * test(@RequestParam(value = "branch_no" , defaultValue = "1" ,required =
    * false) int branch_no) { List<Map<String,Object>> list_for_store =
    * branchService.getBoardList4();//매장리스트
    * 
    * return list_for_store;
    * 
    * }
    */

   @ResponseBody
   @RequestMapping("/test4.do")
   public List<Map<String, Object>> test(
         @RequestParam(value = "branch_type_no", defaultValue = "1", required = false) int branch_type_no) {
      List<Map<String, Object>> list = branchService.getBoardList_Bybranch_type_no(branch_type_no);

      return list;

   }

   @RequestMapping("/employee_add.do")
   public String employee_add(Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {

         List<Map<String, Object>> list_for_branchList = branchService.select_ShowAllBranch();
         int emp = employeeService.selectMaxEmpNoforCreateNewEmpNo();

         model.addAttribute("empNoForEmployee", emp);
         model.addAttribute("BranchListForEmployee2", list_for_branchList);
      }
      return "management/employee_add"; // 사원 등록

   }

   @RequestMapping("/employee_confirm.do")
   public String employee_confirm(int emp_no, Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         EmployeeVo employeeVo = employeeService.selectByEmpNo_for_confirm(emp_no);
         DeptVo deptVo = employeeService.selectDeptName(emp_no);
         EmployeeRankVo employeeRankVo = employeeService.selectRankByEmpNo(emp_no);
         BranchVo branchVo = employeeService.selectBranchByEmpNo(emp_no);

         model.addAttribute("employeeVo", employeeVo);
         model.addAttribute("deptVo", deptVo);
         model.addAttribute("employeeRankVo", employeeRankVo);
         model.addAttribute("branchVo", branchVo);

      }

      return "management/employee_confirm"; // 사원 등록

   }

   @RequestMapping("/employee_modify.do")
   public String employee_modify(int emp_no, Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         EmployeeVo employeeVo = employeeService.selectByEmpNo_for_confirm(emp_no);
         DeptVo deptVo = employeeService.selectDeptName(emp_no);
         EmployeeRankVo employeeRankVo = employeeService.selectRankByEmpNo(emp_no);
         BranchVo branchVo = employeeService.selectBranchByEmpNo(emp_no);
         List<Map<String, Object>> list_for_branchList = branchService.select_ShowAllBranch();

         model.addAttribute("employeeVo", employeeVo);
         model.addAttribute("deptVo", deptVo);
         model.addAttribute("employeeRankVo", employeeRankVo);
         model.addAttribute("branchVo", branchVo);
         model.addAttribute("BranchListForEmployee", list_for_branchList);
      }
      return "management/employee_modify"; // 사원 등록

   }

   @RequestMapping("/employee_modify_process.do")
   public String employee_modify_process(
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no, String emp_name,
         @RequestParam(value = "dept_no", defaultValue = "0", required = false) int dept_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "rank_no", defaultValue = "0", required = false) int rank_no, String emp_email,
         String emp_phone, String emp_address, Model model, HttpSession session) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (emp_email == null) {
         emp_email = "";
      }
      if (emp_phone == null) {
         emp_phone = "";
      }
      if (emp_address == null) {
         emp_address = "";
      }

      if (dept_no == 10) {
         branch_no = 1;
      } else if (dept_no == 21) {
         branch_no = 2;
      } else if (dept_no == 22) {
         branch_no = 3;
      } else if (dept_no == 23) {
         branch_no = 4;
      }

      System.out.println("??????????????" + emp_no);
      if (session.getAttribute("sessionUser") != null) {
         EmployeeVo employeeVo = employeeService.selectByEmpNo_for_confirm(emp_no);
         DeptVo deptVo = employeeService.selectDeptName(emp_no);
         EmployeeRankVo employeeRankVo = employeeService.selectRankByEmpNo(emp_no);
         BranchVo branchVo = employeeService.selectBranchByEmpNo(emp_no);

         model.addAttribute("employeeVo", employeeVo);
         model.addAttribute("deptVo", deptVo);
         model.addAttribute("employeeRankVo", employeeRankVo);
         model.addAttribute("branchVo", branchVo);

         employeeService.modifyEmployee(emp_no, emp_name, dept_no, branch_no, rank_no, emp_email, emp_phone,
               emp_address);
      }
      return "redirect:/management/employee_view.do";

   }

   @RequestMapping("/employee_add_process.do")
   public String employee_add_process(String emp_name, String emp_email, String emp_phone, String start_date,
         String emp_address, String emp_sex,
         @RequestParam(value = "dept_no", defaultValue = "0", required = false) int dept_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "rank_no", defaultValue = "0", required = false) int rank_no, String emp_residentnum,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no, Model model,
         HttpSession session) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (emp_email == null) {
         emp_email = "";
      }
      if (emp_phone == null) {
         emp_phone = "";
      }
      if (start_date == null) {
         start_date = "";
      }
      if (emp_address == null) {
         emp_address = "";
      }
      if (emp_sex == null) {
         emp_sex = "";
      }
      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      if (dept_no == 10) {
         branch_no = 1;
      } else if (dept_no == 21) {
         branch_no = 2;
      } else if (dept_no == 22) {
         branch_no = 3;
      } else if (dept_no == 23) {
         branch_no = 4;
      }

      // ~~~~~~~~~~~~~~~Back-End 예외처리~~~~~~~~~~~~~~~~~~~~~~~~~~

      boolean regex = false;

      String hangul_pattern = "^[ㄱ-ㅎ가-힣]*$";
      regex = Pattern.matches(hangul_pattern, emp_name);
      if (regex == false) {
         System.out.println("1");
         return "commons/fail_page";
      }

      String email_pattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
      regex = Pattern.matches(email_pattern, emp_email);
      if (regex == false) {
         System.out.println("2");
         return "commons/fail_page";
      }

      String phone_pattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$"; // 전화번호 정규식
      regex = Pattern.matches(phone_pattern, emp_phone);
      if (regex == false) {
         System.out.println("3");
         return "commons/fail_page";
      }
      if (start_date == "") {
         System.out.println("4");
         return "commons/fail_page";
      }
      /*
       * String address_pattern = "\\x00-\\x7F"; //모든아스키 regex =
       * Pattern.matches(address_pattern,emp_address); if(regex==false)
       * {System.out.println("5"); return "commons/fail_page"; }
       */
      String residentnum_pattern = "^[0-9]+$"; // 숫자만
      regex = Pattern.matches(residentnum_pattern, emp_residentnum);
      if (regex == false) {
         System.out.println("6");
         return "commons/fail_page";
      }

      if (emp_sex == "" || rank_no == 0 || emp_residentnum == "" || emp_no == 0) {
         System.out.println("7");
         return "commons/fail_page";
      }

      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      // ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

      // CSRF 방어
      emp_address = emp_address.replaceAll("<", "&lt");
      emp_address = emp_address.replaceAll(">", "&gt");

      // DB의 엔터 값을 <br>로 리플레이스
      emp_address = emp_address.replaceAll("\n", "<br>");

      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      if (session.getAttribute("sessionUser") != null) {
         employeeService.addEmployee(emp_name, emp_email, emp_phone, start_date, emp_address, emp_sex, dept_no,
               branch_no, rank_no, emp_residentnum, emp_no);

      }

      return "management/employee_add_complete";
   }

   @RequestMapping("/employee_add_complete_mailing_process.do")
   public String employee_add_complete_mailing_process(int emp_no, String emp_email, String emp_residentnum,
         HttpSession session) {

      if (session.getAttribute("sessionUser") != null) {

         FBMailSenderThread thread = new FBMailSenderThread(mailSender, emp_no, emp_email, emp_residentnum);
         // ID, 키값 넘겨주고
         thread.start(); // 메일링 시작

      }
      return "redirect:/management/employee_add.do";
   }

   @RequestMapping("/employee_resignation_confirm.do")
   public String employee_resignation_confirm(
         @RequestParam(value = "emp_code", defaultValue = "0", required = false) int emp_code, String emp_name,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no, Model model,
         HttpSession session) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (session.getAttribute("sessionUser") != null) {
      }

      return "management/employee_resignation_confirm"; // 사원 퇴사 프로세스 완료
   }

   @RequestMapping("/employee_resignation_process.do")
   public String employee_resignation_process(
         @RequestParam(value = "emp_code", defaultValue = "0", required = false) int emp_code, String emp_name,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no, Model model,
         HttpSession session) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (session.getAttribute("sessionUser") != null) {

         employeeService.resignationEmployee(emp_code, emp_name, emp_no);
      }
      return "redirect:./main_page.do"; // 사원 퇴사 프로세스 완료
   }

   @RequestMapping("/employee_add_complete.do")
   public String employee_add_complete(HttpSession session) {

      if (session.getAttribute("sessionUser") != null) {
      }
      return "management/employee_add_complete"; // 사원등록 완료
   }

   @RequestMapping("/employee_resignation.do")
   public String employee_resignation(
         @RequestParam(value = "dept_no", defaultValue = "444", required = false) int dept_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "emp_no", defaultValue = "0", required = false) int emp_no,
         @RequestParam(value = "rank_no", defaultValue = "0", required = false) int rank_no, String emp_name,
         String start_date, Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page, String end_date,
         HttpSession session) {

      if (emp_name == null) {
         emp_name = "";
      }
      if (start_date == null) {
         start_date = "";
      }
      if (end_date == null) {
         end_date = "";
      }
      if (session.getAttribute("sessionUser") != null) {

         List<Map<String, Object>> list = branchService.getBoardList_employee_resignation(dept_no, branch_no, emp_no,
               rank_no, emp_name, start_date, curr_page, end_date);
         List<Map<String, Object>> list_for_branchList = branchService.select_ShowAllBranch();

         System.out.println("qqq : " + list.size());

         // ++++++++++++++++++++++++++페이징++++++++++++++++++++++++++++++++++++++
         int totalCount = branchService.getBoardList_employee_paging_resignation(dept_no, branch_no, emp_no, rank_no,
               emp_name, start_date, end_date);
         // 페이지바가 5씩 나오도록 (1~5, 6~10, 11~15)

         // System.out.println("토탈 카운트"+totalCount);
         int beginPage = ((curr_page - 1) / 5) * 5 + 1;
         int endPage = ((curr_page - 1) / 5 + 1) * 5;

         // 글 개수에 따라 마지막 페이지 숫자가 달라짐
         if (endPage > ((totalCount - 1) / 10 + 1)) {
            endPage = (totalCount - 1) / 10 + 1;
         }

         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("curr_page", curr_page);

         model.addAttribute("totalCount", totalCount);
         // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         model.addAttribute("boardList_employee_main", list);
         model.addAttribute("BranchListForEmployee", list_for_branchList);

      }

      return "management/employee_resignation"; // 사원_퇴사자 관리

   }

   @RequestMapping("/store_view.do")
   public String store_view(@RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         String emp_name, String branch_address, Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page,
         HttpSession session) {
      if (emp_name == null) {
         emp_name = "";
      }
      if (branch_address == null) {
         branch_address = "";
      }

      System.out.println("ppp : " + branch_no + " : " + emp_name + ":" + branch_address);
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> list = branchService.getBoardList_store(branch_no, emp_name, branch_address,
               curr_page);

         List<Map<String, Object>> list_for_store = branchService.getBoardList4();

         System.out.println("qqq : " + list.size());

         // ++++++++++++++++++++++++++페이징++++++++++++++++++++++++++++++++++++++
         int totalCount = branchService.getBoardList_store_paging(branch_no, emp_name, branch_address);
         // 페이지바가 5씩 나오도록 (1~5, 6~10, 11~15)

         // System.out.println("토탈 카운트"+totalCount);
         int beginPage = ((curr_page - 1) / 5) * 5 + 1;
         int endPage = ((curr_page - 1) / 5 + 1) * 5;

         // 글 개수에 따라 마지막 페이지 숫자가 달라짐
         if (endPage > ((totalCount - 1) / 10 + 1)) {
            endPage = (totalCount - 1) / 10 + 1;
         }

         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("curr_page", curr_page);

         model.addAttribute("totalCount", totalCount);
         // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         model.addAttribute("boardList_store_main", list);
         model.addAttribute("dataList_store", list_for_store);
      }
      return "management/store_view"; // 매장 조회

   }

   @RequestMapping("/store_add.do")
   public String store_add(Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> emp_list = employeeService.selectEmployeeByBranchNo_forStore_AllList();
         model.addAttribute("emp_list", emp_list);
      }
      return "management/store_add"; // 매장 등록

   }

   @RequestMapping("/store_add_process.do")
   public String store_add_process(String branch_name, String branch_address, String branch_phone,
         HttpSession session) {

      // ~~~~~~~~~~~~~~~Back-End 예외처리~~~~~~~~~~~~~~~~~~~~~~~~~~

      boolean regex = false;

      String phone_pattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$"; // 전화번호 정규식
      regex = Pattern.matches(phone_pattern, branch_phone);
      if (regex == false) {
         System.out.println("3");
         return "commons/fail_page";
      }
      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      // ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

      // CSRF 방어
      branch_address = branch_address.replaceAll("<", "&lt");
      branch_address = branch_address.replaceAll(">", "&gt");
      branch_name = branch_name.replaceAll("<", "&lt");
      branch_name = branch_name.replaceAll("<", "&lt");
      // DB의 엔터 값을 <br>로 리플레이스
      branch_address = branch_address.replaceAll("\n", "<br>");
      branch_name = branch_name.replaceAll("\n", "<br>");

      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      int branch_no = 0;

      if (session.getAttribute("sessionUser") != null) {
         branchService.addStore(branch_no, branch_name, branch_address, branch_phone);
      }
      return "redirect:./store_add.do"; // 공장 등록 프로세스
   }

   @RequestMapping("/product_view.do")
   public String product_view(Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page,
         HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> list = productService.getBoardList(curr_page);
         model.addAttribute("dataList", list);

         // ++++++++++++++++++++++++++페이징++++++++++++++++++++++++++++++++++++++
         int totalCount = productService.getBoardList_product_paging();
         // 페이지바가 5씩 나오도록 (1~5, 6~10, 11~15)

         // System.out.println("토탈 카운트"+totalCount);
         int beginPage = ((curr_page - 1) / 5) * 5 + 1;
         int endPage = ((curr_page - 1) / 5 + 1) * 5;

         // 글 개수에 따라 마지막 페이지 숫자가 달라짐
         if (endPage > ((totalCount - 1) / 10 + 1)) {
            endPage = (totalCount - 1) / 10 + 1;
         }

         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("curr_page", curr_page);

         model.addAttribute("totalCount", totalCount);
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

      }

      return "management/product_view"; // 상품 조회
   }

   @RequestMapping("/product_add.do")
   public String product_add(HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
      }
      return "management/product_add"; // 관리부 메인 페이지 글 쓰기
   }

   @RequestMapping("/product_add_process.do")
   public String product_add_process(MultipartFile[] product_upload_files, ProductVo productVo, HttpSession session) {
      // ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

      String content = productVo.getProduct_detail();

      // CSRF 방어
      content = content.replaceAll("<", "&lt");
      content = content.replaceAll(">", "&gt");

      // DB의 엔터 값을 <br>로 리플레이스
      content = content.replaceAll("\n", "<br>");

      productVo.setProduct_detail(content);
      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      if (session.getAttribute("sessionUser") != null) {
         // 파일 업로드
         // 업로드된 파일 저장 경로
         String uploadRootFolderName = "C:/upload/";
         
         Date today = new Date(); // 오늘 날짜 나옴
         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd"); // Date to String
         String todayFolder = df.format(today);

         String saveFolderName = uploadRootFolderName + todayFolder; // 업로드된 파일 저장될 폴더 만듦

         File saveFolder = new File(saveFolderName); // File: directory 관리

         if (!saveFolder.exists()) {
            saveFolder.mkdirs(); // mkdir: 폴더 하나 만듦. mkdirs: 폴더 경로대로 만듦
         }

         ProductFileVo fileVoList = null;

         // 업로드된 파일 하나씩 받아오기
         for (MultipartFile file : product_upload_files) {
            // 예외처리: 파일 없을 때 0이 들어가기 때문
            if (file.getSize() <= 0) {
               continue;
            }

            // 파일 중복을 막기 위해 저장할 때 이름 바뀌어서 저장되도록 함. 원래 파일명에 시간(ms단위)과 랜덤숫자 붙임
            String saveFileName = UUID.randomUUID().toString(); // 랜덤숫자 - math.random() 써도 됨

            saveFileName += "_" + System.currentTimeMillis(); // 시간 추가

            String originalFileName = file.getOriginalFilename();

            saveFileName += originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자 추가

            // "/" 넣어줘야 c:C:/upload/20200514/파일명이 됨. 없으면 c:C:/upload/20200514파일명이 됨
            String saveRealPath = saveFolderName + "/" + saveFileName;
            System.out.println(saveRealPath);
            try {
               file.transferTo(new File(saveRealPath)); // File객체: file 관리
               System.out.println("test getSize: " + file.getSize());
               System.out.println("test getOriginalFilename: " + file.getOriginalFilename());

            } catch (Exception e) {
               e.printStackTrace();

            }

            // DB를 위한 UploadFileVo객체 생성
            ProductFileVo uploadFileVo = new ProductFileVo();
            uploadFileVo.setProduct_file_link_path(todayFolder + "/" + saveFileName); // /2020/05/14/파일명
            uploadFileVo.setProduct_file_real_path(saveRealPath);
            fileVoList = uploadFileVo;

         }
         // 데이터 처리

         // boardVo.setMember_no(memberVo.getMember_no());

         // boardService.writeContent(boardVo, fileVoList);
         productService.addProduct(productVo, fileVoList);

      }
      System.out
            .println("%%%%%%%%" + productVo.getProduct_no() + productVo.getProduct_price() + product_upload_files);

      return "redirect:./product_view.do";
   }

   @RequestMapping("/product_modify.do")
   public String product_modify(HttpSession session, Model model,
         @RequestParam(value = "product_no", required = false, defaultValue = "1") int product_no,
         @RequestParam(value = "product_type_no", required = false, defaultValue = "1") int product_type_no) {
      if (session.getAttribute("sessionUser") != null) {

         Map<String, Object> boardContent = productService.getProductInfo(product_no);

         model.addAttribute("product_modify", boardContent);

      }
      return "management/product_modify"; // 관리부 메인 페이지 글 쓰기
   }

   @RequestMapping("/product_modify_process.do")
   public String product_modify_process(HttpSession session, Model model, int product_no,
         MultipartFile[] product_upload_files, ProductVo productVo) {
      if (session.getAttribute("sessionUser") != null) {
         // 파일 업로드
         // 업로드된 파일 저장 경로
         String uploadRootFolderName = "C:/upload/";
         
         Date today = new Date(); // 오늘 날짜 나옴
         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd"); // Date to String
         String todayFolder = df.format(today);

         String saveFolderName = uploadRootFolderName + todayFolder; // 업로드된 파일 저장될 폴더 만듦

         File saveFolder = new File(saveFolderName); // File: directory 관리

         if (!saveFolder.exists()) {
            saveFolder.mkdirs(); // mkdir: 폴더 하나 만듦. mkdirs: 폴더 경로대로 만듦
         }

         ProductFileVo fileVoList = null;

         // 업로드된 파일 하나씩 받아오기
         for (MultipartFile file : product_upload_files) {
            // 예외처리: 파일 없을 때 0이 들어가기 때문
            if (file.getSize() <= 0) {
               continue;
            }

            // 파일 중복을 막기 위해 저장할 때 이름 바뀌어서 저장되도록 함. 원래 파일명에 시간(ms단위)과 랜덤숫자 붙임
            String saveFileName = UUID.randomUUID().toString(); // 랜덤숫자 - math.random() 써도 됨

            saveFileName += "_" + System.currentTimeMillis(); // 시간 추가

            String originalFileName = file.getOriginalFilename();

            saveFileName += originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자 추가

            // "/" 넣어줘야 c:C:/upload/20200514/파일명이 됨. 없으면 c:C:/upload/20200514파일명이 됨
            String saveRealPath = saveFolderName + "/" + saveFileName;
            System.out.println(saveRealPath);
            try {
               file.transferTo(new File(saveRealPath)); // File객체: file 관리
               System.out.println("test getSize: " + file.getSize());
               System.out.println("test getOriginalFilename: " + file.getOriginalFilename());

            } catch (Exception e) {
               e.printStackTrace();

            }

            // DB를 위한 UploadFileVo객체 생성
            ProductFileVo uploadFileVo = new ProductFileVo();
            uploadFileVo.setProduct_file_link_path(todayFolder + "/" + saveFileName); // /2020/05/14/파일명
            uploadFileVo.setProduct_file_real_path(saveRealPath);
            fileVoList = uploadFileVo;

         }
         // 데이터 처리

         // boardVo.setMember_no(memberVo.getMember_no());

         // boardService.writeContent(boardVo, fileVoList);
         productService.modifyProduct(productVo, fileVoList);

      }
      System.out.println("!!!!!!!!!!!" + productVo.getProduct_name());

      return "redirect:./product_view.do";
   }

   @RequestMapping("/product_delete_process.do")
   public String product_delete_process(HttpSession session,
         @RequestParam(value = "product_no", defaultValue = "0", required = false) int product_no) {
      if (session.getAttribute("sessionUser") != null) {
         productService.deleteProduct(product_no);
      }
      return "redirect:./product_view.do"; // 관리부 메인 페이지 글 쓰기
   }

   @RequestMapping("/factory_view.do")
   public String factory_view(@RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         String emp_name, String branch_address, Model model,
         @RequestParam(value = "curr_page", defaultValue = "1", required = false) int curr_page,
         HttpSession session) {
      if (emp_name == null) {
         emp_name = "";
      }
      if (branch_address == null) {
         branch_address = "";
      }
      System.out.println("ppp : " + branch_no + " : " + emp_name + ":" + branch_address);
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> list = branchService.getBoardList_factory(branch_no, emp_name, branch_address,
               curr_page);
         List<Map<String, Object>> list3 = branchService.getBoardList3();

         System.out.println("qqq : " + list.size());
         // ++++++++++++++++++++++++++페이징++++++++++++++++++++++++++++++++++++++
         int totalCount = branchService.getBoardList_factory_paging(branch_no, emp_name, branch_address);
         // 페이지바가 5씩 나오도록 (1~5, 6~10, 11~15)

         // System.out.println("토탈 카운트"+totalCount);
         int beginPage = ((curr_page - 1) / 5) * 5 + 1;
         int endPage = ((curr_page - 1) / 5 + 1) * 5;

         // 글 개수에 따라 마지막 페이지 숫자가 달라짐
         if (endPage > ((totalCount - 1) / 10 + 1)) {
            endPage = (totalCount - 1) / 10 + 1;
         }

         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("curr_page", curr_page);

         model.addAttribute("totalCount", totalCount);
         // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
         model.addAttribute("boardList_factory", list);
         model.addAttribute("dataList3", list3);
      }

      return "management/factory_view"; // 공장 조회
   }

   @RequestMapping("/factory_add.do")
   public String factory_add(Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> emp_list = employeeService.selectEmployeeByBranchNo_forFactory_AllList();
         model.addAttribute("emp_list", emp_list);
      }
      return "management/factory_add"; // 공장 등록
   }

   @RequestMapping("/factory_add_process.do")
   public String factory_add_process(String branch_name, String branch_address, String branch_phone,
         HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {

         // ~~~~~~~~~~~~~~~Back-End 예외처리~~~~~~~~~~~~~~~~~~~~~~~~~~

         boolean regex = false;

         String phone_pattern = "^\\d{2,3}-\\d{3,4}-\\d{4}$"; // 전화번호 정규식
         regex = Pattern.matches(phone_pattern, branch_phone);
         if (regex == false) {
            System.out.println("3");
            return "commons/fail_page";
         }
         // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

         // ~~~~~~~~~~~~~~~~~CSRF 방어,SQLInjection~~~~~~~~~~~~~~~~~~~~~~~

         // CSRF 방어
         branch_address = branch_address.replaceAll("<", "&lt");
         branch_address = branch_address.replaceAll(">", "&gt");
         branch_name = branch_name.replaceAll("<", "&lt");
         branch_name = branch_name.replaceAll("<", "&lt");
         // DB의 엔터 값을 <br>로 리플레이스
         branch_address = branch_address.replaceAll("\n", "<br>");
         branch_name = branch_name.replaceAll("\n", "<br>");

         // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

         int branch_no = 0;

         branchService.addFactory(branch_no, branch_name, branch_address, branch_phone);
      }
      return "redirect:./factory_add.do"; // 공장 등록 프로세스
   }

   /*
    * @RequestMapping("/logistics_management.do") public String
    * logistics_management(@RequestParam(value = "dept_no", defaultValue = "10" ,
    * required = false) int dept_no, String branch_name, Model model) { public
    * String logistics_management(@RequestParam(value = "dept_no", defaultValue =
    * "10" , required = false) int dept_no, String branch_name, Model model) {
    * 
    * if(branch_name==null) { branch_name = ""; } List<Map<String,Object>> list =
    * branchService.getBoardList(dept_no, branch_name);
    * 
    * model.addAttribute("dataList",list);
    * 
    * return "management/logistics_management"; //물류 관리 }
    */
   @RequestMapping("/logistics_management.do")
   public String logistics_management(
         @RequestParam(value = "dept_no", defaultValue = "0", required = false) int dept_no, String branch_name,
         Model model, @RequestParam(value = "curr_page", defaultValue = "1", required = false) int curr_page,
         HttpSession session) {
      if (branch_name == null) {
         branch_name = "";
      }
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> list = null;
         System.out.println("ppp : " + dept_no + " : " + branch_name);

         list = branchService.getBoardList(dept_no, branch_name, curr_page);

         List<Map<String, Object>> list2 = branchService.getBoardList2();
         System.out.println("qqq : " + list.size());

         // ++++++++++++++++++++++++++페이징++++++++++++++++++++++++++++++++++++++
         int totalCount = branchService.getBoardList_logistics_paging(dept_no, branch_name);
         // 페이지바가 5씩 나오도록 (1~5, 6~10, 11~15)

         // System.out.println("토탈 카운트"+totalCount);
         int beginPage = ((curr_page - 1) / 5) * 5 + 1;
         int endPage = ((curr_page - 1) / 5 + 1) * 5;

         // 글 개수에 따라 마지막 페이지 숫자가 달라짐
         if (endPage > ((totalCount - 1) / 10 + 1)) {
            endPage = (totalCount - 1) / 10 + 1;
         }

         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("curr_page", curr_page);

         model.addAttribute("totalCount", totalCount);
         // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         model.addAttribute("dataList", list);
         model.addAttribute("dataList2", list2); // 물류 1팀, 2팀, 3팀 선택
      }
      return "management/logistics_management"; // 물류 관리
   }

   @RequestMapping("/logistics_add.do")
   public String logistics_add(Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         List<Map<String, Object>> storeList = branchService.getBoardList_All_store();
         List<Map<String, Object>> list = branchService.getBoardList2();

         model.addAttribute("storeList", storeList);
         model.addAttribute("dataList2", list);
      }
      return "management/logistics_add"; // 물류 관리
   }

   @RequestMapping("/logistics_add_process.do")
   public String logistics_add_process(
         @RequestParam(value = "dept_no", defaultValue = "21", required = false) int dept_no, String branch_name,
         HttpSession session) {
      if (branch_name == null) {
         branch_name = "";
      }
      if (session.getAttribute("sessionUser") != null) {
         areaService.addArea(dept_no, branch_name);
      }
      return "redirect:./logistics_add.do"; // 물류 관리
   }

   /*
    * @RequestMapping("/write_content_management_page.do") public String
    * write_content_management_page(HttpSession session) {
    * if(session.getAttribute("sessionUser") != null) {} return
    * "management/write_content_management_page"; //관리부 메인 페이지 글 쓰기 }
    */
   /*
    * @RequestMapping("/read_content_management_page.do") public String
    * read_content_management_page(HttpSession session) {
    * if(session.getAttribute("sessionUser") != null) {} return
    * "management/read_content_management_page"; //관리부 메인 페이지 글 읽기 }
    */
   @RequestMapping("/read_store_page.do")
   public String read_store_page(
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no, Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page,
         HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         BranchVo list = branchService.selectByBranchNo_read_store_page(branch_no, curr_page);
         EmployeeVo employeeVo = employeeService.selectByEmpCode(list.getEmp_code());

         model.addAttribute("branchVo", list);
         model.addAttribute("employeeVo", employeeVo);

         int employeeVoForCount = employeeService.selectCountByBranchNo(branch_no);
         model.addAttribute("employeeVoForCount", employeeVoForCount);

      }
      return "management/read_store_page";
   }

   @RequestMapping("/store_modify.do")
   public String store_modify(@RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         Model model, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {

         System.out.println("ppp : " + branch_no);

         BranchVo list = branchService.selectByBranchNo_read_store_page(branch_no, 1);
         EmployeeVo employeeVo = employeeService.selectByEmpCode(list.getEmp_code());
         List<Map<String, Object>> emp_list = employeeService.selectEmployeeByBranchNo_forStore(branch_no);
         model.addAttribute("emp_list", emp_list);

         model.addAttribute("branchVo", list);
         model.addAttribute("employeeVo", employeeVo);

      }
      return "management/store_modify";
   }

   @RequestMapping("/store_modify_process.do")
   public String store_modify_process(
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no, String branch_name,
         @RequestParam(value = "emp_code", defaultValue = "0", required = false) int emp_code, String branch_phone,
         String branch_address, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         branchService.modifyStore(branch_no, branch_name, emp_code, branch_phone, branch_address);
      }
      return "redirect:./store_view.do";
   }

   @RequestMapping("/read_factory_page.do")
   public String read_factory_page(
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "emp_code", defaultValue = "0", required = false) int emp_code, Model model,
         @RequestParam(value = "curr_page", required = false, defaultValue = "1") int curr_page, HttpSession session)

   {

      System.out.println("ppp : " + branch_no + ":" + emp_code);
      if (session.getAttribute("sessionUser") != null) {

         BranchVo list = branchService.selectByBranchNo_read_factory_page(branch_no, curr_page);
         EmployeeVo employeeVo = employeeService.selectByEmpCode(emp_code);

         model.addAttribute("branchVo", list);
         model.addAttribute("employeeVo", employeeVo);

         int employeeVoForCount = employeeService.selectCountByBranchNo(branch_no);
         model.addAttribute("employeeVoForCount", employeeVoForCount);

      }

      return "management/read_factory_page";
   }

   @RequestMapping("/factory_modify.do")
   public String factory_modify(@RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         Model model, HttpSession session) {
      System.out.println("ppp : " + branch_no);
      if (session.getAttribute("sessionUser") != null) {
         BranchVo list = branchService.selectByBranchNo_read_factory_page(branch_no, 1);
         EmployeeVo employeeVo = employeeService.selectByEmpCode(list.getEmp_code());

         List<Map<String, Object>> emp_list = employeeService.selectEmployeeByBranchNo_forFactory(branch_no);
         model.addAttribute("emp_list", emp_list);

         model.addAttribute("branchVo", list);
         model.addAttribute("employeeVo", employeeVo);
      }
      return "management/factory_modify";
   }

   @RequestMapping("/factory_modify_process.do")
   public String factory_modify_process(
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no, String branch_name,
         @RequestParam(value = "emp_code", defaultValue = "0", required = false) int emp_code, String branch_phone,
         String branch_address, HttpSession session) {
      if (session.getAttribute("sessionUser") != null) {
         branchService.modifyFactory(branch_no, branch_name, emp_code, branch_phone, branch_address);
      }
      return "redirect:./factory_view.do";
   }

   @RequestMapping("/read_product_page.do")
   public String read_product_page(
         @RequestParam(value = "product_no", defaultValue = "0", required = false) int product_no, Model model,
         HttpSession session) {
      System.out.println("ppp : " + product_no);
      if (session.getAttribute("sessionUser") != null) {

         Map<String, Object> boardContent = productService.getProductInfo(product_no);

         model.addAttribute("read_product_page", boardContent);

      }
      return "management/read_product_page";
   }

   @RequestMapping("/sample_page.do")
   public String sample_page(Model model) {
      int mon = 0;

      mon = 1;
      Integer employeeVo1 = employeeService.getCountByMonth(mon);
      if (employeeVo1 == null) {
         employeeVo1 = 0;
      }
      model.addAttribute("count1", employeeVo1);

      mon = 2;
      Integer employeeVo2 = employeeService.getCountByMonth(mon);
      if (employeeVo2 == null) {
         employeeVo2 = 0;
      }
      model.addAttribute("count2", employeeVo2);

      mon = 3;
      Integer employeeVo3 = employeeService.getCountByMonth(mon);
      if (employeeVo3 == null) {
         employeeVo3 = 0;
      }
      model.addAttribute("count3", employeeVo3);

      mon = 4;
      Integer employeeVo4 = employeeService.getCountByMonth(mon);
      if (employeeVo4 == null) {
         employeeVo4 = 0;
      }
      model.addAttribute("count4", employeeVo4);

      mon = 5;
      Integer employeeVo5 = employeeService.getCountByMonth(mon);
      if (employeeVo5 == null) {
         employeeVo5 = 0;
      }
      model.addAttribute("count5", employeeVo5);

      mon = 6;
      Integer employeeVo6 = employeeService.getCountByMonth(mon);
      if (employeeVo6 == null) {
         employeeVo6 = 0;
      }
      model.addAttribute("count6", employeeVo6);
      mon = 7;
      Integer employeeVo7 = employeeService.getCountByMonth(mon);
      if (employeeVo7 == null) {
         employeeVo7 = 0;
      }
      model.addAttribute("count7", employeeVo7);
      mon = 8;
      Integer employeeVo8 = employeeService.getCountByMonth(mon);
      if (employeeVo8 == null) {
         employeeVo8 = 0;
      }
      model.addAttribute("count8", employeeVo8);
      mon = 9;
      Integer employeeVo9 = employeeService.getCountByMonth(mon);
      if (employeeVo9 == null) {
         employeeVo9 = 0;
      }
      model.addAttribute("count9", employeeVo9);

      mon = 10;
      Integer employeeVo10 = employeeService.getCountByMonth(mon);
      if (employeeVo10 == null) {
         employeeVo10 = 0;
      }
      model.addAttribute("count10", employeeVo10);

      mon = 11;
      Integer employeeVo11 = employeeService.getCountByMonth(mon);
      if (employeeVo11 == null) {
         employeeVo11 = 0;
      }
      model.addAttribute("count11", employeeVo11);

      mon = 12;
      Integer employeeVo12 = employeeService.getCountByMonth(mon);
      if (employeeVo12 == null) {
         employeeVo12 = 0;
      }
      model.addAttribute("count12", employeeVo12);
      return "management/sample_page";
   }

}

//========================== 메일링 용 ==================================

class FBMailSenderThread extends Thread { // 메일링을 위한 Thread 처리

   private String to;
   private JavaMailSender mailSender;
   private int emp_no;
   private String emp_residentnum;

   public FBMailSenderThread(JavaMailSender mailSender, int emp_no, String emp_email, String emp_residentnum) {

      this.mailSender = mailSender;
      this.to = emp_email;
      this.emp_no = emp_no;
      this.emp_residentnum = emp_residentnum;
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
         text += emp_residentnum;
         text += "<br>";
         text += "비밀 번호는 회원 가입시 입력한 생년월일 입니다.";
         text += "<a href='" + link + "'>";

         messageHelper.setText(text, true);
         messageHelper.setFrom("111", "BT전자관리자"); // 발송자 이름
         messageHelper.setTo(to);// 보내는 메일을 받을 메일 주소
         // messageHelper.addInline(contentId, dataSource);
         mailSender.send(message);

      } catch (Exception e) {
         e.printStackTrace();
      }

   }
   // ========================== 메일링 용 ==================================

}