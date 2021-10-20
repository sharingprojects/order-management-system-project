package br.com.icastell.restapioms.services.category;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.icastell.restapioms.domain.category.Category;

public interface CategoryService {

	Category findById(Integer id);

	Category save(Category obj);

	Category update(Category obj);

	void deleteById(Integer id);

	List<Category> findAll();

	Page<Category> findByPageNumber(
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String direction);
	

}
