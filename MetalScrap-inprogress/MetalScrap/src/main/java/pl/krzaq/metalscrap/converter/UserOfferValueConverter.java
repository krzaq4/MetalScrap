package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import java.text.DecimalFormat ;

import pl.krzaq.metalscrap.model.UserOffer;

public class UserOfferValueConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		UserOffer uo = (UserOffer) arg0 ;
		String result = "----- brak oferty -----" ;
		if(uo!=null) {
			DecimalFormat df = new DecimalFormat("##########.##") ;
			result = df.format(uo.getPrice())+" PLN" ; 
		} 
		return result ;
	}

}
