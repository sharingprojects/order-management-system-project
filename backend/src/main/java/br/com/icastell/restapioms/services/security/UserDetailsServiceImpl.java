package br.com.icastell.restapioms.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.repositories.CustomerRepository;
import br.com.icastell.restapioms.security.UserSS;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer c = repository.findByEmail(email);
		if (c == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(c.getId(), c.getEmail(), c.getPassword(), c.getProfiles());
	}

}
