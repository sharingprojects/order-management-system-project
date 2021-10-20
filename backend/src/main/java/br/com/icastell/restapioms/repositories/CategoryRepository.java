package br.com.icastell.restapioms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.icastell.restapioms.domain.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	

}
