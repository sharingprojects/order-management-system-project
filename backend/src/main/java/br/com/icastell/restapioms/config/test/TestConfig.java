package br.com.icastell.restapioms.config.test;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.icastell.restapioms.services.db.DBService;
import br.com.icastell.restapioms.services.email.EmailService;
import br.com.icastell.restapioms.services.email.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateTestDatabase() throws ParseException {
		dbService.instantiateDatabase();
		return true;		
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
