package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import pl.krzaq.metalscrap.model.Auction;

public class AuctionIsNewConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Auction a = (Auction) arg0 ;
		Boolean result = false ;
		if (a!=null && a.getId()!=null) {
			
			result = true ;
		} else if (a!=null && a.getId()==null) {
			result = false ;
		}
		return result ;
	}

}
