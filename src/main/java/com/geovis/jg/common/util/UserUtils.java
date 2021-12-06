//package com.geovis.jg.common.util;
//
//import com.geovis.jg.qqzdmb.system.model.SysUser;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
///**
// * 获取登录的用户信息
// */
//public  class UserUtils {
//    public static SysUser getUserInfo(){
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        Object principal = authentication.getPrincipal();
//
//        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return user;
//    }
//
//}
