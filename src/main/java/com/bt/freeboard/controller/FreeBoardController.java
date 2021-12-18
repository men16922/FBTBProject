package com.bt.freeboard.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bt.freeboard.service.FreeBoardServiceImpl;
import com.bt.vo.DepartmentFileVo;
import com.bt.vo.EmployeeVo;
import com.bt.vo.FreeBoardFileVo;
import com.bt.vo.FreeBoardReplyVo;
import com.bt.vo.FreeBoardVo;

@Controller
@RequestMapping("/freeboard/*")
public class FreeBoardController {

   @Autowired
   private FreeBoardServiceImpl freeBoardServiceImpl;

   // 글 전체조회
   @RequestMapping("/list_freeboard_page.do")
   public String readListfreeboard(HttpSession session, Model model,
         @RequestParam(value = "search_type", defaultValue = "0", required = false) int search_type,
         @Param("search_word") String search_word,
         @RequestParam(value = "currPage", defaultValue = "0", required = false) int currPage) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

      if (currPage == 0) {
         currPage = 1;
      }

      String freeboard_title = "";
      String freeboard_content = "";
      String freeboard_writer = "";
      String branch_name = "";

      if (search_type == 0 && search_word != null) {
         freeboard_title = null;
         freeboard_content = null;
         freeboard_writer = null;
         branch_name = null;

      }

      if (search_type == 1) {
         freeboard_title = search_word;
      }

      if (search_type == 2) {
         freeboard_content = search_word;
      }

      if (search_type == 3) {
         freeboard_writer = search_word;
      }

      if (search_type == 4) {
         branch_name = search_word;
      }

      if (sessionData == null) {

         return "redirect:/employee/login_page.do";

      } else {

         List<Map<String, Object>> dataList = freeBoardServiceImpl.getListAll(freeboard_title, freeboard_content,
               freeboard_writer, branch_name, currPage);

         int totalCount = freeBoardServiceImpl.countFreeboardList(freeboard_title, freeboard_content,
               freeboard_writer, branch_name);

         // 전체 페이지수
         int totalPage = totalCount / 10;

         if (totalCount % 10 > 0) {
            totalPage++;
         }

         if (totalPage < currPage) {
            currPage = totalPage;
         }

         int beginPage = ((currPage - 1) / 5) * 5 + 1;
         int endPage = ((currPage - 1) / 5 + 1) * (5);
         System.out.println("엔드페이지" + endPage);

         if (endPage > (totalCount - 1) / 10 + 1) {
            endPage = ((totalCount - 1) / 10 + 1);
         }

         model.addAttribute("currPage", currPage);
         model.addAttribute("beginPage", beginPage);
         model.addAttribute("endPage", endPage);
         model.addAttribute("totalCount", totalCount);
         model.addAttribute("dataList", dataList);

         return "/freeboard/list_freeboard_page";
      }

   }

   // 글 상세보기
   @RequestMapping("/read_content_freeboard_page.do")
   public String readContentFreeboard(int freeboard_no, Model model, HttpSession session, HttpServletRequest request,
         HttpServletResponse response) {
      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");
      int emp_code = employeeVo.getEmp_code();

      if (sessionData == null) {

         return "redirect:/employee/login_page.do";

      } else {
         // ++++++++++++++++++++++++조휘수 쿠키처리++++++++++++++++++++++++++++++
         Cookie[] cookies = request.getCookies();
         // 비교하기 위해 새로운 쿠키
         Cookie viewCookie = null;
         // 쿠키가 있을 경우
         if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
               if (cookies[i].getName().equals("cookie" + freeboard_no)) {
                  System.out.println("처음 쿠키가 생성한 뒤 들어옴.");
                  viewCookie = cookies[i];
               }
            }
         }
         if (viewCookie == null) {
            System.out.println("cookie 없음");

            // 쿠키 생성(이름, 값)
            Cookie newCookie = new Cookie("cookie" + freeboard_no, "|" + freeboard_no + "|");

            // 쿠키 추가
            response.addCookie(newCookie);

            // 쿠키를 추가 시키고 조회수 증가시킴
            freeBoardServiceImpl.updateViewCount(freeboard_no, emp_code);
         }
         // viewCookie가 null이 아닐경우 쿠키가 있으므로 조회수 증가 로직을 처리하지 않음.
         else {
            System.out.println("cookie 있음");

            // 쿠키 값 받아옴.
            String value = viewCookie.getValue();

            System.out.println("cookie 값 : " + value);

         }
         // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         Map<String, Object> freeContentMap = freeBoardServiceImpl.getContentByBoardNo(freeboard_no);

         model.addAttribute("freeBoardData", freeContentMap);

         return "/freeboard/read_content_freeboard_page";

      }

   }

   @RequestMapping("/write_content_freeboard_page.do")
   public String writeContentPage(HttpSession session) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

      if (sessionData == null) {

         return "redirect:/employee/login_page.do";

      } else {
         return "/freeboard/write_content_freeboard_page";
      }

   }

   // 글쓰기!!!
   @RequestMapping("/write_content_freeboard_process.do")
   public String writeContentProcess(HttpSession session, FreeBoardVo freeBoardvo, MultipartFile[] freeboard_files) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

      if (sessionData == null) {

         return "redirect:/employee/login_page.do";

      } else {

         String uploadRootFolderName = "C:/upload/";

         Date today = new Date();
         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
         String todayFolder = df.format(today);

         String saveFolderName = uploadRootFolderName + todayFolder;
         File saveFolder = new File(saveFolderName);

         // 폴더가 존재하는지 확인하는 부분
         if (!saveFolder.exists()) {
            saveFolder.mkdirs();
         }

         List<FreeBoardFileVo> fileVoList = new ArrayList<FreeBoardFileVo>();
         if (freeboard_files == null)

            System.out.println("비었음!!!");

         // 파일 업로드 부분
         if (freeboard_files != null) {
            for (MultipartFile file : freeboard_files) {

               if (file.getSize() <= 0) {
                  continue;
               }

               String oriFileName = file.getOriginalFilename();

               String saveFileName = UUID.randomUUID().toString();
               saveFileName += "_" + System.currentTimeMillis();
               saveFileName += oriFileName.substring(oriFileName.lastIndexOf("."));

               String saveRealPath = saveFolderName + "/" + saveFileName;
               System.out.println(saveRealPath);
               try {
                  file.transferTo(new File(saveRealPath));
                  System.out.println("test getSize: " + file.getSize());
                  System.out.println("test getOriginalFilename: " + file.getOriginalFilename());
               } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

               // DB를 위한 FileVo 객체 생성
               FreeBoardFileVo fileVo = new FreeBoardFileVo();

               fileVo.setFreeboard_file_link_path(todayFolder + "/" + saveFileName);
               fileVo.setFreeboard_file_real_path(saveRealPath);
               fileVoList.add(fileVo);
            }
         }

         freeBoardServiceImpl.insertContentFreeboard(freeBoardvo, fileVoList);

         return "redirect:/freeboard/list_freeboard_page.do";

      }

   }

   // 글 수정 페이지
   @RequestMapping("/update_content_freeboard_page.do")
   public String updateContentPage(int freeboard_no, Model model, HttpSession session) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      Map<String, Object> freeBoardMap = freeBoardServiceImpl.getContentByBoardNo(freeboard_no);
      FreeBoardVo freeBoardVo = (FreeBoardVo) freeBoardMap.get("freeBoardVo");

      if (freeBoardVo.getEmp_code() == employeeVo.getEmp_code()) {
         // 정상적으로 로그인한 사원과 게시글 쓴 사원이 같을 때 작동
         model.addAttribute("freeBoardData",
               freeBoardServiceImpl.updateContentRead(freeboard_no, employeeVo.getEmp_code()));

         return "/freeboard/update_content_freeboard_page";

      } else {
         return "commons/incorrect_access_page";
      }

   }

   // 글 수정 프로세스
   @RequestMapping("/update_content_freeboard_process.do")
   public String updateContentProcess(FreeBoardVo freeBoardvo, Model model, HttpSession session,
         MultipartFile[] board_upload_files) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");

      if (sessionData == null) {

         return "redirect:/employee/login_page.do";

      } else {

         System.out.println("수정 게시글 번호 : " + freeBoardvo.getFreeboard_no());

         String uploadRootFolderName = "C:/upload/";

         Date today = new Date();
         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
         String todayFolder = df.format(today);

         String saveFolderName = uploadRootFolderName + todayFolder;
         File saveFolder = new File(saveFolderName);

         // 폴더가 존재하는지 확인하는 부분
         if (!saveFolder.exists()) {
            saveFolder.mkdirs();
         }

         List<FreeBoardFileVo> fileVoList = new ArrayList<FreeBoardFileVo>();
         if (board_upload_files == null)
            System.out.println("비었음");
         // 파일 업로드 부분
         if (board_upload_files != null) {
            for (MultipartFile file : board_upload_files) {

               if (file.getSize() <= 0) {
                  continue;
               }

               String oriFileName = file.getOriginalFilename();

               String saveFileName = UUID.randomUUID().toString();
               saveFileName += "_" + System.currentTimeMillis();
               saveFileName += oriFileName.substring(oriFileName.lastIndexOf("."));

               String saveRealPath = saveFolderName + "/" + saveFileName;
               System.out.println(saveRealPath);
               try {
                  file.transferTo(new File(saveRealPath));
                  System.out.println("test getSize: " + file.getSize());
                  System.out.println("test getOriginalFilename: " + file.getOriginalFilename());
               } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

               // DB를 위한 FileVo 객체 생성
               FreeBoardFileVo fileVo = new FreeBoardFileVo();

               fileVo.setFreeboard_file_link_path(todayFolder + "/" + saveFileName);
               fileVo.setFreeboard_file_real_path(saveRealPath);

               System.out.println(fileVo.getFreeboard_file_link_path());
               fileVoList.add(fileVo);
            }
         }

         freeBoardServiceImpl.updateContentByBoardNo(freeBoardvo);
         freeBoardServiceImpl.updatefile(freeBoardvo, fileVoList);

         int freeboard_no = freeBoardvo.getFreeboard_no();
         return "redirect:/freeboard/read_content_freeboard_page.do?freeboard_no=" + freeboard_no;
      }

   }

   // 글 삭제 프로세스
   @RequestMapping("/delete_content_freeboard_process.do")
   public String deleteContentProcess(int freeboard_no, HttpSession session) {

      Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionUser");
      EmployeeVo employeeVo = (EmployeeVo) sessionData.get("employeeVo");

      freeBoardServiceImpl.deleteContentByBoardNo(freeboard_no, employeeVo.getEmp_code());

      return "redirect:/freeboard/list_freeboard_page.do";

   }

    @RequestMapping("/get_reply_list.do")
      @ResponseBody
      public List<Map<String, Object>> getReplyList(int freeboard_no) {

         List<Map<String, Object>> list = freeBoardServiceImpl.getReplyList(freeboard_no);

         return list;
      }
      
      @RequestMapping("/write_reply_process.do")
      @ResponseBody
      public String writeReply(FreeBoardReplyVo freeBoardReplyVo, HttpSession session) {

         Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
       
         EmployeeVo employeeVo = null;

         employeeVo = (EmployeeVo) dataMap.get("employeeVo");

         // System.out.println("컨"+employeeVo.getEmp_code());
         freeBoardReplyVo.setEmp_code(employeeVo.getEmp_code());

         freeBoardServiceImpl.writeReply(freeBoardReplyVo); // board_no와 글 내용

         return "true";
      }

      @RequestMapping("/delete_reply_process.do")
      public String deleteReply(int freeboard_reply_no, int freeboard_no, HttpSession session) {
         
          Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
          EmployeeVo employeeVo = (EmployeeVo) dataMap.get("employeeVo");
          
          int emp_code=freeBoardServiceImpl.getempcodebyreplyno(freeboard_reply_no);
           
         if(employeeVo.getEmp_code()==emp_code)    { 
         
             freeBoardServiceImpl.deleteReply(freeboard_reply_no); // board_no와 글 내용
         }
         else {
             return "commons/incorrect_access_page";
         }
         return "redirect:/freeboard/read_content_freeboard_page.do?freeboard_no=" + freeboard_no;
      }
      

      @RequestMapping("/update_reply_process.do")
      public String updatereplyprocess(
            @RequestParam(value = "freeboard_reply_no", required = false, defaultValue = "0") int freeboard_reply_no,
            @Param("freeboard_reply_content") String freeboard_reply_content,
              @RequestParam(value = "freeboard_no", required = false, defaultValue = "0") int freeboard_no, HttpSession session) {
         
         EmployeeVo employeeVo = new EmployeeVo();
            Map<String, Object> dataMap = (Map<String, Object>) session.getAttribute("sessionUser");
            /*
             * if(!check(dataMap)) { return "commons/fail_page"; }
             */
            employeeVo = (EmployeeVo) dataMap.get("employeeVo");
        
        int emp_code=freeBoardServiceImpl.getempcodebyreplyno(freeboard_reply_no);
         System.out.println("xxx"+emp_code);
        if(employeeVo.getEmp_code()==emp_code)    { 
            
           freeBoardServiceImpl.updatereply(freeboard_reply_no, freeboard_reply_content);
            }
            else {
                return "commons/incorrect_access_page";
            }
        
         

         return "redirect:./read_content_freeboard_page.do?freeboard_no="+freeboard_no;
      }
}