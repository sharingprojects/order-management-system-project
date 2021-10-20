package br.com.icastell.restapioms.domain.payment;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.domain.payment.enums.PaymentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@Getter @Setter
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Payment implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;	

	@EqualsAndHashCode.Exclude
    private Integer paymentType;
	
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToOne @MapsId
	@JoinColumn(name="order_id")	
	private Order order;

	public Payment(Integer id, PaymentType paymentType, Order order) {
		super();
		this.id = id;
		this.paymentType = paymentType.getCod();
		this.order = order;
	}
	
	public PaymentType getPaymentType() {
		return PaymentType.toEnum(paymentType);
	}
	
	public void setPaymentType(PaymentType type){
		this.paymentType = type.getCod();		
	}
	
}
