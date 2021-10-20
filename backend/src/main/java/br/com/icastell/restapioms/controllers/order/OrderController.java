package br.com.icastell.restapioms.controllers.order;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.services.order.OrderService;

@RestController
@RequestMapping(value="/orders")
public class OrderController {
	
	@Autowired
	private OrderService service;	 

	@RequestMapping(value="/{id}", method = RequestMethod.GET)	
	public ResponseEntity<Order> findById(@PathVariable Integer id) {
		Order object = service.find(id);
		return ResponseEntity.ok().body(object);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody Order obj) {
		obj = service.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Order>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instant") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Order> list = service.findByPageNumber(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}

}
