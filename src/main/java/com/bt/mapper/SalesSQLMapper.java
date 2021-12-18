package com.bt.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.ProcessManagementVo;
import com.bt.sales.vo.CustomerVo;
import com.bt.sales.vo.Customer_reservation_listVo;
import com.bt.vo.ProcessStatusVo;
import com.bt.vo.ProductVo;
import com.bt.sales.vo.Reservation_statusVo;
import com.bt.sales.vo.Store_in_itemVo;
import com.bt.sales.vo.Store_out_itemVo;
import com.bt.vo.BranchStockVo;
import com.bt.vo.ProductFileVo;
import com.bt.vo.ProductTypeVo;
import com.bt.vo.StoreOrderDetailVo;
import com.bt.vo.StoreOrderReservationVo;


public interface SalesSQLMapper {
   
   public int createcustomerKey();
   
   public int createcustomerReservationKey();
   
   public int createstoreorderdetailreservationKey();
   
   public int createstoreorderreservationKey();
   
   public int createstoreoutitemKey();
   
   public int createrescodekey();
   
   public int createbranchstockKey();
   
   public String selectMaxstorerescode();
   
   public int createstoreinitemkey();
   
   public int selectstoreinprostano(int store_order_detail_no);
   
   public int selectproductno(int store_order_detail_no);
   
   public String selectconfirmqty(int store_order_detail_no);
   
   public String selectfirstqty(int store_order_detail_no);
   
   public  int [] selectprotype(int branch_no);
   
   public int selectstoreorderresno(String store_order_res_no);
   
   public int getmaxcusno();
   
   public int countreservation();
   
   public String selectqty(String res_no);
   
   public String selectproductnofromcus(String res_no);
   
   
   public List<StoreOrderReservationVo> selectstoreorderres( 
         @Param("branch_no") int branch_no,
            @Param("currPage") int currPage,
            @Param("sort") String sort);
   
   public List<StoreOrderReservationVo> selectstoreorderressearch( 
         @Param("branch_no") int branch_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("emp_name") String emp_name,
         @Param("store_order_start_date") String store_order_start_date,
         @Param("store_order_final_date") String store_order_final_date,
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public int getallProductcount(
         @Param("product_no") String product_no,
         @Param("product_name") String product_name,
         @Param("product_type_no") String product_type_no
         );
   public int getProductDataCount(
         @Param("branch_no") int branch_no,
          @Param("product_no") String product_no,
            @Param("product_name") String product_name,
            @Param("product_type_no") String product_type_no
         );
   
   public int storeinviewcount(
         @Param("branch_no") String branch_no,
         @Param("store_in_item_no") int store_in_item_no,
         @Param("product_name") String product_name,
         @Param("emp_name") String emp_name,
         @Param("in_start_date") String in_start_date,
         @Param("in_final_date") String in_final_date
         );
   
   public int storeincount(String branch_no);
   
   public int storeoutviewcount(
         @Param("branch_no") String branch_no,
         @Param("store_out_item_no") int store_out_item_no,
         @Param("product_name") String product_name,
         @Param("out_start_date") String out_start_date,
         @Param("out_final_date") String out_final_date,
         @Param("emp_name") String emp_name
         );
   
   public int customerviewcount(
         @Param("customer_name") String customer_name,
         @Param("customer_phone") String customer_phone);
   
   public int customerreservationviewcount(
         @Param("order_status_no") int order_status_no,
         @Param("customer_name") String customer_phone);
   
   public int storeorderviewcount(
         @Param("branch_no") int branch_no,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("emp_name") String emp_name,
         @Param("store_order_start_date") String store_order_start_date,
         @Param("store_order_final_date") String store_order_final_date
         );
   
   
   public String getcustomername(int customer_no);
   
   public ProductVo selectByProductno(@Param("product_no") int product_no,@Param("currPage") int currPage);
   
   public List<ProductVo> selectjustproduct();
   
   
   public List<ProductVo> selectallProduct(
         @Param("sort") String sort,
         @Param("currPage") int currPage
         );
   public List<ProductVo> selectallproductsearch(
         @Param("product_no") String product_no,
         @Param("product_name") String product_name,
         @Param("product_type_no") String product_type_no,
         @Param("sort") String sort,
         @Param("currPage") int currPage
         );
   
   public List<ProductVo> getproductbyprotype( 
           @Param("product_type_no") String product_type_no,
            @Param("sort") String sort,
            @Param("currPage") int currPage);
   
   public List<ProductVo> getallproductbyprice( 
           @Param("price") String price,
            @Param("currPage") int currPage);
   
   public List<BranchStockVo> getbranchproductbyprotype(
		   @Param("branch_no") int branch_no,
           @Param("product_type_no") String product_type_no,
            @Param("sort") String sort,
            @Param("currPage") int currPage);
   
   public List<BranchStockVo> getbranchproductbyprice(
		   @Param("branch_no") int branch_no,
           @Param("price") String price,
            @Param("currPage") int currPage);
   
   public CustomerVo selectByCustomerno(@Param("customer_no") int product_no);
   
   public Reservation_statusVo selectByorderstatusno(@Param("order_status_no") int order_status_no);
   
   public ProductTypeVo selectByProducttypeno(int product_type_no);
   
   public StoreOrderReservationVo selectBystoreorderrescode(int store_order_res_code);
   
   public List<StoreOrderDetailVo> selectdetailBystoreorderrescode(int store_order_res_code);
   
   public List<ProductVo> selectProductByBranchno(int branch_no);
   
   public List<ProductVo> selectProductBytypeno(
         @Param("product_type_no") int product_type_no,
          @Param("branch_no") int branch_no);
   
   public ProductVo selectProductByproductno(int product_no);
   
   public ProductFileVo selectproductfile(int product_no);
   
   public List<BranchStockVo> selectBranchStock(int branch_no);
   public List<BranchStockVo> selectByBranchno(
         @Param("branch_no") int branch_no,
         @Param("sort") String sort,
         @Param("currPage") int currPage);
   
   public List<CustomerVo> selectAllCustomer(
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public ProcessManagementVo selectByStoreorderdetailno(int store_order_detail_no);
   
   public ProcessStatusVo statusselectByStoreorderdetailno(int store_order_detail_no);
   
   public List<ProcessStatusVo> statusselectByStoreorderdetailno2(int store_order_detail_no);
 
   public List<Customer_reservation_listVo> selectAllCustomerReservation(
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   
   public List<Customer_reservation_listVo> selectCustomerReservationsearch(
         @Param("order_status_no") int order_status_no,
         @Param("customer_name") String customer_name,
         @Param("currPage") int currPage,
         @Param("sort") String sort
         );
   
   public List<Customer_reservation_listVo> selectCustomerReservationshow(
	         @Param("order_status_no") int order_status_no,
	         @Param("sort") String sort,
	         @Param("currPage") int currPage
	         );
   
   
   public void insertCustomer(CustomerVo customerVo);
   
   public void insertstoreorderreservation(
         @Param("store_order_res_code") int store_order_res_code,
         @Param("store_order_res_no") String store_order_res_no,
         @Param("emp_code") int emp_code);
   
   public void insertstoreorderdetail(
         @Param("store_order_res_code") int store_order_res_code,
         @Param("store_order_detail_no") int store_order_detail_no,
         @Param("store_order_res_no") String store_order_res_no, 
         @Param("product_no") String product_no,
         @Param("first_order_qty") String first_order_qty);
   
   public void insertproductstatus(
         @Param("process_status_no") int process_status_no,
         @Param("store_order_detail_no") int store_order_detail_no);
   
   public void insertCustomerreservation(
         @Param("res_no") int res_no,
         @Param("product_name") String product_name, 
         @Param("customer_res_qty") int customer_res_qty,
         @Param("customer_no") int customer_no,
         @Param("emp_code") int emp_code
         );
   
   public List<BranchStockVo> selectByproductnosearch(
         @Param("branch_no") int branch_no,
         @Param("product_no") String product_no, 
         @Param("product_name") String product_name, 
         @Param("product_type_no") String product_type_no,
         @Param("sort") String sort, 
         @Param("currPage") int currPage);
   
   public List<Store_in_itemVo> storeinselectByBranchno(
         @Param("branch_no") int branch_no, 
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public List<Store_in_itemVo> storeinselectBypriceBranchno(
	         @Param("branch_no") int branch_no, 
	         @Param("price") String price,
	         @Param("currPage") int currPage);
   
   public List<Store_out_itemVo> storeoutselectBypriceBranchno(
	         @Param("branch_no") int branch_no, 
	         @Param("price") String price,
	         @Param("currPage") int currPage);
   
   public List<Store_in_itemVo> storeinselectsearch(
         @Param("branch_no") int branch_no,
         @Param("store_in_item_no") int store_in_item_no, 
         @Param("product_name") String product_name, 
         @Param("emp_name") String emp_name, 
         @Param("in_start_date") String in_start_date,
         @Param("in_final_date") String in_final_date,
         @Param("currPage") int currPage);
   
   public List<Store_out_itemVo> storeoutselectByBranchno(
         @Param("branch_no") int branch_no, 
         @Param("currPage") int currPage,
         @Param("sort") String sort
         );
   
   public List<Store_out_itemVo> storeoutselectsearch(
         @Param("branch_no") int branch_no,
         @Param("store_out_item_no") int store_out_item_no, 
         @Param("product_name") String product_name, 
         @Param("out_start_date") String out_start_date,
         @Param("out_final_date") String out_final_date,
         @Param("emp_name") String emp_name,
         @Param("currPage") int currPage,
         @Param("sort") String sort
         );
   
   public List<StoreOrderDetailVo> storeorderselectByBranchno(
         @Param("branch_no") int branch_no, 
         @Param("currPage") int currPage,
         @Param("sort") String sort
         );
   
   public List<CustomerVo> selectCustomersearch(
         @Param("customer_name") String customer_name, 
         @Param("customer_phone") String customer_phone,
         @Param("sort") String sort, 
         @Param("currPage") int currPage
         );
   
   public List<StoreOrderDetailVo> selectAllstoreorder(
         @Param("branch_no") int branch_no, 
         @Param("currPage") int currPage,
         @Param("sort") String sort);
   
   public List<StoreOrderDetailVo> selectstoreordersearch(
         @Param("branch_no") int branch_no,
         @Param("store_order_res_code") int store_order_res_code,
         @Param("emp_name") String emp_name,
         @Param("store_order_start_date") String store_order_start_date,
         @Param("store_order_final_date") String store_order_final_date,
         @Param("currPage") int currPage);
   

   
   public void insertprocess(
         @Param("process_status_no") int process_status_no,
         @Param("store_order_detail_no") int store_order_detail_no);
   
   public void insertstoreinitem(
         @Param("store_in_item_no") int store_in_item_no,
         @Param("product_no") int product_no,
         @Param("confirm_qty") int confirm_qty,
         @Param("emp_code") int emp_code
         );
   
   public void storeoutprocess(
         @Param("store_out_item_no") int store_out_item_no,
         @Param("emp_code") int emp_code, 
         @Param("product_no") int product_no,
         @Param("out_item_qty") int out_item_qty
         );
   
   public void outstock(
         @Param("branch_no") int branch_no, 
         @Param("store_out_item_no") int store_out_item_no,
         @Param("product_no") int process_no
         );
   public void updatestock(
         @Param("branch_no") int branch_no, 
         @Param("process_status_no") int process_status_no
         );
   
   public void updateorderdetail(@Param("store_order_detail_no") int store_order_detail_no);
   
   public void rejectprocess(
         @Param("process_status_no") int process_status_no);
   
   public void insertBranchStock(
            @Param("branch_stock_key") int branch_stock_key, 
            @Param("branch_no") int branch_no,
            @Param("product_no") int product_no
            );
   
   public void updatecustomerreservation(String res_no);
   
   public int selectByTitleCount();
   
}