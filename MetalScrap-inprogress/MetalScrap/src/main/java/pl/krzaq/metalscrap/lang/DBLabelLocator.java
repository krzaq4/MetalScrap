package pl.krzaq.metalscrap.lang;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.zkoss.util.resource.LabelLocator2;

import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class DBLabelLocator implements LabelLocator2 {

	private String _field ;
	
	public DBLabelLocator(String field) {
		_field = field ;
	}
	
	@Override
	public String getCharset() {
		return "UTF-8" ;
	}

	@Override
	public InputStream locate(Locale locale) {
		List<LangLabel> ll = ServicesImpl.getLangLabelService().findAllByLang(locale.getCountry()) ;
		
		StringBuffer sb = new StringBuffer();
		int i=0;
		for(LangLabel lab:ll) {
			sb.append(lab.getLkey()+"="+lab.getLvalue()) ;
			if (i<ll.size()-1) {
			sb.append("\n") ;	
			}
			
			i++ ;
		}
		InputStream is = new ByteArrayInputStream(sb.toString().getBytes()) ;
		return is;
	}

	

}
