package com.bt.vo;

public class DeptVo {
	
	private int dept_no;
	private String dept_name;
	private int dept_type_no;
	
	
	public DeptVo() {
		// TODO Auto-generated constructor stub
	}


	public DeptVo(int dept_no, String dept_name, int dept_type_no) {
		super();
		this.dept_no = dept_no;
		this.dept_name = dept_name;
		this.dept_type_no = dept_type_no;
	}


	public int getDept_no() {
		return dept_no;
	}


	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}


	public String getDept_name() {
		return dept_name;
	}


	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}


	public int getDept_type_no() {
		return dept_type_no;
	}


	public void setDept_type_no(int dept_type_no) {
		this.dept_type_no = dept_type_no;
	}
	
	
	
}
