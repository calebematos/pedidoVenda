package br.com.calebematos.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.calebematos.pedidovenda.model.Produto;
import br.com.calebematos.pedidovenda.repository.ProdutoRepository;
import br.com.calebematos.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

//	@Inject
	private ProdutoRepository produtoRepository;

	public ProdutoConverter() {
		produtoRepository = CDIServiceLocator.getBean(ProdutoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto Produto = null;
		if (value != null) {
			Long id = new Long(value);
			Produto = produtoRepository.obterProdutoPorId(id);
		}
		return Produto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Produto produto = (Produto) value;
			return produto.getCodigo() == null ? null : produto.getCodigo().toString();
		}
		return "";
	}

}
