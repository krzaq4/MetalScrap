package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listbox;

public class ListboxMultipleNotSelected implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Listbox lbx = (Listbox) arg0 ;
		return (lbx.getSelectedItems()==null || lbx.getSelectedItems().size()==0) ;
	}

}
