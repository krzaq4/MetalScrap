package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class AttributeValuesAvailableConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		try {
			Integer val = (Integer) arg0 ;
			return val.equals(new Integer(4)) || val.equals(new Integer(5)) ;
		} catch(ClassCastException cce) {
			return false ;
		}
	}

}
