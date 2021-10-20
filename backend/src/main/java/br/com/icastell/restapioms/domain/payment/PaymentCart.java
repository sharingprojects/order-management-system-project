package br.com.icastell.restapioms.domain.payment;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.domain.payment.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@JsonTypeName("paymentCart")
public class PaymentCart extends Payment {

	private static final long serialVersionUID = 1L;
	
	private Integer installmentNumbers;

	public PaymentCart(Integer id, PaymentType paymentState, Order order, Integer installmentNumbers) {
		super(id, paymentState, order);
		this.installmentNumbers = installmentNumbers;
	}
	
	
	
	
	

	

	

}
