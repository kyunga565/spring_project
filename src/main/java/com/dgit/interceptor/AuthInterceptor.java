package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("pre hander.........");

		// 세선에 들어있는 로긴정보받아오기
		// 있으면 그대로진행, 없으면 로긴화면으로 이동
		HttpSession session = request.getSession();
		Object login = session.getAttribute(LoginInterceptor.LOGIN);
	
		if (login == null) {
			// 로그인안됨
			logger.info("로그인으로 가자!!!");
			saveDest(request);
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		return true;
	}

	private void saveDest(HttpServletRequest req) {
		String uri = req.getRequestURI();// command/ex01/sboard/register
		String query = req.getQueryString();// 매개변수

		if (query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		if (req.getMethod().equals("GET")) {
			logger.info("dest : " + (uri + query));
			req.getSession().setAttribute("dest", uri + query);
		}
	}

}
