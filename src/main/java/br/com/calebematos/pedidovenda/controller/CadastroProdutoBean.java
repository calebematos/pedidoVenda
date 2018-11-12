package br.com.calebematos.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.calebematos.pedidovenda.model.Categoria;
import br.com.calebematos.pedidovenda.model.Produto;
import br.com.calebematos.pedidovenda.repository.CategoriaRepository;
import br.com.calebematos.pedidovenda.service.ProdutoService;
import br.com.calebematos.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaRepository categoriaRepository;

	@Inject
	private ProdutoService produtoService;
	
	private Produto produto;

	private Categoria categoriaPai;

	private List<Categoria> categoriasRaizes;
	private List<Categoria> categorias;

	public CadastroProdutoBean() {
		limpar();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback())
			categoriasRaizes = categoriaRepository.obterCategoriasRaizes();
	}

	public void salvar() {
		produtoService.salvar(produto);
		limpar();
		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
	}



	public void listarSubcategorias() {
		categorias = categoriaRepository.obterSubCategorias(categoriaPai);
	}

	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		categorias = new ArrayList<>();
		
	}
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
}
