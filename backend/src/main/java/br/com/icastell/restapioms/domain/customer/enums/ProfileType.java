package br.com.icastell.restapioms.domain.customer.enums;

import lombok.Getter;

public enum ProfileType {
	
	ADM(1, "ROLE_ADMIN"),
	CUSTOMER(2, "ROLE_CUSTOMER");
	
	@Getter
	private int cod;
	
	@Getter
	private String description;
	

	private ProfileType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}	
	
	public static ProfileType toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (ProfileType x : ProfileType.values()) {
			if (id.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}

}
