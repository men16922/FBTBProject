package com.bt.logistics.service;

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
import com.bt.mapper.LogisticsSQLMapper;
import com.bt.mapper.ProcessManagementSQLMapper;
import com.bt.mapper.ProcessStatusSQLMapper;
import com.bt.vo.BranchVo;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.FactoryVo;
import com.bt.vo.ProcessListVo;
import com.bt.vo.ProcessManagementVo;
import com.bt.vo.ProcessStatusVo;
import com.bt.vo.ProductVo;
import com.bt.vo.StoreOrderDetailVo;
import com.bt.vo.StoreOrderReservationVo;

@Service
public class LogisticsServiceImpl {

   @Autowired
   private LogisticsSQLMapper logisticsSQLMapper;

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
   
   @Autowired
   private ProcessManagementSQLMapper processManagementSQLMapper;
   
   @Autowired
   private ProcessStatusSQLMapper processStatusSQLMapper;

   // 발주조회 전체불러오기
   public List<Map<String, Object>> getAllListByDeptNo(int dept_no, String store_order_res_no,
         int store_order_detail_no, String product_name, String emp_name, String start_date, String end_date,
         int currPage) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      System.out.println("제품명 서비스에 넘어가나? " + product_name);
      System.out.println("emp_name 이름 서비스 넘어오니? " + emp_name);

      List<StoreOrderReservationVo> dataList = null;

      // 검색한 값이 없을 때 리스트 불러오기
      if (store_order_res_no == null && store_order_detail_no == 0 && emp_name == null && product_name == null
            && start_date == null && end_date == null) {

         System.out.println("서비스 현재페이지" + currPage);
         dataList = logisticsSQLMapper.selectAllListByDeptNo(dept_no, currPage);

      } else { // 검색한 값이 있을 때 리스트 불러오기

         dataList = logisticsSQLMapper.selectSearchListByDeptNo(dept_no, store_order_res_no, store_order_detail_no,
               product_name, emp_name, start_date, end_date, currPage);

      }

      for (StoreOrderReservationVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         int emp_code = data.getEmp_code();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(emp_code);

         BranchVo branchVo = employeeSQLMapper.selectByBranchNo(employeeVo.getBranch_no());

         map.put("branchVo", branchVo);
         map.put("storeOrderReservationVo", data);
         map.put("employeeVo", employeeVo);

         list.add(map);

      }

      return list;
   }

   // 발주조회 전체 게시글 수
   public int countAllList(int dept_no, String store_order_res_no, int store_order_detail_no, String product_name,
         String emp_name, String start_date, String end_date) {

      int cnt = logisticsSQLMapper.selectCntAllList(dept_no, store_order_res_no, store_order_detail_no, product_name,
            emp_name, start_date, end_date);

      return cnt;
   };

   // 발주번호로 상세번호 리스트 조회
   public List<Map<String, Object>> getOrderDetailList(int store_order_res_code) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      List<StoreOrderDetailVo> detailList = logisticsSQLMapper.selectOrderDetailByResCode(store_order_res_code);

      for (StoreOrderDetailVo storeOrderDetailVo : detailList) {

         Map<String, Object> map = new HashMap<String, Object>();

         int store_order_detail_no = storeOrderDetailVo.getStore_order_detail_no();

         ProductVo productVo = logisticsSQLMapper.selectByProductNo(storeOrderDetailVo.getProduct_no());

         // 프로세스 매니지먼트
         ProcessManagementVo processManagementVo = processManagementSQLMapper.selectByDn(store_order_detail_no);
         if (processManagementVo != null) {
            map.put("processManagementVo", processManagementVo);
         }

         // 프로세스 Max인 status
         ProcessStatusVo processStatusVo = logisticsSQLMapper.selectFlagNameByMaxNo(store_order_detail_no);

         if (processStatusVo != null) {

            System.out.println("맥스 상태 프로세스넘버" + processStatusVo.getProcess_status_no());
            map.put("processStatusVo", processStatusVo);

         }

         // 프로세스 이름 뽑아오기
         ProcessListVo processListVo = logisticsSQLMapper.selectStatusNameByMaxNo(store_order_detail_no);
         if (processListVo != null) {

            System.out.println("맥스 상태 프로세스이름" + processListVo.getProcess_name());
            map.put("processListVo", processListVo);
         }

         map.put("productVo", productVo);
         map.put("storeOrderDetailVo", storeOrderDetailVo);

         list.add(map);

      }

      return list;
   }

   // 공장검색
   public List<Map<String, Object>> getFactory(int store_order_detail_no) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      StoreOrderDetailVo storeOrderDetailVo = logisticsSQLMapper.selectByDetailNo(store_order_detail_no);
      ProductVo productVo = logisticsSQLMapper.selectByProductNo(storeOrderDetailVo.getProduct_no());

      List<FactoryVo> factoryList = logisticsSQLMapper.selectFactoryByProTypeNo(productVo.getProduct_type_no());
      for (FactoryVo data : factoryList) {

         Map<String, Object> map = new HashMap<String, Object>();
         BranchVo branchVo = employeeSQLMapper.selectByBranchNo(data.getBranch_no());

         map.put("storeOrderDetailVo", storeOrderDetailVo);
         map.put("factoryVo", data);
         map.put("branchVo", branchVo);

         list.add(map);

      }

      return list;
   }

   // 1차검토 process_no 1 , flag 'Y' 조회
   public List<Map<String, Object>> getListByProNoOne(int dept_no, String store_order_res_no,
         int store_order_detail_no, String product_name, String emp_name, String start_date, String end_date,
         int currPage) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<StoreOrderDetailVo> dataList = null;

      // 검색한 값이 없을 때 리스트 불러오기
      if (store_order_res_no == null && store_order_detail_no == 0 && emp_name == null && product_name == null
            && start_date == null && end_date == null) {

         dataList = logisticsSQLMapper.selectListByProNoOne(dept_no, currPage);

      } else { // 검색한 값이 있을 때 리스트 불러오기

         dataList = logisticsSQLMapper.selectSearchListByProNoOne(dept_no, store_order_res_no, store_order_detail_no,
               product_name, emp_name, start_date, end_date, currPage);

      }

      for (StoreOrderDetailVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         int order_res_code = data.getStore_order_res_code();

         StoreOrderReservationVo orderResVo = logisticsSQLMapper.selectByResNo(order_res_code);

         int emp_code = orderResVo.getEmp_code();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(emp_code);

         int product_no = data.getProduct_no();
         ProductVo productVo = logisticsSQLMapper.selectByProductNo(product_no);

         map.put("storeOrderDetailVo", data);
         map.put("storeOrderReservationVo", orderResVo);
         map.put("employeeVo", employeeVo);
         map.put("productVo", productVo);

         list.add(map);

      }

      return list;

   }

   // 1차검토 게시글 수
   public int countFirstCheckList(int dept_no, String store_order_res_no, int store_order_detail_no,
         String product_name, String emp_name, String start_date, String end_date) {

      int cnt = logisticsSQLMapper.selectCntFirstCheckList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

      return cnt;

   };
   

   // 1차 검토 할일 수
   public int countFirstCheckToDo(int dept_no) {

      int cnt = logisticsSQLMapper.selectCntFirstCheckToDo(dept_no);

      return cnt;
   };
   

   // 출고요청 process_no 2 , flag 'Y' 조회
   public List<Map<String, Object>> getListByProNoTwo(int dept_no, String store_order_res_no,
         int store_order_detail_no, String product_name, String emp_name, String start_date, String end_date,
         int currPage) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<StoreOrderDetailVo> dataList = null;

      // 검색한 값이 없을 때 리스트 불러오기
      if (store_order_res_no == null && store_order_detail_no == 0 && emp_name == null && product_name == null
            && start_date == null && end_date == null) {

         dataList = logisticsSQLMapper.selectListByProNoTwo(dept_no, currPage);

      } else { // 검색한 값이 있을 때 리스트 불러오기

         dataList = logisticsSQLMapper.selectSearchListByProNoTwo(dept_no, store_order_res_no, store_order_detail_no,
               product_name, emp_name, start_date, end_date, currPage);

      }

      for (StoreOrderDetailVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         int order_res_code = data.getStore_order_res_code();

         StoreOrderReservationVo orderResVo = logisticsSQLMapper.selectByResNo(order_res_code);

         int emp_code = orderResVo.getEmp_code();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(emp_code);

         int product_no = data.getProduct_no();
         ProductVo productVo = logisticsSQLMapper.selectByProductNo(product_no);

         ProcessManagementVo processManagementVo = processManagementSQLMapper.selectByDn(data.getStore_order_detail_no());

         map.put("storeOrderDetailVo", data);
         map.put("storeOrderReservationVo", orderResVo);
         map.put("employeeVo", employeeVo);
         map.put("productVo", productVo);
         map.put("processManagementVo", processManagementVo);

         list.add(map);

      }

      return list;

   }
   
   
   // 출고요청 할일 수
   public int countLastCheckToDo(int dept_no) {

      int cnt = logisticsSQLMapper.selectCntLastCheckToDo(dept_no);

      return cnt;
   };
   

   // 출고요청 게시글 수
   public int countLastCheckList(int dept_no, String store_order_res_no, int store_order_detail_no,
         String product_name, String emp_name, String start_date, String end_date) {

      int cnt = logisticsSQLMapper.selectCntLastCheckList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

      return cnt;

   };

   // 수정있는 1차검토
   public void updatefirstQtyAndFirstCheck(int store_order_detail_no, int confirm_qty, int first_emp_code) {
      // manegement테이블에 확정수량 INSERT, branch_no 0으로
      // process_no=2 && flag 'U' INSERT
      // process_no=2 && flag 'Y' INSERT

      int process_manage_no = logisticsSQLMapper.createKeyManagement();
      logisticsSQLMapper.insertProcessManagement(process_manage_no, store_order_detail_no, confirm_qty,
            first_emp_code);

      int process_status_no = processStatusSQLMapper.createKey();
      logisticsSQLMapper.insertProTwoAndUpdate(process_status_no, store_order_detail_no);

      process_status_no = processStatusSQLMapper.createKey();
      logisticsSQLMapper.insertProTwoAndYes(process_status_no, store_order_detail_no);

   }

   // 수정없는 1차검토
   public void insertFirstCheck(int[] check_detail, int[] check_qty, int first_emp_code) {
      // manegement테이블에 기존수량이 INSERT , branch_no 0으로
      // process_no=2 && flag 'Y' INSERT

      if (check_detail == null || check_qty == null) {
         return;
      }

      for (int i = 0; i < check_detail.length; i++) {

         int process_manage_no = logisticsSQLMapper.createKeyManagement();
         logisticsSQLMapper.insertProcessManagement(process_manage_no, check_detail[i], check_qty[i],
               first_emp_code);

         int process_status_no = processStatusSQLMapper.createKey();
         logisticsSQLMapper.insertProTwoAndYes(process_status_no, check_detail[i]);

      }

   }

   // 수정있는 출고요청
   public void updateConfirmQtyAndlastCheck(int store_order_detail_no, int confirm_qty, int branch_no,
         int end_emp_code) {
      // manegement테이블 확정수량 UPDATE
      // manegement테이블에 branch_no(담당공장) UPDATE
      // process_no=3 && flag 'U' INSERT
      // process_no=3 && flag 'Y' INSERT

      logisticsSQLMapper.updateFactoryBranchNoAndQty(branch_no, confirm_qty, end_emp_code, store_order_detail_no);

      int process_status_no = processStatusSQLMapper.createKey();
      logisticsSQLMapper.insertProThreeAndUpdate(process_status_no, store_order_detail_no);

      process_status_no = processStatusSQLMapper.createKey();
      logisticsSQLMapper.insertProThreeAndYes(process_status_no, store_order_detail_no);

   }

   // 수정없는 출고요청
   public void updateBranchNoAndLastCheck(int[] check_detail, int[] check_branch, int end_emp_code) {
      // manegement테이블에 branch_no(담당공장) , end_emp)code UPDATE
      // process_no=3 && flag 'Y' INSERT

      if (check_detail == null || check_branch == null) {
         return;
      }

      for (int i = 0; i < check_detail.length; i++) {

         logisticsSQLMapper.updateFactoryBranchNo(check_branch[i], end_emp_code, check_detail[i]);

         int process_status_no = processStatusSQLMapper.createKey();
         logisticsSQLMapper.insertProThreeAndYes(process_status_no, check_detail[i]);

      }

   }

   // 발주반려 조회
   public List<Map<String, Object>> getListForReject(int dept_no, String store_order_res_no, int store_order_detail_no,
         String product_name, String emp_name, String start_date, String end_date, int currPage) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<StoreOrderDetailVo> dataList = null;

      // 검색한 값이 없을 때 리스트 불러오기
      if (store_order_res_no == null && store_order_detail_no == 0 && emp_name == null && product_name == null
            && start_date == null && end_date == null) {

         dataList = logisticsSQLMapper.selectListByOneTwoThree(dept_no, currPage);

      } else { // 검색한 값이 있을 때 리스트 불러오기

         dataList = logisticsSQLMapper.selectSearchListByOneTwoThree(dept_no, store_order_res_no,
               store_order_detail_no, product_name, emp_name, start_date, end_date, currPage);

      }

      for (StoreOrderDetailVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         int order_res_code = data.getStore_order_res_code();

         StoreOrderReservationVo orderResVo = logisticsSQLMapper.selectByResNo(order_res_code);

         int emp_code = orderResVo.getEmp_code();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(emp_code);

         int product_no = data.getProduct_no();
         ProductVo productVo = logisticsSQLMapper.selectByProductNo(product_no);

         int detail_no = data.getStore_order_detail_no();
         ProcessManagementVo processManagementVo = processManagementSQLMapper.selectByDn(detail_no);

         ProcessListVo processListVo = logisticsSQLMapper.selectStatusNameByMaxNo(detail_no);

         if (processManagementVo != null) {
            map.put("processManagementVo", processManagementVo);
         }

         map.put("processListVo", processListVo);
         map.put("storeOrderDetailVo", data);
         map.put("storeOrderReservationVo", orderResVo);
         map.put("employeeVo", employeeVo);
         map.put("productVo", productVo);

         list.add(map);

      }

      return list;

   }

   // 발주반려 게시글 수
   public int countDeleteList(int dept_no, String store_order_res_no, int store_order_detail_no, String product_name,
         String emp_name, String start_date, String end_date) {

      int cnt = logisticsSQLMapper.selectCntDeleteList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

      return cnt;

   };

   public void updateProReject(int[] check_detail) {
      // Max값 먼저 뽑아야함
      // process_no=MAX && flag 'N' UPDATE
      // process management reject = 'Y' 로 UPDATE

      if (check_detail == null) {
         return;
      }

      int process_no = 0;

      for (int i = 0; i < check_detail.length; i++) {
         System.out.println("for문안 체크 디테일 확인용" + check_detail[i]);
         process_no = logisticsSQLMapper.selectMaxByDetailNo(check_detail[i]);
         System.out.println("맥스 프로세스 넘버 확인용" + process_no);
         logisticsSQLMapper.updateProMaxAndReject(check_detail[i], process_no);

         logisticsSQLMapper.updateManagementReject(check_detail[i]);

      }

   }

   // 공장재선택 조회
   public List<Map<String, Object>> getListForReselectFactory(int dept_no, String store_order_res_no,
         int store_order_detail_no, String product_name, String emp_name, String start_date, String end_date,
         int currPage) {
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<StoreOrderDetailVo> dataList = null;

      // 검색한 값이 없을 때 리스트 불러오기
      if (store_order_res_no == null && store_order_detail_no == 0 && emp_name == null && product_name == null
            && start_date == null && end_date == null) {

         dataList = logisticsSQLMapper.selectListForReFactory(dept_no, currPage);

      } else { // 검색한 값이 있을 때 리스트 불러오기

         dataList = logisticsSQLMapper.selectSearchForReFactory(dept_no, store_order_res_no, store_order_detail_no,
               product_name, emp_name, start_date, end_date, currPage);

      }

      for (StoreOrderDetailVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         int order_res_code = data.getStore_order_res_code();

         StoreOrderReservationVo orderResVo = logisticsSQLMapper.selectByResNo(order_res_code);

         int emp_code = orderResVo.getEmp_code();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(emp_code);

         int product_no = data.getProduct_no();
         ProductVo productVo = logisticsSQLMapper.selectByProductNo(product_no);

         int detail_no = data.getStore_order_detail_no();
         ProcessManagementVo processManagementVo = processManagementSQLMapper.selectByDn(detail_no);

         ProcessListVo processListVo = logisticsSQLMapper.selectStatusNameByMaxNo(detail_no);

         if (processManagementVo != null) {
            map.put("processManagementVo", processManagementVo);
         }

         map.put("processListVo", processListVo);
         map.put("storeOrderDetailVo", data);
         map.put("storeOrderReservationVo", orderResVo);
         map.put("employeeVo", employeeVo);
         map.put("productVo", productVo);

         list.add(map);

      }

      return list;

   }

   // 공장재선택 할일 수
   public int countReselectToDo(int dept_no) {

      int cnt = logisticsSQLMapper.selectCntReselectToDo(dept_no);

      return cnt;
   };
   
   // 공장 재선택 게시글 수
   public int countReselectList(int dept_no, String store_order_res_no, int store_order_detail_no, String product_name,
         String emp_name, String start_date, String end_date) {

      int cnt = logisticsSQLMapper.selectCntReselectList(dept_no, store_order_res_no, store_order_detail_no,
            product_name, emp_name, start_date, end_date);

      return cnt;

   };

   public void updateFactoryBranchNoAndFlagYes(int[] check_detail, int[] check_branch) {
      // process 'R'을 'Y'로 업데이트
      // management branch_no 업데이트

      if (check_detail == null || check_branch == null) {
         return;
      }

      for (int i = 0; i < check_detail.length; i++) {

         logisticsSQLMapper.updateFlagYesByProThree(check_detail[i]);
         logisticsSQLMapper.updateManagementBranchNo(check_detail[i], check_branch[i]);

      }

   }

   //-------------------------------------------------------------------------------------------------------------- 부서게시판 시작!
   
   // 게시물 가져오기
   public List<Map<String, Object>> getBoardList(String dept_board_title, String dept_content, String dept_writer,
         int currPage, int dept_type_no) {
      
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

   public void updateCheck(int dept_board_no, int emp_code) {
      // 공지사항 등록하기
      departmentBoardSQLMapper.updateCheck(dept_board_no, emp_code);
   }

   // 게시글 읽기
   public Map<String, Object> readContent(int dept_board_no, int dept_type_no) {

      Map<String, Object> map = new HashMap<String, Object>();

      DepartmentBoardVo departmentBoardVo = departmentBoardSQLMapper.selectByNo(dept_board_no, dept_type_no);
      EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
      EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(employeeVo.getRank_no());
      int branch_no = employeeVo.getBranch_no();
      BranchVo branchVo = employeeSQLMapper.selectByBranchNo(branch_no);
      List<DepartmentFileVo> departmentFileVolist = departmentFileSQLMapper.selectByBoardNo(dept_board_no);

      map.put("departmentBoardVo", departmentBoardVo);
      map.put("employeeVo", employeeVo);
      map.put("employeeRankVo", employeeRankVo);
      map.put("branchVo", branchVo);
      map.put("departmentFileVolist", departmentFileVolist);

      return map;
   }

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

   public void updateRead(int dept_board_no, int emp_code, int dept_type_no) {
      // 조회수 업데이트
      DepartmentBoardVo departmentBoardVo = departmentBoardSQLMapper.selectByNo(dept_board_no, dept_type_no);
      EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(departmentBoardVo.getEmp_code());
      if (!(emp_code == employeeVo.getEmp_code())) {
         departmentBoardSQLMapper.updateView(dept_board_no);
      }
   }

   public void update(String dept_board_title, String dept_board_content, int emp_code, int dept_board_no) {
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

   // 부서게시판 끝

}