package pl.krzaq.metalscrap.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import pl.krzaq.metalscrap.model.Auction;

public class AuctionTimeLeftConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Auction a = (Auction) arg0 ;
		String result = "-" ;
		Date current = new Date() ;
		if (a!=null && a.getEndDate().before(current)) {
			
			DateTime now = new DateTime(new Date()) ;
			DateTime end = new DateTime(a.getEndDate()) ;
			Duration d = new Duration(now, end);
			
			StringBuffer toGo = new StringBuffer() ;
			Long daysToGo = d.getStandardDays() ;
			toGo.append(daysToGo+" dni, ") ;
			
			Period p = d.toPeriod().minusDays(daysToGo.intValue()) ;
			toGo.append(p.getHours()+" godz., "+p.getMinutes()+" min., "+p.getSeconds()+" sek.") ; 
			result = toGo.toString() ;
		} else {
			DateFormat df = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy") ;
			result = "Aukcja zakoñczona" ;
		}
		
		return result ;
	}

}
