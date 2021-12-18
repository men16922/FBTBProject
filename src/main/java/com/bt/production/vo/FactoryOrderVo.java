package com.bt.production.vo;

import java.util.Date;

public class FactoryOrderVo {
   
   private int factory_order_code;
   private String factory_order_res_no;
   private int emp_code;
   private Date factory_order_res_date;
   public FactoryOrderVo() {
      super();
   }
   public FactoryOrderVo(int factory_order_code, String factory_order_res_no, int emp_code,
         Date factory_order_res_date) {
      super();
      this.factory_order_code = factory_order_code;
      this.factory_order_res_no = factory_order_res_no;
      this.emp_code = emp_code;
      this.factory_order_res_date = factory_order_res_date;
   }
   public int getFactory_order_code() {
      return factory_order_code;
   }
   public void setFactory_order_code(int factory_order_code) {
      this.factory_order_code = factory_order_code;
   }
   public String getFactory_order_res_no() {
      return factory_order_res_no;
   }
   public void setFactory_order_res_no(String factory_order_res_no) {
      this.factory_order_res_no = factory_order_res_no;
   }
   public int getEmp_code() {
      return emp_code;
   }
   public void setEmp_code(int emp_code) {
      this.emp_code = emp_code;
   }
   public Date getFactory_order_res_date() {
      return factory_order_res_date;
   }
   public void setFactory_order_res_date(Date factory_order_res_date) {
      this.factory_order_res_date = factory_order_res_date;
   }
   
   
}