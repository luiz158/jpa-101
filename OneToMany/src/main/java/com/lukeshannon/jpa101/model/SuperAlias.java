/**
 * 
 */
package com.lukeshannon.jpa101.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author luke
 *
 */
@Entity
@Table(name="super_alias")
public class SuperAlias implements Serializable {

	private static final long serialVersionUID = -8348050022738586007L;
	
	//@ManyToOne
	//@JoinColumn(name="hero_id")
	//private SuperHero superHero;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="alias_id")
    private Long id;
	
	@Column(name="alias_name")
	private String aliasName;
	
	@Column(name="time_started_using")
	private Date timeStartedUsing;
	
	@Column(name="time_stopped_using")
	private Date timeStoppedUsing;
	
	protected SuperAlias() { }
	
	public SuperAlias(String aliasName, Date timeStartedUsing, Date timeStoppedUsing) {
		this.aliasName = aliasName;
		this.timeStartedUsing = timeStartedUsing;
		this.timeStoppedUsing = timeStoppedUsing;
	}

	@Override
	public String toString() {
		return "SecretAlias [id=" + id + ", aliasName=" + aliasName + ", timeStartedUsing="
				+ timeStartedUsing + ", timeStoppedUsing=" + timeStoppedUsing + "]";
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Date getTimeStartedUsing() {
		return timeStartedUsing;
	}

	public void setTimeStartedUsing(Date timeStartedUsing) {
		this.timeStartedUsing = timeStartedUsing;
	}

	public Date getTimeStoppedUsing() {
		return timeStoppedUsing;
	}

	public void setTimeStoppedUsing(Date timeStoppedUsing) {
		this.timeStoppedUsing = timeStoppedUsing;
	}

	public Long getId() {
		return id;
	}
	
	
	
}
