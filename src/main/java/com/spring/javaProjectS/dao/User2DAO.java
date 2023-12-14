package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.UserVO;

public interface User2DAO {

	public List<UserVO> getUser2List();

	public List<UserVO> getUser2Search(@Param("name") String name);

	public int setUser2Delete(@Param("idx") int idx);	//변수는 @param 없어도 되지만, 객체는 @param 이 꼭 있어야 함

	public int setUser2Input(@Param("vo") UserVO vo);

	public int setUser2Update(@Param("vo") UserVO vo); 

}
