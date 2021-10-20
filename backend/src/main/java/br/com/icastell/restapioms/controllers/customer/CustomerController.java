package br.com.icastell.restapioms.controllers.customer;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.dto.model.customer.CustomerDTO;
import br.com.icastell.restapioms.dto.model.customer.NewCustomerDTO;
import br.com.icastell.restapioms.services.customer.CustomerService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;	 

	@ApiOperation(value = "Only user with ADMIN role and own user can findById")	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)	
	public ResponseEntity<Customer> findById(@PathVariable Integer id) {
		Customer object = service.findById(id);		
		return ResponseEntity.ok().body(object);		
	}
	
	@ApiOperation(value = "all people can be Customer or user")		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody NewCustomerDTO dto) {
		Customer obj = service.save(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Only user with ADMIN role and own user can update")			
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CustomerDTO dto, @PathVariable Integer id) {	
		Customer obj = dto.convertDtoToEntity();
		obj.setId(id);
		obj = service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Only Role: ADMIN can delete Customer")	
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Only Role: ADMIN can findAll Customer")	
	@PreAuthorize("hasAnyRole('ADMIN')")	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDTO>> findAll() {
		List<Customer> list = service.findAll();
		List<CustomerDTO> listDTO = list.stream().map(obj -> new CustomerDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value = "Only Role: ADMIN can find by page")	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CustomerDTO>> findByPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {		
		Page<Customer> list = service.findByPageNumber(page, linesPerPage, orderBy, direction);
		Page<CustomerDTO> listDTO = list.map(obj -> new CustomerDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value = "any customer or user can send file to S3 - AWS")
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfileImage(@RequestParam(name = "file") MultipartFile file) {
		URI uri = service.uploadImage(file);
		return ResponseEntity.created(uri).build();
	}

}
