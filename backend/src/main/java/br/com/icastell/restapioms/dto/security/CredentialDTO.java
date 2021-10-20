package br.com.icastell.restapioms.dto.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CredentialDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//username
	
	private String email;
	private String password;
	
	

}
