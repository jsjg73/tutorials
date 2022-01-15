package com.jsjg73.hibernate.multiplicity.onetomany;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private long id;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST)
	private Set<Items> items=new HashSet<>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Items> getItems() {
		return items;
	}

	public void setItems(Set<Items> items) {
		this.items = items;
	}
	
	public void add(Items item) {
		items.add(item);
	}
	public int size() {
		return items.size();
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", items=" + items + "]";
	}
	
}
