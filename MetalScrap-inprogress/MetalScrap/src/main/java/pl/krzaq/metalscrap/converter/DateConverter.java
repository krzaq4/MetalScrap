package pl.krzaq.metalscrap.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;


public class DateConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {

		Date date = (Date) arg0 ;
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy") ;
		return df.format(date) ;
		
	}

}
