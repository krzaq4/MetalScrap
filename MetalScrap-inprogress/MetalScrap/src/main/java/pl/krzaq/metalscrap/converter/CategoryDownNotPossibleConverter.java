package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
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
		if (arg0==null) return true ;
		else {
		
			Category cat = (Category) arg0 ;
			if (cat.getParent()!=null) {
			
				return cat.getPosition()==ServicesImpl.getCategoryService().findSubCategories(cat.getParent()).size() ;
						
			} else {
				return cat.getPosition()==ServicesImpl.getCategoryService().findRootCategories().size() ;
			}
			
			
		}
	}

}
