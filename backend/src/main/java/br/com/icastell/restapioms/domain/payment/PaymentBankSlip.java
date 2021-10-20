package br.com.icastell.restapioms.domain.payment;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.domain.payment.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@JsonTypeName("paymentBankSlip")
public class PaymentBankSlip extends Payment {

	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dueDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date payDate;
	
		
	public PaymentBankSlip(Integer id, PaymentType paymentState, Order order, Date dueDate, Date payDate) {
		super(id, paymentState, order);
		this.dueDate = dueDate;
		this.payDate = payDate;
	}

	public PaymentBankSlip(Integer id, PaymentType paymentState, Order order) {
		super(id, paymentState, order);
	
	}	

}
