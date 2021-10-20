package br.com.icastell.restapioms.services.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.order.Order;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired(required = false)
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Order order) {		
		SimpleMailMessage sm = prepareSimpleMessageFromOrder(order);
		sendEmail(sm);		
	}

	protected SimpleMailMessage prepareSimpleMessageFromOrder(Order order) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(order.getCustomer().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Confirmed Order! Id: " + order.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(order.toString());
		return sm;
	}
	
	protected String htmlFromTemplateOrder(Order orderObj) {
		//send the order object to html template
		Context context = new Context();
		
		//this define that the template will go use the orderObj with nickname "order"
		context.setVariable("order", orderObj);
		
		//to process the template
		return templateEngine.process("email/orderConfirmation", context);		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Order order) {
		
		try {
			MimeMessage mm = prepareMimeMessageFromOrder(order);
			sendHtmlEmail(mm);
		}
		catch (MessagingException e) {
			sendOrderConfirmationEmail(order);
		}
	
	}

	//converter an Order type object to MimeMessage type
	protected MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
		//We create JavaMailSender instance  and now we can create the MimeMessage type
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		//then, we can add values in the mimeMessage but we have to create MimeMessageHelper instance
		//multipart = true
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(order.getCustomer().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Confirmed Order! Id: " + order.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		
		//the second parameter of type boolean indicates if the content is html
		mmh.setText(htmlFromTemplateOrder(order), true);
		
		return mimeMessage;
	}
	
	@Override	
	public void sendNewPasswordEmail(Customer customer, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(customer, newPass);
		sendEmail(sm);			
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Customer customer, String newPass) {		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(customer.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitation for a new password");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("New password: "+newPass);
		return sm;
	}


}
