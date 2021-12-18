package com.bt.logistics.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.bt.logistics.service.LogisticsServiceImpl;
import com.bt.order.service.OrderServiceImpl;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.StoreOrderDetailVo;

@Controller
@RequestMapping("/logistics/*")
public class LogisticsController {

   @Autowired
   private LogisticsServiceImpl logisticsServiceImpl;
   
   @Autowired
   private OrderServiceImpl orderServiceImpl;

   

   public boolean check(Map<String, Object> employeeMap) {

      if (employeeMap == null) {
         return false;
      } else
         return true;
   }

   public boolean deptcheck(int dept_no) {
      if (dept_no/10 == 2) {
         return true;
      } else
         return false;

   }


   // -----------------------------------------------------------------------------------발주조회

   
   // 물류팀이 담당하는 지점들 발주 리스트 뽑기
   @RequestMapping("/read_list_order_page.do")
   public String readListOrderPage(HttpSession session, Model model,
         @RequestParam(value = "store_order_detail_no", defaultValue = "0", required = false) int store_order_detail_no,
         @Param("store_order_res_no") String store_order_res_no, @Param("emp_name") String emp_name,
         @Param("product_name") String product_name, @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @RequestParam(value = "currPage", defaultValue = "1", required = false) int currPage) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      

      if (!check(sessionData)) {
         return "commons/fail_page";
      }
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }
      
      
      //파라미터 null처리
      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }

      if (start_date != null && start_date.equals("")) {
         start_date = null;
      }
      if (end_date != null && end_date.equals("")) {
         end_date = null;
      }

      System.out.println("검색 제품명 : " + product_name);
      System.out.println("검색발주상세 : " + store_order_detail_no);
      System.out.println("검색시작날짜 : " + start_date);
      System.out.println("검색마지막날짜 : " + end_date);
      System.out.println("담당자이름 : " + emp_name);
      System.out.println("현재페이지" + currPage);

      if (currPage == 0) {
         currPage = 1;
      }

      int dept_no = employeeVo.getDept_no();

      List<Map<String, Object>> dataList = logisticsServiceImpl.getAllListByDeptNo(dept_no, store_order_res_no,
            store_order_detail_no, product_name, emp_name, start_date, end_date, currPage);

      // 전체 게시글 수
      int totalCount = logisticsServiceImpl.countAllList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

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

      return "/logistics/read_list_order_page";
   }

   
   //발주번호로 상세번호 리스트 조회
   @RequestMapping("/sendOrderResCode.do")
   @ResponseBody
   public List<Map<String, Object>> sendOrderResNo(
         @RequestParam(value = "store_order_res_code", defaultValue = "0", required = false) int store_order_res_code
         ){
      
      List<Map<String, Object>> detailList = logisticsServiceImpl.getOrderDetailList(store_order_res_code);

      return detailList;
      
   }
   
   
   
   
   
   
   // -----------------------------------------------------------------------------------1차검토

   // 1차검토 페이지
   @RequestMapping("/first_check_page.do")
   public String firstCheckPage(HttpSession session, Model model,
         @Param("store_order_res_no") String store_order_res_no,
         @RequestParam(value = "store_order_detail_no", defaultValue = "0", required = false) int store_order_detail_no,
         @Param("emp_name") String emp_name, @Param("product_name") String product_name,
         @Param("start_date") String start_date, @Param("end_date") String end_date,
         @RequestParam(value = "currPage", defaultValue = "1", required = false) int currPage) {

      // process_no=1 && flag 'Y'인 발주상세번호 조회

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      //파라미터 null처리
      if (currPage == 0) {
         currPage = 1;
      }

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (start_date != null && start_date.equals("")) {
         start_date = null;
      }
      if (end_date != null && end_date.equals("")) {
         end_date = null;
      }

      int dept_no = employeeVo.getDept_no();
      List<Map<String, Object>> dataList = logisticsServiceImpl.getListByProNoOne(dept_no, store_order_res_no,
            store_order_detail_no, product_name, emp_name, start_date, end_date, currPage);

      // 전체 게시글 수
      int totalCount = logisticsServiceImpl.countFirstCheckList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

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

      return "/logistics/first_check_page";
   }

   // 수정 없는 1차검토 프로세스
   @RequestMapping("/first_check_process.do")
   public String firstCheckProcess(HttpSession session, int[] check_detail, int[] check_qty) {
      // manegement테이블에 기존수량이 INSERT , branch_no 0으로
      // process_no=2 && flag 'Y' INSERT

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      
      if (!check(sessionData)) {
         return "commons/fail_page";
      }
      
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      int first_emp_code = employeeVo.getEmp_code();

      for (int data : check_detail) {
         System.out.println("디테일체크 " + data);
      }

      for (int data2 : check_qty) {
         System.out.println("수량체크 " + data2);
      }

      if (check_detail != null && check_qty != null) {

         logisticsServiceImpl.insertFirstCheck(check_detail, check_qty, first_emp_code);

      }

      return "redirect:/logistics/first_check_page.do";

   }

   // 초기수량 수정페이지
   @RequestMapping("/update_first_qty_page.do")
   public String updatefirstQtyPage(int store_order_detail_no, Model model, HttpSession session, int return_page) {
      // 주문상세번호 조회

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }
      
      Map<String, Object> detailMap = orderServiceImpl.getDetailOrder(store_order_detail_no);
      List<Map<String, Object>> statusList = orderServiceImpl.getDetailOrderStatus(store_order_detail_no);

      model.addAttribute("detailMap", detailMap);
      model.addAttribute("statusList", statusList);
      model.addAttribute("returnPage", return_page);
      
      return "/logistics/update_first_qty_page";

   }

   // 수정 있는 1차검토 프로세스
   @RequestMapping("/update_first_qty_process.do")
   public String updatefirstQtyProcess(HttpSession session, @Param("store_order_detail_no") int store_order_detail_no,
         @Param("confirm_qty") int confirm_qty,
         int return_page) {

      // manegement테이블에 확정수량 INSERT, branch_no null로
      // process_no=2 && flag 'U' INSERT
      // process_no=2 && flag 'Y' INSERT

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      int first_emp_code = employeeVo.getEmp_code();

      logisticsServiceImpl.updatefirstQtyAndFirstCheck(store_order_detail_no, confirm_qty, first_emp_code);
      
      if(return_page == 1) {
         
         return "redirect:/order/read_detail_order_page.do?store_order_detail_no="+store_order_detail_no;
         
      }else {
         
         return "redirect:/logistics/first_check_page.do";
      }
      
      

   }

   // -----------------------------------------------------------------------------------출고요청

   // 출고요청 페이지
   @RequestMapping("/last_check_page.do")
   public String lastCheckPage(HttpSession session, Model model,
         @Param("store_order_res_no") String store_order_res_no,
         @RequestParam(value = "store_order_detail_no", defaultValue = "0", required = false) int store_order_detail_no,
         @Param("emp_name") String emp_name, @Param("product_name") String product_name,
         @Param("start_date") String start_date, @Param("end_date") String end_date,
         @RequestParam(value = "currPage", defaultValue = "1", required = false) int currPage) {
      // process_no=2 && flag 'Y'인 발주상세번호 조회

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      //파라미터 null처리
      if (currPage == 0) {
         currPage = 1;
      }

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (start_date != null && start_date.equals("")) {
         start_date = null;
      }
      if (end_date != null && end_date.equals("")) {
         end_date = null;
      }
      
      

      int dept_no = employeeVo.getDept_no();
      List<Map<String, Object>> dataList = logisticsServiceImpl.getListByProNoTwo(dept_no, store_order_res_no,
            store_order_detail_no, product_name, emp_name, start_date, end_date, currPage);

      // 전체 게시글 수
      int totalCount = logisticsServiceImpl.countLastCheckList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

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

      List<List<Map<String, Object>>> factoryDataList = new ArrayList<List<Map<String, Object>>>();

      for (Map<String, Object> data : dataList) {

         StoreOrderDetailVo storeOrderDetailVo = (StoreOrderDetailVo) data.get("storeOrderDetailVo");
         System.out.println("리스트발주상세 " + storeOrderDetailVo.getStore_order_detail_no());

         List<Map<String, Object>> list = logisticsServiceImpl
               .getFactory(storeOrderDetailVo.getStore_order_detail_no());

//         for(Map<String, Object> data2 : list) {         
//            StoreOrderDetailVo storeOrderDetailVo2 = (StoreOrderDetailVo)data2.get("storeOrderDetailVo");
//            FactoryVo factoryVo = (FactoryVo)data2.get("factoryVo");
//            System.out.println("발주상세번호 : "+storeOrderDetailVo2.getStore_order_detail_no());
//            System.out.println("제품유형번호 : "+factoryVo.getProduct_type_no()); 
//          }

         factoryDataList.add(list);

      }

      model.addAttribute("factoryDataList", factoryDataList);
      model.addAttribute("dataList", dataList);

      return "/logistics/last_check_page";
   }

   // 수정없는 출고요청 프로세스
   @RequestMapping("/last_check_process.do")
   public String lastCheckProcess(HttpSession session, int[] check_detail, int[] check_branch) {
      // manegement테이블에 branch_no(담당공장) , end_emp)code UPDATE
      // process_no=3 && flag 'Y' INSERT

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      int end_emp_code = employeeVo.getEmp_code();

      for (int data : check_detail) {
         System.out.println("디테일체크 " + data);
      }

      for (int data2 : check_branch) {
         System.out.println("브랜치넘버 " + data2);
      }

      if (check_detail != null && check_branch != null) {

         logisticsServiceImpl.updateBranchNoAndLastCheck(check_detail, check_branch, end_emp_code);
      }

      return "redirect:/logistics/last_check_page.do";
   }

   // 확정수량 수정페이지
   @RequestMapping("/update_confirm_qty_page.do")
   public String updateLastQtyPage(int store_order_detail_no, Model model, HttpSession session, int return_page) {
      // 주문상세번호 조회

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      Map<String, Object> detailMap = orderServiceImpl.getDetailOrder(store_order_detail_no);
      List<Map<String, Object>> statusList = orderServiceImpl.getDetailOrderStatus(store_order_detail_no);
      List<Map<String, Object>> factoryList = logisticsServiceImpl.getFactory(store_order_detail_no);

      model.addAttribute("detailMap", detailMap);
      model.addAttribute("statusList", statusList);
      model.addAttribute("factoryList", factoryList);
      model.addAttribute("returnPage", return_page);

      return "/logistics/update_confirm_qty_page";

   }

   // 수정있는 출고요청 프로세스
   @RequestMapping("/update_confirm_qty_process.do")
   public String updateLastQtyProcess(HttpSession session,
         @RequestParam(value = "store_order_detail_no", defaultValue = "0", required = false) int store_order_detail_no,
         @RequestParam(value = "branch_no", defaultValue = "0", required = false) int branch_no,
         @RequestParam(value = "confirm_qty", defaultValue = "0", required = false) int confirm_qty,
         int return_page) {
      // manegement테이블 확정수량 UPDATE
      // manegement테이블에 branch_no(담당공장) UPDATE
      // process_no=3 && flag 'U' INSERT
      // process_no=3 && flag 'Y' INSERT

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      int end_emp_code = employeeVo.getEmp_code();

      logisticsServiceImpl.updateConfirmQtyAndlastCheck(store_order_detail_no, confirm_qty, branch_no, end_emp_code);

      
      
      if(return_page == 1) {
         
         return "redirect:/order/read_detail_order_page.do?store_order_detail_no="+store_order_detail_no;
         
      }else {
         
         return "redirect:/logistics/last_check_page.do";
      }
      
      
      
   }

   // ----------------------------------------------------------------------------------발주반려

   // 반려 가능한 발주리스트 조회 1&Y , 2&Y , 3&Y
   @RequestMapping("/delete_order_page.do")
   public String deleteOrderPage(HttpSession session, Model model,
         @Param("store_order_res_no") String store_order_res_no,
         @RequestParam(value = "store_order_detail_no", defaultValue = "0", required = false) int store_order_detail_no,
         @Param("emp_name") String emp_name, @Param("product_name") String product_name,
         @Param("start_date") String start_date, @Param("end_date") String end_date,
         @RequestParam(value = "currPage", defaultValue = "1", required = false) int currPage
         ) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      //파라미터 null처리
      if (currPage == 0) {
         currPage = 1;
      }

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (start_date != null && start_date.equals("")) {
         start_date = null;
      }
      if (end_date != null && end_date.equals("")) {
         end_date = null;
      }

      
      
      int dept_no = employeeVo.getDept_no();

      List<Map<String, Object>> dataList = logisticsServiceImpl.getListForReject(dept_no, store_order_res_no,
            store_order_detail_no, product_name, emp_name, start_date, end_date, currPage);

      // 전체 게시글 수
      int totalCount = logisticsServiceImpl.countDeleteList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

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

      int return_page = 2;
      
      model.addAttribute("currPage", currPage);
      model.addAttribute("beginPage", beginPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("totalCount", totalCount);
      model.addAttribute("dataList", dataList);
      model.addAttribute("returnPage", return_page);
      
      return "/logistics/delete_order_page";

   }
   
   // 상세페이지 공장수정
      @RequestMapping("/update_delete_page.do")
      public String updateDeletePage(int store_order_detail_no, Model model, HttpSession session, int return_page) {
         // 주문상세번호 조회

         // 세션처리하기!(부서,로그아웃)
         Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
         EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

         if (!check(sessionData)) {
            return "commons/fail_page";
         }

         if (!deptcheck(employeeVo.getDept_no())) {
            return "commons/fail_page";
         }

         
         
         Map<String, Object> detailMap = orderServiceImpl.getDetailOrder(store_order_detail_no);
         List<Map<String, Object>> statusList = orderServiceImpl.getDetailOrderStatus(store_order_detail_no);
         List<Map<String, Object>> factoryList = logisticsServiceImpl.getFactory(store_order_detail_no);

         model.addAttribute("detailMap", detailMap);
         model.addAttribute("statusList", statusList);
         model.addAttribute("factoryList", factoryList);
         model.addAttribute("returnPage", return_page);

         return "/logistics/update_delete_page";

      }

   @RequestMapping("/delete_order_process.do")
   public String deleteOrderProcess(int[] check_detail, HttpSession session, int return_page) {
      // process_no=MAX && flag 'N' UPDATE
      
      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      for (int data : check_detail) {
         System.out.println(data);
      }

      if (check_detail != null) {

         logisticsServiceImpl.updateProReject(check_detail);
      }

      

      //URI로 디테일넘버 보내기 위해 배열에서 뽑아오기
      int store_order_detail_no = 0; 
      if(check_detail.length == 1) {
         for(int i=0; i < check_detail.length; i++) {   
            store_order_detail_no = check_detail[0];            
         }   
      }
      

      if(return_page == 1) {
         
         return "redirect:/order/read_detail_order_page.do?store_order_detail_no="+store_order_detail_no+"&return_page=2";
         
      }else {
         
         return "redirect:/logistics/delete_order_page.do";
      }
      

      
   }

   // ------------------------------------------------------------------공장재배치

   // 3&R 리스트 출력
   @RequestMapping("/reselect_factory_page.do")
   public String reselectFactoryPage(HttpSession session, Model model,
         @Param("store_order_res_no") String store_order_res_no,
         @RequestParam(value = "store_order_detail_no", defaultValue = "0", required = false) int store_order_detail_no,
         @Param("emp_name") String emp_name, @Param("product_name") String product_name,
         @Param("start_date") String start_date, @Param("end_date") String end_date,
         @RequestParam(value = "currPage", defaultValue = "1", required = false) int currPage) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      //파라미터 null처리
      if (currPage == 0) {
         currPage = 1;
      }

      if (store_order_res_no != null && store_order_res_no.equals("")) {
         store_order_res_no = null;
      }

      if (emp_name != null && emp_name.equals("")) {
         emp_name = null;
      }

      if (product_name != null && product_name.equals("")) {
         product_name = null;
      }
      if (start_date != null && start_date.equals("")) {
         start_date = null;
      }
      if (end_date != null && end_date.equals("")) {
         end_date = null;
      }

      
      
      int dept_no = employeeVo.getDept_no();

      List<Map<String, Object>> dataList = logisticsServiceImpl.getListForReselectFactory(dept_no, store_order_res_no,
            store_order_detail_no, product_name, emp_name, start_date, end_date, currPage);

      // 전체 게시글 수
      int totalCount = logisticsServiceImpl.countReselectList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

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

      List<List<Map<String, Object>>> factoryDataList = new ArrayList<List<Map<String, Object>>>();

      for (Map<String, Object> data : dataList) {

         StoreOrderDetailVo storeOrderDetailVo = (StoreOrderDetailVo) data.get("storeOrderDetailVo");
         System.out.println("공장재선택 리스트 발주상세 " + storeOrderDetailVo.getStore_order_detail_no());

         List<Map<String, Object>> list = logisticsServiceImpl
               .getFactory(storeOrderDetailVo.getStore_order_detail_no());

         factoryDataList.add(list);

      }

      model.addAttribute("factoryDataList", factoryDataList);
      model.addAttribute("dataList", dataList);

      return "/logistics/reselect_factory_page";

   }

   // 상세페이지 공장수정
   @RequestMapping("/update_factory_page.do")
   public String updateFactoryPage(int store_order_detail_no, Model model, HttpSession session, int return_page) {
      // 주문상세번호 조회

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      Map<String, Object> detailMap = orderServiceImpl.getDetailOrder(store_order_detail_no);
      List<Map<String, Object>> statusList = orderServiceImpl.getDetailOrderStatus(store_order_detail_no);
      List<Map<String, Object>> factoryList = logisticsServiceImpl.getFactory(store_order_detail_no);

      model.addAttribute("detailMap", detailMap);
      model.addAttribute("statusList", statusList);
      model.addAttribute("factoryList", factoryList);
      model.addAttribute("returnPage", return_page);

      return "/logistics/update_factory_page";

   }
   
   // 공장 재선택 프로세스
   @RequestMapping("/reselect_factory_process.do")
   public String reselectFactoryProcess(int[] check_detail, int[] check_branch, HttpSession session, int return_page) {
      // process 'R'을 'Y'로 업데이트
      // management branch_no 업데이트

      
      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      if (check_detail != null && check_branch != null) {

         logisticsServiceImpl.updateFactoryBranchNoAndFlagYes(check_detail, check_branch);
      }
      
      //URI로 디테일넘버 보내기 위해 배열에서 뽑아오기
      int store_order_detail_no = 0; 
      if(check_detail.length == 1) {
         for(int i=0; i < check_detail.length; i++) {   
            store_order_detail_no = check_detail[0];            
         }   
      }
      

      if(return_page == 1) {
         
         return "redirect:/order/read_detail_order_page.do?store_order_detail_no="+store_order_detail_no;
         
      }else {
         
         return "redirect:/logistics/reselect_factory_page.do";
      }
      

      

   }

//--------------------------------------------------------------------------------------------------부서게시판
   
   // 메인페이지
      @RequestMapping("/main_page.do")
      public String main_page(Model model, String searchWord, HttpSession session,
            @RequestParam(value = "search_type", defaultValue = "0", required = false) int search_type,
            @RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage
            ) {

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
         
        

         List<Map<String, Object>> list = logisticsServiceImpl.getBoardList(dept_title, dept_content, dept_writer, currPage, dept_type_no);
         List<Map<String, Object>> noticelist = logisticsServiceImpl.getnoticeBoardList(dept_title, dept_content, dept_writer, currPage, dept_type_no);

         int totalCount = logisticsServiceImpl.getBoardCount(dept_title, dept_content, dept_writer, dept_type_no);
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

         return "logistics/main_page";
      }

   // 부서 게시판 글쓰기 페이지
   @RequestMapping("/board_write_page.do")
   public String board_write_page(HttpSession session) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      return "logistics/board_write_page";

   }

   // 부서 게시판 글쓰기 프로세스
   @RequestMapping("/board_write_process.do")
   public String board_write_process(HttpSession session, DepartmentBoardVo departmentBoardVo,
         MultipartFile[] board_upload_files, String check_notice) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

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

      int boardNo = logisticsServiceImpl.createKey(departmentBoardVo);
      System.out.println("공지 게시글번호 : " + boardNo);

      if (check_notice == null) {
         // 아무것도 안날라온 경우
         logisticsServiceImpl.writeContent(departmentBoardVo, fileVoList);
      } else {
         // 라디오체크로 넘어온 값이 Y인 경우 공지사항 등록
         logisticsServiceImpl.writeContent(departmentBoardVo, fileVoList);
         logisticsServiceImpl.updateCheck(boardNo, employeeVo.getEmp_code());
      }

      return "redirect:/logistics/main_page.do";
   }

   // 부서 게시판 읽기
    @RequestMapping("/board_read_page.do")
    public String board_read_page(int dept_board_no, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
       
       Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
       /*
        * if(!check(employeeMap)) { return "commons/fail_page"; } 
        * 
        * if(!deptcheck(employeeVo.getDept_no())) { return "commons/fail_page"; }
        */
       
       EmployeeVo employeeVo=(EmployeeVo) employeeMap.get("employeeVo");
       int dept_type_no = employeeVo.getDept_no() / 10;
       int emp_code= employeeVo.getEmp_code();
       
       Cookie[] cookies = request.getCookies();
       // 비교하기 위해 새로운 쿠키
        Cookie viewCookie = null;
        // 쿠키가 있을 경우 
        if (cookies != null && cookies.length > 0) 
        {
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equals("cookie"+dept_board_no))
                { 
                    System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
                    viewCookie = cookies[i];
                }
            }
        }
        if (viewCookie == null) {    
            System.out.println("cookie 없음");
            
            // 쿠키 생성(이름, 값)
            Cookie newCookie = new Cookie("cookie"+dept_board_no, "|" + dept_board_no + "|");
                            
            // 쿠키 추가
            response.addCookie(newCookie);

            // 쿠키를 추가 시키고 조회수 증가시킴
            logisticsServiceImpl.updateRead(dept_board_no,emp_code,dept_type_no);         
        }
        // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
        else {
            System.out.println("cookie 있음");
            
            // 쿠키 값 받아옴.
            String value = viewCookie.getValue();
            
            System.out.println("cookie 값 : " + value);
    
        }
       
       
       Map<String, Object> map = logisticsServiceImpl.readContent(dept_board_no, dept_type_no);
       model.addAttribute("map", map);
       
       // 조회수 업데이트
      

       return "logistics/board_read_page";
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
       Map<String,Object> map = logisticsServiceImpl.readContent(dept_board_no, dept_type_no);
       EmployeeVo checkEmp = (EmployeeVo) map.get("employeeVo");
       
       if(checkEmp.getEmp_code() == employeeVo.getEmp_code() && checkEmp.getDept_no()/10 == dept_type_no) {
          // 정상적으로 로그인한 사원과 게시글 쓴 사원이 같을 때 작동
          model.addAttribute("data", logisticsServiceImpl.updateReadContent(dept_board_no, dept_type_no, employeeVo.getEmp_code()));
          return "logistics/board_update_page";
       } else {
          return "commons/incorrect_access_page";
       }
    
    }
    
    
 // 부서 게시글 수정 프로세스
    @RequestMapping("/update_content_process.do")
    public String board_update_process(
          DepartmentBoardVo departmentBoardVo,
          @RequestParam(value = "emp_code", required = false, defaultValue = "0") int emp_code,
          @RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no,
          String dept_board_title, String dept_board_content,
          String check_notice,
          MultipartFile[] board_upload_files, HttpSession session) {

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
          logisticsServiceImpl.update(dept_board_title, dept_board_content, employeeVo.getEmp_code(), dept_board_no);
          logisticsServiceImpl.updateCheck(dept_board_no, employeeVo.getEmp_code());
       } else {
          logisticsServiceImpl.update(dept_board_title, dept_board_content, employeeVo.getEmp_code(), dept_board_no);
       }

       logisticsServiceImpl.updatefile(departmentBoardVo, fileVoList);

       return "redirect:/logistics/main_page.do";

    }
      
   // 부서 게시글 삭제 프로세스
      @RequestMapping("/delete_process.do")
      public String board_delete_process(HttpSession session,
            @RequestParam(value = "dept_board_no", required = false, defaultValue = "0") int dept_board_no   
            ) {
         Map<String, Object> employeeMap = (Map<String, Object>) session.getAttribute("sessionUser");
         EmployeeVo employeeVo = (EmployeeVo) employeeMap.get("employeeVo");
      
         logisticsServiceImpl.delete(dept_board_no, employeeVo.getEmp_code());

         return "redirect:/logistics/main_page.do";
      }
      
      
      
   // 댓글 작성하기 프로세스
   @RequestMapping("/write_reply_process.do")
   @ResponseBody
   public String writeReply(DepartmentboardreplyVo departmentboardreplyVo, HttpSession session) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }
      
      

      departmentboardreplyVo.setEmp_code(employeeVo.getEmp_code());

      logisticsServiceImpl.writeReply(departmentboardreplyVo); // board_no와 글 내용

      return "true";

   }

   // 댓글 삭제하기 프로세스
   @RequestMapping("/delete_reply_process.do")
   public String deleteReply(int dept_board_reply_no, int dept_board_no, HttpSession session) {

      // 세션처리하기!(부서,로그아웃)
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      if (!check(sessionData)) {
         return "commons/fail_page";
      }

      if (!deptcheck(employeeVo.getDept_no())) {
         return "commons/fail_page";
      }

      
      
      System.out.println("컨" + dept_board_no);
      logisticsServiceImpl.deleteReply(dept_board_reply_no); // board_no와 글 내용

      return "redirect:/logistics/board_read_page.do?dept_board_no=" + dept_board_no;

   }

   // 댓글 목록 가져오기
   @RequestMapping("/get_reply_list.do")
   @ResponseBody
   public List<Map<String, Object>> getReplyList(int board_no) {

      List<Map<String, Object>> list = logisticsServiceImpl.getReplyList(board_no);

      return list;
   }   
   
   
   
}