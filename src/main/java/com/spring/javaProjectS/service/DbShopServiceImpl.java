package com.spring.javaProjectS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.dao.DbShopDAO;
import com.spring.javaProjectS.vo.DbCartVO;
import com.spring.javaProjectS.vo.DbOrderVO;
import com.spring.javaProjectS.vo.DbProductVO;

@Service
public class DbShopServiceImpl implements DbShopService {

	@Autowired
	DbShopDAO dbShopDAO;

	@Override
	public DbProductVO getCategoryMainOne(String categoryMainCode, String categoryMainName) {
		return dbShopDAO.getCategoryMainOne(categoryMainCode, categoryMainName);
	}

	@Override
	public int setCategoryMainInput(DbProductVO vo) {
		return dbShopDAO.setCategoryMainInput(vo);
	}

	@Override
	public List<DbProductVO> getCategoryMain() {
		return dbShopDAO.getCategoryMain();
	}

	@Override
	public List<DbProductVO> getCategoryMiddle() {
		return dbShopDAO.getCategoryMiddle();
	}
	
	@Override
	public List<DbProductVO> getCategorySub() {
		return dbShopDAO.getCategorySub();
	}
	
	@Override
	public DbProductVO getCategoryMiddleOne(DbProductVO vo) {
		return dbShopDAO.getCategoryMiddleOne(vo);
	}

	@Override
	public int setCategoryMainDelete(String categoryMainCode) {
		return dbShopDAO.setCategoryMainDelete(categoryMainCode);
	}

	@Override
	public int setCategoryMiddleInput(DbProductVO vo) {
		return dbShopDAO.setCategoryMiddleInput(vo);
	}

	@Override
	public DbProductVO getCategorySubOne(DbProductVO vo) {
		return dbShopDAO.getCategorySubOne(vo);
	}

	@Override
	public int setCategoryMiddleDelete(String categoryMiddleCode) {
		return dbShopDAO.setCategoryMiddleDelete(categoryMiddleCode);
	}

	@Override
	public int setCategorySubInput(DbProductVO vo) {
		return dbShopDAO.setCategorySubInput(vo);
	}

	@Override
	public List<DbProductVO> getCategoryMiddleName(String categoryMainCode) {
		return dbShopDAO.getCategoryMiddleName(categoryMainCode);
	}

	@Override
	public List<DbProductVO> getCategorySubName(String categoryMainCode, String categoryMiddleCode) {
		return dbShopDAO.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}

	@Override
	public DbProductVO getCategoryProductName(DbProductVO vo) {
		return dbShopDAO.getCategoryProductName(vo);
	}

	@Override
	public int setCategorySubDelete(String categorySubCode) {
		return dbShopDAO.setCategorySubDelete(categorySubCode);
	}

  @Override
  public int imgCheckProductInput(MultipartFile file, DbProductVO vo) {
    int res = 0;
    // 메인 이미지 업로드 처리
    try {
      String originalFilename = file.getOriginalFilename();
      if(originalFilename != null && originalFilename != "") {
        // 상품 메인 이미지 업로드 할 때 파일명 중복되지 않게 처리
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String saveFileName = sdf.format(date) + "_" + originalFilename;
        
        // 메인 이미지 파일을 서버 파일 시스템에 업로드 처리하는 메소드 호출
        writeFile(file, saveFileName, "product");
        vo.setFSName(saveFileName);	// 업로드된 메인 이미지의 저장된 파일명을 vo에 저장
      }
      else {
        return res;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // ckeditor로 올린 상품 상세 이미지 파일을 'dbShop'에서 'dbShop/product' 폴더로 복사한다
    //             0         1         2         3         4         5
    //             012345678901234567890123456789012345678901234567890
    // <img alt="" src="/javaProductS/data/dbShop/211229124318_4.jpg"
    // <img alt="" src="/javaProductS/data/dbShop/product/211229124318_4.jpg"

    // ckeditor을 이용해서 담은 상품의 상세설명에 이미지가 포함되어 있으면 이미지를 dbShop폴더에서 dbShop/product폴더로 복사작업처리 시켜준다.
    String content = vo.getContent();
    if(content.indexOf("src=\"/") == -1) return 0;    // content에 이미지가 없으면 돌아간다.

    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/");

    int position = 31;
    String nextImg = content.substring(content.indexOf("src=\"/") + position);
    boolean sw = true;

    while(sw) {
      String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
      String copyFilePath = "";
      String oriFilePath = uploadPath + imgFile;

      copyFilePath = uploadPath + "product/" + imgFile;

      fileCopyCheck(oriFilePath, copyFilePath);

      if(nextImg.indexOf("src=\"/") == -1) sw = false;
      else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
    }
    
    vo.setContent(vo.getContent().replace("/data/dbShop/", "/data/dbShop/product/"));

    
    int maxIdx = 1;
    DbProductVO maxVo = dbShopDAO.getProductMaxIdx();
    if(maxVo != null) maxIdx = maxVo.getIdx() + 1;
     
    vo.setIdx(maxIdx);
    vo.setProductCode(vo.getCategoryMainCode()+vo.getCategoryMiddleCode()+vo.getCategorySubCode()+maxIdx);
    res = dbShopDAO.setDbProductInput(vo);
    return res;
  }


  private void fileCopyCheck(String oriFilePath, String copyFilePath) {
    File oriFile = new File(oriFilePath);
    File copyFile = new File(copyFilePath);

    try {
      FileInputStream  fis = new FileInputStream(oriFile);
      FileOutputStream fos = new FileOutputStream(copyFile);

      byte[] buffer = new byte[2048];
      int count = 0;
      while((count = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, count);
      }
      fos.flush();
      fos.close();
      fis.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void writeFile(MultipartFile fName, String saveFileName, String flag) throws IOException {
    byte[] data = fName.getBytes();

    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    String readPath = "";
    if(flag.equals("product")) {
      readPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/product/");
    }
    else if(flag.equals("mainImage")) {
      readPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/mainImage/");
    }

    FileOutputStream fos = new FileOutputStream(readPath + saveFileName);
    fos.write(data);
    fos.close();
  }

	@Override
	public List<DbProductVO> getDbShopList(String part) {
		return dbShopDAO.getDbShopList(part);
	}

	@Override
	public List<DbProductVO> getSubTitle() {
		return dbShopDAO.getSubTitle();
	}

	@Override
	public DbProductVO getDbShopProduct(int idx) {
		return dbShopDAO.getDbShopProduct(idx);
	}

	@Override
	public List<DbProductVO> getCategoryProductList(String categoryMainCode, String categoryMiddleCode, String categorySubCode) {
		return dbShopDAO.getCategoryProductList(categoryMainCode, categoryMiddleCode, categorySubCode);
	}

	@Override
	public DbProductVO getProductInfor(String productName) {
		return dbShopDAO.getProductInfor(productName);
	}

	@Override
	public List<DbProductVO> getOptionList(int productIdx) {
		return dbShopDAO.getOptionList(productIdx);
	}

	@Override
	public int getOptionSearch(int productIdx, String optionName) {
		return dbShopDAO.getOptionSearch(productIdx, optionName);
	}

	@Override
	public int setDbOptionInput(DbProductVO vo) {
		return dbShopDAO.setDbOptionInput(vo);
	}

	@Override
	public List<DbProductVO> getDbShopOption(int idx) {
		return dbShopDAO.getDbShopOption(idx);
	}

	@Override
	public DbCartVO getDbCartProductOptionSearch(String productName, String optionName, String mid) {
		return dbShopDAO.getDbCartProductOptionSearch(productName, optionName, mid);
	}

	@Override
	public int dbShopCartUpdate(DbCartVO vo) {
		return dbShopDAO.dbShopCartUpdate(vo);
	}

	@Override
	public int dbShopCartInput(DbCartVO vo) {
		return dbShopDAO.dbShopCartInput(vo);
	}

	@Override
	public List<DbCartVO> getDbCartList(String mid) {
		return dbShopDAO.getDbCartList(mid);
	}

	@Override
	public int dbCartDelete(int idx) {
		return dbShopDAO.dbCartDelete(idx);
	}

	@Override
	public DbOrderVO getOrderMaxIdx() {
		return dbShopDAO.getOrderMaxIdx();
	}

	@Override
	public DbCartVO getCartIdx(int idx) {
		return dbShopDAO.getCartIdx(idx);
	}
	
	
}
