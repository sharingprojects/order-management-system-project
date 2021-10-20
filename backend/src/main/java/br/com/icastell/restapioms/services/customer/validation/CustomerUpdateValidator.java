package br.com.icastell.restapioms.services.customer.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.icastell.restapioms.controllers.exception.FieldMessage;
import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.dto.model.customer.CustomerDTO;
import br.com.icastell.restapioms.repositories.CustomerRepository;

public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public void initialize(CustomerUpdate name) {
	}

	@Override
	public boolean isValid(CustomerDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer uriId = Integer.parseInt(map.get("id"));		
		
		List<FieldMessage> list = new ArrayList<>();

		Customer customer = repository.findByEmail(objDto.getEmail());
		
		if (customer != null && !customer.getId().equals(uriId))
			list.add(new FieldMessage("email", "Email already exists!"));
			
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}


}
