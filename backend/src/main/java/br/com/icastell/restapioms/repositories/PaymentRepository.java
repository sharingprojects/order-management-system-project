package br.com.icastell.restapioms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.icastell.restapioms.domain.payment.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	

}
