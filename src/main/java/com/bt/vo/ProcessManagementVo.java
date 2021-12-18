package com.bt.vo;

public class ProcessManagementVo {
	

	private int process_manage_no;
	private int store_order_detail_no;
	private char check_reject;
	private int branch_no;
	private int confirm_qty;
	private int first_emp_code;
	private int end_emp_code;
	
	public ProcessManagementVo() {
		// TODO Auto-generated constructor stub
	}

	public ProcessManagementVo(int process_manage_no, int store_order_detail_no, char check_reject, int branch_no,
			int confirm_qty, int first_emp_code, int end_emp_code) {
		super();
		this.process_manage_no = process_manage_no;
		this.store_order_detail_no = store_order_detail_no;
		this.check_reject = check_reject;
		this.branch_no = branch_no;
		this.confirm_qty = confirm_qty;
		this.first_emp_code = first_emp_code;
		this.end_emp_code = end_emp_code;
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

	public int getFirst_emp_code() {
		return first_emp_code;
	}

	public void setFirst_emp_code(int first_emp_code) {
		this.first_emp_code = first_emp_code;
	}

	public int getEnd_emp_code() {
		return end_emp_code;
	}

	public void setEnd_emp_code(int end_emp_code) {
		this.end_emp_code = end_emp_code;
	}
	
	
	
	
}
