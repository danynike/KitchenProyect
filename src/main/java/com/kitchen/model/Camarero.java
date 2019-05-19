package com.kitchen.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@SuppressWarnings("serial")
public class Camarero implements Serializable{

	private Long idCamarero;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	public Camarero() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdCamarero() {
		return idCamarero;
	}

	public void setIdCamarero(Long idCamarero) {
		this.idCamarero = idCamarero;
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
		return obj != null && this.getIdCamarero() != null && ((Camarero)obj).getIdCamarero() != null && this.getIdCamarero().longValue() == ((Camarero)obj).getIdCamarero().longValue();
	}

	@Override
	public String toString() {
		return getNombre() + " " + getApellido1();
	}
}
