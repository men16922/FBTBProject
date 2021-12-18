package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.BranchVo;
import com.bt.vo.DeptTypeVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;

public interface EmployeeSQLMapper {

	public EmployeeVo selectByIdPwForConfirm(
			@Param("emp_no") int emp_no,
			@Param("emp_password") String emp_password);
	
	public EmployeeVo Findpassword(@Param("emp_name") String emp_name,
			@Param("emp_no") String emp_no,
			@Param("emp_email") String emp_email);

	public DeptVo selectByDeptNo(int dept_no);

	public DeptTypeVo selectByDeptTypeNo(int dept_type_no);

	public EmployeeRankVo selectByEmpRankNo(int rank_no);

	public BranchVo selectByBranchNo(int branch_no);

	public EmployeeVo selectByCode(int emp_code);

	public List<EmployeeVo> selectEmp(int branch_no);

	public List<EmployeeVo> selectByEmpName_and_EmpAddress(@Param("branch_no") int branch_no,
			@Param("emp_name") String emp_name, @Param("branch_address") String branch_address);

	public List<EmployeeVo> selectByEmpName_and_EmpAddressForStore(@Param("branch_no") int branch_no,
			@Param("emp_name") String emp_name, @Param("emp_address") String emp_address);

	public List<EmployeeVo> selectByEmpName_and_EmpCodeForEmployeeManagement(@Param("dept_no") int dept_no,
			@Param("branch_no") int branch_no, @Param("emp_no") int emp_no, @Param("rank_no") int rank_no,
			@Param("emp_name") String emp_name, @Param("start_date") String start_date,
			@Param("curr_page") int curr_page, @Param("orderby") int orderby);

	public List<EmployeeVo> selectByEmpName_and_EmpCodeForEmployeeManagement_resignation(@Param("dept_no") int dept_no,
			@Param("branch_no") int branch_no, @Param("emp_no") int emp_no, @Param("rank_no") int rank_no,
			@Param("emp_name") String emp_name, @Param("start_date") String start_date,
			@Param("curr_page") int curr_page, @Param("end_date") String end_date);

	public int getBoardList_employee_paging(@Param("dept_no") int dept_no, @Param("branch_no") int branch_no,
			@Param("emp_no") int emp_no, @Param("rank_no") int rank_no, @Param("emp_name") String emp_name,
			@Param("start_date") String start_date, @Param("orderby") int orderby);

	public EmployeeVo selectAll();

	public int selectAllCount();

	public EmployeeVo selectEmployeeByDept_no(@Param("dept_no") int dept_no, @Param("branch_no") int branch_no);

	public void insertEmployee(@Param("emp_name") String emp_name, @Param("emp_email") String emp_email,
			@Param("emp_phone") String emp_phone, @Param("start_date") String start_date,
			@Param("emp_address") String emp_address, @Param("emp_sex") String emp_sex, @Param("dept_no") int dept_no,
			@Param("branch_no") int branch_no, @Param("rank_no") int rank_no,
			@Param("emp_residentnum") String emp_residentnum, @Param("emp_no") int emp_no);

	public int selectMaxEmpNoforCreateNewEmpNo();

	public void resignationEmployee(@Param("emp_code") int emp_code, @Param("emp_name") String emp_name,
			@Param("emp_no") int emp_no);

	public List<EmployeeVo> selectByEmpByDeptNo(int dept_no);

	public List<EmployeeVo> selectEmpByBranchNo(int branch_no);
	
	public EmployeeVo selectEmployeeByBranch_no(int branch_no);
	
	public EmployeeVo selectByEmpCodeAndPw(@Param("emp_code") int emp_code, @Param("emp_password") String emp_password);

	public void updatePasswordByEmpCode(@Param("emp_code") int emp_code,
			@Param("new_emp_password") String new_emp_password);

	public EmployeeVo selectByEmpCode(int emp_code);

	public List<DeptVo> selectDeptAll();

	public EmployeeVo selectByEmpNo_for_confirm(int emp_no);

	public DeptVo selectDeptName(int emp_no); // employee_confirm.jsp 용

	public EmployeeRankVo selectRankByEmpNo(int emp_no); // employee_confirm.jsp 용

	public BranchVo selectBranchByEmpNo(int emp_no);

	public void modifyEmployee(@Param("emp_no") int emp_no, @Param("emp_name") String emp_name,
			@Param("dept_no") int dept_no, @Param("branch_no") int branch_no, @Param("rank_no") int rank_no,
			@Param("emp_email") String emp_email, @Param("emp_phone") String emp_phone,
			@Param("emp_address") String emp_address);

	public List<EmployeeVo> selectEmployeeByBranchNo_forStore();

	public List<EmployeeVo> selectEmployeeByBranchNo_forFactory();

	public EmployeeVo selectByEmpCodeForStoreReservationPage(@Param("emp_code") int emp_code);

	public int getReservationInfo_paging(int emp_code);

	public EmployeeVo selectByEmpCodeForFactoryOrderPage(int emp_code);

	public int getFactoryOrderInfo_paging(int emp_code);

	public int getBoardList_employee_paging_resignation(@Param("dept_no") int dept_no,
			@Param("branch_no") int branch_no, @Param("emp_no") int emp_no, @Param("rank_no") int rank_no,
			@Param("emp_name") String emp_name, @Param("start_date") String start_date,
			@Param("end_date") String end_date);

	public void modifyBranchNoForModifyStore(@Param("branch_no") int branch_no, @Param("emp_code") int emp_code);
	
	public int selectCountByBranchNo(int branch_no);

	public Integer getCountByMonth(int mon);

	public void resignationMakeBranchNotoZeroEmployee(int emp_code);

	public List<EmployeeVo> selectEmployeeByBranchNo_forStore_AllList();

	public List<EmployeeVo> selectEmployeeByBranchNo_forFactory_AllList();
	
	public List<EmployeeVo> selectEmployeeByBranchNo_forFactory(int branch_no);
	 
	public List<EmployeeVo> selectEmployeeByBranchNo_forStore(int branch_no);
	  
	public String getempcodebyempno(String emp_no);
	  

}