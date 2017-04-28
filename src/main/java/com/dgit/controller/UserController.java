package com.dgit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String postinsert(UserVO vo, Model model) throws Exception {
		logger.info("POST실행");
		service.join(vo);
		return "redirect:/login";
	}

}
