package br.com.icastell.restapioms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.icastell.restapioms.domain.location.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
