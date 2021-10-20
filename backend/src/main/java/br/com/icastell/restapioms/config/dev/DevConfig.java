package br.com.icastell.restapioms.config.dev;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.icastell.restapioms.services.db.DBService;
import br.com.icastell.restapioms.services.email.EmailService;
import br.com.icastell.restapioms.services.email.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateTestDatabase() throws ParseException {

		if (!"create".equals(strategy)) {
			return false;
		}		
		
		dbService.instantiateDatabase();		
		return true;		
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
