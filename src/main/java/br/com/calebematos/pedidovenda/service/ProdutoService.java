package br.com.calebematos.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.calebematos.pedidovenda.model.Produto;
import br.com.calebematos.pedidovenda.repository.ProdutoRepository;
import br.com.calebematos.pedidovenda.util.jpa.Transactional;

public class ProdutoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto salvar(Produto produto) {
		
		Produto produtoExistente = produtoRepository.obterPorSku(produto.getSku());
		
		if(produtoExistente != null) {
			throw new NegocioException("Já existe um produto com o SKU informado!");
		}
		
		produtoRepository.guardar(produto);
		
		return produto;
	}
}
