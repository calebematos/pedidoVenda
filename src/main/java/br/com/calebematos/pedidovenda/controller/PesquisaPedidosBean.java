package br.com.calebematos.pedidovenda.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class PesquisaPedidosBean {
	
	private List<Integer> pedidosFiltrados;

	public PesquisaPedidosBean() {
		pedidosFiltrados = new ArrayList<>();
		for(int i=0; i<50; i++) {
			pedidosFiltrados.add(i);
		}
	}
	
	public List<Integer> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	
}