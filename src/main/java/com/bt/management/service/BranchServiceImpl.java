package com.bt.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.BranchSQLMapper;
import com.bt.mapper.BranchTypeSQLMapper;
import com.bt.mapper.DepartmentSQLMapper;
import com.bt.mapper.EmployeeRankSQLMapper;
import com.bt.mapper.EmployeeSQLMapper;
import com.bt.vo.BranchTypeVo;
import com.bt.vo.BranchVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;


@Service
public class BranchServiceImpl implements BranchService{
   

   @Autowired
   private BranchSQLMapper branchSQLMapper;
   @Autowired
   private BranchTypeSQLMapper branchTypeSQLMapper;
   @Autowired
   private DepartmentSQLMapper departmentSQLMapper;
   @Autowired
   private EmployeeSQLMapper employeeSQLMapper;
   @Autowired
   private EmployeeRankSQLMapper employeeRankSQLMapper;
   
   public List<Map<String,Object>> getBoardList(int dept_no, String branch_name, int curr_page){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList = branchSQLMapper.selectByBranchName(dept_no, branch_name,curr_page);
   
      for(BranchVo branchVo : boardList) {
         BranchTypeVo branchTypeVo = branchTypeSQLMapper.selectByNo(branchVo.getBranch_type_no());
         DeptVo deptVo = departmentSQLMapper.selectByBranchNo(branchVo.getBranch_no());

         
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         map.put("branchTypeVo", branchTypeVo);

         map.put("deptVo",deptVo);
         list.add(map);
      }
      
      return list;

   
   }
   
   
   public List<Map<String,Object>> getBoardList2(){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<DeptVo> boardList2 = departmentSQLMapper.selectByLogisticsName();
      
      for(DeptVo deptVo : boardList2) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("deptVo", deptVo);
         
         list.add(map);
      }
      
      return list;
      
   }
   public List<Map<String, Object>> getBoardList_factory(int branch_no, String emp_name, String branch_address,
         int curr_page) {
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList_factory 
      = branchSQLMapper.selectByEmpName_and_EmpAddressForFactory(branch_no, emp_name, branch_address,curr_page);
      for(BranchVo branchVo : boardList_factory) {
         Map<String,Object> map = new HashMap<String,Object>();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(branchVo.getEmp_code());

            map.put("branchVo", branchVo);
            map.put("employeeVo", employeeVo);
            list.add(map);
      }
      return list;
   }
   
   public List<Map<String,Object>> getBoardList3(){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList3 = branchSQLMapper.selectFactory();
      
      for(BranchVo branchVo : boardList3) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;
      
   }
   
   
   
   public List<Map<String,Object>> getBoardList4(){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList3 = branchSQLMapper.selectStore();
      
      for(BranchVo branchVo : boardList3) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;
      
   }

   public List<Map<String, Object>> getBoardList_store(int branch_no, String emp_name, String branch_address,
         int curr_page) {
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList_store = branchSQLMapper.selectByEmpName_and_EmpAddressForStore(branch_no, emp_name, branch_address,curr_page);
      for(BranchVo branchVo : boardList_store) {
         Map<String,Object> map = new HashMap<String,Object>();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(branchVo.getEmp_code());

            map.put("branchVo", branchVo);
            map.put("employeeVo", employeeVo);
            list.add(map);
      }
      return list;
   }

   public List<Map<String, Object>> getBoardList_employee
   (int dept_no, int branch_no, int emp_no,int rank_no, String emp_name, String start_date, int curr_page,int orderby) {
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<EmployeeVo> boardList_employee = employeeSQLMapper.selectByEmpName_and_EmpCodeForEmployeeManagement(dept_no, branch_no, emp_no,rank_no, emp_name, start_date,curr_page,orderby);
      
      for(EmployeeVo employeeVo : boardList_employee) {
         Map<String,Object> map = new HashMap<String,Object>();

         BranchVo branchVo = branchSQLMapper.selectByBNO(employeeVo.getBranch_no());
         EmployeeRankVo employeeRankVo = employeeRankSQLMapper.selectByRankNo(employeeVo.getRank_no());
         
         map.put("employeeVo", employeeVo);
         map.put("branchVo", branchVo);
         map.put("employeeRankVo", employeeRankVo);
         list.add(map);
      }
      
      return list;
   }
   
   public List<Map<String, Object>> getBoardList_employee_resignation
   (int dept_no, int branch_no, int emp_no,int rank_no, String emp_name, String start_date, int curr_page,String end_date) {
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<EmployeeVo> boardList_employee = employeeSQLMapper.selectByEmpName_and_EmpCodeForEmployeeManagement_resignation(dept_no, branch_no, emp_no,rank_no, emp_name, start_date,curr_page,end_date);
      
      for(EmployeeVo employeeVo : boardList_employee) {
         Map<String,Object> map = new HashMap<String,Object>();

         BranchVo branchVo = branchSQLMapper.selectByBNO(employeeVo.getBranch_no());
         EmployeeRankVo employeeRankVo = employeeRankSQLMapper.selectByRankNo(employeeVo.getRank_no());
         
         map.put("employeeVo", employeeVo);
         map.put("branchVo", branchVo);
         map.put("employeeRankVo", employeeRankVo);
         list.add(map);
      }
      
      return list;
   }
   
   
   public List<Map<String,Object>> select_ShowAllBranch(){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> select_ShowAllBranch = branchSQLMapper.select_ShowAllBranch();
      
      for(BranchVo branchVo : select_ShowAllBranch) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;
      
   }

   public BranchVo selectByBranchNo_read_store_page(int branch_no, int curr_page) {
      BranchVo branchVo = branchSQLMapper.selectByBranchNo_read_store_page(branch_no, curr_page); 

         return branchVo;

   }
   
   
   public BranchVo selectByBranchNo_read_factory_page(int branch_no, int curr_page) {
      BranchVo branchVo = branchSQLMapper.selectByBranchNo_read_factory_page(branch_no, curr_page); 

         return branchVo;
   }
   
   

   public void addFactory(int branch_no, String branch_name, String branch_address, String branch_phone)
   {   
     branch_no = branchSQLMapper.createKey();
      
      branchSQLMapper.insertFactoryByEmp_code(branch_no,branch_name,branch_address,branch_phone);
      
   
      
   }

   public void addStore(int branch_no, String branch_name, String branch_address, String branch_phone) {
      branch_no = branchSQLMapper.createKey();
      
      branchSQLMapper.insertStoreByEmp_code
      (branch_no,branch_name,branch_address,branch_phone);

   }
   
   public List<Map<String,Object>> getBoardList_for_employee_add(){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList = branchSQLMapper.select_ShowAllBranch();
      
      for(BranchVo branchVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;
      
   }



   
   public int getBoardList_employee_paging(int dept_no, int branch_no, int emp_no, int rank_no, String emp_name,String start_date,int orderby ) {
      if(emp_name==null ) {
         return employeeSQLMapper.selectAllCount();
      } else {
      return employeeSQLMapper.getBoardList_employee_paging(dept_no, branch_no, emp_no, rank_no, emp_name, start_date,orderby);
      }
   }

   public int getBoardList_store_paging(int branch_no,String emp_name,String branch_address) {
      // TODO Auto-generated method stub
      return branchSQLMapper.getBoardList_store_paging(branch_no, emp_name, branch_address);
      }

   public int getBoardList_factory_paging(int branch_no,String emp_name,String branch_address) {
      // TODO Auto-generated method stub
      return branchSQLMapper.getBoardList_factory_paging(branch_no, emp_name, branch_address);
   }

   public int getBoardList_logistics_paging(int dept_no, String branch_name) {
      // TODO Auto-generated method stub
      return branchSQLMapper.getBoardList_logistics_paging(dept_no, branch_name);
   }


   public List<Map<String, Object>> getBoardList_All_store() {
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<BranchVo> boardList = branchSQLMapper.getBoardList_All_store();
      
      for(BranchVo branchVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;
   }


   public BranchVo list_for_branch_name_in_branch_stock_page(int branch_no) {

   
      BranchVo branchVo= branchSQLMapper.selectByBNO(branch_no);
      System.out.println(branchVo.getBranch_name());
      return branchVo;
         
   }


   public void modifyStore(int branch_no, String branch_name, int emp_code, String branch_phone,
         String branch_address) {

      branchSQLMapper.modifyStore(branch_no,branch_name,emp_code,branch_phone,branch_address);
      employeeSQLMapper.modifyBranchNoForModifyStore(branch_no,emp_code);
   }


   public void modifyFactory(int branch_no, String branch_name, int emp_code, String branch_phone,
         String branch_address) {
	  // 공장 수정 ?
      branchSQLMapper.modifyFactory(branch_no,branch_name,emp_code,branch_phone,branch_address);
      employeeSQLMapper.modifyBranchNoForModifyStore(branch_no,emp_code);
   }


   public BranchVo getBranchNameByEmpCode(int emp_code) {
      return branchSQLMapper.getBranchNameByEmpCode(emp_code);
   }



   public List<Map<String, Object>> getBoardList_Bybranch_type_no(int branch_type_no) {
      // TODO Auto-generated method stub
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      
      
      
      List<BranchVo> boardList = branchSQLMapper.selectBranchByType(branch_type_no);
      
      for(BranchVo branchVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;

   }


   public int getBoardList_employee_paging_resignation(int dept_no, int branch_no, int emp_no, int rank_no,
         String emp_name, String start_date,String end_date) {
      // TODO Auto-generated method stub
      return employeeSQLMapper.getBoardList_employee_paging_resignation(dept_no, branch_no, emp_no, rank_no, emp_name, start_date,end_date);
   }


   public List<Map<String, Object>> getBranchListByDeptNo(int dept_no) {
      // TODO Auto-generated method stub
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

      List<BranchVo> boardList = branchSQLMapper.getBranchListByDeptNo(dept_no);
      for(BranchVo branchVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("branchVo", branchVo);
         
         list.add(map);
      }
      
      return list;
   }


public List<Map<String, Object>> getBranchListByDeptNoForEmployeeAdd(int dept_no) {
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

    List<BranchVo> boardList = branchSQLMapper.getBranchListByDeptNoForEmployeeAdd(dept_no);
    for(BranchVo branchVo : boardList) {
       Map<String,Object> map = new HashMap<String,Object>();
       
       map.put("branchVo", branchVo);
       
       list.add(map);
    }
    
    return list;
}


}















   






   
   
