package com.bt.vo;

public class BranchVo {

	private int branch_no;
	private String branch_name;
	private String branch_address;
	private String branch_phone;
	private int branch_type_no;
	private int emp_code;
	
	public BranchVo() {
		// TODO Auto-generated constructor stub
	}

	public BranchVo(int branch_no, String branch_name, String branch_address, String branch_phone, int branch_type_no,
			int emp_code) {
		super();
		this.branch_no = branch_no;
		this.branch_name = branch_name;
		this.branch_address = branch_address;
		this.branch_phone = branch_phone;
		this.branch_type_no = branch_type_no;
		this.emp_code = emp_code;
	}

	public int getBranch_no() {
		return branch_no;
	}

	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getBranch_address() {
		return branch_address;
	}

	public void setBranch_address(String branch_address) {
		this.branch_address = branch_address;
	}

	public String getBranch_phone() {
		return branch_phone;
	}

	public void setBranch_phone(String branch_phone) {
		this.branch_phone = branch_phone;
	}

	public int getBranch_type_no() {
		return branch_type_no;
	}

	public void setBranch_type_no(int branch_type_no) {
		this.branch_type_no = branch_type_no;
	}

	public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	
	
}
