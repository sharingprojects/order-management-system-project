package br.com.icastell.restapioms.services.category.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.icastell.restapioms.domain.category.Category;
import br.com.icastell.restapioms.repositories.CategoryRepository;
import br.com.icastell.restapioms.services.category.CategoryService;
import br.com.icastell.restapioms.services.exceptions.DataIntegrityException;
import br.com.icastell.restapioms.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Override
	public Category findById(Integer id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Category.class.getName()));
	}

	@Override
	public Category save(Category obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	@Override
	public Category update(Category obj) {
		Category newObj = findById(obj.getId());
		updateSave(newObj, obj);
		return repository.save(newObj);
	}

	@Override
	public void deleteById(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You cannot delete a category that has product.");
		}
	}

	@Override
	public List<Category> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Category> findByPageNumber(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	private void updateSave(Category newObj, Category obj) {
		newObj.setName(obj.getName());		
	}


}
