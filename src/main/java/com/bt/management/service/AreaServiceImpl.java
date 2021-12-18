package com.bt.management.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.AreaSQLMapper;


@Service
public class AreaServiceImpl {
   
   @Autowired
   private AreaSQLMapper areaSQLMapper;
   
   public void addArea(int dept_no, String branch_name) {
      areaSQLMapper.addArea(dept_no, branch_name);
   }

}