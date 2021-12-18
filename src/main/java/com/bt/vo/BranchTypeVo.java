package com.bt.vo;

public class BranchTypeVo {

	private int branch_type_no;
	private String branch_type_name;
	public BranchTypeVo() {
		super();
	}
	public BranchTypeVo(int branch_type_no, String branch_type_name) {
		super();
		this.branch_type_no = branch_type_no;
		this.branch_type_name = branch_type_name;
	}
	public int getBranch_type_no() {
		return branch_type_no;
	}
	public void setBranch_type_no(int branch_type_no) {
		this.branch_type_no = branch_type_no;
	}
	public String getBranch_type_name() {
		return branch_type_name;
	}
	public void setBranch_type_name(String branch_type_name) {
		this.branch_type_name = branch_type_name;
	}

	
	
}
