/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.models;

/**
 *
 * @author Houcem
 */
public class Type {
	
	private int id;
	private String name;
	private int value;
	private int np;
	private int nu;

	public Type() {
	}

	public Type(int id) {
		this.id = id;
	}

	public Type(String name, int value, int np, int nu) {
		this.name = name;
		this.value = value;
		this.np = np;
		this.nu = nu;
	}

	public Type(int id, String name, int value, int np, int nu) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.np = np;
		this.nu = nu;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getNp() {
		return np;
	}

	public void setNp(int np) {
		this.np = np;
	}

	public int getNu() {
		return nu;
	}

	public void setNu(int nu) {
		this.nu = nu;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
