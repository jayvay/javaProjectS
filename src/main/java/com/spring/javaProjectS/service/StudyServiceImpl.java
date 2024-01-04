package com.spring.javaProjectS.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.spring.javaProjectS.dao.StudyDAO;
import com.spring.javaProjectS.dao.User2DAO;
import com.spring.javaProjectS.vo.Chart2VO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.QrCodeVO;
import com.spring.javaProjectS.vo.UserVO;

import net.coobird.thumbnailator.Thumbnailator;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	StudyDAO studyDAO;

	@Autowired
	User2DAO user2DAO;	//이미 있으니까 갖다 쓰기
	
	@Override
	public String[] getCityStringArray(String dodo) {
		
		String[] strArray = new String[100];
		
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "서초구";
			strArray[2] = "관악구";
			strArray[3] = "마포구";
			strArray[4] = "영등포구";
			strArray[5] = "강북구";
			strArray[6] = "동대문구";
			strArray[7] = "성북구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "평택시";
			strArray[4] = "용인시";
			strArray[5] = "의정부시";
			strArray[6] = "광명시";
			strArray[7] = "성남시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "괴산군";
			strArray[3] = "제천시";
			strArray[4] = "단양군";
			strArray[5] = "증평군";
			strArray[6] = "옥천군";
			strArray[7] = "영동군";
		}
		else if(dodo.equals("충남")) {
			strArray[0] = "천안시";
			strArray[1] = "아산시";
			strArray[2] = "논산시";
			strArray[3] = "공주시";
			strArray[4] = "부여군";
			strArray[5] = "홍성군";
			strArray[6] = "예산군";
			strArray[7] = "청양군";
		}
		
		return strArray;
	}

	@Override
	public ArrayList<String> getCityArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("서초구");
			vos.add("관악구");
			vos.add("마포구");
			vos.add("영등포구");
			vos.add("강북구");
			vos.add("동대문구");
			vos.add("성북구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("용인시");
			vos.add("의정부시");
			vos.add("광명시");
			vos.add("성남시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("괴산군");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("증평군");
			vos.add("옥천군");
			vos.add("영동군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("논산시");
			vos.add("공주시");
			vos.add("부여군");
			vos.add("홍성군");
			vos.add("예산군");
			vos.add("청양군");
		}
		return vos;
	}

	@Override
	public UserVO getUserSearchVO(String mid) {
		return user2DAO.getUserSearchVO(mid);
	}

	@Override
	public List<UserVO> getUserSearchVOS(String mid) {
		return user2DAO.getUserSearchVOS(mid);
	}

	@Override
	public int fileUpload(MultipartFile fName, String mid) {
		int res = 0;
		
		//파일 이름 중복 처리
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String sFileName = mid + "_" + uid + "_" + oFileName;
		
		//파일 복사 처리 (서버 메모리(아직 서버가 아님)에 올라와 있는 파일의 정보를 실제 서버 파일 시스템에 저장시킨다)
		try {
			writeFile(fName, sFileName);
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	//서버 메모리(아직 서버가 아님)에 올라와 있는 파일의 정보를 실제 서버 파일 시스템에 저장하는 일반 메소드
	private void writeFile(MultipartFile fName, String sFileName) throws IOException {
		//request 객체를 사용하기 위해 불러온다
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		FileOutputStream fos = new FileOutputStream(realPath + sFileName);
		
		if(fName.getBytes().length != -1) fos.write(fName.getBytes());	//바이너리 형식으로 존재하는 파일을 바이트로 변환하면 껍데기에 내용이 넣어진다
		fos.flush();
		fos.close();
	}

	@Override
	public KakaoAddressVO getKakaoAddressSearch(String address) {
		return studyDAO.getKakaoAddressSearch(address);
	}

	@Override
	public void setKakaoAddressInput(KakaoAddressVO vo) {
		studyDAO.setKakaoAddressInput(vo);
	}

	@Override
	public List<KakaoAddressVO> getKakaoAddressList() {
		return studyDAO.getKakaoAddressList();
	}

	@Override
	public int setKakaoAddressDelete(String address) {
		return studyDAO.setKakaoAddressDelete(address);
	}

	@Override
	public List<Chart2VO> getVisitCount() {
		return studyDAO.getVisitCount();
	}

	//QR코드 연습1 - 개인정보
	@Override
	public String setQrCodeCreate1(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMid() + "_" + vo.getName() + "_" + vo.getEmail() + "_" + strUid;
			qrCodeName2 = "생성일 : " + strToday + "\n아이디 : " + vo.getMid() + "\n성명 : " + vo.getName() + "\n이메일 : " + vo.getEmail();
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();	//폴더가 존재하지 않는 경우 폴더를 생성한다
			
			//QR코드 만들기
			int qrCodeColor = 0xFF000000;	//QR코드의 글자색 - 검정색
			int qrCodeBackColor = 0xFFFFFFFF;	//QR코드의 배경색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			//생성된 QR 코드 이미지를 그림 파일로 만든다
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}
	
	
	//QR코드 연습2 - 소개할 사이트 주소를 QR로 만들기
	@Override
	public String setQrCodeCreate2(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString().substring(0,2);
			
//			qrCodeName = strToday + "_" + vo.getSiteUrl() + "_" + strUid;
			qrCodeName = strToday + "_" + vo.getSiteUrl().replace(":","_").replace("/","_").replace("?","_") + "_" + strUid;
			qrCodeName2 = vo.getSiteUrl();
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();	//폴더가 존재하지 않는 경우 폴더를 생성한다
			
			//QR코드 만들기
			int qrCodeColor = 0xFF000000;	//QR코드의 글자색 - 검정색
			int qrCodeBackColor = 0xFFFFFFFF;	//QR코드의 배경색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			//생성된 QR 코드 이미지를 그림 파일로 만든다
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	//QR코드 연습3 - 영화 예매 정보를 QR로 만들기
	@Override
	public String setQrCodeCreate3(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			//UUID uid = UUID.randomUUID(); 
			//String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime() + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild() + vo.getMid();
			qrCodeName2 = "구매일자 : " + strToday + "\n영화제목 : " + vo.getMovieName() + "\n상영일자 : " + vo.getMovieDate() + "\n상영시간 : " + vo.getMovieTime() + "\n성인 : " + vo.getMovieAdult() + "매\n어린이 : " + vo.getMovieChild() + "매\n구매자 아이디 : " + vo.getMid();
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();	// 폴더가 존재하지 않으면 폴더를 생성시켜준다.
			
			// qr 코드 만들기
			int qrCodeColor = 0xFF000000;	// qr코드의 글자색 - 검색
			int qrCodeBackColor = 0xFFFFFFFF;	// qr코드의 배경색(바탕색) - 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 생성된 QR코드 이미지를 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate4(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			//UUID uid = UUID.randomUUID();
			//String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime() + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild() + vo.getMid();
			qrCodeName2 = "구매일자 : " + strToday + "\n영화제목 : " + vo.getMovieName() + "\n상영일자 : " + vo.getMovieDate() + "\n상영시간 : " + vo.getMovieTime() + "\n성인 : " + vo.getMovieAdult() + "매\n어린이 : " + vo.getMovieChild() + "매\n구매자 아이디 : " + vo.getMid();
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();	// 폴더가 존재하지 않으면 폴더를 생성시켜준다.
			
			// qr 코드 만들기
			int qrCodeColor = 0xFF000000;	// qr코드의 글자색 - 검색
			int qrCodeBackColor = 0xFFFFFFFF;	// qr코드의 배경색(바탕색) - 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 생성된 QR코드 이미지를 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
			// QR코드 생성후 정보를 DB에 저장시켜준다.
			vo.setPurchaseDate(strToday);
			vo.setQrCodeName(qrCodeName);
			studyDAO.setQrCode(vo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public QrCodeVO getQrCodeSearch(String qrCode) {
		return studyDAO.getQrCodeSearch(qrCode);
	}

	@Override
	public int setThumbnailCreate(MultipartFile file) {
		int res = 0;
		try {
			//파일 이름 중복 안 되게 하기
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString(); 
			String sFileName = strUid.substring(strUid.lastIndexOf("-") + 1) + "_" + file.getOriginalFilename();
			
			//지정된 경로에 원본 이미지 저장하기
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
			File realFileName = new File(realPath + sFileName);
			file.transferTo(realFileName); //원본 이미지를 저장
			
			//썸네일 이미지 저장하기
			String thumbnailSaveName = realPath + "s_" + sFileName;
			File thumbnailFile = new File(thumbnailSaveName);
			
			int width = 160;
			int height = 120;
			Thumbnailator.createThumbnail(realFileName, thumbnailFile, width, height);

			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
