package com.kitchen.view;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import com.kitchen.controller.Manager;
import com.kitchen.model.Camarero;
import com.kitchen.model.Cliente;
import com.kitchen.model.Cocinero;
import com.kitchen.model.DetalleFactura;
import com.kitchen.model.Factura;
import com.kitchen.model.Mesa;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Setter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Push
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyUI extends UI {

	private ComboBox<Cocinero> cocineros;
	private BeanValidationBinder<Factura> binder;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(build());
    }
    
    private TabSheet build() {
		
    	binder = new BeanValidationBinder<>(Factura.class);
		TabSheet tab = new TabSheet();
		tab.setSizeUndefined();
		FormLayout formPrincipal = new FormLayout();
		formPrincipal.setMargin(false);
		formPrincipal.setWidth("800px");
		formPrincipal.addComponent(new Label("CLIENTE"));
		TextField name = new TextField("Nombre");
		TextField lastName1 = new TextField("Apellido1");
		TextField lastName2 = new TextField("Apellido2");
		formPrincipal.addComponent(new HorizontalLayout(name, lastName1, lastName2));
		TextArea obs = new TextArea("Observaciones");
		obs.setRows(4);
		formPrincipal.addComponent(obs);
		
		formPrincipal.addComponent(new Label("CAMARERO"));
		ComboBox<Camarero> camareros = null;
		try {
			camareros = new ComboBox<>("Camarero", getManagerBean().findWaiterAll());
			camareros.setEmptySelectionAllowed(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		formPrincipal.addComponent(camareros);
		
		ComboBox<Mesa> mesas = null;
		try {
			mesas = new ComboBox<>("Mesa", getManagerBean().findTablesAll());
			mesas.setEmptySelectionAllowed(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		formPrincipal.addComponent(mesas);
		formPrincipal.addComponent(new Label("PRODUCTO"));
		final Grid<DetalleFactura> grid = new Grid<>();
		grid.setSizeUndefined();
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setColumnReorderingAllowed(true);
		grid.setResponsive(true);
		grid.setItems(new ArrayList<>());
		grid.addColumn(DetalleFactura::getPlato).setId("plato").setSortable(true).setHidable(true).setCaption("Plato");
		grid.addColumn(DetalleFactura::getImporte).setId("importe").setSortable(true).setHidable(true).setCaption("Precio");
		
		Button agregarPlato = new Button("Agregar Producto");
		agregarPlato.addClickListener(event -> {
			final Window wnd = new Window("Agregar Produco");
			wnd.setDraggable(true);
			wnd.setResizable(true);
			wnd.setModal(true);
			wnd.setWindowMode(WindowMode.NORMAL);
			wnd.setClosable(false);
			wnd.center();
			VerticalLayout v = new VerticalLayout();
			TextField plato = new TextField("Plato");
			v.addComponent(plato);
			TextField precio = new TextField("Precio");
			v.addComponent(precio);
			try {
				cocineros = new ComboBox<>("Cocinero", getManagerBean().findChefAll());
				cocineros.setEmptySelectionAllowed(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			v.addComponent(cocineros);
			CssLayout buttons = new CssLayout();
	        buttons.setStyleName("buttons");
	        v.addComponent(buttons);
	        final Button ch = new Button("Guardar", event1 -> {
	        	if(plato.isEmpty() || (precio.isEmpty() || new NumberValidator("ingrese un dato numerico").apply(precio.getValue(), null).isError()) || cocineros.isEmpty()) {
	        		Notification notification = new Notification("");
    				notification.setDescription("Diligencie todos los campos y verifique que el precio sea numerico");
    				notification.setStyleName("error failure small");
    				notification.setDelayMsec(1000);
    				notification.setPosition(Position.TOP_CENTER);
    		        notification.show(Page.getCurrent());
	        	}else {
	        		DetalleFactura detalleFactura = new DetalleFactura();
	        		detalleFactura.setPlato(plato.getValue());
	        		detalleFactura.setImporte(precio.getValue());
	        		detalleFactura.setCocinero(cocineros.getValue());
	        		
	        		LinkedList<DetalleFactura> ps = new LinkedList<DetalleFactura>( grid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()) );
	        		ps.add(detalleFactura);
	        		
	        		grid.setItems(ps);
	        		grid.getDataProvider().refreshAll();
	        		getUI().removeWindow(wnd);
	        	}
	        });
	        ch.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	        ch.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	        buttons.addComponent(ch);
			Button cl = new Button("Cerrar", event2 -> {
				getUI().removeWindow(wnd);
			});
			cl.addStyleName(ValoTheme.BUTTON_LINK);
			buttons.addComponent(cl);
			wnd.setContent(v);
			
			getUI().addWindow(wnd);
		});
		formPrincipal.addComponent(agregarPlato);
		formPrincipal.addComponent(grid);
		
		CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        formPrincipal.addComponent(buttons);
        final Button ch = new Button("Guardar", event1 -> {
    		try {
    			if(binder.isValid()) {
    				Factura factura = new Factura();
    				factura.setCamarero(new Camarero());
    				factura.setCliente(new Cliente());
    				factura.setFechaFactura(new Date());
    				binder.writeBean(factura);
    				LinkedList<DetalleFactura> ps = new LinkedList<DetalleFactura>( grid.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()) );
    				factura = getManagerBean().saveBill(factura);
    				if(!ps.isEmpty()) {
    					getManagerBean().saveBillWithDetail(ps, factura);
    				}
    				
    				Notification notification = new Notification("");
    				notification.setDescription("<b>Se creo la factura No. "+factura.getIdFactura()+" correctamente</b><br/>");
    				notification.setHtmlContentAllowed(true);
    				notification.setStyleName("tray success");
    				notification.setDelayMsec(1000);
    				notification.setPosition(Position.TOP_CENTER);
    		        notification.show(Page.getCurrent());
    		        
    		        factura = new Factura();
    				factura.setCamarero(new Camarero());
    				factura.setCliente(new Cliente());
    				factura.setFechaFactura(new Date());
    				binder.readBean(factura);
    				grid.setItems(new ArrayList<>());
    				grid.getDataProvider().refreshAll();
    			}else {
    				Notification notification = new Notification("");
    				notification.setDescription("Diligencie todos los campos");
    				notification.setStyleName("error failure small");
    				notification.setDelayMsec(1000);
    				notification.setPosition(Position.TOP_CENTER);
    		        notification.show(Page.getCurrent());
    			}
    		}catch (Exception e) {
				e.printStackTrace();
			}
        });
        ch.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        ch.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttons.addComponent(ch);
		
		tab.addTab(formPrincipal, "Agregar Factura");
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(false);
		verticalLayout.setWidth("800px");
		DateField date = new DateField("Ingrese la Fecha");
		Button btn = new Button("Buscar");
		final Label lb = new Label("", ContentMode.HTML);
		btn.addClickListener(e -> {
			if(date.getValue() != null) {
				try {
					final List<String> result = getManagerBean().reportByDate(Date.from(date.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
					if(!result.isEmpty()) {
						final StringBuilder s = new StringBuilder("<table style=\"border-collapse: separate;\"><tr><th>Camarero</th><th>Mes</th><th>Total</th></tr>");
						for (String r : result) {
							s.append("<tr><td>" + r.split(",")[0] + "</td><td>" + r.split(",")[1] + "</td><td>" + r.split(",")[2] + "</td></tr>");
						}
						s.append("</table>");
						getUI().access(() -> {
							lb.setValue(s.toString());
						});
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		verticalLayout.addComponents(date, btn, lb);
		tab.addTab(verticalLayout, "Total Facturado");
		
		VerticalLayout verticalLayout1 = new VerticalLayout();
		verticalLayout1.setMargin(false);
		verticalLayout1.setWidth("800px");
		Button btn1 = new Button("Generar Busqueda");
		final Label lb1 = new Label("", ContentMode.HTML);
		btn1.addClickListener(e -> {
			if(date.getValue() != null) {
				try {
					final List<String> result = getManagerBean().reportByClient();
					if(!result.isEmpty()) {
						final StringBuilder s = new StringBuilder("<table style=\"border-collapse: separate;\"><tr><th>Cliente</th><th>Importe</th></tr>");
						for (String r : result) {
							s.append("<tr><td>" + r.split(",")[0] + "</td><td>" + r.split(",")[1] + "</td></tr>");
						}
						s.append("</table>");
						getUI().access(() -> {
							lb1.setValue(s.toString());
						});
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		verticalLayout1.addComponents(btn1, lb1);
		tab.addTab(verticalLayout1, "Total Facturado");
		
		binder.forField(mesas).bind(new ValueProvider<Factura, Mesa>() {
		    @Override
		    public Mesa apply(Factura t) {
		      return t.getMesa();
		    }
		  },
		  new Setter<Factura, Mesa>() {
		    @Override
		    public void accept(Factura t, Mesa value) {
		    	t.setMesa(value);
		    }
	    });
		binder.forField(camareros).bind(new ValueProvider<Factura, Camarero>() {
		    @Override
		    public Camarero apply(Factura t) {
		      return t.getCamarero();
		    }
		  },
		  new Setter<Factura, Camarero>() {
		    @Override
		    public void accept(Factura t, Camarero value) {
		    	t.setCamarero(value);
		    }
	    });
		binder.forField(name).withValidator(new StringLengthValidator("the field not empty", 4, 255)).bind(new ValueProvider<Factura, String>() {
		    @Override
		    public String apply(Factura t) {
		      return t.getCliente().getNombre();
		    }
		  },
		  new Setter<Factura, String>() {
		    @Override
		    public void accept(Factura t, String value) {
		    	t.getCliente().setNombre(value);
		    }
	    });
		binder.forField(lastName1).withValidator(new StringLengthValidator("the field not empty", 4, 255)).bind(new ValueProvider<Factura, String>() {
		    @Override
		    public String apply(Factura t) {
		      return t.getCliente().getApellido1();
		    }
		  },
		  new Setter<Factura, String>() {
		    @Override
		    public void accept(Factura t, String value) {
		    	t.getCliente().setApellido1(value);
		    }
	    });
		binder.forField(lastName2).bind(new ValueProvider<Factura, String>() {
		    @Override
		    public String apply(Factura t) {
		      return t.getCliente().getApellido2();
		    }
		  },
		  new Setter<Factura, String>() {
		    @Override
		    public void accept(Factura t, String value) {
		    	t.getCliente().setApellido2(value);
		    }
	    });
		binder.forField(obs).bind(new ValueProvider<Factura, String>() {
		    @Override
		    public String apply(Factura t) {
		      return t.getCliente().getObservacion();
		    }
		  },
		  new Setter<Factura, String>() {
		    @Override
		    public void accept(Factura t, String value) {
		    	t.getCliente().setObservacion(value);
		    }
	    });
		return tab;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(closeIdleSessions = true, heartbeatInterval = 300, ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    	@EJB
		private Manager manager;

		public Manager getManager() {
			return manager;
		}
    }
    
    public static Manager getManagerBean() {
		return ((MyUIServlet) VaadinServlet.getCurrent()).getManager();
	}
}
