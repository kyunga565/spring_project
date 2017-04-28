package com.dgit.service;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

public interface UserService {
	public void join(UserVO vo) throws Exception;
	public void idCheck(UserVO vo) throws Exception;
	public UserVO login(LoginDTO dto) throws Exception;
}
