package com.dgit.service;

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

}
