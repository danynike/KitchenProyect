package com.kitchen.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@SuppressWarnings("serial")
public class Mesa implements Serializable{

	private Long idMesa;
	private Integer numMaxComensales;
	private String ubicacion;
	
	public Mesa() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}

	public Integer getNumMaxComensales() {
		return numMaxComensales;
	}

	public void setNumMaxComensales(Integer numMaxComensales) {
		this.numMaxComensales = numMaxComensales;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && this.getIdMesa() != null && ((Mesa)obj).getIdMesa() != null && this.getIdMesa().longValue() == ((Mesa)obj).getIdMesa().longValue();
	}

	@Override
	public String toString() {
		return "Mesa: " + getUbicacion() + " comensales: " + getNumMaxComensales();
	}
}
