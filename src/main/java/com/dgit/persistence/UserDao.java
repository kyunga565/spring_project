package com.dgit.persistence;

import com.dgit.domain.UserVO;

public interface UserDao {
	public UserVO join(UserVO vo) throws Exception;
}
