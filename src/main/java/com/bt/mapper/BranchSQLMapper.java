package com.bt.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.bt.vo.BranchVo;

public interface BranchSQLMapper {

   public int createKey();   
   // branch_no = 1인거 가져오기
   public BranchVo selectByNo(int branch_no);
   
   public List<BranchVo> selectBranchByType(int branch_type_no);

   public BranchVo selectByBNO(int branch_no);

   public List<BranchVo> selectByBranchName(@Param("dept_no") int dept_no, @Param("branch_name") String branch_name,
         @Param("curr_page") int curr_page);

   public List<BranchVo> selectFactory();

   public List<BranchVo> selectStore();

   public List<BranchVo> select_ShowAllBranch();

   public BranchVo selectByBranchNo_read_store_page(@Param("branch_no") int branch_no,
         @Param("curr_page") int curr_page);

   public BranchVo selectByBranchNo_read_factory_page(@Param("branch_no") int branch_no,
         @Param("curr_page") int curr_page);

   public BranchVo insertFactoryByEmp_code(@Param("branch_no") int branch_no, @Param("branch_name") String branch_name,
         @Param("branch_address") String branch_address, @Param("branch_phone") String branch_phone);

   public BranchVo insertStoreByEmp_code(@Param("branch_no") int branch_no, @Param("branch_name") String branch_name,
         @Param("branch_address") String branch_address, @Param("branch_phone") String branch_phone);

   public int getBoardList_store_paging(@Param("branch_no") int branch_no, @Param("emp_name") String emp_name,
         @Param("branch_address") String branch_address);

   public int getBoardList_factory_paging(@Param("branch_no") int branch_no, @Param("emp_name") String emp_name,
         @Param("branch_address") String branch_address);

   public int getBoardList_logistics_paging(@Param("dept_no") int dept_no, @Param("branch_name") String branch_name);

   public List<BranchVo> selectByBTN();

   public List<BranchVo> getBoardList_All_store();

   public BranchVo selectBySodn(int store_order_detail_no);

   public void modifyStore(@Param("branch_no") int branch_no, @Param("branch_name") String branch_name,
         @Param("emp_code") int emp_code, @Param("branch_phone") String branch_phone,
         @Param("branch_address") String branch_address);

   public void modifyFactory(@Param("branch_no") int branch_no, @Param("branch_name") String branch_name,
         @Param("emp_code") int emp_code, @Param("branch_phone") String branch_phone,
         @Param("branch_address") String branch_address);

   public BranchVo selectAll();

   public List<BranchVo> selectByEmpCode(@Param("emp_code") int emp_code);// 매장용

   public List<BranchVo> selectByEmpCode2(@Param("emp_code") int emp_code);// 공장용

   public List<BranchVo> selectByEmpName_and_EmpAddressForStore(@Param("branch_no") int branch_no,
         @Param("emp_name") String emp_name, @Param("branch_address") String branch_address,
         @Param("curr_page") int curr_page);

   public List<BranchVo> selectByEmpName_and_EmpAddressForFactory(@Param("branch_no") int branch_no,
         @Param("emp_name") String emp_name, @Param("branch_address") String branch_address,
         @Param("curr_page") int curr_page);
   
   public BranchVo getBranchNameByEmpCode(int emp_code);

   public List<BranchVo> getBranchListByDeptNo(int dept_no);
   
   public List<BranchVo> getBranchListByDeptNoForEmployeeAdd(int dept_no);

   public void resignationMakeBranchNotoZero(int emp_code);
}