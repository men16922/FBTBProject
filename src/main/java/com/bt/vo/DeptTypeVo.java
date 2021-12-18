package com.bt.vo;

public class DeptTypeVo {
	
	private int dept_type_no;
	private String dept_type_name;
	
	public DeptTypeVo() {
		// TODO Auto-generated constructor stub
	}

	public DeptTypeVo(int dept_type_no, String dept_type_name) {
		super();
		this.dept_type_no = dept_type_no;
		this.dept_type_name = dept_type_name;
	}

	public int getDept_type_no() {
		return dept_type_no;
	}

	public void setDept_type_no(int dept_type_no) {
		this.dept_type_no = dept_type_no;
	}

	public String getDept_type_name() {
		return dept_type_name;
	}

	public void setDept_type_name(String dept_type_name) {
		this.dept_type_name = dept_type_name;
	}
	
	
	
}
