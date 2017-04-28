package com.dgit.persistence;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

public interface UserDao {
	public void join(UserVO vo) throws Exception;
	public UserVO login(LoginDTO dto) throws Exception;
	
	
	
	public void idCheck(UserVO vo) throws Exception;
}
