package com.iktakademija.Projekat.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface FileUploadService {
	
	public String uploadOfferImage(MultipartFile file, RedirectAttributes redirectAttributes)throws IOException;
	
}
