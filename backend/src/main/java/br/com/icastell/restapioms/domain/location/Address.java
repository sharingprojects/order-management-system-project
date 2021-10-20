package br.com.icastell.restapioms.domain.location;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.icastell.restapioms.domain.customer.Customer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@EqualsAndHashCode.Exclude 
	private String street;
	
	@EqualsAndHashCode.Exclude 
	private String number;
	
	@EqualsAndHashCode.Exclude 
	private String complement;
	
	@EqualsAndHashCode.Exclude 
	private String distric;
	
	@EqualsAndHashCode.Exclude 
	private String postalCode;

	@EqualsAndHashCode.Exclude 
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;

	@EqualsAndHashCode.Exclude 
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;

	public Address(Integer id, String street, String number, String complement, String distric, String postalCode,
			Customer customer, City city) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.distric = distric;
		this.postalCode = postalCode;
		this.customer = customer;
		this.city = city;
	}	

}
