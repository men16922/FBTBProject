package com.bt.vo;

import java.util.Date;

public class DepartmentboardreplyVo {
	private int dept_board_reply_no;
	private int dept_board_no;
	private String dept_board_reply_content;
	private int emp_code;
	private Date dept_board_reply_date;
	public DepartmentboardreplyVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DepartmentboardreplyVo(int dept_board_reply_no, int dept_board_no, String dept_board_reply_content,
			int emp_code, Date dept_board_reply_date) {
		super();
		this.dept_board_reply_no = dept_board_reply_no;
		this.dept_board_no = dept_board_no;
		this.dept_board_reply_content = dept_board_reply_content;
		this.emp_code = emp_code;
		this.dept_board_reply_date = dept_board_reply_date;
	}
	public int getDept_board_reply_no() {
		return dept_board_reply_no;
	}
	public void setDept_board_reply_no(int dept_board_reply_no) {
		this.dept_board_reply_no = dept_board_reply_no;
	}
	public int getDept_board_no() {
		return dept_board_no;
	}
	public void setDept_board_no(int dept_board_no) {
		this.dept_board_no = dept_board_no;
	}
	public String getDept_board_reply_content() {
		return dept_board_reply_content;
	}
	public void setDept_board_reply_content(String dept_board_reply_content) {
		this.dept_board_reply_content = dept_board_reply_content;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	public Date getDept_board_reply_date() {
		return dept_board_reply_date;
	}
	public void setDept_board_reply_date(Date dept_board_reply_date) {
		this.dept_board_reply_date = dept_board_reply_date;
	}
	
	
}
