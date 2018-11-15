package br.com.calebematos.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.calebematos.pedidovenda.model.Produto;
import br.com.calebematos.pedidovenda.repository.ProdutoRepository;
import br.com.calebematos.pedidovenda.repository.filter.ProdutoFilter;
import br.com.calebematos.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;

	private ProdutoFilter filtro;
	private List<Produto> produtosFiltrados;
	private Produto produtoSelecionado;

	public PesquisaProdutosBean() {
		filtro = new ProdutoFilter();
	}

	public void pesquisar() {
		produtosFiltrados = produtoRepository.filtrar(filtro);
	}

	public void excluir() {
		produtoRepository.remover(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);
		FacesUtil.addInfoMessage("O produto " + produtoSelecionado.getSku() + " foi excluído com sucesso!");
	}

	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getFiltro() {
		return filtro;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}
