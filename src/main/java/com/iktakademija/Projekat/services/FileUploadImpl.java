package com.iktakademija.Projekat.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class FileUploadImpl implements FileUploadService {

	@Override
	public String uploadOfferImage(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		return singleFileUpload(file, redirectAttributes);
	}

	private String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

		// Trivial check
		if (file == null || redirectAttributes== null ) return null;
		
		// Check if file is empty
		if (file.isEmpty()) return null;		

		// If note empty, save file to disk
		byte[] bytes = file.getBytes();
		
		// bolje za naiv fajla GUID ili tmestamp + extenzija
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		String fileName = String.format("%s_%s", UUID.randomUUID().toString(), LocalDate.now().format(formatter));
		
		Path path = Paths.get("c:\\temp\\" + fileName);
		Files.write(path, bytes);

		return path.toAbsolutePath().toString();
	}
	
}
