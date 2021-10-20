package br.com.icastell.restapioms.services.security;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.icastell.restapioms.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			//return logged in user
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
		    return null;
		}		
	}

}
