package com.spring.javaProjectS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaProjectS.dao.User2DAO;
import com.spring.javaProjectS.dao.UserDAO;
import com.spring.javaProjectS.vo.UserVO;

//@Component 하위 @controller, service, repository
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	User2DAO user2DAO;

	@Override
	public List<UserVO> getUserList() {
//		List<UserVO> vos = userDAO.getUserList();
//		return vos;
		return userDAO.getUserList();
	}

	@Override
	public int setUserDelete(int idx) {
		return userDAO.setUserDelete(idx);
	}

	@Override
	public List<UserVO> getUserSearch(String name) {
		return userDAO.getUserSearch(name);
	}
	
	@Override
	public List<UserVO> getUser2List() {
		return user2DAO.getUser2List();
	}
	
	@Override
	public List<UserVO> getUser2Search(String name) {
		return user2DAO.getUser2Search(name);
	}

	@Override
	public int setUser2Delete(int idx) {
		return user2DAO.setUser2Delete(idx);
	}

	@Override
	public int setUser2Input(UserVO vo) {
		return user2DAO.setUser2Input(vo);
	}

	@Override
	public int setUser2Update(UserVO vo) {
		return user2DAO.setUser2Update(vo);
	}
	
}
