package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;

public class AuctionIsNotStartedConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Auction a = (Auction) arg0 ;
		Date now = new Date() ;
		return  (a!=null && a.getStatus().getCode()!=AuctionStatus.STATUS_STARTED ||( a.getEndDate().after(now))) ;
	}

}
