package br.com.icastell.restapioms.dto.model.product;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.com.icastell.restapioms.domain.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;	
	
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 5, max = 80, message = "Name must be between 5 and 80 characters")
	private String name;
	
	private Double unitPrice;
	
	public ProductDTO(Product obj) {
		id = obj.getId();
		name = obj.getName();
		unitPrice = obj.getUnitPrice();
	}

}
