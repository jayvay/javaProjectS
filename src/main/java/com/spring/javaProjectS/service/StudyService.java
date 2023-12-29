package com.spring.javaProjectS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.vo.Chart2VO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.QrCodeVO;
import com.spring.javaProjectS.vo.UserVO;

public interface StudyService {

	public String[] getCityStringArray(String dodo);

	public ArrayList<String> getCityArrayList(String dodo);

	public UserVO getUserSearchVO(String mid);

	public List<UserVO> getUserSearchVOS(String mid);

	public int fileUpload(MultipartFile fName, String mid);

	public KakaoAddressVO getKakaoAddressSearch(String address);

	public void setKakaoAddressInput(KakaoAddressVO vo);

	public List<KakaoAddressVO> getKakaoAddressList();

	public int setKakaoAddressDelete(String address);

	public List<Chart2VO> getVisitCount();

	public String setQrCodeCreate1(String realPath, QrCodeVO vo);
	public String setQrCodeCreate2(String realPath, QrCodeVO vo); //같은 내용임(공부 차원에서 새로 또 만듦)
	public String setQrCodeCreate3(String realPath, QrCodeVO vo);
	public String setQrCodeCreate4(String realPath, QrCodeVO vo);
	public QrCodeVO getQrCodeSearch(String qrCode);

	
}
