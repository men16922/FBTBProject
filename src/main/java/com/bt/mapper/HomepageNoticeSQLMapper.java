package com.bt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.vo.HomepageNoticeVo;

public interface HomepageNoticeSQLMapper {

   public List<HomepageNoticeVo> getHomepageNotice(int hp_notice_type_no);

   public void homepageNotice_modify_process( @Param("hp_notice_type_no")int hp_notice_type_no, 
                                    @Param("hp_notice_title")String hp_notice_title, 
                                    @Param("hp_notice_content")String hp_notice_content);

}