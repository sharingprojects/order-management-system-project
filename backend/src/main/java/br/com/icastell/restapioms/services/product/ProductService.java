package br.com.icastell.restapioms.services.product;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.icastell.restapioms.domain.product.Product;

public interface ProductService {

	Product find(Integer id);
	
	Page<Product> search(String name, 
			             List<Integer> ids, 
			             Integer page, 
			             Integer linesPerPage, 
			             String orderBy, 
			             String direction);
}
