package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.DeliveryVo;
import com.bt.vo.FactoryVo;
import com.bt.vo.ProcessListVo;
import com.bt.vo.ProcessStatusVo;
import com.bt.vo.ProductTypeVo;
import com.bt.vo.ProductVo;
import com.bt.vo.StoreOrderDetailVo;
import com.bt.vo.StoreOrderReservationVo;

public interface LogisticsSQLMapper {
   
   //발주조회 전체리스트
   public List<StoreOrderReservationVo> selectAllListByDeptNo(
         @Param("dept_no") int dept_no,
         @Param("currPage") int currPage
         );
   
   //발주조회 검색리스트
   public List<StoreOrderReservationVo> selectSearchListByDeptNo(
         @Param("dept_no") int dept_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("product_name") String product_name,
         @Param("emp_name") String emp_name,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("currPage") int currPage
         );
   
   //발주조회 게시글 수 
   public int selectCntAllList(
         @Param("dept_no") int dept_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("product_name") String product_name,
         @Param("emp_name") String emp_name,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date
         );
   
   //발주상세번호 리스트
   public List<StoreOrderDetailVo> selectOrderDetailByResCode(int store_order_res_code);
   
   
   
   //-------------------------------------------------------------------1차검토
   
   //1차검토 전체리스트
   public List<StoreOrderDetailVo> selectListByProNoOne(
         @Param("dept_no") int dept_no,
         @Param("currPage") int currPage);
   
   //1차검토 검색리스트
   public List<StoreOrderDetailVo> selectSearchListByProNoOne(
         @Param("dept_no") int dept_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("product_name") String product_name,
         @Param("emp_name") String emp_name,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("currPage") int currPage
         );
   
   //1차검토 해야할 일 수
   public int selectCntFirstCheckToDo(int dept_no);
   
   //1차검토 게시글 수 
   public int selectCntFirstCheckList(
            @Param("dept_no") int dept_no,
            @Param("store_order_res_no") String store_order_res_no,
            @Param("store_order_detail_no") int store_order_detail_no,
            @Param("product_name") String product_name,
            @Param("emp_name") String emp_name,
            @Param("start_date") String start_date,
            @Param("end_date") String end_date
            );
   
   
   //1차검토시 Management 테이블 생성키
   public int createKeyManagement();
   
   //1차검토시 Management 테이블 생성
   public void insertProcessManagement(
         @Param("process_manage_no") int process_manage_no,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("confirm_qty") int confirm_qty,
         @Param("first_emp_code") int first_emp_code
         );
   
   
   //-------------------------------------------------------------------출고요청
   
   
   
      //출고요청 전체리스트
      public List<StoreOrderDetailVo> selectListByProNoTwo(
            @Param("dept_no") int dept_no,
            @Param("currPage") int currPage);
      
      //출고요청 검색리스트
      public List<StoreOrderDetailVo> selectSearchListByProNoTwo(
            @Param("dept_no") int dept_no,
            @Param("store_order_res_no") String store_order_res_no,
            @Param("store_order_detail_no") int store_order_detail_no,
            @Param("product_name") String product_name,
            @Param("emp_name") String emp_name,
            @Param("start_date") String start_date,
            @Param("end_date") String end_date,
            @Param("currPage") int currPage
            );
      
      //출고요청 해야할 일
      public int selectCntLastCheckToDo(int dept_no);
      
      //출고요청 게시글 수 
      public int selectCntLastCheckList(
               @Param("dept_no") int dept_no,
               @Param("store_order_res_no") String store_order_res_no,
               @Param("store_order_detail_no") int store_order_detail_no,
               @Param("product_name") String product_name,
               @Param("emp_name") String emp_name,
               @Param("start_date") String start_date,
               @Param("end_date") String end_date
               );
      
      //수정없는 출고요청
      public void updateFactoryBranchNo(
            @Param("branch_no") int branch_no,
            @Param("end_emp_code") int end_emp_code,
            @Param("store_order_detail_no") int store_order_detail_no
            
            );

   
   
   //수정있는 출고요청
   public void updateFactoryBranchNoAndQty(
         @Param("branch_no") int branch_no,
         @Param("confirm_qty") int confirm_qty,
         @Param("end_emp_code") int end_emp_code,
         @Param("store_order_detail_no") int store_order_detail_no
         );
   
   
   //-------------------------------------------------------------------발주반려
   
   //발주반려 전체리스트
   
   public List<StoreOrderDetailVo> selectListByOneTwoThree(
         @Param("dept_no") int dept_no,
         @Param("currPage") int currPage);
   
   //발주반려 검색리스트
   public List<StoreOrderDetailVo> selectSearchListByOneTwoThree(
         @Param("dept_no") int dept_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("product_name") String product_name,
         @Param("emp_name") String emp_name,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("currPage") int currPage
         );

   //발주반려 게시글 수 
   public int selectCntDeleteList(
            @Param("dept_no") int dept_no,
            @Param("store_order_res_no") String store_order_res_no,
            @Param("store_order_detail_no") int store_order_detail_no,
            @Param("product_name") String product_name,
            @Param("emp_name") String emp_name,
            @Param("start_date") String start_date,
            @Param("end_date") String end_date
            );

   
   //-------------------------------------------------------------------Process 변경 쿼리들

   public void insertProTwoAndUpdate(
         @Param("process_status_no") int process_status_no,
         @Param("store_order_detail_no") int store_order_detail_no
         );
   
   public void insertProTwoAndYes(
         @Param("process_status_no") int process_status_no,
         @Param("store_order_detail_no") int store_order_detail_no
         );
   
   public void insertProThreeAndUpdate(
         @Param("process_status_no") int process_status_no,
         @Param("store_order_detail_no") int store_order_detail_no
         );
   
   public void insertProThreeAndYes(
         @Param("process_status_no") int process_status_no,
         @Param("store_order_detail_no") int store_order_detail_no);
   
   //-------------------------------------------------------------------발주반려
   
   //거절 프로세스 업데이트
   public void updateProMaxAndReject(
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("process_no") int process_no
         );
   
   //거절 management 업데이트
   public void updateManagementReject(
         @Param("store_order_detail_no") int store_order_detail_no
         );
   
   //-----------------------------------------------------------------공장 재선택
   
   //공장재선택 리스트
   public List<StoreOrderDetailVo> selectListForReFactory(
         @Param("dept_no") int dept_no,
         @Param("currPage") int currPage
         );
   
   //공장재선택 검색리스트
   public List<StoreOrderDetailVo> selectSearchForReFactory(
         @Param("dept_no") int dept_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("product_name") String product_name,
         @Param("emp_name") String emp_name,
         @Param("start_date") String start_date,
         @Param("end_date") String end_date,
         @Param("currPage") int currPage
         );
   
   //공장재선택 해야할 일
    public int selectCntReselectToDo(int dept_no);


   //공장재선택 게시글 수 
   public int selectCntReselectList(
            @Param("dept_no") int dept_no,
            @Param("store_order_res_no") String store_order_res_no,
            @Param("store_order_detail_no") int store_order_detail_no,
            @Param("product_name") String product_name,
            @Param("emp_name") String emp_name,
            @Param("start_date") String start_date,
            @Param("end_date") String end_date
            );
   
   //업데이트 management 공장 branch_no
   public void updateManagementBranchNo(
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("branch_no") int branch_no
         );
   
   //업데이트 process_status 3 && Y
   public void updateFlagYesByProThree(int store_order_detail_no);
   
   
   public ProcessStatusVo selectFlagNameByMaxNo(int store_order_detail_no);

   
   public ProcessListVo selectStatusNameByMaxNo(int store_order_detail_no);
   
   public int selectMaxByDetailNo(int store_order_detail_no);
   
   public List<FactoryVo> selectFactoryByProTypeNo(int product_type_no);

   public StoreOrderReservationVo selectByResNo(int store_order_res_code);
   
   public StoreOrderDetailVo selectByResCode(int store_order_res_code);
   
   public StoreOrderDetailVo selectByDetailNo(int store_order_detail_no);
   
   public ProductVo selectByProductNo(int product_no);
   
   public ProductTypeVo selectByProductTypeNo(int product_type_no);
   
   public List<ProcessStatusVo> selectProcessListByDetailNo(int store_order_detail_no);
   
   public DeliveryVo selectDeliveryByDetailNo(int store_order_detail_no);

   
   
   
   
}