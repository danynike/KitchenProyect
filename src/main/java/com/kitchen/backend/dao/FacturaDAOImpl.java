package com.kitchen.backend.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.kitchen.model.Factura;

@Stateless
public class FacturaDAOImpl extends GenericDAOImpl<Factura, Long>{

	public List<String> getBillByMonth(Date date){
		List<String> report = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append(" select c.nombre, c.apellido1, date_trunc('month', f.fechafactura), SUM(CAST(coalesce(NULLIF(replace(replace(df.importe,'.',''),',',''), ''), '0') AS integer)) as sales  from factura f  ");
		sb.append(" full outer join detallefactura df on (df.factura_idfactura = f.idfactura) ");
		sb.append(" full outer join Camarero c on (f.camarero_idcamarero = c.idcamarero) ");
		sb.append(" where 1 = 1 ");
		sb.append(" AND EXTRACT(MONTH FROM f.fechafactura) >= EXTRACT(MONTH FROM to_timestamp('"+new SimpleDateFormat("yyyy-MM-dd").format(date)+"', 'yyyy-mm-dd')) or f.fechafactura is null ");
		sb.append(" group by c.nombre, c.apellido1,f.fechafactura ");
		Query query = getEntityManager().createNativeQuery(sb.toString());

		List<?> result = query.getResultList(); 
		if(!result.isEmpty()) {
			for (Object o : result) {
				Object[] o1 = (Object[]) o;
				report.add(o1[0] + " " + o1[1] + "," + (ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)) + "," + ((BigInteger)o1[3]).toString());
			}
		}
		
		return report;
	}
	
	public List<String> getClientsByMonth(){
		List<String> report = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append(" select c.nombre, c.apellido1, df.importe from cliente c  ");
		sb.append(" join factura f on (f.cliente_idcliente = c.idcliente) ");
		sb.append(" join detallefactura df on(f.idfactura = df.factura_idfactura) ");
		sb.append(" where (df.importe is not null AND CAST(coalesce(NULLIF(replace(replace(df.importe,'.',''),',',''), ''), '0') AS integer) >= 100000 ) ");
		Query query = getEntityManager().createNativeQuery(sb.toString());

		List<?> result = query.getResultList(); 
		if(!result.isEmpty()) {
			for (Object o : result) {
				Object[] o1 = (Object[]) o;
				report.add(o1[0] + " " + o1[1] + "," + o1[2]);
			}
		}
		
		return report;
	}
}