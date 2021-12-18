package com.bt.sales.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.bt.employee.service.EmployeeServiceImpl;
import com.bt.sales.service.SalesServiceImpl;
import com.bt.sales.vo.CustomerVo;
import com.bt.sales.vo.Customer_reservation_listVo;
import com.bt.vo.ProductVo;
import com.bt.vo.BranchVo;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.DeptTypeVo;
import com.bt.vo.EmployeeVo;

@Controller
@RequestMapping("/sales/*")
public class SalesController {

   @Autowired
   private SalesServiceImpl salesService;

   @RequestMapping("/fail_page.do")
   public String failpage() {

      return "commons/fail_page";
   }

   public boolean deptcheck(int dept_no) {
      if (dept_no == 30) {
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
      DeptTypeVo deptTypeVo = (DeptTypeVo) employeeMap.get("deptTypeVo");

      List<Map<String, Object>> list = salesService.getBoardList(dept_title, dept_content, dept_writer, currPage,
            deptTypeVo.getDept_type_no());

      int totalCount = salesService.getBoardCount(dept_title, dept_content, dept_writer,
            deptTypeVo.getDept_type_no());

      // 숫자 5개씩 나오게
      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);
      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }
      List<Map<String, Object>> noticelist = salesService.getnoticeBoardList(dept_title, dept_content, dept_writer,
            currPage, deptTypeVo.getDept_type_no());

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);
      model.addAttribute("totalCount", totalCount);
      model.addAttribute("dataList", list);
      model.addAttribute("noticelist", noticelist);

      return "sales/main_page";
   }

// 부서 게시판 글쓰기 페이지
   @RequestMapping("/board_write_page.do")
   public String board_write_page() {

      return "sales/board_write_page";
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

      int boardNo = salesService.createKey(departmentBoardVo);
      System.out.println("공지 게시글번호 : " + boardNo);

      if (check_notice == null) {
         // 아무것도 안날라온 경우
         salesService.writeContent(departmentBoardVo, fileVoList);
      } else {
         // 라디오체크로 넘어온 값이 Y인 경우 공지사항 등록
         salesService.writeContent(departmentBoardVo, fileVoList);
         salesService.updateCheck(boardNo, employeeVo.getEmp_code());
      }

      return "redirect:/sales/main_page.do";
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
         salesService.updateRead(dept_board_no, emp_code, dept_type_no);
      }
      // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
      else {
         System.out.println("cookie 있음");

         // 쿠키 값 받아옴.
         String value = viewCookie.getValue();

         System.out.println("cookie 값 : " + value);

      }

      Map<String, Object> map = salesService.readContent(dept_board_no, dept_type_no);
      model.addAttribute("map", map);

      // 조회수 업데이트

      return "sales/board_read_page";
   }

   // 부서 게시글 수정페이지
   // 부서 게시글 수정페이지
   @RequestMapping("/board_update_page.do")
   public String board_update_page(int dept_board_no, Model model, HttpSession session) {
      Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");

      EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

      int dept_type_no = employeeVo.getDept_no() / 10;
      Map<String, Object> map = salesService.readContent(dept_board_no, dept_type_no);
      EmployeeVo checkEmp = (EmployeeVo) map.get("employeeVo");

      if (checkEmp.getEmp_code() == employeeVo.getEmp_code() && checkEmp.getDept_no() / 10 == dept_type_no) {
         // 정상적으로 로그인한 사원과 게시글 쓴 사원이 같을 때 작동
         model.addAttribute("data",
               salesService.updateReadContent(dept_board_no, dept_type_no, employeeVo.getEmp_code()));
         return "sales/board_update_page";
      } else {
         return "commons/incorrect_access_page";
      }

   }

   // 부서 게시글 수정 프로세스

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
         salesService.update(dept_board_no, dept_board_title, dept_board_content, employeeVo.getEmp_code());
         salesService.updateCheck(dept_board_no, employeeVo.getEmp_code());
      } else {
         salesService.update(dept_board_no, dept_board_title, dept_board_content, employeeVo.getEmp_code());
      }

      salesService.updatefile(departmentBoardVo, fileVoList);

      return "redirect:/sales/main_page.do";

   }

   // 부서 게시글 삭제 프로세스
// 부서 게시글 삭제 프로세스
   @RequestMapping("/delete_process.do")
   public String board_delete_process(HttpSession session,
         @RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no) {
      Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");

      salesService.delete(dept_board_no, employeeVo.getEmp_code());

      return "redirect:/sales/main_page.do";
   }

   @RequestMapping("/write_reply_process.do")
      @ResponseBody
      public String writeReply(DepartmentboardreplyVo departmentboardreplyVo, HttpSession session) {

         Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
         /*
          * if(!check(dataMap)) { return "commons/fail_page"; }
          */
         EmployeeVo employeeVo = null;

         employeeVo = (EmployeeVo) dataMap.get("employeeVo");
         
         if(departmentboardreplyVo.getDept_board_reply_content()==null || departmentboardreplyVo.getDept_board_reply_content()=="")
         {
            return "false";
         }

         
           if(!deptcheck(employeeVo.getDept_no())) { return "commons/fail_page"; }
          
         // System.out.println("컨"+employeeVo.getEmp_code());
         departmentboardreplyVo.setEmp_code(employeeVo.getEmp_code());

         salesService.writeReply(departmentboardreplyVo); // board_no와 글 내용

         return "true";
      }

      @RequestMapping("/delete_reply_process.do")
      public String deleteReply(int dept_board_reply_no, int dept_board_no,HttpSession session) {
         
          Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
          EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");
          
         if(!deptcheck(employeeVo.getDept_no())) { return "commons/fail_page"; }
           
         int emp_code=salesService.getempcodebyreplyno(dept_board_reply_no);
         if(employeeVo.getEmp_code()==emp_code)    { 
         
             salesService.deleteReply(dept_board_reply_no); // board_no와 글 내용
         }
         else {
             return "commons/incorrect_access_page";
         }
     

         return "redirect:/sales/board_read_page.do?dept_board_no=" + dept_board_no;
      }

      @RequestMapping("/get_reply_list.do")
      @ResponseBody
      public List<Map<String, Object>> getReplyList(int board_no) {

         List<Map<String, Object>> list = salesService.getReplyList(board_no);

         return list;
      }

   @RequestMapping("/product_search_process.do")
   @ResponseBody
   public List<Map<String, Object>> productsearchProcess(HttpSession session, @Param("product_no") String product_no,
         @Param("product_name") String product_name, @Param("product_type_no") String product_type_no,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      BranchVo branchVo = new BranchVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (product_no != null && product_no.equals("")) {
         product_no = null;
      }
      if (product_type_no != null && product_type_no.equals("")) {
         product_type_no = null;
      }

      System.out.println("제품번호" + product_no + "제품유형번호" + product_type_no + "제품이름" + product_name + "sort" + sort);

      branchVo = (BranchVo) dataMap.get("branchVo");

      List<Map<String, Object>> product_list = salesService.getproduct(branchVo.getBranch_no(), product_no,
            product_name, product_type_no, sort, currPage);

      return product_list;
   }

   @RequestMapping("/product_page.do")
   public String productPage(Model model, HttpSession session, @Param("product_no") String product_no,
         @Param("product_name") String product_name, @Param("product_type_no") String product_type_no,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {
      BranchVo branchVo = new BranchVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (product_no != null && product_no.equals("")) {
         product_no = null;
      }
      if (product_type_no != null && product_type_no.equals("0")) {
         product_type_no = null;
      }

      branchVo = (BranchVo) dataMap.get("branchVo");
      System.out.println("제품" + branchVo.getBranch_no());
      List<Map<String, Object>> product_list = salesService.getproduct(branchVo.getBranch_no(), product_no,
            product_name, product_type_no, sort, currPage);

      int totalCount = salesService.getProductDataCount(branchVo.getBranch_no(), product_no, product_name,
            product_type_no);
      System.out.println(totalCount);
      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("product_list", product_list);

      return "sales/product_page";
   }

   @RequestMapping("/allproduct_price_process.do")
   @ResponseBody
   public List<Map<String, Object>> allproductpriceProcess(@Param("price") String price,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (price != null && price.equals("")) {
         price = null;
      }

      List<Map<String, Object>> product_list2 = salesService.getallproductbyprice(price, currPage);

      return product_list2;
   }

   @RequestMapping("/allproduct_protype_process.do")
   @ResponseBody
   public List<Map<String, Object>> allproductprotypeProcess(@Param("product_type_no") String product_type_no2,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (product_type_no2 != null && product_type_no2.equals("")) {
         product_type_no2 = null;
      }

      System.out.println("서치" + product_type_no2 + "정렬" + sort);

      List<Map<String, Object>> product_list2 = salesService.getproductbyprotype(product_type_no2, sort, currPage);

      return product_list2;
   }

   @RequestMapping("/product_protype_process.do")
   @ResponseBody
   public List<Map<String, Object>> productprotypeProcess(HttpSession session,
         @Param("product_type_no") String product_type_no2, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      EmployeeVo employeeVo = new EmployeeVo();

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (product_type_no2 != null && product_type_no2.equals("")) {
         product_type_no2 = null;
      }

      System.out.println("서치" + product_type_no2 + "정렬" + sort);

      List<Map<String, Object>> product_list2 = salesService.getbranchproductbyprotype(employeeVo.getBranch_no(),
            product_type_no2, sort, currPage);

      return product_list2;
   }

   @RequestMapping("/product_price_process.do")
   @ResponseBody
   public List<Map<String, Object>> productprotypeProcess(HttpSession session, @Param("price") String price,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      EmployeeVo employeeVo = new EmployeeVo();

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (price != null && price.equals("")) {
         price = null;
      }

      List<Map<String, Object>> product_list2 = salesService.getbranchproductbyprice(employeeVo.getBranch_no(), price,
            currPage);

      return product_list2;
   }

   @RequestMapping("/allproduct_search_process.do")
   @ResponseBody
   public List<Map<String, Object>> allproductsearchProcess(@Param("product_no") String product_no,
         @Param("product_name") String product_name, @Param("product_type_no") String product_type_no,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (product_no != null && product_no.equals("")) {
         product_no = null;
      }
      if (product_type_no != null && product_type_no.equals("")) {
         product_type_no = null;
      }

      System.out.println("서치" + product_no + "서치" + product_name + "서치" + product_type_no + "정렬" + sort);

      List<Map<String, Object>> product_list = salesService.getallproduct(product_no, product_name, product_type_no,
            sort, currPage);

      return product_list;
   }

   @RequestMapping("/allproduct_page.do")
   public String allproductPage(Model model, @Param("product_no") String product_no, HttpSession session,
         @Param("product_name") String product_name, @Param("product_type_no") String product_type_no,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (product_no != null && product_no.equals("")) {
         product_no = null;
      }
      if (product_type_no != null && product_type_no.equals("0")) {
         product_type_no = null;
      }

      List<Map<String, Object>> product_list = salesService.getallproduct(product_no, product_name, product_type_no,
            sort, currPage);

      int totalCount = salesService.getallProductcount(product_no, product_name, product_type_no);

      System.out.println("all" + totalCount);

      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("product_list", product_list);

      return "sales/allproduct_page";
   }

   @RequestMapping("/read_product_page.do")
   public String readproductPage(Model model, @Param("product_no") int product_no) {

      Map<String, Object> product = salesService.selectByprono(product_no);

      model.addAttribute("product", product);

      return "sales/read_product_page";
   }

   @RequestMapping("/storein_price_process.do")
   @ResponseBody
   public List<Map<String, Object>> storeinsearchProcess(HttpSession session, @Param("price") String price,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (price != null && price.equals("")) {
         price = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_in_list = salesService
            .storeinpriceview(Integer.toString(employeeVo.getBranch_no()), price, currPage);

      return store_in_list;
   }

   @RequestMapping("/storein_search_process.do")
   @ResponseBody
   public List<Map<String, Object>> storeinsearchProcess(HttpSession session,
         @RequestParam(value = "store_in_item_no", required = false, defaultValue = "0") int store_in_item_no,
         @Param("product_name") String product_name, @Param("emp_name") String emp_name,
         @Param("in_start_date") String in_start_date, @Param("in_final_date") String in_final_date,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }
      if (in_start_date != null && in_start_date.equals("")) {
         in_start_date = null;
      }
      if (in_final_date != null && in_final_date.equals("")) {
         in_final_date = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_in_list = salesService.storeinview(Integer.toString(employeeVo.getBranch_no()),
            store_in_item_no, product_name, emp_name, in_start_date, in_final_date, sort, currPage);

      return store_in_list;
   }

   @RequestMapping("/store_in_view_page.do")
   public String storeinviewPage(Model model, HttpSession session,
         @RequestParam(value = "store_in_item_no", required = false, defaultValue = "0") int store_in_item_no,
         @Param("product_name") String product_name, @Param("emp_name") String emp_name,
         @Param("in_start_date") String in_start_date, @Param("in_final_date") String in_final_date,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }
      if (in_start_date != null && in_start_date.equals("")) {
         in_start_date = null;
      }
      if (in_final_date != null && in_final_date.equals("")) {
         in_final_date = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      List<Map<String, Object>> store_in_list = salesService.storeinview(Integer.toString(employeeVo.getBranch_no()),
            store_in_item_no, product_name, emp_name, in_start_date, in_final_date, sort, currPage);

      int totalCount = salesService.storeinviewcount(Integer.toString(employeeVo.getBranch_no()), store_in_item_no,
            product_name, emp_name, in_start_date, in_final_date);

      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("store_in_list", store_in_list);

      return "sales/store_in_view_page";
   }

   @RequestMapping("/store_in_process.do")
   public String storeinProcess(HttpSession session, int check[]) {
      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (check != null) {

         for (int i = 0; i < check.length; i++) {
            salesService.insertprocess(employeeVo.getBranch_no(), employeeVo.getEmp_code(), check[i]);

         }
      }

      return "redirect:./store_in_page.do";
   }

   @RequestMapping("/storeinpage_search_process.do")
   @ResponseBody
   public List<Map<String, Object>> storeinpagesearchProcess(HttpSession session,
         @RequestParam(value = "store_in_item_no", required = false, defaultValue = "0") int store_in_item_no,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      EmployeeVo employeeVo = null;
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_order_list = salesService.storein(Integer.toString(employeeVo.getBranch_no()),
            currPage, sort);

      return store_order_list;
   }

   @RequestMapping("/store_in_page.do")
   public String storeinPage(Model model, HttpSession session, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      List<Map<String, Object>> store_order_list = salesService.storein(Integer.toString(employeeVo.getBranch_no()),
            currPage, sort);

      int totalCount = salesService.storeincount(Integer.toString(employeeVo.getBranch_no()));

      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("store_order_list", store_order_list);

      return "sales/store_in_page";
   }

   @RequestMapping("/storeout_price_process.do")
   @ResponseBody
   public List<Map<String, Object>> storeoutpriceProcess(HttpSession session, @Param("price") String price,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (price != null && price.equals("")) {
         price = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_out_list = salesService
            .storeoutpriceview(Integer.toString(employeeVo.getBranch_no()), price, currPage);

      return store_out_list;
   }

   @RequestMapping("/storeout_search_process.do")
   @ResponseBody
   public List<Map<String, Object>> storeoutsearchProcess(HttpSession session,
         @RequestParam(value = "store_out_item_no", required = false, defaultValue = "0") int store_out_item_no,
         @Param("product_name") String product_name, @Param("out_start_date") String out_start_date,
         @Param("out_final_date") String out_final_date, @Param("emp_name") String emp_name,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (out_start_date != null && out_start_date.equals("")) {
         out_start_date = null;
      }
      if (out_final_date != null && out_final_date.equals("")) {
         out_final_date = null;
      }
      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_out_list = salesService.storeoutview(
            Integer.toString(employeeVo.getBranch_no()), store_out_item_no, product_name, out_start_date,
            out_final_date, emp_name, sort, currPage);

      return store_out_list;
   }

   @RequestMapping("/store_out_view_page.do")
   public String storeoutviewPage(Model model, HttpSession session,
         @RequestParam(value = "store_out_item_no", required = false, defaultValue = "0") int store_out_item_no,
         @Param("product_name") String product_name, @Param("out_start_date") String out_start_date,
         @Param("out_final_date") String out_final_date, @Param("emp_name") String emp_name,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (out_start_date != null && out_start_date.equals("")) {
         out_start_date = null;
      }
      if (out_final_date != null && out_final_date.equals("")) {
         out_final_date = null;
      }
      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      List<Map<String, Object>> store_out_list = salesService.storeoutview(
            Integer.toString(employeeVo.getBranch_no()), store_out_item_no, product_name, out_start_date,
            out_final_date, emp_name, sort, currPage);

      int totalCount = salesService.storeoutviewcount(Integer.toString(employeeVo.getBranch_no()), store_out_item_no,
            product_name, out_start_date, out_final_date, emp_name);
      System.out.println(totalCount);
      System.out.println(currPage);
      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("store_out_list", store_out_list);

      return "sales/store_out_view_page";
   }

   @RequestMapping("/store_out_process.do")
   public String storeoutProcess(HttpSession session,
         @RequestParam(value = "product_no_2", required = false, defaultValue = "0") int product_no_2,
         @RequestParam(value = "out_item_qty", required = false, defaultValue = "0") int out_item_qty) {
      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (product_no_2 != 0 && out_item_qty != 0 && out_item_qty > 0 && out_item_qty <= 9999999) {

         salesService.storeoutprocess(employeeVo.getBranch_no(), employeeVo.getEmp_code(), product_no_2,
               out_item_qty);
         return "redirect:./store_out_page.do";
      } else
         return "commons/fail_page";

   }

   @RequestMapping("/store_out_page.do")
   public String storeoutPage(Model model, HttpSession session) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      List<Map<String, Object>> store_productlist = salesService.storeorderproductview(employeeVo.getBranch_no());

      model.addAttribute("store_productlist", store_productlist);

      return "sales/store_out_page";
   }

   @RequestMapping("/sendproductno.do")
   @ResponseBody
   public Map<String, Object> sendproductno(int product_no) {
      Map<String, Object> map = salesService.getproductprice(product_no);
      return map;
   }

   @RequestMapping("/sendproducttypeno.do")
   @ResponseBody
   public List<ProductVo> sendproducttypeno(int product_type_no, HttpSession session) {
      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<ProductVo> list = new ArrayList<>();

      list = salesService.getproductlist(product_type_no, employeeVo.getBranch_no());
      return list;
   }

   @RequestMapping("/store_order_view_process.do")
   public String storeorderviewprocess(int[] check) {

      if (check != null) {
         for (int i = 0; i < check.length; i++) {
            System.out.println("체크" + check[i]);
            salesService.rejectprocess(check[i]);

         }
      }

      return "redirect:./store_order_view_page.do";
   }

   @RequestMapping("/storeorderview_search_process.do")
   @ResponseBody
   public List<Map<String, Object>> storeorderviewsearchProcess(Model model, HttpSession session,
         @Param("store_order_res_no") String store_order_res_no, @Param("emp_name") String emp_name,
         @Param("store_order_start_date") String store_order_start_date,
         @Param("store_order_final_date") String store_order_final_date, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (store_order_start_date != null && store_order_start_date.equals("")) {
         store_order_start_date = null;
      }
      if (store_order_final_date != null && store_order_final_date.equals("")) {
         store_order_final_date = null;
      }
      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_order_viewlist = salesService.storeorderview(employeeVo.getBranch_no(),
            store_order_res_no, emp_name, store_order_start_date, store_order_final_date, sort, currPage);

      return store_order_viewlist;
   }

   @RequestMapping("/storeorderview_detail.do")
   @ResponseBody
   public List<Map<String, Object>> storeorderviewsearchProcess(Model model, HttpSession session,
         @Param("store_order_res_no") String store_order_res_no) {

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");

      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      List<Map<String, Object>> store_order_viewlist2 = salesService.getDetail(store_order_res_no);
      return store_order_viewlist2;
   }

   @RequestMapping("/store_order_view_page.do")
   public String storeorderviewPage(Model model, HttpSession session,
         @Param("store_order_res_no") String store_order_res_no, @Param("emp_name") String emp_name,
         @Param("store_order_start_date") String store_order_start_date,
         @Param("store_order_final_date") String store_order_final_date, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (store_order_start_date != null && store_order_start_date.equals("")) {
         store_order_start_date = null;
      }
      if (store_order_final_date != null && store_order_final_date.equals("")) {
         store_order_final_date = null;
      }
      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (sort == null) {
         sort = "0";
      }
      List<Map<String, Object>> store_order_viewlist = salesService.storeorderview(employeeVo.getBranch_no(),
            store_order_res_no, emp_name, store_order_start_date, store_order_final_date, sort, currPage);
      // 두번쨰 리스트
      // List<Map<String, Object>> store_order_viewlist2 =
      // salesService.getDetail(store_order_res_no);
      int totalCount = salesService.storeorderviewcount(employeeVo.getBranch_no(), store_order_res_no, emp_name,
            store_order_start_date, store_order_final_date);

      System.out.println("막" + totalCount);
      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("store_order_viewlist", store_order_viewlist);

      return "sales/store_order_view_page";
   }

   @RequestMapping("/store_order_reservation_process.do")
   public String storeorderreservationprocess(HttpSession session, String store_order_res_no, String product_no[],
         String first_order_qty[]) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (product_no != null && first_order_qty != null && store_order_res_no != null) {
         Pattern p = Pattern.compile("(^[0-9]*$)");

         boolean check = true;

         for (int i = 0; i < product_no.length; i++) {
            Matcher m = p.matcher(first_order_qty[i]);
            Matcher m2 = p.matcher(product_no[i]);
            if (!m.find() || !m2.find()) {
               check = false;
            } else if (product_no[i].equals("") || first_order_qty[i].equals("")
                  || Integer.parseInt(first_order_qty[i]) <= 0
                  || Integer.parseInt(first_order_qty[i]) > 99999999) {
               check = false;
            }
         }
         if (check) {
            salesService.insertstoreorderdetailprocess(employeeVo.getEmp_code(), store_order_res_no, product_no,
                  first_order_qty);
            return "redirect:./store_order_reservation_page.do";
         } else
            return "commons/fail_page";
      } else
         return "commons/fail_page";
   }

   @RequestMapping("/store_order_reservation_page.do")
   public String storeorderreservationPage(Model model, HttpSession session) {
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (employeeVo.getRank_no() > 5) {
         return "commons/fail_page";
      }
      List<Map<String, Object>> store_product = salesService.productselect();

      String max_storerescode = salesService.selectMaxstorerescode();
      if (max_storerescode == null)
         max_storerescode = Integer.toString(salesService.createrescodekey());

      model.addAttribute("max_storeresno", Integer.parseInt(max_storerescode));
      model.addAttribute("store_product", store_product);

      return "sales/store_order_reservation_page";
   }

   @RequestMapping("/join_customer_process.do")
   public String storecustomerjoinPage(CustomerVo customerVo) {
      Pattern p = Pattern.compile("^[가-힣a-zA-Z\\s]+$");
      Pattern p2 = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");

      boolean check = true;
      Matcher m = p.matcher(customerVo.getCustomer_name());
      Matcher m2 = p2.matcher(customerVo.getCustomer_phone());
      Matcher m3 = p.matcher(customerVo.getCustomer_address());

      if (!m.find() || !m2.find() || !m3.find()) {
         check = false;
      }
      if (check) {
         salesService.joinCustomer(customerVo);
         return "redirect:./customer_page.do";
      }
      return "commons/fail_page";
   }

   @RequestMapping("/customer_page.do")
   public String storecustomerPage(CustomerVo customerVo, HttpSession session) {
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      return "sales/customer_page";
   }

   @ResponseBody
   @RequestMapping("/customer_search_process.do")
   public List<Map<String, Object>> customersearch(Model model, @Param("customer_name") String customer_name,
         @Param("customer_phone") String customer_phone, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (customer_name != null && customer_name.equals("")) {
         customer_name = null;
      }
      if (customer_phone != null && customer_phone.equals("")) {
         customer_phone = null;
      }

      List<Map<String, Object>> customer_list = salesService.customerview(customer_name, customer_phone, sort,
            currPage);

      return customer_list;
   }

   @RequestMapping("/customer_view_page.do")
   public String storecustomerviewPage(Model model, HttpSession session, @Param("customer_name") String customer_name,
         @Param("customer_phone") String customer_phone, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (customer_name != null && customer_name.equals("")) {
         customer_name = null;
      }
      if (customer_phone != null && customer_phone.equals("")) {
         customer_phone = null;
      }

      List<Map<String, Object>> customer_list = salesService.customerview(customer_name, customer_phone, sort,
            currPage);

      int totalCount = salesService.customerviewcount(customer_name, customer_phone);

      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("customer_list", customer_list);

      return "sales/customer_view_page";
   }

   @RequestMapping("/customer_reservation_process.do")
   public String storecustomerjoinPage(Customer_reservation_listVo customer_reservation_listVo, HttpSession session,
         @RequestParam(value = "customer_no", required = false, defaultValue = "0") int customer_no,
         @Param("product_name") String product_name,
         @RequestParam(value = "customer_res_qty", required = false, defaultValue = "0") int customer_res_qty) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (customer_no != 0 && product_name != null && customer_res_qty != 0) {

         int max_cusno = salesService.maxcusno();
         if (customer_no > 0 && customer_no <= max_cusno && customer_res_qty > 0 && customer_res_qty <= 99999999) {

            salesService.storeorderreservation(product_name, customer_res_qty, customer_no,
                  employeeVo.getEmp_code());
            return "redirect:./customer_reservation_page.do";
         }
      }
      return "commons/fail_page";

   }

   @RequestMapping("/customer_reservation_page.do")
   public String storecustomerreservationPage(Model model, HttpSession session,
         @RequestParam(value = "radio", required = false, defaultValue = "0") int radio) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      List<Map<String, Object>> store_product = salesService.storeorderresview(employeeVo.getBranch_no());
      String cusname = "no";
      if (radio != 0) {
         cusname = salesService.getcustomername(radio);
         System.out.println(cusname);
      }

      int max_cusno = salesService.maxcusno();
      model.addAttribute("cusname", cusname);
      model.addAttribute("cusno", radio);
      model.addAttribute("store_product", store_product);
      model.addAttribute("max_cusno", max_cusno);
      return "sales/customer_reservation_page";
   }

   @ResponseBody
   @RequestMapping("/customer_reservationsearch_process.do")
   public List<Map<String, Object>> customerreservationsearch(Model model,
         @RequestParam(value = "res_no", required = false, defaultValue = "0") int res_no,
         @Param("customer_name") String customer_name, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (customer_name != null && customer_name.equals("")) {
         customer_name = null;
      }

      List<Map<String, Object>> customer_reservation_list = salesService.customerreservationview(res_no,
            customer_name, sort, currPage);

      return customer_reservation_list;
   }

   @ResponseBody
   @RequestMapping("/customer_showprocess.do")
   public List<Map<String, Object>> customerreservationshow(Model model,
         @RequestParam(value = "order_status_no", required = false, defaultValue = "0") int order_status_no,
         @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      if (sort == null) {
         sort = "1";
      }
      List<Map<String, Object>> customer_reservation_list2 = salesService.customerreservationshow(order_status_no,
            sort, currPage);

      return customer_reservation_list2;
   }

   @RequestMapping("/customer_reservation_view_page.do")
   public String storecustomerreservationviewPage(Model model, HttpSession session,
         @RequestParam(value = "order_status_no", required = false, defaultValue = "0") int order_status_no,
         @Param("customer_name") String customer_name, @Param("sort") String sort,
         @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (customer_name != null && customer_name.equals("")) {
         customer_name = null;
      }
      if (sort == null) {
         sort = "7";
      }
      List<Map<String, Object>> customer_reservation_list = salesService.customerreservationview(order_status_no,
            customer_name, sort, currPage);

      int totalCount = salesService.customerreservationviewcount(order_status_no, customer_name);

      int beginPage = ((currPage - 1) / 5) * 5 + 1;
      int endPage = ((currPage - 1) / 5 + 1) * (5);

      if (endPage > ((totalCount - 1) / 10) + 1) {
         endPage = ((totalCount - 1) / 10) + 1;
      }

      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("currPage", currPage);

      model.addAttribute("totalCount", totalCount);
      model.addAttribute("customer_reservation_list", customer_reservation_list);

      return "sales/customer_reservation_view_page";
   }

   @RequestMapping("/read_detail_order_page.do")
   public String storereaddetailorderPage(String store_order_res_no, HttpSession session, Model model) {
      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      List<Map<String, Object>> read_detail_list = salesService.getDetail(store_order_res_no);

      model.addAttribute("read_detail_list", read_detail_list);
      return "sales/read_detail_order_page";
   }

   @RequestMapping("/customer_order_reservation_process.do")
   public String customerorderresrvation(HttpSession session, String res_no[]) {

      EmployeeVo employeeVo = new EmployeeVo();
      Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
      /*
       * if(!check(dataMap)) { return "commons/fail_page"; }
       */
      employeeVo = (EmployeeVo) dataMap.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      if (res_no != null) {
         Calendar cal = Calendar.getInstance();

         String year = Integer.toString(cal.get(cal.YEAR));
         year = year.substring(2, 4);
         String month = Integer.toString(cal.get(cal.MONTH) + 1);
         if (month.length() == 1) {
            month = "0" + month;
         }
         String day = Integer.toString(cal.get(cal.DATE));
         if (day.length() == 1) {
            day = "0" + day;
         }
         String max_storerescode = salesService.selectMaxstorerescode();
         if (max_storerescode == null)
            max_storerescode = Integer.toString(salesService.createrescodekey());

         max_storerescode = Integer.toString(Integer.parseInt(max_storerescode) + 1);
         if (max_storerescode.length() == 1) {
            max_storerescode = "00" + max_storerescode;
         } else if (max_storerescode.length() == 2) {
            max_storerescode = "0" + max_storerescode;
         }

         if (Integer.parseInt(max_storerescode) < 1000) {
            max_storerescode = "A" + max_storerescode;
         } else {
            max_storerescode = "B" + max_storerescode;
         }

         String store_order_res_no = max_storerescode + year + month + day;
         System.out.println(store_order_res_no);

         salesService.insertstoreorderdetailprocessfromcus(employeeVo.getEmp_code(), store_order_res_no, res_no);
         salesService.updatecustomerreservation(res_no);

      }

      return "redirect:./customer_reservation_view_page.do";
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

      int emp_code = salesService.getempcodebyreplyno(dept_board_reply_no);

      if (employeeVo.getEmp_code() == emp_code) {

         salesService.updatereply(dept_board_reply_no, dept_board_reply_content);
      } else {
         return "commons/incorrect_access_page";
      }

      return "redirect:./board_read_page.do?dept_board_no=" + dept_board_no;

   }

}