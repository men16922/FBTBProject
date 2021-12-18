package com.bt.logistics.vo;

public class Process_managementVo {
	private int process_manage_no;
	private int store_order_detail_no;
	private char check_reject;
	private int branch_no;
	private int confirm_qty;
	private int emp_code;
	public Process_managementVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Process_managementVo(int process_manage_no, int store_order_detail_no, char check_reject, int branch_no,
			int confirm_qty, int emp_code) {
		super();
		this.process_manage_no = process_manage_no;
		this.store_order_detail_no = store_order_detail_no;
		this.check_reject = check_reject;
		this.branch_no = branch_no;
		this.confirm_qty = confirm_qty;
		this.emp_code = emp_code;
	}
	public int getProcess_manage_no() {
		return process_manage_no;
	}
	public void setProcess_manage_no(int process_manage_no) {
		this.process_manage_no = process_manage_no;
	}
	public int getStore_order_detail_no() {
		return store_order_detail_no;
	}
	public void setStore_order_detail_no(int store_order_detail_no) {
		this.store_order_detail_no = store_order_detail_no;
	}
	public char getCheck_reject() {
		return check_reject;
	}
	public void setCheck_reject(char check_reject) {
		this.check_reject = check_reject;
	}
	public int getBranch_no() {
		return branch_no;
	}
	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}
	public int getConfirm_qty() {
		return confirm_qty;
	}
	public void setConfirm_qty(int confirm_qty) {
		this.confirm_qty = confirm_qty;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	
	
}
