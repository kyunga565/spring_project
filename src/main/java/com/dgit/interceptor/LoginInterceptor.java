package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter { //로긴정보를 세션에 저장

	public static final String LOGIN = "login";
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("prehandler----------------");
		HttpSession session = request.getSession();
		if (session.getAttribute(LOGIN) != null) {
			logger.info("clear login data prehandler");
			session.removeAttribute(LOGIN);
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("posthandler----------------");

		HttpSession session = request.getSession();
		Object userVO = modelAndView.getModel().get("userVO");
		if (userVO != null) {
			logger.info("new login success");
			session.setAttribute(LOGIN, userVO);
			
			Object dest = session.getAttribute("dest");
			String path = dest != null ? (String) dest : request.getContextPath();
			//dest가 있으면 등록화면으로 없으면 홈
			response.sendRedirect(path);
			
			//response.sendRedirect(request.getContextPath()); -- home
		}//가입되지않은회원이면 loginPost에서 리스트로감
	}
}
