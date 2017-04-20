package com.wong.learn.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wong.learn.annotation.Permission;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		Permission permission = method.getMethodAnnotation(Permission.class);
		if (permission != null) {
			HttpSession session = request.getSession(false);
			
			if(session != null){
				return true;
			}
			
			response.addHeader("Content-Type", "text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write("forbid by system");
			return false;
		} else {
			return true;
		}
	}

}
