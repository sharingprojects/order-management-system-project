package br.com.icastell.restapioms.domain.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@Entity
public class State implements Serializable {
	
	private static final long serialVersionUID = 1L;	
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@EqualsAndHashCode.Exclude
	private String name;

	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToMany(mappedBy="state")
	private List<City> cities = new ArrayList<>();

	public State(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}		
}
