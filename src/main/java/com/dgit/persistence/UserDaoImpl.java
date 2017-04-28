package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession session;

	private static String namespace = "com.dgit.mapper.UserMapper";

	@Override
	public void join(UserVO vo) throws Exception {
		session.insert(namespace + ".join", vo);
	}

	@Override
	public void idCheck(UserVO vo) throws Exception {
		session.selectOne(namespace + ".idCheck", vo);
	}

	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return session.selectOne(namespace + ".login", dto);
	}

}
