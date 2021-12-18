package com.bt.sales.vo;

import java.util.Date;

public class Store_in_itemVo {
	private int store_in_item_no;
	private int product_no;
	private int in_item_qty;
	private int emp_code;
	private Date in_item_date;
	public Store_in_itemVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Store_in_itemVo(int store_in_item_no, int product_no, int in_item_qty, int emp_code, Date in_item_date) {
		super();
		this.store_in_item_no = store_in_item_no;
		this.product_no = product_no;
		this.in_item_qty = in_item_qty;
		this.emp_code = emp_code;
		this.in_item_date = in_item_date;
	}
	public int getStore_in_item_no() {
		return store_in_item_no;
	}
	public void setStore_in_item_no(int store_in_item_no) {
		this.store_in_item_no = store_in_item_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public int getIn_item_qty() {
		return in_item_qty;
	}
	public void setIn_item_qty(int in_item_qty) {
		this.in_item_qty = in_item_qty;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	public Date getIn_item_date() {
		return in_item_date;
	}
	public void setIn_item_date(Date in_item_date) {
		this.in_item_date = in_item_date;
	}

}
