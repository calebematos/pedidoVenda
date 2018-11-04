package br.com.calebematos.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.calebematos.pedidovenda.model.Cliente;
import br.com.calebematos.pedidovenda.model.Endereco;
import br.com.calebematos.pedidovenda.model.TipoPessoa;

public class CadastroClienteTest {

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
		trx.commit();
		entityManager.close();
	}

	@Test
	public void deveSalvarCliente() {

		Cliente cliente = new Cliente();
		cliente.setNome("João da Silva Sauro");
		cliente.setDocumentoReceitaFederal("123.123.123-12");
		cliente.setTipo(TipoPessoa.FISICA);

		Endereco endereco = new Endereco();
		endereco.setCidade("Blumenau");
		endereco.setCep("9554456");
		endereco.setCliente(cliente);

		cliente.getEnderecos().add(endereco);

		entityManager.persist(cliente);

	}
}
