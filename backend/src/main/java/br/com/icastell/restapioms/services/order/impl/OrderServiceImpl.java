package br.com.icastell.restapioms.services.order.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.customer.enums.ProfileType;
import br.com.icastell.restapioms.domain.order.ItemOrder;
import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.domain.payment.PaymentBankSlip;
import br.com.icastell.restapioms.domain.payment.enums.PaymentType;
import br.com.icastell.restapioms.repositories.ItemOrderRepository;
import br.com.icastell.restapioms.repositories.OrderRepository;
import br.com.icastell.restapioms.repositories.PaymentRepository;
import br.com.icastell.restapioms.security.UserSS;
import br.com.icastell.restapioms.services.customer.impl.CustomerServiceImpl;
import br.com.icastell.restapioms.services.email.EmailService;
import br.com.icastell.restapioms.services.exceptions.AuthorizationException;
import br.com.icastell.restapioms.services.exceptions.ObjectNotFoundException;
import br.com.icastell.restapioms.services.order.OrderService;
import br.com.icastell.restapioms.services.order.util.Util;
import br.com.icastell.restapioms.services.product.impl.ProductServiceImpl;
import br.com.icastell.restapioms.services.security.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductServiceImpl productService;	
	
	@Autowired
	private ItemOrderRepository itemOrderRepository;
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private EmailService emailService;

	public Order find(Integer id) {
		
		Optional<Order> obj = repository.findById(id);		
		UserSS user = UserService.authenticated();
		Customer c =  customerService.findById(user.getId());
	
		if (user == null || (!user.hasRole(ProfileType.ADM) && !user.getId().equals(c.getId()))) {
			throw new AuthorizationException("Access denied");
		}
		
		if (!obj.isPresent())
			new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Order.class.getName());
		
		return obj.get();
		
	}	

	@Override
	@Transactional
	public Order save(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setCustomer(customerService.findById(obj.getCustomer().getId()));
		obj.getPayment().setPaymentType(PaymentType.PENDING);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentBankSlip) {
			PaymentBankSlip paymentBankSlip = (PaymentBankSlip) obj.getPayment();
			
			//date Payment will be 7 days after date Order (obj.getInstant())
			//replace by payment slip 'Webservice' that will calculate the due date
			paymentBankSlip.setPayDate(Util.addDaysToDate(obj.getInstant(), 7));
		}		
		obj = repository.save(obj);
		paymentRepository.save(obj.getPayment());
		for (ItemOrder io : obj.getItems()) {
			io.setDiscount(0.0);
			io.setProduct(productService.find(io.getProduct().getId()));		  
			io.setPrice(io.getProduct().getUnitPrice());
			io.setOrder(obj);
		}
		itemOrderRepository.saveAll(obj.getItems());
		//emailService.sendOrderConfirmationEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	@Override
	public Page<Order> findByPageNumber(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();		
		if (user != null) {			
			Customer c =  customerService.findById(user.getId());			
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
			return repository.findByCustomer(c, pageRequest);			
		}
		return null;	
	}

}
