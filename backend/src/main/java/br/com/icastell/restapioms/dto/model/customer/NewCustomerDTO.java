	package br.com.icastell.restapioms.dto.model.customer;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.icastell.restapioms.services.customer.validation.CustomerInsert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@CustomerInsert
public class NewCustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "This field cannot be empty")
	@Size(min = 5, max = 120, message = "Name must be between 5 and 120 characters")
	private String name;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Invalid email")
	private String email;
	
	@NotEmpty(message = "This field cannot be empty")
	private String cpfOrCnpj;
	
	private Integer customerTypeId;	
	
	@NotEmpty(message = "This field cannot be empty")
	private String password;
	
	@NotEmpty(message = "This field cannot be empty")
	private String street;	
	
	@NotEmpty(message = "This field cannot be empty")
	private String number;	
	
	private String complement;	

	private String distric;	
	
	@NotEmpty(message = "This field cannot be empty")
	private String postalCode;
	
	@NotEmpty(message = "This field cannot be empty")
	private String telephone1;
	private String telephone2;
	private String telephone3;
	
	private Integer cityId;

}
