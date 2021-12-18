package com.bt.vo;

import java.util.Date;

public class DepartmentFileVo {
	
	private int dept_file_no;
	private int dept_board_no;
	private String dept_file_link_path;
	private String dept_file_real_path;
	private Date dept_file_upload_date;
	
	public DepartmentFileVo() {
		super();
	}

	public DepartmentFileVo(int dept_file_no, int dept_board_no, String dept_file_link_path, String dept_file_real_path,
			Date dept_file_upload_date) {
		super();
		this.dept_file_no = dept_file_no;
		this.dept_board_no = dept_board_no;
		this.dept_file_link_path = dept_file_link_path;
		this.dept_file_real_path = dept_file_real_path;
		this.dept_file_upload_date = dept_file_upload_date;
	}

	public int getDept_file_no() {
		return dept_file_no;
	}

	public void setDept_file_no(int dept_file_no) {
		this.dept_file_no = dept_file_no;
	}

	public int getDept_board_no() {
		return dept_board_no;
	}

	public void setDept_board_no(int dept_board_no) {
		this.dept_board_no = dept_board_no;
	}

	public String getDept_file_link_path() {
		return dept_file_link_path;
	}

	public void setDept_file_link_path(String dept_file_link_path) {
		this.dept_file_link_path = dept_file_link_path;
	}

	public String getDept_file_real_path() {
		return dept_file_real_path;
	}

	public void setDept_file_real_path(String dept_file_real_path) {
		this.dept_file_real_path = dept_file_real_path;
	}

	public Date getDept_file_upload_date() {
		return dept_file_upload_date;
	}

	public void setDept_file_upload_date(Date dept_file_upload_date) {
		this.dept_file_upload_date = dept_file_upload_date;
	}
	
	
}
