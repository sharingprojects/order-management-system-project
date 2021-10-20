package br.com.icastell.restapioms.dto.model.customer;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.services.customer.validation.CustomerUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@CustomerUpdate
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 5, max = 120, message = "Name must be between 5 and 120 characters")
	private String name;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Invalid email")
	private String email;

	public CustomerDTO(Customer obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
	}

	public Customer convertDtoToEntity() {
		return new ModelMapper().map(this, Customer.class);
	}

}
