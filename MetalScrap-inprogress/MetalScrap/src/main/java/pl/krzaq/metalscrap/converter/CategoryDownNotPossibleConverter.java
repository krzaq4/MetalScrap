package pl.krzaq.metalscrap.converter;

import java.util.Locale;

import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.TypeConverter;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class CategoryDownNotPossibleConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		if (arg0==null) return true ;
		else {
		
			Category cat = (Category) arg0 ;
			if (cat.getParent()!=null) {
			
				return cat.getPosition()==ServicesImpl.getCategoryService().findSubCategoriesByLang(cat.getParent(), locale.getLanguage()).size() ;
						
			} else {
				return cat.getPosition()==ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()).size() ;
			}
			
			
		}
	}

}
