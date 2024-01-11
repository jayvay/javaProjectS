package com.spring.javaProjectS.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.vo.DbBaesongVO;
import com.spring.javaProjectS.vo.DbCartVO;
import com.spring.javaProjectS.vo.DbOrderVO;
import com.spring.javaProjectS.vo.DbProductVO;

public interface DbShopService {

	public DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName);

	public int setCategoryMainInput(DbProductVO vo);

	public List<DbProductVO> getCategoryMain();
	public List<DbProductVO> getCategoryMiddle();
	public List<DbProductVO> getCategorySub();

	public DbProductVO getCategoryMiddleOne(DbProductVO vo);

	public int setCategoryMainDelete(String categoryMainCode);

	public int setCategoryMiddleInput(DbProductVO vo);

	public DbProductVO getCategorySubOne(DbProductVO vo);

	public int setCategoryMiddleDelete(String categoryMiddleCode);

	public int setCategorySubInput(DbProductVO vo);

	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode);

	public List<DbProductVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode);

	public DbProductVO getCategoryProductName(DbProductVO vo);

	public int setCategorySubDelete(String categorySubCode);

	public int imgCheckProductInput(MultipartFile file, DbProductVO vo);

	public List<DbProductVO> getDbShopList(String part);

	public List<DbProductVO> getSubTitle();

	public DbProductVO getDbShopProduct(int idx);

	public List<DbProductVO> getCategoryProductList(String categoryMainCode, String categoryMiddleCode, String categorySubCode);

	public DbProductVO getProductInfor(String productName);

	public List<DbProductVO> getOptionList(int productIdx);

	public int getOptionSearch(int productIdx, String optionName);

	public int setDbOptionInput(DbProductVO vo);

	public List<DbProductVO> getDbShopOption(int idx);

	public DbCartVO getDbCartProductOptionSearch(String productName, String optionName, String mid);

	public int dbShopCartUpdate(DbCartVO vo);

	public int dbShopCartInput(DbCartVO vo);

	public List<DbCartVO> getDbCartList(String mid);

	public int dbCartDelete(int idx);

	public DbOrderVO getOrderMaxIdx();

	public DbCartVO getCartIdx(int idx);

	public void setDbOrder(DbOrderVO vo);

	public void setDbCartDeleteAll(int cartIdx);

	public void setDbBaesong(DbBaesongVO baesongVO);

	public void setMemberPointPlus(int point, String mid);

	public int getTotalBaesongOrder(String orderIdx);

	public List<DbBaesongVO> getOrderBaesong(String orderIdx);



}
