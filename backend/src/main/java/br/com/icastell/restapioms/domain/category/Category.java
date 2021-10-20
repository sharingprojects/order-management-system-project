package br.com.icastell.restapioms.domain.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.modelmapper.ModelMapper;

import br.com.icastell.restapioms.domain.product.Product;
import br.com.icastell.restapioms.dto.model.category.CategoryDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@Getter @Setter
@Entity(name="category")
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@EqualsAndHashCode.Exclude 
	private String name;

	@EqualsAndHashCode.Exclude 
	@ManyToMany(mappedBy="categories")
	List<Product> products = new ArrayList<>();

	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO convertEntityToDto() {
		return new ModelMapper().map(this, CategoryDTO.class);
	}
}
