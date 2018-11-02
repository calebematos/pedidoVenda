package br.com.calebematos.pedidovenda.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class CadastroPedidoBean {

	private List<Integer> itens;
	
	public CadastroPedidoBean() {
		itens = new ArrayList<>();
		itens.add(1);
	}

	public List<Integer> getItens() {
		return itens;
	}

	public void setItens(List<Integer> itens) {
		this.itens = itens;
	}
}
