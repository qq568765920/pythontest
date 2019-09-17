package com.cy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cy.pojo.Imagess;
import com.cy.service.ImageService;
import com.cy.vo.SysResult;

@Controller
public class ImageController {
	@Autowired
	private ImageService imageService;

	@RequestMapping("/index")
	public String toIndex() {
		return "index";
	}

	@RequestMapping(value="/pic/upload",method = RequestMethod.POST)
	@ResponseBody
	public Imagess fileUpload(MultipartFile uploadFile) {
		System.out.println(uploadFile);
		Imagess file = imageService.fileUpload(uploadFile);
		return file;
	}

	@RequestMapping(value="/pic/discern",method = RequestMethod.POST)
	@ResponseBody
	public SysResult discern(String string) {
		System.out.println(string);
		String str="E:/000009.png";
		return SysResult.success(str);
	}




}
