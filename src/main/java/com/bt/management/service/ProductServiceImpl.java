package com.bt.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.BranchStockSQLMapper;
import com.bt.mapper.ComponentStockSQLMapper;
import com.bt.mapper.FactoryOrderDetailSQLMapper;
import com.bt.mapper.FactoryOrderSQLMapper;
import com.bt.mapper.ProductComponentSQLMapper;
import com.bt.mapper.ProductFileSQLMapper;
import com.bt.mapper.ProductSQLMapper;
import com.bt.mapper.ProductTypeSQLMapper;
import com.bt.mapper.SupplierSQLMapper;
import com.bt.production.vo.ComponentStockVo;
import com.bt.production.vo.FactoryOrderDetailVo;
import com.bt.production.vo.FactoryOrderVo;
import com.bt.production.vo.ProductComponentVo;
import com.bt.production.vo.SupplierVo;
import com.bt.vo.BranchStockVo;
import com.bt.vo.ProductFileVo;
import com.bt.vo.ProductTypeVo;
import com.bt.vo.ProductVo;

@Service
public class ProductServiceImpl {
   
   
   @Autowired
   private ProductSQLMapper productSQLMapper;

   @Autowired
   private ProductFileSQLMapper productFileSQLMapper;
   @Autowired
   private ProductTypeSQLMapper productTypeSQLMapper;
   
   @Autowired
   private BranchStockSQLMapper branchStockSQLMapper;
   
   @Autowired
   private ComponentStockSQLMapper componentStockSQLMapper;
   
   @Autowired
   private ProductComponentSQLMapper productComponentSQLMapper;
   
   @Autowired
   private SupplierSQLMapper supplierSQLMapper;
   @Autowired
   private FactoryOrderDetailSQLMapper factoryOrderDetailSQLMapper;
   @Autowired
   private FactoryOrderSQLMapper factoryOrderSQLMapper;
   
   public List<Map<String,Object>> getBoardList(int curr_page){
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<ProductVo> boardList = productSQLMapper.selectAll(curr_page);

      for(ProductVo productVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         ProductTypeVo productTypeVo = productTypeSQLMapper.selectByProductNo(productVo.getProduct_no());
         map.put("productVo", productVo);
         map.put("productTypeVo", productTypeVo);
         list.add(map);
      }
   
      return list;
      
   }




   
   public void addProduct(ProductVo productVo, ProductFileVo fileVoList) {
	      int productKey = productSQLMapper.createKey_product();
	      productVo.setProduct_no(productKey);
	      productSQLMapper.addProduct(productVo);
		         if(fileVoList!=null) {
		        int productFileKey = productSQLMapper.createKey();
		        fileVoList.setProduct_no(productFileKey);
		         productFileSQLMapper.insert(productVo.getProduct_no(),fileVoList.getProduct_file_link_path(),fileVoList.getProduct_file_real_path());
		      }
		         }
	      
	   
	   

   
   
   
   
   
   
   public Map<String,Object> getProductInfo(int product_no){

      Map<String,Object> map = new HashMap<String,Object>();      
      ProductVo productVo = productSQLMapper.selectByProduct_no(product_no);

      ProductFileVo fileVoList = productFileSQLMapper.selectByProductNo(product_no);
      ProductTypeVo productTypeVo = productTypeSQLMapper.selectByProductNo(product_no);
   
      map.put("productTypeVo", productTypeVo);
      //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~         
         map.put("productVo", productVo);
         //~~~~~~~~~파일 업로드 기능~~~~~~~~~~~~~~~~~~~~~~~~
         map.put("fileVoList",fileVoList);
         //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
      
      return map;
      
   }



   public int getBoardList_product_paging() {
      // TODO Auto-generated method stub
      return productSQLMapper.getBoardList_product_paging();

   }



   public List<Map<String, Object>> store_branch_stock_list(int branch_no,int curr_page) {
      System.out.println("!!!!!!!!!!!!"+branch_no);
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<ProductVo> boardList = productSQLMapper.selectByBranchNo(branch_no,curr_page);
      System.out.println("2222222222222222222222SS"+branch_no);

      for(ProductVo productVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         ProductTypeVo productTypeVo = productTypeSQLMapper.selectByProductNo(productVo.getProduct_no());
         BranchStockVo branchStockVo = branchStockSQLMapper.selectByProductNo(productVo.getProduct_no());
         
         map.put("productVo", productVo);
         map.put("productTypeVo", productTypeVo);
         map.put("branchStockVo", branchStockVo);

         
         list.add(map);
      }
   
      return list;
   }



   public int store_branch_stock_list_paging(int branch_no) {
      // TODO Auto-generated method stub
      return productSQLMapper.store_branch_stock_list_paging(branch_no);
   }

   public List<Map<String, Object>> factory_branch_stock_list(int branch_no, int curr_page) {
      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<ComponentStockVo> boardList = componentStockSQLMapper.selectByBranchNo(branch_no,curr_page);
      for(ComponentStockVo componentStockVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         ProductComponentVo productComponentVo = productComponentSQLMapper.selectCompNo(componentStockVo.getComp_no());
         List<SupplierVo> boardList2 = supplierSQLMapper.selectByCompNo(componentStockVo.getComp_no());
         for(SupplierVo supplierVo : boardList2) {
            map.put("componentStockVo", componentStockVo);
            map.put("productComponentVo", productComponentVo);
            map.put("supplierVo", supplierVo);
            list.add(map);
         }

      }
   
      return list;
   }


   public int factory_branch_stock_list_paging(int branch_no) {
      // TODO Auto-generated method stub
      return componentStockSQLMapper.factory_branch_stock_list_paging(branch_no);
   }







   public List<Map<String, Object>> getFactoryOrderDetailInfo(int emp_code, String factory_order_res_no) {

      List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      List<FactoryOrderDetailVo> boardList = factoryOrderDetailSQLMapper.getFactoryOrderDetailInfo(emp_code, factory_order_res_no);

      for(FactoryOrderDetailVo factoryOrderDetailVo : boardList) {
         Map<String,Object> map = new HashMap<String,Object>();
         SupplierVo supplierVo = supplierSQLMapper.getFactoryOrderDetailInfo(emp_code,factory_order_res_no,factoryOrderDetailVo.getFactory_order_detail_no());
         FactoryOrderVo factoryOrderVo = factoryOrderSQLMapper.getFactoryOrderDetailInfo(emp_code,factory_order_res_no,factoryOrderDetailVo.getFactory_order_detail_no());
         ProductComponentVo productComponentVo = productComponentSQLMapper.getFactoryOrderDetailInfo(emp_code,factory_order_res_no,factoryOrderDetailVo.getFactory_order_detail_no());
         map.put("productComponentVo", productComponentVo);
         map.put("supplierVo", supplierVo);
         map.put("factoryOrderDetailVo", factoryOrderDetailVo);
         map.put("factoryOrderVo", factoryOrderVo);
         
         list.add(map);
      }
      return list;
   }





public void modifyProduct(ProductVo productVo,ProductFileVo fileVoList) {
	//int productKey = productSQLMapper.createKey_product();
    //productVo.setProduct_no(productKey);
    productSQLMapper.modifyProduct(productVo);
    if(fileVoList!=null) {
	      int productFileKey = productSQLMapper.createKey();
	       fileVoList.setProduct_no(productFileKey);
	       productFileSQLMapper.delete(productVo.getProduct_no());
	         productFileSQLMapper.insert(productVo.getProduct_no(),fileVoList.getProduct_file_link_path(),fileVoList.getProduct_file_real_path());
	      }else if(fileVoList==null) {
	    	  productFileSQLMapper.delete(productVo.getProduct_no());
	      }
    
}





public void deleteProduct(int product_no) {
    productSQLMapper.deleteProduct(product_no);
    productFileSQLMapper.delete(product_no);
	
}
    
   
}













