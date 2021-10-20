package br.com.icastell.restapioms.services.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.icastell.restapioms.domain.category.Category;
import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.customer.enums.CustomerType;
import br.com.icastell.restapioms.domain.customer.enums.ProfileType;
import br.com.icastell.restapioms.domain.location.Address;
import br.com.icastell.restapioms.domain.location.City;
import br.com.icastell.restapioms.domain.location.State;
import br.com.icastell.restapioms.domain.order.ItemOrder;
import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.domain.payment.Payment;
import br.com.icastell.restapioms.domain.payment.PaymentBankSlip;
import br.com.icastell.restapioms.domain.payment.PaymentCart;
import br.com.icastell.restapioms.domain.payment.enums.PaymentType;
import br.com.icastell.restapioms.domain.product.Product;
import br.com.icastell.restapioms.repositories.AddressRepository;
import br.com.icastell.restapioms.repositories.CategoryRepository;
import br.com.icastell.restapioms.repositories.CityRepository;
import br.com.icastell.restapioms.repositories.CustomerRepository;
import br.com.icastell.restapioms.repositories.ItemOrderRepository;
import br.com.icastell.restapioms.repositories.OrderRepository;
import br.com.icastell.restapioms.repositories.PaymentRepository;
import br.com.icastell.restapioms.repositories.ProductRepository;
import br.com.icastell.restapioms.repositories.StateRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoryRepository categoryRepository;	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired	
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ItemOrderRepository itemOrderRepository;
	
	public void instantiateDatabase() throws ParseException  {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");
			
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de Escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajur", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));		
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));	
		
		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));			
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));		
	
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));		
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));			
		
		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");
		City city1 = new City(null, "Uberlândia", state1);
		City city2 = new City(null, "São Paulo", state2);
		City city3 = new City(null, "Campinas", state2);		
		
		state1.getCities().addAll(Arrays.asList(city1));	
		state2.getCities().addAll(Arrays.asList(city2, city3));	
		
		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));		
		
		Customer cust1 = new Customer(null, "Maria Silva", "infocastell@gmail.com", "36378912377", CustomerType.PESSOAFISICA, pe.encode("123"));
		cust1.getPhones().addAll(Arrays.asList("27363323", "93838393"));
		
		Customer cust2 = new Customer(null, "Ana das Flores", "testhomolproddev@gmail.com", "92996758056", CustomerType.PESSOAFISICA, pe.encode("123"));
		cust2.addProfile(ProfileType.ADM);
		cust2.getPhones().addAll(Arrays.asList("9523456665", "56667788998"));
		
		Address address1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cust1, city1);
		Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cust1, city2);
		Address address3 = new Address(null, "Avenida Floriano", "2106", null, "Centro", "56777012", cust2, city2);
		
		cust1.getAddresses().addAll(Arrays.asList(address1, address2));
		cust2.getAddresses().addAll(Arrays.asList(address3));
		
		customerRepository.saveAll(Arrays.asList(cust1, cust2));
			
		addressRepository.saveAll(Arrays.asList(address1, address2, address3));
		
		
		//Order
		Order order1 = new Order(null, parseDate("30/09/2017 10:32"), cust1, address1);
		Order order2 = new Order(null, parseDate("10/10/2017 19:35"), cust1, address2);
		
		Payment pay1 = new PaymentCart(null, PaymentType.CONFIRMED, order1, 6);
		order1.setPayment(pay1);
		
		Payment pay2 = new PaymentBankSlip(null, PaymentType.PENDING, order2, parseDate("20/10/2017 00:00"), null);
		order1.setPayment(pay2);
		
		cust1.getOrders().addAll(Arrays.asList(order1, order2));		
		
		//salve order
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
		orderRepository.saveAll(Arrays.asList(order1, order2));
	
		ItemOrder io1 = new ItemOrder(order1, p1, 0.00, 1, 2000.00);
		ItemOrder io2 = new ItemOrder(order1, p3, 0.00, 2, 80.00);
		ItemOrder io3 = new ItemOrder(order2, p2, 100.00, 1, 800.00);
	
		order1.getItems().addAll(Arrays.asList(io1, io2));
		order2.getItems().addAll(Arrays.asList(io3));
		
		p1.getItems().addAll(Arrays.asList(io1));
		p2.getItems().addAll(Arrays.asList(io3));
		p3.getItems().addAll(Arrays.asList(io2));
		
		itemOrderRepository.saveAll(Arrays.asList(io1, io2, io3));		
		
	}

	private Date parseDate(String sDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return sdf.parse(sDate);
	}
	

}
