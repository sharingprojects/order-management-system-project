package br.com.icastell.restapioms.controllers.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.icastell.restapioms.controllers.utils.URL;
import br.com.icastell.restapioms.domain.product.Product;
import br.com.icastell.restapioms.dto.model.product.ProductDTO;
import br.com.icastell.restapioms.services.product.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	private ProductService service;

	@RequestMapping(value="/{id}", method = RequestMethod.GET)	
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		Product object = service.find(id);
		return ResponseEntity.ok().body(object);		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProductDTO>> findByPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories, 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {		
		String nameDecoder = URL.decodeParam(name);
		List<Integer> ids = URL.decodeIntList(categories);
		Page<Product> list = service.search(nameDecoder, ids, page, linesPerPage, orderBy, direction);
		Page<ProductDTO> listDTO = list.map(obj -> new ProductDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

}
