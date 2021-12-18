package com.bt.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bt.vo.BranchVo;
import com.bt.vo.DeptTypeVo;
import com.bt.vo.EmployeeVo;

/**
 * Servlet Filter implementation class SessionCheck
 */
public class SessionCheck implements Filter {

    /**
     * Default constructor. 
     */
    public SessionCheck() {
        // TODO Auto-generated constructor stub
   
    }

   /**
    * @see Filter#destroy()
    */
   public void destroy() {
      // TODO Auto-generated method stub
   }

   /**
    * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
    */
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
   
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse res = (HttpServletResponse) response;
      
      boolean isRedirect = false;
      boolean isCheck = false;
      String requestURI = req.getRequestURI();
      
      String contextPath = req.getContextPath();
      
      System.out.println(contextPath);
      // 필터를 거치지않는 페이지들
      if(!requestURI.equals(contextPath + "/employee/login_page.do") && !requestURI.equals(contextPath + "/employee/confirmIdPw.do")
            && !requestURI.equals(contextPath + "/employee/login_process.do") && !requestURI.equals(contextPath + "/employee/logout_process.do")
            && !requestURI.equals(contextPath + "/employee/find_password.do") && !requestURI.equals(contextPath + "/employee/employee_find_mailing_process.do")
            && !requestURI.equals(contextPath+"/") && !requestURI.equals(contextPath+"/employee/update_password_process.do"))      {
         
         // 세션정보 가져오기
         Map<String, Object> map = (Map<String, Object>) req.getSession().getAttribute("sessionUser");
   
         EmployeeVo employeeVo = null;
         BranchVo branchVo = null;
         
         // 세션이 비어있는지 확인하기
         if(map == null) {
            isRedirect = true;
         } else {
            // 로그인되면 employeeVo에 세션 달아주기
            employeeVo = (EmployeeVo) map.get("employeeVo");
            branchVo = (BranchVo) map.get("branchVo");
            // 퇴사자인 경우
            if(employeeVo.getCheck_resignation() == 'Y') {
               isRedirect = true;
               isCheck = true;
            }
            // branch에서 emp_code 가 0 인경우 -> 지점을 할당받지 못한 사람
            if(branchVo.getEmp_code() == 0) {
               isRedirect = true;
               isCheck = true;
            }
         }
      }
      
      if(isRedirect == true && isCheck == true) {
         // 접근 권한 페이지로 이동하기
         req.getRequestDispatcher("/employee/no_access_page.do").forward(req, res);
      } else if(isRedirect == true){
         req.getRequestDispatcher("/employee/fail_page.do").forward(req, res);
      } else {
         chain.doFilter(req, res);
      }

   }

   /**
    * @see Filter#init(FilterConfig)
    */
   public void init(FilterConfig fConfig) throws ServletException {
      // TODO Auto-generated method stub
   }

}