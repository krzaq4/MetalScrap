package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import java.text.DecimalFormat ;

import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.utils.Utilities;

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
			Double bidStep = new Double(Utilities.getServices().getConfigService().findByKey("auction.autobidder.step").getValue()) ;
			String currencySymbol = Utilities.getServices().getConfigService().findByKey("auction.currency.symbol").getValue() ;
			Double bestPrice = new Double("0.00") ;
			if(uo.getAuction().getBestUserOffer()!=null)
				bestPrice = uo.getAuction().getBestUserOffer().getPrice() ;
			
			Double currentPrice = uo.getPrice() ;
			
			Double substract = currentPrice - bestPrice ;
			
			if(substract.compareTo(bidStep)>=0) {
				currentPrice = bestPrice+bidStep ;
			}
			
			DecimalFormat df = new DecimalFormat("##########.##") ;
			result = df.format(currentPrice)+" "+currencySymbol ; 
		} 
		return result ;
	}

}
