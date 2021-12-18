package com.bt.management.service;


import java.util.List;
import java.util.Map;
import com.bt.vo.BranchVo;

public interface BranchService {
	 public List<Map<String,Object>> getBoardList(int dept_no, String branch_name, int curr_page);
	   
	   
	   public List<Map<String,Object>> getBoardList2();
	   public List<Map<String, Object>> getBoardList_factory(int branch_no, String emp_name, String branch_address,
	         int curr_page);
	   
	   public List<Map<String,Object>> getBoardList3();
	   
	   
	   
	   public List<Map<String,Object>> getBoardList4();

	   public List<Map<String, Object>> getBoardList_store(int branch_no, String emp_name, String branch_address,
	         int curr_page) ;

	   public List<Map<String, Object>> getBoardList_employee
	   (int dept_no, int branch_no, int emp_no,int rank_no, String emp_name, String start_date, int curr_page,int orderby)
	   ;
	   
	   public List<Map<String, Object>> getBoardList_employee_resignation
	   (int dept_no, int branch_no, int emp_no,int rank_no, String emp_name, String start_date, int curr_page,String end_date);
	   
	   
	   public List<Map<String,Object>> select_ShowAllBranch();

	   public BranchVo selectByBranchNo_read_store_page(int branch_no, int curr_page);
	   
	   public BranchVo selectByBranchNo_read_factory_page(int branch_no, int curr_page);
	   

	   public void addFactory(int branch_no, String branch_name, String branch_address, String branch_phone)
	  ;

	   public void addStore(int branch_no, String branch_name, String branch_address, String branch_phone) ;
	   
	   public List<Map<String,Object>> getBoardList_for_employee_add();



	   
	   public int getBoardList_employee_paging(int dept_no, int branch_no, int emp_no, int rank_no, String emp_name,String start_date,int orderby );
	   public int getBoardList_store_paging(int branch_no,String emp_name,String branch_address) ;
	   public int getBoardList_factory_paging(int branch_no,String emp_name,String branch_address);
	   public int getBoardList_logistics_paging(int dept_no, String branch_name);


	   public List<Map<String, Object>> getBoardList_All_store();


	   public BranchVo list_for_branch_name_in_branch_stock_page(int branch_no);


	   public void modifyStore(int branch_no, String branch_name, int emp_code, String branch_phone,
	         String branch_address);


	   public void modifyFactory(int branch_no, String branch_name, int emp_code, String branch_phone,
	         String branch_address);


	   public BranchVo getBranchNameByEmpCode(int emp_code);


	   public List<Map<String, Object>> getBoardList_Bybranch_type_no(int branch_type_no);


	   public int getBoardList_employee_paging_resignation(int dept_no, int branch_no, int emp_no, int rank_no,
	         String emp_name, String start_date,String end_date);


	   public List<Map<String, Object>> getBranchListByDeptNo(int dept_no);

	public List<Map<String, Object>> getBranchListByDeptNoForEmployeeAdd(int dept_no);
}
