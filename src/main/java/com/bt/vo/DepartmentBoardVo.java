package com.bt.vo;

import java.util.Date;

public class DepartmentBoardVo {
	
	private int dept_board_no;
	private String dept_board_title;
	private int emp_code;
	private Date dept_board_writedate;
	private String dept_board_content;
	private int dept_file_no;
	private int dept_board_view;
	private int dept_type_no;
	private String dept_writer;
	private char check_notice;
	
	public DepartmentBoardVo() {
		super();
	}

	public DepartmentBoardVo(int dept_board_no, String dept_board_title, int emp_code, Date dept_board_writedate,
			String dept_board_content, int dept_file_no, int dept_board_view, int dept_type_no, String dept_writer,
			char check_notice) {
		super();
		this.dept_board_no = dept_board_no;
		this.dept_board_title = dept_board_title;
		this.emp_code = emp_code;
		this.dept_board_writedate = dept_board_writedate;
		this.dept_board_content = dept_board_content;
		this.dept_file_no = dept_file_no;
		this.dept_board_view = dept_board_view;
		this.dept_type_no = dept_type_no;
		this.dept_writer = dept_writer;
		this.check_notice = check_notice;
	}

	public int getDept_board_no() {
		return dept_board_no;
	}

	public void setDept_board_no(int dept_board_no) {
		this.dept_board_no = dept_board_no;
	}

	public String getDept_board_title() {
		return dept_board_title;
	}

	public void setDept_board_title(String dept_board_title) {
		this.dept_board_title = dept_board_title;
	}

	public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	public Date getDept_board_writedate() {
		return dept_board_writedate;
	}

	public void setDept_board_writedate(Date dept_board_writedate) {
		this.dept_board_writedate = dept_board_writedate;
	}

	public String getDept_board_content() {
		return dept_board_content;
	}

	public void setDept_board_content(String dept_board_content) {
		this.dept_board_content = dept_board_content;
	}

	public int getDept_file_no() {
		return dept_file_no;
	}

	public void setDept_file_no(int dept_file_no) {
		this.dept_file_no = dept_file_no;
	}

	public int getDept_board_view() {
		return dept_board_view;
	}

	public void setDept_board_view(int dept_board_view) {
		this.dept_board_view = dept_board_view;
	}

	public int getDept_type_no() {
		return dept_type_no;
	}

	public void setDept_type_no(int dept_type_no) {
		this.dept_type_no = dept_type_no;
	}

	public String getDept_writer() {
		return dept_writer;
	}

	public void setDept_writer(String dept_writer) {
		this.dept_writer = dept_writer;
	}

	public char getCheck_notice() {
		return check_notice;
	}

	public void setCheck_notice(char check_notice) {
		this.check_notice = check_notice;
	}

}
