package com.bt.vo;

import java.util.Date;

public class FreeBoardVo {
	
	  private int freeboard_no;
	  private String freeboard_title;
	  private int emp_code;
	  private Date freeboard_writedate;
	  private String freeboard_content;
	  private int freeboard_file_no;
	  private int freeboard_view;
	  private String freeboard_writer;
	  
	  public FreeBoardVo() {
		// TODO Auto-generated constructor stub
	  }

	public FreeBoardVo(int freeboard_no, String freeboard_title, int emp_code, Date freeboard_writedate,
			String freeboard_content, int freeboard_file_no, int freeboard_view, String freeboard_writer) {
		super();
		this.freeboard_no = freeboard_no;
		this.freeboard_title = freeboard_title;
		this.emp_code = emp_code;
		this.freeboard_writedate = freeboard_writedate;
		this.freeboard_content = freeboard_content;
		this.freeboard_file_no = freeboard_file_no;
		this.freeboard_view = freeboard_view;
		this.freeboard_writer = freeboard_writer;
	}

	public int getFreeboard_no() {
		return freeboard_no;
	}

	public void setFreeboard_no(int freeboard_no) {
		this.freeboard_no = freeboard_no;
	}

	public String getFreeboard_title() {
		return freeboard_title;
	}

	public void setFreeboard_title(String freeboard_title) {
		this.freeboard_title = freeboard_title;
	}

	public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	public Date getFreeboard_writedate() {
		return freeboard_writedate;
	}

	public void setFreeboard_writedate(Date freeboard_writedate) {
		this.freeboard_writedate = freeboard_writedate;
	}

	public String getFreeboard_content() {
		return freeboard_content;
	}

	public void setFreeboard_content(String freeboard_content) {
		this.freeboard_content = freeboard_content;
	}

	public int getFreeboard_file_no() {
		return freeboard_file_no;
	}

	public void setFreeboard_file_no(int freeboard_file_no) {
		this.freeboard_file_no = freeboard_file_no;
	}

	public int getFreeboard_view() {
		return freeboard_view;
	}

	public void setFreeboard_view(int freeboard_view) {
		this.freeboard_view = freeboard_view;
	}

	public String getFreeboard_writer() {
		return freeboard_writer;
	}

	public void setFreeboard_writer(String freeboard_writer) {
		this.freeboard_writer = freeboard_writer;
	}
	  
	  
	  
	  
	
	
}
