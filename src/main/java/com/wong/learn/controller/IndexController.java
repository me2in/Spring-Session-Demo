package com.wong.learn.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wong.learn.annotation.Permission;
import com.wong.learn.pojo.User;

@RequestMapping("/")
@RestController
public class IndexController {
	
	@RequestMapping("/login")
	public String login(String username,String password){
		User user = new User(username,password);
		HttpSession session = getSession();
		session.setAttribute("loginUser", user);
		session.setMaxInactiveInterval(7 * 24 * 60 * 60);
		return String.format("sessionId:%s  msg:%s", session.getId(),"login success!");
	}
	
	@RequestMapping("/addAttr")
	public String addAttr(String key,String value){
		HttpSession session = getSession();
		session.setAttribute(key, value);
		return String.format("sessionId:%s  msg:%s", session.getId(),"add attr ok!");
	}
	
	@RequestMapping("/getAttr")
	public String getAttr(String key){
		HttpSession session = getSession();
		return String.format("sessionId:%s  msg:%s", session.getId(),session.getAttribute(key).toString());
	}
	
	@RequestMapping("/withOutGetSession")
	public String withOutGetSession(){
		return "I'm not try to get Session!";
	}
	/**
	 * 需要servlet3.1  tomcat8.x以上支持
	 * @return
	 */
	@RequestMapping("/changeSessionId")
	public String changeSessionId(){
		HttpSession session = getSession();
		String oldId = session.getId();
		HttpServletRequest request = getRequest();
		String newId = request.changeSessionId();;// since servlet3.1 tomcat8 support
		return String.format("old sessionId:%s, new sessionId:%s", oldId,newId);
	}
	
	@RequestMapping("/test")
	@Permission
	public String test(){
		return "ok";
	}
	/**
	 * 获取当前请求的request
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
	}
	
	/**
	 * 获取当前线程请求到的response对象
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getResponse();
	}
	
	public HttpSession getSession(){
		return getRequest().getSession();
	}

}
