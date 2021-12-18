package com.bt.mapper;

import java.util.List;

import com.bt.vo.FreeBoardFileVo;

public interface FreeBoardFileSQLMapper {

      public int createFreeBoardFileKey();
      
      public void insertFreeBoardFile(FreeBoardFileVo freeBoardFileVo);
      
      public List<FreeBoardFileVo> selectByFreeBoardNo(int freeboard_no);
   
      public void deletebyboardno(int freeboard_no);
}