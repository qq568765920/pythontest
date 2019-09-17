package com.cy.service;

import org.springframework.web.multipart.MultipartFile;

import com.cy.pojo.Imagess;

public interface ImageService {

	Imagess fileUpload(MultipartFile uploadFile);

}
