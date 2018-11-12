package br.com.calebematos.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.calebematos.pedidovenda.model.Categoria;
import br.com.calebematos.pedidovenda.repository.CategoriaRepository;
import br.com.calebematos.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

//	@Inject
	private CategoriaRepository categoriaRepository;

	public CategoriaConverter() {
		categoriaRepository = CDIServiceLocator.getBean(CategoriaRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria categoria = null;
		if (value != null) {
			Long id = new Long(value);
			categoria = categoriaRepository.obterCategoriaPorId(id);
		}
		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null)
			return ((Categoria) value).getCodigo().toString();
		return "";
	}

}
