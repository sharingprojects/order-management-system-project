package br.com.icastell.restapioms.domain.location;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	

	@EqualsAndHashCode.Exclude 
	private String name;

	@EqualsAndHashCode.Exclude 
	@ManyToOne
	@JoinColumn(name="state_id")
	private State state;
}
