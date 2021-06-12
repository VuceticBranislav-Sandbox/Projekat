package com.iktakademija.Projekat.exceptions;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceltionHandler {
	@ExceptionHandler(SizeLimitExceededException.class)
	public String handleError(SizeLimitExceededException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "File to large, try again");
		return "redirect:/uploadStatus";
	}
	
	// na projektu ovim hadlovati sto vise
}
