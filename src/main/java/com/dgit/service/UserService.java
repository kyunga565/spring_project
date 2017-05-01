package com.dgit.service;

import java.util.List;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

public interface UserService {
	public void join(UserVO vo) throws Exception;
	public void idCheck(UserVO vo) throws Exception;
	public UserVO login(LoginDTO dto) throws Exception;
	public UserVO read(String userid) throws Exception;
	
	public void addAttach(UserVO vo) throws Exception;
	public List<String> getAttach(String userid) throws Exception;
}
