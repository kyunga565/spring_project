package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.interceptor.LoginInterceptor;
import com.dgit.service.UserService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/*")
public class UserController {

	@Inject
	private UserService service;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "uploadPath")
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
	public void loginGET(@ModelAttribute("dto") LoginDTO dto) {
		logger.info("로그인 GET 실행");
	}

	@RequestMapping(value = "loginpost", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, Model model, HttpSession session) throws Exception {

		logger.info("로그인 POST 실행");
		UserVO vo = service.login(dto);
		if (vo == null)
			model.addAttribute("error", "ok");
		model.addAttribute("userVO", vo);
		return;

	}

	@RequestMapping(value = "picview", method = RequestMethod.GET)
	public void picviewGET() throws Exception {
		logger.info("사진추가 GET 실행");
	}

	@ResponseBody
	@RequestMapping("displayFile")
	public ResponseEntity<byte[]> displayFile(String filename) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		logger.info("[displayFile] filename : " + filename);

		try {
			String format = filename.substring(filename.lastIndexOf(".") + 1);// 파일확장자만
																				// 뽑기
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

	@RequestMapping(value = "addAttach", method = RequestMethod.POST)
	public String registerPost(List<MultipartFile> imagefiles, RedirectAttributes rttr, HttpServletRequest request)
			throws Exception {
		logger.info("사진보기 POST 실행");
		HttpSession session = request.getSession();
		UserVO vo = (UserVO) session.getAttribute(LoginInterceptor.LOGIN);

		// 파일업로드--------------------------------------------------------
		ArrayList<String> filenames = new ArrayList<>();
		for (MultipartFile file : imagefiles) {
			logger.info("originalName : " + file.getOriginalFilename());
			logger.info("size : " + file.getSize());
			logger.info("contentType : " + file.getContentType());
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			filenames.add(savedName);
		}
		String[] sFiles = filenames.toArray(new String[filenames.size()]);
		vo.setFiles(sFiles);
		// ----------------------------------------------------------------
		service.addAttach(vo);
		logger.info(vo.toString());

		rttr.addAttribute("userid", vo.getUserid());
		return "redirect:/piclist";
	}

	@RequestMapping(value = "piclist", method = RequestMethod.GET)
	public void piclistGET(Model model, String userid) throws Exception {
		logger.info("사진리스트 GET 실행-" + userid);
		model.addAttribute("userVO", service.getAttach(userid));
	}

	@RequestMapping(value = "removeFile", method = RequestMethod.POST)
	public ResponseEntity<String> DeleteFile(String filename, RedirectAttributes rttr) throws Exception {
		logger.info("사진지우기 POST");
		ResponseEntity<String> entity = null;
		try {
			String thumName1 = filename.substring(0, 12);
			String thumName2 = filename.substring(14);
			new File(uploadPath + thumName1 + thumName2).delete();
			new File(uploadPath + filename).delete();

			rttr.addFlashAttribute("removeFile", "success");
			
			service.removeFile(filename);
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
