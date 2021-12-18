package com.bt.vo;

import java.util.Date;

public class StoreOrderReservationVo {

	  private int store_order_res_code;
	  private int emp_code;
	  private Date store_order_res_date;
	  private String store_order_res_no;
	  
	  public StoreOrderReservationVo() {
		// TODO Auto-generated constructor stub
	}

	public StoreOrderReservationVo(int store_order_res_code, int emp_code, Date store_order_res_date,
			String store_order_res_no) {
		super();
		this.store_order_res_code = store_order_res_code;
		this.emp_code = emp_code;
		this.store_order_res_date = store_order_res_date;
		this.store_order_res_no = store_order_res_no;
	}

	public int getStore_order_res_code() {
		return store_order_res_code;
	}

	public void setStore_order_res_code(int store_order_res_code) {
		this.store_order_res_code = store_order_res_code;
	}

	public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	public Date getStore_order_res_date() {
		return store_order_res_date;
	}

	public void setStore_order_res_date(Date store_order_res_date) {
		this.store_order_res_date = store_order_res_date;
	}

	public String getStore_order_res_no() {
		return store_order_res_no;
	}

	public void setStore_order_res_no(String store_order_res_no) {
		this.store_order_res_no = store_order_res_no;
	}


}
