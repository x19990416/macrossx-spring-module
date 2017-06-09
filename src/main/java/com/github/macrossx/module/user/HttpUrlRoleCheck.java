package com.github.macrossx.module.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.AntPathMatcher;

import com.github.macrossx.module.user.service.ISysRoleService;
import com.macrossx.springframework.Constants.RESPONSE_MESSAGE;
import com.macrossx.springframework.common.MapResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 1.校验用户是否登录，若没有登录则返回{@code Constants.RESPONSE_MESSAGE.M401}，反之则从http头中获取用户信息
 * {@code Account}，若对应方法又{@code AnnotationAccount}则将account传入其中<br>
 * eg: method(String,String, @AnnotationAccount Account)<br>
 * 
 * @author guolimin
 *
 */
@Slf4j
public class HttpUrlRoleCheck {
	@Autowired
	private HttpServletRequest request;
	private AntPathMatcher apm;
	@Autowired   
	@Qualifier("module.user.SysUrlRoleService")   
	private ISysRoleService accountService;


	public Object arround(ProceedingJoinPoint pjp) throws Throwable {
		if (request != null && !StringUtils.isEmpty(request.getHeader("token"))) {
		
			
			
			
			return pjp.proceed(pjp.getArgs());
		} else {
			return new MapResponse(RESPONSE_MESSAGE.M401.code(), RESPONSE_MESSAGE.M401.message());

		}
	}
	
	
}
