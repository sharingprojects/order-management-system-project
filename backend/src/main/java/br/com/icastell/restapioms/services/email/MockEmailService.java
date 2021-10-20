package br.com.icastell.restapioms.services.email;


import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);			
			
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("This is a simulation of email send...");
		LOG.info(msg.toString());
		LOG.info("Email successfully sent!");		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("This is a simulation of email send HTML...");
		LOG.info(msg.toString());
		LOG.info("Email successfully sent!");	
		
	}
}
