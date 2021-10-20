package br.com.icastell.restapioms.domain.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.icastell.restapioms.domain.category.Category;
import br.com.icastell.restapioms.domain.order.ItemOrder;
import br.com.icastell.restapioms.domain.order.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@Getter @Setter
@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@EqualsAndHashCode.Exclude
	private String name;
	
	@EqualsAndHashCode.Exclude
	private Double unitPrice;

	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "PRODUCT_CATEGORY", 
	           joinColumns = @JoinColumn(name = "product_id"), 
	           inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories = new ArrayList<>();

	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "id.product")
	private Set<ItemOrder> items = new HashSet<>();

	public Product(Integer id, String name, Double unitPrice) {
		this.id = id;
		this.name = name;
		this.unitPrice = unitPrice;
	}

	@JsonIgnore
	public List<Order> getOrders() {
		List<Order> list = new ArrayList<>();
		items.stream().forEach(i -> list.add(i.getOrder()));
		return list;
	}
		
}
