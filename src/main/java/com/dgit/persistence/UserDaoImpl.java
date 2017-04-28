package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.UserVO;


@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession session;

	private static String namespace = "com.dgit.mapper.UserMapper";


	@Override
	public UserVO join(UserVO vo) throws Exception {
		return session.selectOne(namespace + ".join", vo);
	}


}
