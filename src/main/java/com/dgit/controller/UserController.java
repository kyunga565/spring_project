package com.dgit.controller;

import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.service.UserService;

@Controller
@RequestMapping("/*")
public class UserController {

	@Inject
	private UserService service;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "join", method = RequestMethod.GET)
	public void joinGET() {
		logger.info("회원가입 GET 실행");
	}

	@RequestMapping(value = "join", method = RequestMethod.POST)
	public String joinPOST(UserVO vo ,RedirectAttributes rttr) throws Exception {
		logger.info("회원가입POST 실행");
		service.join(vo);
		rttr.addFlashAttribute("result", "success");
		return "redirect:/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public void loginGET() {
		logger.info("로그인 GET 실행");
	}
	
	@RequestMapping(value = "loginpost", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, Model model) throws Exception {

		UserVO vo = service.login(dto);
		if (vo == null)
			return;
		model.addAttribute("userVO", vo);
		logger.info("로그인 POST 실행");
	}
	
	@RequestMapping(value = "picview", method = RequestMethod.GET)
	public void picviewGET() {
		logger.info("사진보기 GET 실행");
	}
	
	
	
	@RequestMapping(value = "idCheck", method = RequestMethod.GET)
	public void checkId(UserVO vo, Model model, HttpServletRequest req) throws Exception {
		String id2 = req.getParameter("id");
		logger.info(id2 + "------------------");
		logger.info("------------------");
	}
}
