package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.Chart2VO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.QrCodeVO;
import com.spring.javaProjectS.vo.TransactionVO;

public interface StudyDAO {

	public KakaoAddressVO getKakaoAddressSearch(@Param("address") String address);

	public void setKakaoAddressInput(@Param("vo") KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressList();

	public int setKakaoAddressDelete(@Param("address") String address);

	public List<Chart2VO> getVisitCount();

	public void setQrCode(@Param("vo") QrCodeVO vo);

	public QrCodeVO getQrCodeSearch(@Param("qrCode") String qrCode);

	public List<TransactionVO> getTransactionList();

	public List<TransactionVO> getTransactionList2();

	public void setTransactionUser1Input(@Param("vo") TransactionVO vo);

	public void setTransactionUser2Input(@Param("vo") TransactionVO vo);

	public void setTransactionUserInput(@Param("vo") TransactionVO vo);

	public void setTransactionUserInput2(@Param("mid") String mid, @Param("name") String name, @Param("age") int age, @Param("address") String address, @Param("job") String job);


}
