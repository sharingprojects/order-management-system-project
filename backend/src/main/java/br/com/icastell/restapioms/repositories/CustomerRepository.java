package br.com.icastell.restapioms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.icastell.restapioms.domain.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Transactional(readOnly = true)
	Customer findByEmail(String email);

}
