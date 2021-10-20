package br.com.icastell.restapioms.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.order.Order;

public interface EmailService {
	
	//send email with plain text
	void sendOrderConfirmationEmail(Order order);
	
	void sendEmail(SimpleMailMessage msg);
	
	//send email with html	
	void sendOrderConfirmationHtmlEmail(Order order);	
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Customer customer, String newPass);


}
