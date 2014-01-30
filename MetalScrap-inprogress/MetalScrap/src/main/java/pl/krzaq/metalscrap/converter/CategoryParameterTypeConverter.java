package pl.krzaq.metalscrap.converter;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class CategoryParameterTypeConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		String typeName = " --- " ;
		
		Integer type = (Integer) arg0 ;
		switch(type) {
		case 1 :
				typeName = Labels.getLabel("common.type.text") ;
				break ;
		case 2 :
				typeName = Labels.getLabel("common.type.combo") ;
				break ;
		case 3 : 
				typeName = Labels.getLabel("common.type.date") ;
				break ;
		case 4 : 
				typeName = Labels.getLabel("common.type.choice") ;
				break ;
		}
		
		return typeName ;
	}

}
