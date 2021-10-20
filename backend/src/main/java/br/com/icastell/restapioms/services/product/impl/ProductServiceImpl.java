package br.com.icastell.restapioms.services.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.icastell.restapioms.domain.category.Category;
import br.com.icastell.restapioms.domain.product.Product;
import br.com.icastell.restapioms.repositories.CategoryRepository;
import br.com.icastell.restapioms.repositories.ProductRepository;
import br.com.icastell.restapioms.services.exceptions.ObjectNotFoundException;
import br.com.icastell.restapioms.services.product.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Product find(Integer id) {
		Optional<Product> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Product.class.getName()));

	}

	@Override
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return repository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}


}
