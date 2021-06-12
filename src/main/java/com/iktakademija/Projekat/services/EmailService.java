package com.iktakademija.Projekat.services;

import com.iktakademija.Projekat.dto.EmailObject;

public interface EmailService {
	
	public void sendTemplateMessage(EmailObject object) throws Exception;
	
}
