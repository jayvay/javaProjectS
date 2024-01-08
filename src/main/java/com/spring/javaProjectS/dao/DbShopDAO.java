package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.DbProductVO;

public interface DbShopDAO {

	public DbProductVO getCategoryMainOne(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMainName") String categoryMainName);

	public int setCategoryMainInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategoryMain();
	public List<DbProductVO> getCategoryMiddle();
	public List<DbProductVO> getCategorySub();

	public DbProductVO getCategoryMiddleOne(@Param("vo") DbProductVO vo);

	public int setCategoryMainDelete(@Param("categoryMainCode") String categoryMainCode);

	public int setCategoryMiddleInput(@Param("vo") DbProductVO vo);

	public DbProductVO getCategorySubOne(@Param("vo") DbProductVO vo);

	public int setCategoryMiddleDelete(@Param("categoryMiddleCode") String categoryMiddleCode);

	public int setCategorySubInput(@Param("vo") DbProductVO vo);

	public List<DbProductVO> getCategoryMiddleName(@Param("categoryMainCode") String categoryMainCode);

	public List<DbProductVO> getCategorySubName(@Param("categoryMainCode") String categoryMainCode, @Param("categoryMiddleCode") String categoryMiddleCode);

	public DbProductVO getCategoryProductName(@Param("vo") DbProductVO vo);

	public int setCategorySubDelete(@Param("categorySubCode") String categorySubCode);

	public DbProductVO getProductMaxIdx();

	public int setDbProductInput(@Param("vo") DbProductVO vo);

	

	
	
}
