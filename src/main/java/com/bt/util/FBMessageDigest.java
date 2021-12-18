package com.bt.util;

import java.security.MessageDigest;

public class FBMessageDigest {
   
   public static String digest(String value) {
      
      String hashCode = null;
      //==================비밀번호 해싱==================
      try {
         
         //String hashCode = "";
         
         StringBuilder sb = new StringBuilder();
         
         MessageDigest messageDigest 
             = MessageDigest.getInstance("SHA-1"); 
         
         messageDigest.reset();
         messageDigest.update((value+"@EWRQR@#$RQWQ!@!##R").getBytes()); 
                              //보안을 위해 쓰레기값을 합쳐준다.
         
         byte[] chars = messageDigest.digest();
         
         for(int i = 0 ; i < chars.length ; i++) {
            
            String tmp = Integer.toHexString(chars[i] & 0xff);
            
            if(tmp.length() == 1) {
               sb.append("0");//항상 똑같은 길이의 문자를 만들기 위해
            }
            
            sb.append(tmp);
         }

         hashCode = sb.toString();
         
         //memberVo.setMember_pw(hashCode);
         
      }catch(Exception e) {
         e.printStackTrace();
      }
//================================================
            return hashCode;
   }
}