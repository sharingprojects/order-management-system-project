package br.com.icastell.restapioms.services.security;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.repositories.CustomerRepository;
import br.com.icastell.restapioms.services.email.EmailService;
import br.com.icastell.restapioms.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;	
	
	public void sendNewPassword(String email) {
		Customer c = customerRepository.findByEmail(email);
		
		if (c == null) {
			throw new ObjectNotFoundException("Email not found!");
		}
		
		String newPass = generatePassayPassword();
		c.setPassword(pe.encode(newPass));
		
		customerRepository.save(c);
		
		emailService.sendNewPasswordEmail(c, newPass);
	}

	private String generatePassayPassword() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}


}
