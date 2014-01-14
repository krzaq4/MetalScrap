package pl.krzaq.metalscrap.converter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import pl.krzaq.metalscrap.model.UserOffer;

public class UserOfferDateConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		UserOffer uo = (UserOffer) arg0 ;
		String result = "------" ;
		if(uo!=null) {
			DateFormat df = new SimpleDateFormat("HH:mm:ss dd:MM:yyyy") ;
			result = df.format(uo.getDateIssued()) ; 
		} 
		return result ;
	}

}
