package com.kitchen.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kitchen.backend.dao.CamareroDAOImpl;
import com.kitchen.backend.dao.ClienteDAOImpl;
import com.kitchen.backend.dao.CocineroDAOImpl;
import com.kitchen.backend.dao.DetalleFacturaDAOImpl;
import com.kitchen.backend.dao.FacturaDAOImpl;
import com.kitchen.backend.dao.MesaDAOImpl;
import com.kitchen.model.Camarero;
import com.kitchen.model.Cocinero;
import com.kitchen.model.DetalleFactura;
import com.kitchen.model.Factura;
import com.kitchen.model.Mesa;

@Stateless
public class ManagerBean implements Manager{

    @EJB private ClienteDAOImpl clienteDAO;
    @EJB private CamareroDAOImpl camareroDAO;
    @EJB private CocineroDAOImpl cocineroDAO;
    @EJB private FacturaDAOImpl facturaDAO;
    @EJB private DetalleFacturaDAOImpl detalleFacturaDAO;
    @EJB private MesaDAOImpl mesaDAO;
    
	@Override
	public List<Mesa> findTablesAll() throws Exception {
		return mesaDAO.findAll();
	}
	
	@Override
	public List<Camarero> findWaiterAll() throws Exception {
		return camareroDAO.findAll();
	}

	@Override
	public Factura saveBill(Factura fact) throws Exception {
		return facturaDAO.merge(fact);
	}

	@Override
	public void saveBillWithDetail(LinkedList<DetalleFactura> detalleFacturas, Factura factura) throws Exception {
		detalleFacturas.forEach(d -> {
			d.setFactura(factura);
		});
		
		detalleFacturaDAO.mergeAll(detalleFacturas);
	}
	
	@Override
	public List<Cocinero> findChefAll() throws Exception {
		return cocineroDAO.findAll();
	}

	@Override
	public List<String> reportByDate(Date date) throws Exception {
		return facturaDAO.getBillByMonth(date);
	}
	
	@Override
	public List<String> reportByClient() throws Exception {
		return facturaDAO.getClientsByMonth();
	}
}
