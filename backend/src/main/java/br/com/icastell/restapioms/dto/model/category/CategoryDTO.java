package br.com.icastell.restapioms.dto.model.category;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import br.com.icastell.restapioms.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 5, max = 80, message = "Name must be between 5 and 80 characters")
	private String name;

	public CategoryDTO(Category obj) {
		this.id = obj.getId();
		this.name = obj.getName();
	}	

	public Category convertDtoToEntity() {		
		return new ModelMapper().map(this, Category.class);
	}


}
