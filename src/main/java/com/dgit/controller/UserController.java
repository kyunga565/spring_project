package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.service.UserService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/*")
public class UserController {

	@Inject
	private UserService service;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public void joinGET() {
		logger.info("회원가입 GET 실행");
	}

	@RequestMapping(value = "join", method = RequestMethod.POST)
	public String joinPOST(UserVO vo, RedirectAttributes rttr) throws Exception {
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
		logger.info("로그인 POST 실행");
		UserVO vo = service.login(dto);
		if (vo == null)
		model.addAttribute("error","ok");
		System.out.println(model);
		return;
		
	}

	@RequestMapping(value = "picview", method = RequestMethod.GET)
	public void picviewGET() {
		logger.info("사진보기 GET 실행");
	}
	@ResponseBody
	@RequestMapping("displayFile")
	public ResponseEntity<byte[]> displayFile(String filename) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		logger.info("[displayFile] filename : " + filename);

		try{
			String format = filename.substring(filename.lastIndexOf(".") + 1);// 파일확장자만 뽑기
			MediaType mType = MediaUtils.getMediaType(format);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mType);
			in = new FileInputStream(uploadPath + "/" + filename);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	@ResponseBody 
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public ResponseEntity<ArrayList<String>> DragUploadResult(List<MultipartFile> files , String writer) throws IOException {
		logger.info("writer : " + writer);
		
		ResponseEntity<ArrayList<String>> entity = null;
		try{
			ArrayList<String> fileNames = new ArrayList<>();
			for(MultipartFile file : files){
				logger.info(file.getOriginalFilename());
				logger.info(file.getSize() + "");
				logger.info(file.getContentType());
				String thumFile = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				fileNames.add(thumFile);
				logger.info(thumFile+": thumfile"); 
			}
			entity = new ResponseEntity<>(fileNames,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value = "removeFile", method = RequestMethod.POST)
	public ResponseEntity<String> DeleteFile(String filename, RedirectAttributes rttr) throws Exception {
		logger.info("filename ------------------" + filename);
		ResponseEntity<String> entity = null;
//엑스버튼 눌러서 수정화면에서 없어지고 zzz/upload에서도 없어짐
		try {
			String thumName1 = filename.substring(0, 12);
			String thumName2 = filename.substring(14);
			new File(uploadPath + thumName1 + thumName2).delete();
			new File(uploadPath + filename).delete();

			rttr.addFlashAttribute("removeFile", "success");
			logger.info("filename ------------------" + filename);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		}
		return entity;
	}
	
	
	
	
	
	
	
	

	@RequestMapping(value = "idCheck", method = RequestMethod.GET)
	public void checkId(UserVO vo, Model model, HttpServletRequest req) throws Exception {
		String id2 = req.getParameter("id");
		logger.info(id2 + "------------------");
		logger.info("------------------");
	}
}
