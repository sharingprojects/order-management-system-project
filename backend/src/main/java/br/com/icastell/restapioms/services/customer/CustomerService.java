package br.com.icastell.restapioms.services.customer;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.dto.model.customer.NewCustomerDTO;

public interface CustomerService {
	
	Customer findById(Integer id);

	Customer update(Customer obj);

	void deleteById(Integer id);

	List<Customer> findAll();

	Page<Customer> findByPageNumber(
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String direction);
	
	Customer save(NewCustomerDTO dto);
	
	URI uploadImage(MultipartFile multipartFile);


	



}
