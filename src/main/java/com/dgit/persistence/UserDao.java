package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

public interface UserDao {
	public void join(UserVO vo) throws Exception;

	public UserVO login(LoginDTO dto) throws Exception;

	public UserVO read(String userid) throws Exception;

	// 파일추가
	public void addAttach(String userid, String fullname) throws Exception;

	public void idCheck(UserVO vo) throws Exception;

	public List<String> getAttach(String userid) throws Exception;
	public void deleteFile(String fullname) throws Exception;
}
