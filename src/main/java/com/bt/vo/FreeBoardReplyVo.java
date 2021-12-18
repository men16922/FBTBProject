package com.bt.vo;

import java.util.Date;

public class FreeBoardReplyVo {

	 private int freeboard_reply_no;
	 private int freeboard_no;
	 private String freeboard_reply_content;
	 private int emp_code;
	 private Date freeboard_reply_date;
	 
	 public FreeBoardReplyVo() {
		// TODO Auto-generated constructor stub
	 }

	public FreeBoardReplyVo(int freeboard_reply_no, int freeboard_no, String freeboard_reply_content, int emp_code,
			Date freeboard_reply_date) {
		super();
		this.freeboard_reply_no = freeboard_reply_no;
		this.freeboard_no = freeboard_no;
		this.freeboard_reply_content = freeboard_reply_content;
		this.emp_code = emp_code;
		this.freeboard_reply_date = freeboard_reply_date;
	}

	public int getFreeboard_reply_no() {
		return freeboard_reply_no;
	}

	public void setFreeboard_reply_no(int freeboard_reply_no) {
		this.freeboard_reply_no = freeboard_reply_no;
	}

	public int getFreeboard_no() {
		return freeboard_no;
	}

	public void setFreeboard_no(int freeboard_no) {
		this.freeboard_no = freeboard_no;
	}

	public String getFreeboard_reply_content() {
		return freeboard_reply_content;
	}

	public void setFreeboard_reply_content(String freeboard_reply_content) {
		this.freeboard_reply_content = freeboard_reply_content;
	}

	public int getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}

	public Date getFreeboard_reply_date() {
		return freeboard_reply_date;
	}

	public void setFreeboard_reply_date(Date freeboard_reply_date) {
		this.freeboard_reply_date = freeboard_reply_date;
	}
	 
	 
	
}
