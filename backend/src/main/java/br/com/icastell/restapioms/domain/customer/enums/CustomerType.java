package br.com.icastell.restapioms.domain.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CustomerType {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	@Getter
	private int cod;
	
	@Getter
	private String descricao;
	
	public static CustomerType toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (CustomerType x : CustomerType.values()) {
			if (id.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}

}
