package com.bt.vo;

import java.util.Date;

public class HomepageNoticeVo {
   
     private int hp_notice_no;
     private int hp_notice_type_no;
     private String hp_notice_title;
     private int emp_code;
     private Date hp_notice_writedate;
     private String hp_notice_content;
     
     
      public HomepageNoticeVo() {
         super();
      }
      public HomepageNoticeVo(int hp_notice_no, int hp_notice_type_no, String hp_notice_title, int emp_code,
            Date hp_notice_writedate, String hp_notice_content) {
         super();
         this.hp_notice_no = hp_notice_no;
         this.hp_notice_type_no = hp_notice_type_no;
         this.hp_notice_title = hp_notice_title;
         this.emp_code = emp_code;
         this.hp_notice_writedate = hp_notice_writedate;
         this.hp_notice_content = hp_notice_content;
      }
      public int getHp_notice_no() {
         return hp_notice_no;
      }
      public void setHp_notice_no(int hp_notice_no) {
         this.hp_notice_no = hp_notice_no;
      }
      public int getHp_notice_type_no() {
         return hp_notice_type_no;
      }
      public void setHp_notice_type_no(int hp_notice_type_no) {
         this.hp_notice_type_no = hp_notice_type_no;
      }
      public String getHp_notice_title() {
         return hp_notice_title;
      }
      public void setHp_notice_title(String hp_notice_title) {
         this.hp_notice_title = hp_notice_title;
      }
      public int getEmp_code() {
         return emp_code;
      }
      public void setEmp_code(int emp_code) {
         this.emp_code = emp_code;
      }
      public Date getHp_notice_writedate() {
         return hp_notice_writedate;
      }
      public void setHp_notice_writedate(Date hp_notice_writedate) {
         this.hp_notice_writedate = hp_notice_writedate;
      }
      public String getHp_notice_content() {
         return hp_notice_content;
      }
      public void setHp_notice_content(String hp_notice_content) {
         this.hp_notice_content = hp_notice_content;
      }
      
     
}