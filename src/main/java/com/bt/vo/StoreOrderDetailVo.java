package com.bt.vo;

import java.util.Date;

public class StoreOrderDetailVo {

	  private int store_order_detail_no;
	  private int store_order_res_code;
	  private int product_no;
	  private int first_order_qty;
	  private Date store_order_generating_time;
	  private Date store_order_completion_time;
	
	  public StoreOrderDetailVo() {
		// TODO Auto-generated constructor stub
	}

	public StoreOrderDetailVo(int store_order_detail_no, int store_order_res_code, int product_no, int first_order_qty,
			Date store_order_generating_time, Date store_order_completion_time) {
		super();
		this.store_order_detail_no = store_order_detail_no;
		this.store_order_res_code = store_order_res_code;
		this.product_no = product_no;
		this.first_order_qty = first_order_qty;
		this.store_order_generating_time = store_order_generating_time;
		this.store_order_completion_time = store_order_completion_time;
	}

	public int getStore_order_detail_no() {
		return store_order_detail_no;
	}

	public void setStore_order_detail_no(int store_order_detail_no) {
		this.store_order_detail_no = store_order_detail_no;
	}

	public int getStore_order_res_code() {
		return store_order_res_code;
	}

	public void setStore_order_res_code(int store_order_res_code) {
		this.store_order_res_code = store_order_res_code;
	}

	public int getProduct_no() {
		return product_no;
	}

	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}

	public int getFirst_order_qty() {
		return first_order_qty;
	}

	public void setFirst_order_qty(int first_order_qty) {
		this.first_order_qty = first_order_qty;
	}

	public Date getStore_order_generating_time() {
		return store_order_generating_time;
	}

	public void setStore_order_generating_time(Date store_order_generating_time) {
		this.store_order_generating_time = store_order_generating_time;
	}

	public Date getStore_order_completion_time() {
		return store_order_completion_time;
	}

	public void setStore_order_completion_time(Date store_order_completion_time) {
		this.store_order_completion_time = store_order_completion_time;
	}

	
	
	  
}
