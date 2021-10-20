package br.com.icastell.restapioms.domain.order;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.icastell.restapioms.domain.product.Product;
import br.com.icastell.restapioms.services.order.util.Util;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(exclude = {"discount", "quantity", "price"})
@NoArgsConstructor
@Getter @Setter
@Entity
public class ItemOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private ItemOrderPK id = new ItemOrderPK();

	private Double discount;
	private Integer quantity;
	private Double price;

	public ItemOrder(Order order, Product product, Double discount, Integer quantity, Double price) {
		super();
		id.setOrder(order);
		id.setProduct(product);
		this.discount = discount;
		this.quantity = quantity;
		this.price = price;
	}
	
	public double getSubtotal() {
		return (price - discount) * quantity;
	}

	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}

	public Product getProduct() {
		return id.getProduct();
	}
	
	public void setProduct(Product product) {
		id.setProduct(product);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getProduct().getName());
		sb.append(", Qty: ");
		sb.append(getQuantity());
		sb.append(", Unit price: ");
		sb.append(Util.findCurrencyInstance().format(getPrice()));
		sb.append(", Subtotal: ");
		sb.append(Util.findCurrencyInstance().format(getSubtotal()));
		sb.append("\n");	
		return sb.toString();
	}
	
	

}
