package br.com.icastell.restapioms.services.order;

import org.springframework.data.domain.Page;

import br.com.icastell.restapioms.domain.order.Order;

public interface OrderService {

	Order find(Integer id);
	
	Order save(Order obj);	

	Page<Order> findByPageNumber(Integer page, Integer linesPerPage, String orderBy, String direction);


}
