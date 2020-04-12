/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.models;

import java.sql.Date;

/**
 *
 * @author Houcem
 */
public class Abonnement {
	
	private int id;
	private String name;
	private Date date;
	private int active;
	private Type T;

	public Abonnement() {
	}

	public Abonnement(int id) {
		this.id = id;
	}

	public Abonnement(String name, Date date, int active, Type T) {
		this.name = name;
		this.date = date;
		this.active = active;
		this.T = T;
	}

	public Abonnement(int id, String name, Date date, int active, Type T) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.active = active;
		this.T = T;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Type getT() {
		return T;
	}

	public void setT(Type T) {
		this.T = T;
	}

	@Override
	public String toString() {
		return "Abonnement{" + "id=" + id + ", name=" + name + ", date=" + date + ", active=" + active + ", T=" + T + '}';
	}
	
	
	
}
