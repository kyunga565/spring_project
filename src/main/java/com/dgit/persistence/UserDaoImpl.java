package com.dgit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<UserVO> idCheck() throws Exception {
		return session.selectList(namespace + ".idCheck");
	}

	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		return session.selectOne(namespace + ".login", dto);
	}

	@Override
	public void addAttach(String userid, String fullname) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("fullName", fullname);
		System.out.println(fullname + "--------------------------------");
		session.insert(namespace + ".addAttach", map);
	}

	@Override
	public UserVO read(String userid) throws Exception {
		return session.selectOne(namespace + ".read", userid);
	}

	@Override
	public List<String> getAttach(String userid) throws Exception {
		return session.selectList(namespace + ".getAttach", userid);
	}

	@Override
	public void deleteFile(String fullname) throws Exception {
		session.delete(namespace + ".deleteFile", fullname);

	}

}
