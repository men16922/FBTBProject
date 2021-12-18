package com.bt.production.vo;

import java.util.Date;

public class FactoryOutItemVo {

	private int factory_out_item_code;
	private int factory_out_item_no;
	private int product_no;
	private int factory_out_item_qty;
	private int emp_code;
	private Date factory_out_item_date;
	private int branch_no;

	public FactoryOutItemVo() {
		super();
	}

	public FactoryOutItemVo(int factory_out_item_code, int factory_out_item_no, int product_no,
			int factory_out_item_qty, int emp_code, Date factory_out_item_date, int branch_no) {
		super();
		this.factory_out_item_code = factory_out_item_code;
		this.factory_out_item_no = factory_out_item_no;
		this.product_no = product_no;
		this.factory_out_item_qty = factory_out_item_qty;
		this.emp_code = emp_code;
		this.factory_out_item_date = factory_out_item_date;
		this.branch_no = branch_no;
	}

	public int getFactory_out_item_code() {
		return factory_out_item_code;
	}

	public void setFactory_out_item_code(int factory_out_item_code) {
		this.factory_out_item_code = factory_out_item_code;
	}

	public int getFactory_out_item_no() {
		return factory_out_item_no;
	}

	public void setFactory_out_item_no(int factory_out_item_no) {
		this.factory_out_item_no = factory_out_item_no;
	}

	public int getProduct_no() {
		return product_no;
	}

	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}

	public int getFactory_out_item_qty() {
		return factory_out_item_qty;
	}

	public void setFactory_out_item_qty(int factory_out_item_qty) {
		this.factory_out_item_qty = factory_out_item_qty;
	}

	public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	public Date getFactory_out_item_date() {
		return factory_out_item_date;
	}

	public void setFactory_out_item_date(Date factory_out_item_date) {
		this.factory_out_item_date = factory_out_item_date;
	}

	public int getBranch_no() {
		return branch_no;
	}

	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}

}