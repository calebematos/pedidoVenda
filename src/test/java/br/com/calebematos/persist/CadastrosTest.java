package br.com.calebematos.persist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.calebematos.pedidovenda.model.Categoria;
import br.com.calebematos.pedidovenda.model.Cliente;
import br.com.calebematos.pedidovenda.model.Endereco;
import br.com.calebematos.pedidovenda.model.EnderecoEntrega;
import br.com.calebematos.pedidovenda.model.FormaPagamento;
import br.com.calebematos.pedidovenda.model.Grupo;
import br.com.calebematos.pedidovenda.model.ItemPedido;
import br.com.calebematos.pedidovenda.model.Pedido;
import br.com.calebematos.pedidovenda.model.Produto;
import br.com.calebematos.pedidovenda.model.StatusPedido;
import br.com.calebematos.pedidovenda.model.TipoPessoa;
import br.com.calebematos.pedidovenda.model.Usuario;

public class CadastrosTest {

	private static EntityManagerFactory factory;

	private EntityManager entityManager;

	private EntityTransaction trx;

	@BeforeClass
	public static void initClass() {
		factory = Persistence.createEntityManagerFactory("PedidoPU");
	}

	@Before
	public void setup() {
		entityManager = factory.createEntityManager();
		trx = entityManager.getTransaction();
		trx.begin();
	}

	@After
	public void tearDawn() {
		trx.rollback();
		entityManager.close();
	}

	@Test
	public void deveSalvarCliente() {

		Cliente cliente = obterCliente();

		entityManager.persist(cliente);
	}

	@Test
	public void deveSalvarPedido() {

		Cliente cliente = obterCliente();
		Usuario vendedor = obterVendedor();
		Produto produto = obterProduto();

		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
		enderecoEntrega.setLogradouro("Rua dos Mercados");
		enderecoEntrega.setNumero("1000");
		enderecoEntrega.setCidade("Uberlândia");
		enderecoEntrega.setUf("MG");
		enderecoEntrega.setCep("38400-123");

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setDataEntrega(LocalDate.now());
		pedido.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
		pedido.setObservacao("Aberto das 08 às 18h");
		pedido.setStatusPedido(StatusPedido.ORCAMENTO);
		pedido.setValorDesconto(BigDecimal.ZERO);
		pedido.setValorFrete(BigDecimal.ZERO);
		pedido.setValorTotal(new BigDecimal(23.2));
		pedido.setVendedor(vendedor);
		pedido.setEnderecoEntrega(enderecoEntrega);

		ItemPedido item = new ItemPedido();
		item.setProduto(produto);
		item.setQuantidade(10);
		item.setValorUnitario(new BigDecimal(2.32));
		item.setPedido(pedido);

		pedido.getItens().add(item);
		
		entityManager.persist(pedido);
	}
	

	@Test
	public void deveSalvarCategoria() {
		Categoria categoria = new Categoria();
		categoria.setDescricao("Informática");
		
		entityManager.persist(categoria);
	}

	private Cliente obterCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("João das Couves");
		cliente.setEmail("joao@dascouves.com");
		cliente.setDocumentoReceitaFederal("123.123.123-12");
		cliente.setTipo(TipoPessoa.FISICA);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua das Aboboras Vermelhas");
		endereco.setNumero("111");
		endereco.setCidade("Uberlândia");
		endereco.setUf("MG");
		endereco.setCep("38400-000");
		endereco.setCliente(cliente);

		cliente.getEnderecos().add(endereco);
		return cliente;
	}

	private Usuario obterVendedor() {
		Usuario usuario = new Usuario();
		usuario.setNome("Maria");
		usuario.setEmail("maria@abadia.com");
		usuario.setSenha("123");

		Grupo grupo = new Grupo();
		grupo.setNome("Vendedores");
		grupo.setDescricao("Vendedores da empresa");

		usuario.getGrupos().add(grupo);

		return usuario;
	}

	private Produto obterProduto() {
		// instanciamos a categoria pai (Bebidas)
		Categoria categoriaPai = new Categoria();
		categoriaPai.setDescricao("Bebidas");

		// instanciamos a categoria filha (Refrigerantes)
		Categoria categoriaFilha = new Categoria();
		categoriaFilha.setDescricao("Refrigerantes");
		categoriaFilha.setCategoriaPai(categoriaPai);

		// adicionamos a categoria Refrigerantes como filha de Bebidas
		categoriaPai.getSubcategorias().add(categoriaFilha);

		// instanciamos e persistimos um produto
		Produto produto = new Produto();
		produto.setCategoria(categoriaFilha);
		produto.setNome("Guaraná 2L");
		produto.setQuantidadeEstoque(10);
		produto.setSku("GUA00123");
		produto.setValorUnitario(new BigDecimal(2.21));
		
		return produto;
	}
}
