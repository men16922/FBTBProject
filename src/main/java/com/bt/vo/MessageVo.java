package com.bt.vo;

import java.util.Date;

public class MessageVo {
	
	private int msg_no;
	private int	sender;
	private int receiver;
	private String msg_content;
	private String msg_title;
	private Date msg_writedate;
	private char check_read;
	
	public MessageVo() {
		// TODO Auto-generated constructor stub
	}

	public MessageVo(int msg_no, int sender, int receiver, String msg_content, String msg_title, Date msg_writedate,
			char check_read) {
		super();
		this.msg_no = msg_no;
		this.sender = sender;
		this.receiver = receiver;
		this.msg_content = msg_content;
		this.msg_title = msg_title;
		this.msg_writedate = msg_writedate;
		this.check_read = check_read;
	}

	public int getMsg_no() {
		return msg_no;
	}

	public void setMsg_no(int msg_no) {
		this.msg_no = msg_no;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public Date getMsg_writedate() {
		return msg_writedate;
	}

	public void setMsg_writedate(Date msg_writedate) {
		this.msg_writedate = msg_writedate;
	}

	public char getCheck_read() {
		return check_read;
	}

	public void setCheck_read(char check_read) {
		this.check_read = check_read;
	}
	
	
	
	
}
