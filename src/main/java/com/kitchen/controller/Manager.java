package com.kitchen.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;

import com.kitchen.model.Camarero;
import com.kitchen.model.Cocinero;
import com.kitchen.model.DetalleFactura;
import com.kitchen.model.Factura;
import com.kitchen.model.Mesa;

@Remote
public interface Manager {

	public List<Mesa> findTablesAll() throws Exception;
	
	public List<Camarero> findWaiterAll() throws Exception;
	
	public Factura saveBill(Factura fact) throws Exception;
	
	public void saveBillWithDetail(LinkedList<DetalleFactura> detalleFacturas, Factura factura) throws Exception;
	
	public List<Cocinero> findChefAll() throws Exception;
	
	public List<String> reportByDate(Date date) throws Exception;
	
	public List<String> reportByClient() throws Exception;
}
