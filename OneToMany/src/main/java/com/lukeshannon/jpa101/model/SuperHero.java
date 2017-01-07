package com.lukeshannon.jpa101.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="super_hero")
public class SuperHero implements Serializable {
	

	private static final long serialVersionUID = 7422437606063914922L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="hero_id")
    private Long id;
	
	@Column(unique=true)
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.MERGE},orphanRemoval=true)
	@JoinTable
	(
	  name="hero_alias",
	 joinColumns={ @JoinColumn(name="hero_id", referencedColumnName="hero_id") },
	  inverseJoinColumns={ @JoinColumn(name="alias_id", referencedColumnName="alias_id") }
	)
	//@OneToMany(targetEntity=SuperAlias.class,mappedBy="superHero",orphanRemoval=true, fetch=FetchType.EAGER,cascade = {CascadeType.MERGE})
	private List<SuperAlias> aliases;
	
	public SuperHero() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SuperAlias> getAliases() {
		return aliases;
	}

	public void setAliases(List<SuperAlias> aliases) {
		this.aliases = aliases;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "SuperHero [id=" + id + ", name=" + name + ", aliases=" + aliases + "]";
	}
	

}
