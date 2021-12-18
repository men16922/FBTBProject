package com.bt.vo;

public class AreaVo {

	private int area_no;
	private int dept_no;
	private int branch_no;
	
	public AreaVo() {
		// TODO Auto-generated constructor stub
	}

	public AreaVo(int area_no, int dept_no, int branch_no) {
		super();
		this.area_no = area_no;
		this.dept_no = dept_no;
		this.branch_no = branch_no;
	}

	public int getArea_no() {
		return area_no;
	}

	public void setArea_no(int area_no) {
		this.area_no = area_no;
	}

	public int getDept_no() {
		return dept_no;
	}

	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}

	public int getBranch_no() {
		return branch_no;
	}

	public void setBranch_no(int branch_no) {
		this.branch_no = branch_no;
	}
	
	
}
