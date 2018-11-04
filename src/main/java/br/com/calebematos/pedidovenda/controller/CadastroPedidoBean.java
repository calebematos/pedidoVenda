package br.com.calebematos.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.calebematos.pedidovenda.model.EnderecoEntrega;
import br.com.calebematos.pedidovenda.model.Pedido;
import br.com.calebematos.pedidovenda.service.NegocioException;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Pedido pedido;
	private List<Integer> itens;

	public CadastroPedidoBean() {
	}

	@PostConstruct
	public void init() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
		itens = new ArrayList<>();
		itens.add(1);
	}
	
	public void salvar() {
		throw new NegocioException("Não pode ser salvo, pois ainda não foi implementado");
	}

	public List<Integer> getItens() {
		return itens;
	}

	public Pedido getPedido() {
		return pedido;
	}

}
