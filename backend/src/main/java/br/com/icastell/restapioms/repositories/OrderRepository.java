package br.com.icastell.restapioms.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.order.Order;

@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {	
	
	@Transactional(readOnly=true)
	Page<Order> findByCustomer(Customer customer, Pageable pageRequest);
	
}
