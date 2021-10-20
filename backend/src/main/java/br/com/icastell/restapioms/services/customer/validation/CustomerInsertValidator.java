package br.com.icastell.restapioms.services.customer.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.icastell.restapioms.controllers.exception.FieldMessage;
import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.customer.enums.CustomerType;
import br.com.icastell.restapioms.dto.model.customer.NewCustomerDTO;
import br.com.icastell.restapioms.repositories.CustomerRepository;
import br.com.icastell.restapioms.services.customer.validation.utils.BR;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, NewCustomerDTO> {
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public void initialize(CustomerInsert name) {
	}

	@Override
	public boolean isValid(NewCustomerDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getCustomerTypeId() == null)
			list.add(new FieldMessage("CustomerType", "CustomerType id cannot be null"));

		if (objDto.getCustomerTypeId().equals(CustomerType.PESSOAFISICA.getCod())
				&& !BR.isValidCPF(objDto.getCpfOrCnpj()))
			list.add(new FieldMessage("cpfOrCnpj", "Invalid CPF!"));
		
		if (objDto.getCustomerTypeId().equals(CustomerType.PESSOAJURIDICA.getCod())
				&& !BR.isValidCNPJ(objDto.getCpfOrCnpj()))
			list.add(new FieldMessage("cpfOrCnpj", "Invalid CNPJ!"));
		
		Customer customer = repository.findByEmail(objDto.getEmail());
		
		if (customer != null)list.add(new FieldMessage("email", "Email already exists!"));
			
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
