package com.dgit.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.persistence.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao dao;

	@Override
	public void join(UserVO vo) throws Exception {
		dao.join(vo);

	}

	@Override
	public void idCheck(UserVO vo) throws Exception {
		dao.idCheck(vo);
	}

	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return dao.login(dto);
	}

	@Override
	public void addAttach(UserVO vo) throws Exception {
		// 파일추가
		String[] files = vo.getFiles();
		if (files == null)
			return;
		for (String fileName : files) {
			dao.addAttach(vo.getUserid(), fileName);
		}
	}

	@Override
	public UserVO read(String userid) throws Exception {
		return dao.read(userid);
	}

	@Override
	public List<String> getAttach(String userid) throws Exception {
		return dao.getAttach(userid);
	}

	@Override
	public void removeFile(String fullname) throws Exception {
		dao.deleteFile(fullname);
		
	}

}
