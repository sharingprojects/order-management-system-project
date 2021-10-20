package br.com.icastell.restapioms.domain.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.icastell.restapioms.domain.customer.enums.CustomerType;
import br.com.icastell.restapioms.domain.customer.enums.ProfileType;
import br.com.icastell.restapioms.domain.location.Address;
import br.com.icastell.restapioms.domain.order.Order;
import br.com.icastell.restapioms.dto.model.customer.CustomerDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Getter @Setter
	@EqualsAndHashCode.Exclude
	private String name;

	@Column(unique = true)
	@Getter @Setter
	@EqualsAndHashCode.Exclude
	private String email;

	@Getter @Setter
	@EqualsAndHashCode.Exclude
	private String cpfOrCnpj;

	@EqualsAndHashCode.Exclude 
	private Integer customerType;	
	
	@Getter @Setter
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	private String password;	

	@EqualsAndHashCode.Exclude 
	@Getter @Setter
	@OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();	

	@EqualsAndHashCode.Exclude 
	@Getter @Setter
	@ElementCollection
	@CollectionTable(name="TELEPHONE")
	private Set<String> phones = new HashSet<>();
	
	@EqualsAndHashCode.Exclude 
	@CollectionTable(name="PROFILE")
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Integer> profiles = new HashSet<>();
	
	@EqualsAndHashCode.Exclude 
	@Getter @Setter
	@JsonIgnore
	@OneToMany(mappedBy="customer" )
	private List<Order> orders = new ArrayList<>();		
	
	public Customer() {
		addProfile(ProfileType.CUSTOMER);
	}

	public Customer(Integer id, String name, String email, String cpfOrCnpj, CustomerType type, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOrCnpj = cpfOrCnpj;
		this.customerType = type.getCod();
		this.password = password;
		addProfile(ProfileType.CUSTOMER);
	}
	
	public CustomerType getCustomerType() {
		return CustomerType.toEnum(customerType);
	}
	
	public void setCustomerType(CustomerType type){
		this.customerType = type.getCod();		
	}
	
	public CustomerDTO convertEntityToDto() {
		return new ModelMapper().map(this, CustomerDTO.class);
	}
	
	public Set<ProfileType> getProfiles() {
		return profiles.stream().map(p -> ProfileType.toEnum(p)).collect(Collectors.toSet());
	}

	public void addProfile(ProfileType profile) {
		profiles.add(profile.getCod());
	}	

}
