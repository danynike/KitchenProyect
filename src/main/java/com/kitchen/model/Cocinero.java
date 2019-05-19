package com.kitchen.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@SuppressWarnings("serial")
public class Cocinero implements Serializable{

	private Long idCocinero;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	public Cocinero() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdCocinero() {
		return idCocinero;
	}

	public void setIdCocinero(Long idCocinero) {
		this.idCocinero = idCocinero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && this.getIdCocinero() != null && ((Cocinero)obj).getIdCocinero() != null && this.getIdCocinero().longValue() == ((Cocinero)obj).getIdCocinero().longValue();
	}

	@Override
	public String toString() {
		return getNombre() + " " + getApellido1();
	}
}
