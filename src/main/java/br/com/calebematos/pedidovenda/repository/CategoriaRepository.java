package br.com.calebematos.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.calebematos.pedidovenda.model.Categoria;

public class CategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Categoria obterCategoriaPorId(Long id) {
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> obterCategoriasRaizes() {
		return manager.createQuery("from Categoria where categoriaPai is null", Categoria.class).getResultList();
	}
	
	public List<Categoria> obterSubCategorias(Categoria categoria){
		return manager.createQuery("from Categoria where categoriaPai = :categoria", Categoria.class).setParameter("categoria", categoria)
				.getResultList();
	}
}
