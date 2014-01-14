package pl.krzaq.metalscrap.converter;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.mysql.jdbc.StringUtils;

import pl.krzaq.metalscrap.model.Auction;

public class AuctionNameConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Auction a = (Auction) arg0 ;
		String result = "" ;
		if (a!=null && a.getName()!=null && !StringUtils.isNullOrEmpty(a.getName())) {
			result = a.getName() ;
		}
		return result ;
	}

}
