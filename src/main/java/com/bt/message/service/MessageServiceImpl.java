package com.bt.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.mapper.BranchSQLMapper;
import com.bt.mapper.EmployeeSQLMapper;
import com.bt.mapper.MessageSQLMapper;
import com.bt.vo.BranchVo;
import com.bt.vo.DeptVo;
import com.bt.vo.EmployeeRankVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.MessageVo;

@Service
public class MessageServiceImpl {

   @Autowired
   private MessageSQLMapper messageSQLMapper;

   @Autowired
   private EmployeeSQLMapper employeeSQLMapper;

   @Autowired
   private BranchSQLMapper branchSQLMapper;

   public List<Map<String, Object>> getListAll(int receiver, String branch_name, String emp_name, String msg_title,
         int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      List<MessageVo> dataList = null;

      // 검색한 값이 없을 때 리스트
      if (branch_name == null && emp_name == null && msg_title == null) {

         dataList = messageSQLMapper.selectByReceiverNo(receiver, currPage);

      } else {// 검색한 값이 없을 때 리스트

         dataList = messageSQLMapper.selectSearchByReceiverNo(receiver, msg_title, emp_name, branch_name, currPage);

      }

      for (MessageVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         EmployeeVo employeeVoRev = employeeSQLMapper.selectByEmpCode(data.getReceiver());
         EmployeeVo employeeVoSend = employeeSQLMapper.selectByEmpCode(data.getSender());

         int branch_no = employeeVoSend.getBranch_no();
         BranchVo branchVoSend = employeeSQLMapper.selectByBranchNo(branch_no);

         System.out.println("보낸사람이름: " + employeeVoSend.getEmp_name());

         map.put("messageVo", data);
         map.put("employeeVoRev", employeeVoRev);
         map.put("employeeVoSend", employeeVoSend);
         map.put("branchVoSend", branchVoSend);

         list.add(map);
      }

      return list;

   }

   // 보낸 메시지 리스트
   public List<Map<String, Object>> getSendedListAll(int sender, String branch_name, String emp_name, String msg_title,
         int currPage) {

      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
      List<MessageVo> dataList = null;

      // 검색한 값이 없을 때 리스트
      if (branch_name == null && emp_name == null && msg_title == null) {

         dataList = messageSQLMapper.selectBySenderNo(sender, currPage);

      } else {// 검색한 값이 없을 때 리스트

         dataList = messageSQLMapper.selectSearchBySenderNo(sender, msg_title, emp_name, branch_name, currPage);
      }

      for (MessageVo data : dataList) {

         Map<String, Object> map = new HashMap<String, Object>();

         EmployeeVo employeeVoRev = employeeSQLMapper.selectByEmpCode(data.getReceiver());
         EmployeeVo employeeVoSend = employeeSQLMapper.selectByEmpCode(data.getSender());

         int sender_branch_no = employeeVoSend.getBranch_no();
         BranchVo branchVoSend = employeeSQLMapper.selectByBranchNo(sender_branch_no);
         
         int receiver_branch_no = employeeVoRev.getBranch_no();
         BranchVo branchVoRev = employeeSQLMapper.selectByBranchNo(receiver_branch_no);

         System.out.println("보낸사람이름: " + employeeVoSend.getEmp_name());

         map.put("messageVo", data);
         map.put("employeeVoRev", employeeVoRev);
         map.put("employeeVoSend", employeeVoSend);
         map.put("branchVoSend", branchVoSend);
         map.put("branchVoRev", branchVoRev);

         list.add(map);
      }

      return list;

   }

   // 보낸 메시지 갯수
   public int countSendedMessageList(int sender, String msg_title, String emp_name, String branch_name) {

      int cnt = messageSQLMapper.selectCntSendedMessage(sender, msg_title, emp_name, branch_name);

      return cnt;
   }
   
   
   //받은 메시지 갯수
   public int countMessageList(int receiver, String msg_title, String emp_name, String branch_name) {

      int cnt = messageSQLMapper.selectCntMessage(receiver, msg_title, emp_name, branch_name);

      return cnt;
   }

   public Map<String, Object> getContentByMsgNo(int msg_no) {

      Map<String, Object> map = new HashMap<String, Object>();

      messageSQLMapper.updateCheck(msg_no);

      MessageVo messageVo = messageSQLMapper.selectByMsgNo(msg_no);
      EmployeeVo employeeVoSend = employeeSQLMapper.selectByEmpCode(messageVo.getSender());
      int branch_no = employeeVoSend.getBranch_no();
      BranchVo branchVo = employeeSQLMapper.selectByBranchNo(branch_no);

      int rank_no = employeeVoSend.getRank_no();
      EmployeeRankVo employeeRankVo = employeeSQLMapper.selectByEmpRankNo(rank_no);

      map.put("branchVo", branchVo);
      map.put("employeeRankVo", employeeRankVo);
      map.put("messageVo", messageVo);
      map.put("employeeVoSend", employeeVoSend);

      return map;

   }

   public MessageVo getMsgByMsgNo(int msg_no) {

      MessageVo messageVo = messageSQLMapper.selectByMsgNo(msg_no);

      return messageVo;

   }

   public void deleteContentByMsgNo(int msg_no) {

      messageSQLMapper.deleteContent(msg_no);

   }

   public List<DeptVo> getListDept() {

      List<DeptVo> deptList = employeeSQLMapper.selectDeptAll();

      return deptList;

   }

   public List<EmployeeVo> getEmpByDeptNo(int dept_no) {

      List<EmployeeVo> empList = employeeSQLMapper.selectByEmpByDeptNo(dept_no);

      return empList;

   }

   public List<BranchVo> getBranchByBranchType(int branch_type_no) {

      List<BranchVo> branchList = branchSQLMapper.selectBranchByType(branch_type_no);

      return branchList;

   }

   public List<EmployeeVo> getEmpByBranchNo(int branch_no) {

      List<EmployeeVo> branchList = employeeSQLMapper.selectEmpByBranchNo(branch_no);

      return branchList;

   }

   public void insertMessage(int sender, int receiver, String msg_title, String msg_content) {

      int msg_no = messageSQLMapper.createKeyMessage();
      System.out.println("메시지 넘버: " + msg_no);

      messageSQLMapper.insertContent(msg_no, sender, receiver, msg_content, msg_title);

   }
   
   public int getCountMessageCheckN(int receiver) {
      int count = messageSQLMapper.selectCntCheckN(receiver);
      return count;
   }

}