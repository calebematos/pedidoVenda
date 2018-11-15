package br.com.calebematos.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.calebematos.pedidovenda.model.Produto;
import br.com.calebematos.pedidovenda.repository.filter.ProdutoFilter;
import br.com.calebematos.pedidovenda.service.NegocioException;
import br.com.calebematos.pedidovenda.util.jpa.Transactional;

public class ProdutoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto guardar(Produto produto) {
		return manager.merge(produto);
	}

	@Transactional
	public void remover(Produto produto) {

		try {
			produto = obterProdutoPorId(produto.getCodigo());
			manager.remove(produto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído!");
		}
	}

	public Produto obterPorSku(String sku) {
		try {
			return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
					.setParameter("sku", sku.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Produto> filtrar(ProdutoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);

		Predicate[] predicates = criarRestricoes(filtro, builder, root);

		criteria.where(predicates);

		TypedQuery<Produto> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	private Predicate[] criarRestricoes(ProdutoFilter filtro, CriteriaBuilder builder, Root<Produto> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getSku())) {
			predicates.add(builder.like(builder.lower(root.get("sku")), "%" + filtro.getSku().toLowerCase() + "%"));
		}
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public Produto obterProdutoPorId(Long id) {
		return manager.find(Produto.class, id);
	}

}
