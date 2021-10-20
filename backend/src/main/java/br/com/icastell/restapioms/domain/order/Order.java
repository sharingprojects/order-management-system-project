package br.com.icastell.restapioms.domain.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.icastell.restapioms.domain.customer.Customer;
import br.com.icastell.restapioms.domain.location.Address;
import br.com.icastell.restapioms.domain.payment.Payment;
import br.com.icastell.restapioms.services.order.util.Util;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@Getter @Setter
@Entity(name="tb_order")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;		

	@EqualsAndHashCode.Exclude
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date instant;
	
	@EqualsAndHashCode.Exclude
	@OneToOne(cascade=CascadeType.ALL, mappedBy="order")	
	private Payment payment;
	
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name="delivery_address_id")
	private Address deliveryAddress;
	
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "id.order")
	private Set<ItemOrder> items = new HashSet<>();

	public Order(Integer id, Date instant, Customer customer, Address deliveryAddress) {
		super();
	    this.id = id;
		this.instant = instant;
		this.customer = customer;
		this.deliveryAddress = deliveryAddress;
	}
	
	public double getValueTotal() {
		double valueTotal = 0.0;		
		for (ItemOrder i : items)
			valueTotal = valueTotal + i.getSubtotal();
		return valueTotal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order number: ");
		sb.append(getId());
		sb.append(", Order data: ");
		sb.append(Util.convertDateToString(getInstant()));
		sb.append(", Customer: ");
		sb.append(getCustomer().getName());
		sb.append(", Payment type: ");
		sb.append(getPayment().getPaymentType().getDescription());
		sb.append("\nDetails: \n");		
		for (ItemOrder i : getItems())sb.append(" "+ i.toString());
		sb.append("Total:");	
		sb.append(Util.findCurrencyInstance().format(getValueTotal()));
	
		return sb.toString();
	}
	
	
	
	
	

	
}
