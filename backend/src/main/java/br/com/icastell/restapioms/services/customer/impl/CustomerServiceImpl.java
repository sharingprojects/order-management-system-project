package br.com.icastell.restapioms.services.customer.impl;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.customer.enums.CustomerType;
import br.com.icastell.restapioms.domain.customer.enums.ProfileType;
import br.com.icastell.restapioms.domain.location.Address;
import br.com.icastell.restapioms.domain.location.City;
import br.com.icastell.restapioms.dto.model.customer.NewCustomerDTO;
import br.com.icastell.restapioms.repositories.AddressRepository;
import br.com.icastell.restapioms.repositories.CustomerRepository;
import br.com.icastell.restapioms.security.UserSS;
import br.com.icastell.restapioms.services.aws.S3Service;
import br.com.icastell.restapioms.services.customer.CustomerService;
import br.com.icastell.restapioms.services.exceptions.AuthorizationException;
import br.com.icastell.restapioms.services.exceptions.DataIntegrityException;
import br.com.icastell.restapioms.services.exceptions.ObjectNotFoundException;
import br.com.icastell.restapioms.services.security.UserService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;

	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private AddressRepository addressRepos;
	

	public Customer findById(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(ProfileType.ADM) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied");
		}	
		
		Optional<Customer> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Customer.class.getName()));
	}	
	
	@Override
	@Transactional
	public Customer save(NewCustomerDTO dto) {
		Customer c = convertNewCustomerDtoToEntity(dto);	
		c = repository.save(c);
		addressRepos.saveAll(c.getAddresses());
		return c;
	}

	@Override
	public Customer update(Customer obj) {
		Customer newObj = findById(obj.getId());
		updateSave(newObj, obj);		
		return repository.save(newObj);
	}

	@Override
	public void deleteById(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You cannot delete a customer because it has related orders.");
		}
		
	}

	@Override
	public List<Customer> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Customer> findByPageNumber(
			Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(
				page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}	
	
	@Override
	public URI uploadImage(MultipartFile multipartFile) {		
		return s3Service.upLoadFile(multipartFile);		
	}	

	private void updateSave(Customer newObj, Customer obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());		
	}
	
	private Customer convertNewCustomerDtoToEntity(NewCustomerDTO dto) {
		Customer cust = new Customer(null, dto.getName(), dto.getEmail(), dto.getCpfOrCnpj(),
				CustomerType.toEnum(dto.getCustomerTypeId()), pe.encode(dto.getPassword()));

		City city = new City(dto.getCityId(), null, null);

		Address address = new Address(null, dto.getStreet(), dto.getNumber(), dto.getComplement(), dto.getDistric(),
				dto.getPostalCode(), cust, city);

		cust.getAddresses().add(address);
		cust.getPhones().add(dto.getTelephone1());
		if (dto.getTelephone2() != null)
			cust.getPhones().add(dto.getTelephone2());
		if (dto.getTelephone3() != null)
			cust.getPhones().add(dto.getTelephone3());

		return cust;
	}



}
