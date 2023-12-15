package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.UserVO;

public interface UserDAO {

	public List<UserVO> getUserList();

	public int setUserDelete(int idx);

	public List<UserVO> getUserSearch(String name);

}
