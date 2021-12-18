package com.bt.sales.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.vo.ProcessManagementVo;
import com.bt.mapper.BranchSQLMapper;
import com.bt.mapper.DepartmentBoardSQLMapper;
import com.bt.mapper.DepartmentFileSQLMapper;
import com.bt.mapper.DepartmentboardreplySQLMapper;
import com.bt.mapper.EmployeeSQLMapper;
import com.bt.mapper.ProcessListSQLMapper;
import com.bt.mapper.ProcessStatusSQLMapper;
import com.bt.mapper.SalesSQLMapper;
import com.bt.sales.vo.CustomerVo;
import com.bt.sales.vo.Customer_reservation_listVo;
import com.bt.vo.ProductVo;
import com.bt.sales.vo.Reservation_statusVo;
import com.bt.sales.vo.Store_in_itemVo;
import com.bt.sales.vo.Store_out_itemVo;
import com.bt.vo.BranchStockVo;
import com.bt.vo.BranchVo;
import com.bt.vo.DepartmentBoardVo;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.DepartmentboardreplyVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.ProcessListVo;
import com.bt.vo.ProcessStatusVo;
import com.bt.vo.ProductFileVo;
import com.bt.vo.ProductTypeVo;
import com.bt.vo.StoreOrderDetailVo;
import com.bt.vo.StoreOrderReservationVo;


@Service
public class SalesServiceImpl {

   @Autowired
   private SalesSQLMapper salesSQLMapper;

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
   private ProcessListSQLMapper processListSQLMapper;
   
   @Autowired
   private ProcessStatusSQLMapper processStatusSQLMapper;
   
   public List<Map<String, Object>> getBoardList(String dept_title, String dept_content, String dept_writer, int currPage, int dept_type_no) {
         // 게시물 가져오기
         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         List<DepartmentBoardVo> boardList = null;

         if (dept_title == null && dept_content==null && dept_writer ==null) {
            boardList = departmentBoardSQLMapper.selectAll(currPage, dept_type_no);
         } else {
            // 검색하는 경우
            boardList = departmentBoardSQLMapper.selectByTitle(dept_title,dept_content,dept_writer, currPage, dept_type_no);
         }

         for (DepartmentBoardVo departmentBoardVo : boardList) {
            EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("employeeVo", employeeVo);
            map.put("departmentBoardVo", departmentBoardVo);

            list.add(map);
         }

         return list;

      }

      public int getBoardCount(String dept_title,String dept_content,String dept_writer, int dept_type_no) {
         // 게시물 갯수 세기
         if (dept_title == null && dept_content==null && dept_writer ==null) {
            // 디폴트
            return departmentBoardSQLMapper.selectAllCount(dept_type_no);
         } else {
            // 검색했을때
            return departmentBoardSQLMapper.selectByTitleCount(dept_title, dept_content, dept_writer, dept_type_no);
         }
      }
      
      public int countreservation() {
    	  
    	 return salesSQLMapper.countreservation();
      }
      
      public int getempcodebyreplyno(int dept_board_reply_no) {
      	  
      	  return departmentboardreplySQLMapper.getempcodebyreplyno(dept_board_reply_no);
        }
   
   public List<Map<String, Object>> getnoticeBoardList(String dept_title,String dept_content,String dept_writer, int currPage, int dept_type_no) {
      // 게시물 가져오기
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      List<DepartmentBoardVo> boardList = null;

      if (dept_title == null && dept_content==null && dept_writer ==null) {
         boardList = departmentBoardSQLMapper.selectnotice(currPage, dept_type_no);
      } else {
         // 검색하는 경우
         boardList = departmentBoardSQLMapper.selectnoticeByTitle(dept_title, dept_content, dept_writer, currPage, dept_type_no);
      }

      for (DepartmentBoardVo departmentBoardVo : boardList) {
         EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
         Map<String, Object> map = new HashMap<String, Object>();

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
   
   public int maxcusno() {
	   int maxcusno = salesSQLMapper.getmaxcusno();
	   return maxcusno;
   }

   public void writeContent(DepartmentBoardVo departmentBoardVo, List<DepartmentFileVo> fileVoList) {
      // 게시글 작성
      departmentBoardSQLMapper.insert(departmentBoardVo);
   
      // System.out.println("게시글 ~ : "+departmentBoardVo.getDept_board_no());
      for (DepartmentFileVo departmentFileVo : fileVoList) {
         int dept_file_no=departmentFileSQLMapper.createKey();
         departmentFileVo.setDept_file_no(dept_file_no);
         departmentFileVo.setDept_board_no(departmentBoardVo.getDept_board_no());
   
         departmentFileSQLMapper.insert(departmentFileVo);
      }
   }
   
   public void updatefile(DepartmentBoardVo departmentBoardVo, List<DepartmentFileVo> fileVoList) {
	   departmentFileSQLMapper.deletebyboardno(departmentBoardVo.getDept_board_no());
         // System.out.println("게시글 ~ : "+departmentBoardVo.getDept_board_no());
         for (DepartmentFileVo departmentFileVo : fileVoList) {
            int dept_file_no=departmentFileSQLMapper.createKey();
            departmentFileVo.setDept_file_no(dept_file_no);
            departmentFileVo.setDept_board_no(departmentBoardVo.getDept_board_no());
            
            departmentFileSQLMapper.insert(departmentFileVo);
         }
      }


   public void updateCheck(int dept_board_no, int emp_code) {
	      // 공지사항 등록하기
	      departmentBoardSQLMapper.updateCheck(dept_board_no, emp_code);
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

   public Map<String, Object> readContent(int dept_board_no, int dept_no) {
      // 게시글 읽기
      Map<String, Object> map = new HashMap<String, Object>();

      DepartmentBoardVo departmentBoardVo = departmentBoardSQLMapper.selectByNo(dept_board_no, dept_no);
      EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentBoardVo.getEmp_code());
      EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(employeeVo.getRank_no());
      BranchVo branchVo = branchSQLMapper.selectByBNO(employeeVo.getBranch_no());
      List<DepartmentFileVo> departmentFileVolist = departmentFileSQLMapper.selectByBoardNo(dept_board_no);
      map.put("departmentBoardVo", departmentBoardVo);
      map.put("employeeVo", employeeVo);
      map.put("employeeRankVo", employeeRankVo);
      map.put("branchVo", branchVo);
      map.put("departmentFileVolist",departmentFileVolist);

      return map;
   }

   public void updateRead(int dept_board_no, int emp_code, int dept_type_no) {
      // 조회수 업데이트
	   DepartmentBoardVo departmentBoardVo= departmentBoardSQLMapper.selectByNo(dept_board_no, dept_type_no);
	   EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(departmentBoardVo.getEmp_code());
	   if(!(emp_code == employeeVo.getEmp_code())) {
      departmentBoardSQLMapper.updateView(dept_board_no);
	   }
   }

   public void update(int dept_board_no,String dept_board_title, String dept_board_content,int emp_code) {
      // 게시글 수정
      departmentBoardSQLMapper.update(dept_board_no,dept_board_title,emp_code,dept_board_content);
   }

   public void delete(int dept_board_no, int emp_code) {
	      departmentBoardSQLMapper.delete(dept_board_no, emp_code);
	      departmentboardreplySQLMapper.deletebyboardno(dept_board_no);
	      departmentFileSQLMapper.deletebyboardno(dept_board_no);
	   }
   // 부서게시판 끝
   
   public int getProductDataCount(
         @Param("branch_no") int branch_no,
         @Param("product_no") String product_no,
         @Param("product_name") String product_name,
         @Param("product_type_no") String product_type_no
         ) {
         
      return salesSQLMapper.getProductDataCount(branch_no,product_no, product_name, product_type_no);
           }

   
     public int getallProductcount(
           @Param("product_no") String product_no,
           @Param("product_name") String product_name,
           @Param("product_type_no") String product_type_no) 
     {
     
     return salesSQLMapper.getallProductcount(product_no,product_name,product_type_no);
     
     }
    
     public int storeinviewcount( 
           @Param("branch_no") String branch_no,
             @Param("store_in_item_no") int store_in_item_no,
             @Param("product_name") String product_name,
             @Param("emp_name") String emp_name,
             @Param("in_start_date") String in_start_date,
             @Param("in_final_date") String in_final_date
           )
     
     {
        
        return salesSQLMapper.storeinviewcount(branch_no,store_in_item_no,product_name,emp_name,in_start_date,in_final_date);
     }
     
     public int storeincount(String branch_no)
     {
        return salesSQLMapper.storeincount(branch_no);
     }
     
     public int storeoutviewcount( 
           @Param("branch_no") String branch_no,
             @Param("store_out_item_no") int store_out_item_no,
             @Param("product_name") String product_name,
             @Param("out_start_date") String out_start_date,
             @Param("out_final_date") String out_final_date,
             @Param("emp_name") String emp_name
           )
        
     {
        
        return salesSQLMapper.storeoutviewcount(branch_no,store_out_item_no,product_name,out_start_date,out_final_date,emp_name);     
        }
     
     public int customerviewcount(String customer_name, String customer_phone) {
        
        return salesSQLMapper.customerviewcount(customer_name,customer_phone);
     }
     
     public int customerreservationviewcount(int order_status_no, String customer_name) {
        return salesSQLMapper.customerreservationviewcount(order_status_no,customer_name);
     }
     
     public int storeorderviewcount(int branch_no, String store_order_res_no, String emp_name, String store_order_start_date,String store_order_final_date)
     {
        return salesSQLMapper.storeorderviewcount(branch_no,store_order_res_no, emp_name,store_order_start_date,store_order_final_date);
     }
     public String getcustomername(int customer_no) {
        return salesSQLMapper.getcustomername(customer_no);
     }

     
   public List<Map<String, Object>> getproduct(int branch_no, String product_no, String product_name,
         String product_type_no, String sort, int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<BranchStockVo> branch_stockList = null;

      if (product_no == null && product_type_no == null && product_name == null) {
         branch_stockList = salesSQLMapper.selectByBranchno(branch_no, sort, currPage);

      } else {

         System.out.println(
               branch_no + " : " + product_no + " : " + product_name + " : " + product_type_no + " : " + sort);

         branch_stockList = salesSQLMapper.selectByproductnosearch(branch_no, product_no, product_name,
               product_type_no, sort, currPage);

      }

      for (BranchStockVo branch_stockVo : branch_stockList) {

         ProductVo productVo = salesSQLMapper.selectByProductno(branch_stockVo.getProduct_no(), currPage);

         ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());

         Map<String, Object> map = new HashMap<String, Object>();
         map.put("branch_stockVo", branch_stockVo);
         map.put("productVo", productVo);
         map.put("productTypeVo", productTypeVo);

         list.add(map);
      }

      return list;
   }
   
   public List<Map<String, Object>> getproductbyprotype(String product_type_no,
            String sort, int currPage) {

         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

         List<ProductVo> productList = null;

         productList = salesSQLMapper.getproductbyprotype(product_type_no, sort,currPage);

     

         for (ProductVo productVo : productList) {

            ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productVo", productVo);
            map.put("productTypeVo", productTypeVo);

            list.add(map);
         }

         return list;
      }
   
   public List<Map<String, Object>> getallproductbyprice(String price, int currPage) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<ProductVo> productList = null;

        productList = salesSQLMapper.getallproductbyprice(price,currPage);

    

        for (ProductVo productVo : productList) {

           ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("productVo", productVo);
           map.put("productTypeVo", productTypeVo);

           list.add(map);
        }

        return list;
     }
   
   public List<Map<String, Object>> getbranchproductbyprotype(int branch_no, String product_type_no,
           String sort, int currPage) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<BranchStockVo> productList = null;

        productList = salesSQLMapper.getbranchproductbyprotype(branch_no,product_type_no, sort,currPage);

    

        for (BranchStockVo branchStockVo : productList) {
           ProductVo productVo = salesSQLMapper.selectProductByproductno(branchStockVo.getProduct_no());
           ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("branchStockVo", branchStockVo);
           map.put("productVo", productVo);
           map.put("productTypeVo", productTypeVo);

           list.add(map);
        }

        return list;
     }
   
   public List<Map<String, Object>> getbranchproductbyprice(int branch_no, String price,
            int currPage) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<BranchStockVo> productList = null;

        productList = salesSQLMapper.getbranchproductbyprice(branch_no,price, currPage);

    

        for (BranchStockVo branchStockVo : productList) {
           ProductVo productVo = salesSQLMapper.selectProductByproductno(branchStockVo.getProduct_no());
           ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("branchStockVo", branchStockVo);
           map.put("productVo", productVo);
           map.put("productTypeVo", productTypeVo);

           list.add(map);
        }

        return list;
     }
   
   public Map<String, Object> selectByprono(int product_no) {

         Map<String, Object> map = new HashMap<String, Object>();

         ProductVo productVo = salesSQLMapper.selectProductByproductno(product_no);
         ProductFileVo productFileVo = salesSQLMapper.selectproductfile(product_no);
         ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
         map.put("productVo", productVo);
         map.put("productFileVo", productFileVo);
         map.put("productTypeVo", productTypeVo);

         return map;
      }
   public List<Map<String, Object>> getallproduct(String product_no, String product_name, String product_type_no,
         String sort, int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<ProductVo> productList = null;

      if (product_no == null && product_type_no == null && product_name == null) {
         productList = salesSQLMapper.selectallProduct(sort, currPage);

      } else {

         productList = salesSQLMapper.selectallproductsearch(product_no, product_name, product_type_no, sort,
               currPage);

      }

      for (ProductVo productVo : productList) {

         ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("productVo", productVo);
         map.put("productTypeVo", productTypeVo);

         list.add(map);
      }

      return list;
   }
   
   public List<Map<String, Object>> storeinpriceview(String branch_no, String price, int currPage) {

	      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	      List<Store_in_itemVo> store_in_itemVoList = null;

	         store_in_itemVoList = salesSQLMapper.storeinselectBypriceBranchno(Integer.parseInt(branch_no), price, currPage);

	      
	      for (Store_in_itemVo store_in_itemVo : store_in_itemVoList) {

	         ProductVo productVo = salesSQLMapper.selectByProductno(store_in_itemVo.getProduct_no(), currPage);
	         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(store_in_itemVo.getEmp_code());
	         Map<String, Object> map = new HashMap<String, Object>();
	         map.put("store_in_itemVo", store_in_itemVo);
	         map.put("productVo", productVo);
	         map.put("employeeVo", employeeVo);

	         list.add(map);
	      }

	      return list;
	   }
   
   
   public List<Map<String, Object>> storeinview(String branch_no, int store_in_item_no, String product_name,
         String emp_name,String in_start_date, String in_final_date, String sort, int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<Store_in_itemVo> store_in_itemVoList = null;

      if (store_in_item_no == 0 && product_name == null && emp_name == null && in_start_date == null && in_final_date == null) {
         store_in_itemVoList = salesSQLMapper.storeinselectByBranchno(Integer.parseInt(branch_no), currPage, sort);

      } else {


         store_in_itemVoList = salesSQLMapper.storeinselectsearch(Integer.parseInt(branch_no), store_in_item_no,
               product_name, emp_name, in_start_date, in_final_date, currPage);
      }

      for (Store_in_itemVo store_in_itemVo : store_in_itemVoList) {

         ProductVo productVo = salesSQLMapper.selectByProductno(store_in_itemVo.getProduct_no(), currPage);
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(store_in_itemVo.getEmp_code());
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("store_in_itemVo", store_in_itemVo);
         map.put("productVo", productVo);
         map.put("employeeVo", employeeVo);

         list.add(map);
      }

      return list;
   }
   public List<Map<String, Object>> storeoutpriceview(String branch_no, String price, int currPage) {

	      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	      List<Store_out_itemVo> store_out_itemVoList = null;

	      store_out_itemVoList = salesSQLMapper.storeoutselectBypriceBranchno(Integer.parseInt(branch_no), price, currPage);

	      
	      for (Store_out_itemVo store_out_itemVo : store_out_itemVoList) {

	         ProductVo productVo = salesSQLMapper.selectByProductno(store_out_itemVo.getProduct_no(), currPage);
	         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(store_out_itemVo.getEmp_code());
	         Map<String, Object> map = new HashMap<String, Object>();
	         map.put("store_out_itemVo", store_out_itemVo);
	         map.put("productVo", productVo);
	         map.put("employeeVo", employeeVo);

	         list.add(map);
	      }

	      return list;
	   }
   
   
   public List<Map<String, Object>> storeoutview(String branch_no, int store_out_item_no, String product_name,
         String out_start_date, String out_final_date, String emp_name, String sort, int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<Store_out_itemVo> store_out_itemVoList = null;

      if (store_out_item_no == 0 && product_name == null && out_start_date == null && out_final_date == null
            && emp_name == null) {
         store_out_itemVoList = salesSQLMapper.storeoutselectByBranchno(Integer.parseInt(branch_no), currPage, sort);

      } else {
         System.out.println(branch_no + " : " + store_out_item_no + " : " + product_name + " : " + out_start_date
               + " : " + out_final_date + " : " + emp_name);

         store_out_itemVoList = salesSQLMapper.storeoutselectsearch(Integer.parseInt(branch_no), store_out_item_no,
               product_name, out_start_date, out_final_date, emp_name, currPage, sort);
      }

      for (Store_out_itemVo store_out_itemVo : store_out_itemVoList) {

         ProductVo productVo = salesSQLMapper.selectByProductno(store_out_itemVo.getProduct_no(), currPage);
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(store_out_itemVo.getEmp_code());
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("store_out_itemVo", store_out_itemVo);
         map.put("productVo", productVo);
         map.put("employeeVo", employeeVo);

         list.add(map);
      }

      return list;
   }

   public List<Map<String, Object>> storein(String branch_no, int currPage, String sort) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<StoreOrderDetailVo> store_order_detailList = null;

      System.out.println(branch_no + ":" + currPage);
      store_order_detailList = salesSQLMapper.storeorderselectByBranchno(Integer.parseInt(branch_no), currPage, sort);

      for (StoreOrderDetailVo storeorderdetailVo : store_order_detailList) {

         ProductVo productVo = salesSQLMapper.selectByProductno(storeorderdetailVo.getProduct_no(), currPage);
         System.out.println("프"+storeorderdetailVo.getStore_order_detail_no());
         ProcessManagementVo processManagementVo = salesSQLMapper.selectByStoreorderdetailno(storeorderdetailVo.getStore_order_detail_no());
         ProductTypeVo ProductTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());

         System.out.println("qweweqe : " + storeorderdetailVo.getStore_order_detail_no());
         List<ProcessStatusVo> processStatusVolist = salesSQLMapper.statusselectByStoreorderdetailno2(storeorderdetailVo.getStore_order_detail_no());

         for (ProcessStatusVo processStatusVo : processStatusVolist) {
            Map<String, Object> map = new HashMap<String, Object>();

            if (processStatusVo.getProcess_no() == 5) {
               map.put("storeorderdetailVo", storeorderdetailVo);
               map.put("productVo", productVo);
               map.put("processManagementVo", processManagementVo);
               map.put("ProductTypeVo", ProductTypeVo);
               map.put("processStatusVo", processStatusVo);
               list.add(map);
            }
         }

      }

      return list;
   }

   public List<Map<String, Object>> customerview(String customer_name, String customer_phone, String sort,
         int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<CustomerVo> customerList = null;

      if (customer_name == null && customer_phone == null) {
         customerList = salesSQLMapper.selectAllCustomer(currPage, sort);

      } else {
         customerList = salesSQLMapper.selectCustomersearch(customer_name, customer_phone, sort, currPage);
      }

      for (CustomerVo customerVo : customerList) {

         Map<String, Object> map = new HashMap<String, Object>();

         map.put("customerVo", customerVo);
         list.add(map);
      }

      return list;
   }
   
   public List<Map<String, Object>> customerreservationshow(int order_status_no, String sort,
	         int currPage) {

	      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	      List<Customer_reservation_listVo> customerreservationList = null;

	    
	         customerreservationList = salesSQLMapper.selectCustomerReservationshow(order_status_no, sort,currPage);
	   
	      for (Customer_reservation_listVo customer_reservation_listVo : customerreservationList) {

	         CustomerVo customerVo = salesSQLMapper.selectByCustomerno(customer_reservation_listVo.getCustomer_no());
	         Reservation_statusVo reservation_statusVo = salesSQLMapper
	               .selectByorderstatusno(customer_reservation_listVo.getOrder_status_no());
	         ProductVo productVo = salesSQLMapper.selectByProductno(customer_reservation_listVo.getProduct_no(),
	               currPage);

	         Map<String, Object> map = new HashMap<String, Object>();

	         map.put("customer_reservation_listVo", customer_reservation_listVo);
	         map.put("customerVo", customerVo);
	         map.put("reservation_statusVo", reservation_statusVo);
	         map.put("productVo", productVo);
	         list.add(map);
	      }

	      return list;
	   }
   
   public List<Map<String, Object>> customerreservationview(int order_status_no, String customer_name, String sort,
         int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<Customer_reservation_listVo> customerreservationList = null;

      if (customer_name == null && order_status_no == 0) {
         customerreservationList = salesSQLMapper.selectAllCustomerReservation(currPage, sort);

      } else {
         customerreservationList = salesSQLMapper.selectCustomerReservationsearch(order_status_no, customer_name, currPage,
               sort);

      }

      for (Customer_reservation_listVo customer_reservation_listVo : customerreservationList) {

         CustomerVo customerVo = salesSQLMapper.selectByCustomerno(customer_reservation_listVo.getCustomer_no());
         Reservation_statusVo reservation_statusVo = salesSQLMapper
               .selectByorderstatusno(customer_reservation_listVo.getOrder_status_no());
         ProductVo productVo = salesSQLMapper.selectByProductno(customer_reservation_listVo.getProduct_no(),
               currPage);

         Map<String, Object> map = new HashMap<String, Object>();

         map.put("customer_reservation_listVo", customer_reservation_listVo);
         map.put("customerVo", customerVo);
         map.put("reservation_statusVo", reservation_statusVo);
         map.put("productVo", productVo);
         list.add(map);
      }

      return list;
   }

   public List<Map<String, Object>> storeorderview(int branch_no, String store_order_res_no, String emp_name,
         String store_order_start_date, String store_order_final_date, String sort, int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      List<StoreOrderReservationVo> store_order_reslist=null;

      if (store_order_res_no == null && emp_name == null && store_order_start_date == null
              && store_order_final_date == null) {
         store_order_reslist= salesSQLMapper.selectstoreorderres(branch_no,currPage,sort);
      }
      else {
     
          store_order_reslist = salesSQLMapper.selectstoreorderressearch(branch_no, store_order_res_no, emp_name,
                  store_order_start_date, store_order_final_date, currPage,sort);
      }
      for(StoreOrderReservationVo storeOrderReservationVo : store_order_reslist) {
         Map<String, Object> map = new HashMap<String, Object>();
         EmployeeVo employeeVo = employeeSQLMapper.selectByEmpCode(storeOrderReservationVo.getEmp_code());
         map.put("storeOrderReservationVo", storeOrderReservationVo);
         map.put("employeeVo", employeeVo);
         list.add(map);
      }
     

      return list;
   }

  
   
   public List<Map<String, Object>> getDetail(String store_order_res_no) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      
      int store_order_res_code=salesSQLMapper.selectstoreorderresno(store_order_res_no);
      
      System.out.println(store_order_res_code);
      List<StoreOrderDetailVo> storeorderdetailList = salesSQLMapper.selectdetailBystoreorderrescode(store_order_res_code);
    
      for(StoreOrderDetailVo storeOrderDetailVo : storeorderdetailList) {
         Map<String, Object> map = new HashMap<String, Object>();
         ProductVo productVo = salesSQLMapper.selectByProductno(storeOrderDetailVo.getProduct_no(), 1);
         ProcessStatusVo processStatusVo = salesSQLMapper.statusselectByStoreorderdetailno(storeOrderDetailVo.getStore_order_detail_no());

         ProcessListVo processListVo = processListSQLMapper.selectByPrNo(processStatusVo.getProcess_no());
         ProductTypeVo ProductTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
         System.out.println("프"+storeOrderDetailVo.getStore_order_detail_no());
         ProcessManagementVo processManagementVo = salesSQLMapper.selectByStoreorderdetailno(storeOrderDetailVo.getStore_order_detail_no());
         if(processManagementVo==null) {
        	 System.out.println("비었음");
         }
                map.put("storeOrderDetailVo",storeOrderDetailVo); // map은 순서가 없다
                map.put("productVo", productVo);
                map.put("processStatusVo", processStatusVo);
                map.put("process_listVo", processListVo);
                map.put("ProductTypeVo", ProductTypeVo);
                map.put("processManagementVo", processManagementVo);
                
        
         list.add(map);
      }


     
      return list;
   }

   public List<Map<String, Object>> storeorderresview(int branch_no) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      List<ProductVo> storeproductList = null;

      storeproductList = salesSQLMapper.selectProductByBranchno(branch_no);

      for (ProductVo productVo : storeproductList) {

         HashMap<String, Object> map = new HashMap<String, Object>();
         ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());

         map.put("productVo", productVo);
         map.put("productTypeVo", productTypeVo);

         list.add(map);

      }

      return list;
   }
   
   public List<Map<String, Object>> productselect() {

         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

         List<ProductVo> storeproductList = null;

         storeproductList = salesSQLMapper.selectjustproduct();

         for (ProductVo productVo : storeproductList) {

            HashMap<String, Object> map = new HashMap<String, Object>();
            ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());

            map.put("productVo", productVo);
            map.put("productTypeVo", productTypeVo);

            list.add(map);

         }

         return list;
      }

   public List<Map<String, Object>> storeorderproductview(int branch_no) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      // List<ProductVo> storeproductList=null;

      // storeproductList =salesSQLMapper.selectProductByBranchno(branch_no);

      int[] typeList = salesSQLMapper.selectprotype(branch_no);

      for (int i = 0; i < typeList.length; i++) {
         HashMap<String, Object> map = new HashMap<String, Object>();

         ProductTypeVo productTypeVo = salesSQLMapper.selectByProducttypeno(typeList[i]);
         map.put("productTypeVo", productTypeVo);
         list.add(map);
      }

      /*
       * for(ProductVo productVo: storeproductList) {
       * 
       * 
       * HashMap<String,Object> map = new HashMap<String,Object>(); ProductTypeVo
       * productTypeVo
       * =salesSQLMapper.selectByProducttypeno(productVo.getProduct_type_no());
       * 
       * map.put("productVo",productVo); map.put("productTypeVo",productTypeVo);
       * 
       * list.add(map);
       * 
       * }
       */

      return list;
   }

   public List<ProductVo> getproductlist(int product_type_no, int branch_no) {

      List<ProductVo> list = salesSQLMapper.selectProductBytypeno(product_type_no,branch_no);

      return list;
   }

   public Map<String, Object> getproductprice(int product_no) {

      Map<String, Object> map = new HashMap<String, Object>();

      ProductVo productVo = salesSQLMapper.selectProductByproductno(product_no);
      ProductFileVo productFileVo = salesSQLMapper.selectproductfile(product_no);

      map.put("productVo", productVo);
      map.put("productFileVo", productFileVo);

      return map;
   }

   public void joinCustomer(CustomerVo customerVo)

   {
      int customer_code = salesSQLMapper.createcustomerKey();

      customerVo.setCustomer_code(customer_code);
      customerVo.setCustomer_no(customer_code);
      salesSQLMapper.insertCustomer(customerVo);

   }

   public void insertstoreorderdetailprocess(int emp_code, String store_order_res_no, String product_no[],
         String first_order_qty[]) {
      int store_order_res_code = salesSQLMapper.createstoreorderreservationKey();

      salesSQLMapper.insertstoreorderreservation(store_order_res_code, store_order_res_no, emp_code);

      for (int i = 0; i < product_no.length; i++) {
         System.out.println(product_no[i]);
         System.out.println(first_order_qty[i]);
         int store_order_detail_no = salesSQLMapper.createstoreorderdetailreservationKey();
         salesSQLMapper.insertstoreorderdetail(store_order_res_code, store_order_detail_no, store_order_res_no,
               product_no[i], first_order_qty[i]);
         int process_status_no = processStatusSQLMapper.createKey();
         salesSQLMapper.insertproductstatus(process_status_no, store_order_detail_no);
      }
   }
   
   public void insertstoreorderdetailprocessfromcus(int emp_code, String store_order_res_no, String res_no[]) {
	      int store_order_res_code = salesSQLMapper.createstoreorderreservationKey();

	      salesSQLMapper.insertstoreorderreservation(store_order_res_code, store_order_res_no, emp_code);

	      for (int i = 0; i < res_no.length; i++) {
	         System.out.println(res_no[i]);
	        
	         int store_order_detail_no = salesSQLMapper.createstoreorderdetailreservationKey();
	         String product_no = salesSQLMapper.selectproductnofromcus(res_no[i]);
	         String first_order_qty = salesSQLMapper.selectqty(res_no[i]);
	         salesSQLMapper.insertstoreorderdetail(store_order_res_code, store_order_detail_no, store_order_res_no,
	               product_no, first_order_qty);
	         int process_status_no = processStatusSQLMapper.createKey();
	         salesSQLMapper.insertproductstatus(process_status_no, store_order_detail_no);
	      }
	   }

   public void storeorderreservation(String product_name, int customer_res_qty, int customer_no, int emp_code)

   {
      int res_no = salesSQLMapper.createcustomerReservationKey();

      salesSQLMapper.insertCustomerreservation(res_no, product_name, customer_res_qty, customer_no, emp_code);

   }

   public void insertprocess(int branch_no, int emp_code, int check) {

      int process_status_no = processStatusSQLMapper.createKey();
      salesSQLMapper.insertprocess(process_status_no, check);
      salesSQLMapper.updateorderdetail(check);
      int process_status_no_2 = salesSQLMapper.selectstoreinprostano(check);
      int product_no = salesSQLMapper.selectproductno(check);
      List<BranchStockVo> branchstocklist = salesSQLMapper.selectBranchStock(branch_no);
      boolean count=false;
      for(BranchStockVo branchStockVo : branchstocklist) {
         if(branchStockVo.getProduct_no()==product_no) {
            count=true;
         }
         
      }
      if(!count) {
         int branch_stock_key= salesSQLMapper.createbranchstockKey();
         
         salesSQLMapper.insertBranchStock(branch_stock_key,branch_no,product_no);
      }
      
      salesSQLMapper.updatestock(branch_no, process_status_no_2);
      int store_in_item_no = salesSQLMapper.createstoreinitemkey();
      int confirm_qty = Integer.parseInt(salesSQLMapper.selectfirstqty(check));
      if(salesSQLMapper.selectconfirmqty(check) !=null){
      
      confirm_qty = Integer.parseInt(salesSQLMapper.selectconfirmqty(check));
      }
     
    	  
      salesSQLMapper.insertstoreinitem(store_in_item_no, product_no, confirm_qty, emp_code);
   }

   public void rejectprocess(int check) {
      salesSQLMapper.rejectprocess(check);
   }

   public void storeoutprocess(int branch_no, int emp_code, int product_no_2, int out_item_qty) {
      int store_out_item_no = salesSQLMapper.createstoreoutitemKey();
      salesSQLMapper.storeoutprocess(store_out_item_no, emp_code, product_no_2, out_item_qty);
      salesSQLMapper.outstock(branch_no, store_out_item_no, product_no_2);

   }
   
   public void updatecustomerreservation(String[] res_no) {
	   for(int i=0; i<res_no.length; i++) {
	   salesSQLMapper.updatecustomerreservation(res_no[i]);
	   }
   }

   public String selectMaxstorerescode() {
      return salesSQLMapper.selectMaxstorerescode();
   }
   public int createrescodekey() {
      return salesSQLMapper.createrescodekey();
   }
   
   
   public void writeReply(DepartmentboardreplyVo departmentboardreplyVo) {
      
      int dept_board_reply_no = departmentboardreplySQLMapper.replyCreatekey();
      departmentboardreplyVo.setDept_board_reply_no(dept_board_reply_no);
      departmentboardreplySQLMapper.insert(departmentboardreplyVo);
      
   }
   
  public void deleteReply(int dept_board_reply_no) {

      departmentboardreplySQLMapper.delete(dept_board_reply_no);
      
   }
   
   public List<Map<String, Object>> getReplyList(int board_no){
      
      List<Map<String,Object>> list = new ArrayList<>();
      
      List<DepartmentboardreplyVo> replyVoList = departmentboardreplySQLMapper.selectByBoardNo(board_no);
      
      for(DepartmentboardreplyVo departmentboardreplyVo: replyVoList) {
         
         EmployeeVo employeeVo = employeeSQLMapper.selectByCode(departmentboardreplyVo.getEmp_code());
         
         Map<String,Object> map = new HashMap<String,Object>();
         
         map.put("employeeVo",employeeVo);
         map.put("departmentboardreplyVo",departmentboardreplyVo);
         
         list.add(map);
      }
      
      return list;
   }
   public void updatereply(int dept_board_reply_no, String dept_board_reply_content) 
   {
         
         departmentboardreplySQLMapper.update(dept_board_reply_no, dept_board_reply_content);
   }
}