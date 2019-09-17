package com.cy.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cy.mapper.ImageMapper;
import com.cy.pojo.Imagess;
import com.cy.service.ImageService;

@Service
@PropertySource("classpath:/properties/image.properties")
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageMapper imageMapper;
	
	@Value("${image.localDirPath}")
	private String localDirPath;//="E:/test/images/";
	@Value("${image.urlDirPath}")
	private String urlDirPath;//="http://image.cy.com/";
	/**
	 * 文件上传思路:
	 * 		1.获取用户文件名称用户校验
	 * 		2.校验文件名称是否为图片
	 * 		3.利用工具API校验图片高度和宽度
	 * 		4.以时间格式创建
	 * 		
	 * */
	@Override
	public Imagess fileUpload(MultipartFile uploadFile) {
		Imagess image = new Imagess();
		//获取文件名称  abc.jpg
		String fileName = uploadFile.getOriginalFilename();
		//校验文件名称     正则表达式
		//先将文件名全部转为小写
		fileName=fileName.toLowerCase();
		if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			image.setError(1);
			return image;
		}
		//利用API读取用户提交的数据
		try {
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			if (height==0||width==0) {
				image.setError(1);
				return image;
			}
			//封装图片数据
			image.setHeight(height).setWidth(width);
			//以时间格式创建文件夹    	E:/Dict/images/yyyy/MM/dd
			String datePathDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String realDirPath = localDirPath + datePathDir;
			File dirFile=new File(realDirPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			//采用UUID命名文件名称xxxx-xxxx,使用replace替换名称中的-符号
			String uuid=UUID.randomUUID().toString().replace("-", "");
			//截串  含头不含尾   	zbc.jpg
			String fileType=fileName.substring(fileName.lastIndexOf("."));

			String realName=uuid+fileType;
			System.out.println("文件名称:"+realName);
			String realFilePath= realDirPath + "/" + realName;

			File file=new File(realFilePath);
			uploadFile.transferTo(file);
			System.out.println("文件上传成功!!");
			String realUrlPath= urlDirPath + datePathDir + "/"+realName;
			image.setUrl(realUrlPath);
		} catch (Exception e) {
			e.printStackTrace();
			image.setError(1);
			return image;
		}
		return image;

	}

}




