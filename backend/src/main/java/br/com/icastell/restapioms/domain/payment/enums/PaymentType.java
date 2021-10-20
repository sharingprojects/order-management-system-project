package br.com.icastell.restapioms.domain.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PaymentType {
	
	PENDING(1, "Pendente"),
	CONFIRMED(2, "Quitado"),
	CANCELLED(3, "Cancelo");
	
	@Getter
	private int cod;
	
	@Getter
	private String description;
	
	public static PaymentType toEnum(Integer id) {
		if (id == null) {
			return null;
		}
		for (PaymentType x : PaymentType.values()) {
			if (id.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido " + id);
	}
}
