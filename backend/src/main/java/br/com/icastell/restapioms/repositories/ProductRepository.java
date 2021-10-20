package br.com.icastell.restapioms.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icastell.restapioms.domain.category.Category;
import br.com.icastell.restapioms.domain.product.Product;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Integer> {	
	
	@Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% AND cat IN :categories")
	Page<Product> findDistinctByNameContainingAndCategoriesIn(@Param("name") String name, @Param("categories") List<Category> categories, Pageable pageRequest);	

	/*//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories
	 * Page<Product> findDistinctByNameContainingAndCategoriesIn(String name,
	 * List<Category> categories, Pageable pageRequest);
	 */
}
