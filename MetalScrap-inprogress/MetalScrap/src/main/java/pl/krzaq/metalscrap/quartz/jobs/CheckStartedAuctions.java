package pl.krzaq.metalscrap.quartz.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;











import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.krzaq.metalscrap.dao.AuctionDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;

public class CheckStartedAuctions {

	
	Logger logger = LoggerFactory.getLogger(CheckStartedAuctions.class) ;
	
	private List<Auction> startedAuctions ;
	private List<Auction> allAuctions ;
	
	private AuctionStatus[] allStatuses;
	
	private AuctionDAO auctionDAO ; 
	
	
	public void checkIfStarted() {
		
		//Aktualny czas serwera
		Date currentTime = new Date() ;
		
		// sp³aszczenie milisekund
		Calendar cal = new GregorianCalendar() ;
		cal.setTime(currentTime);
		cal.set(Calendar.MILLISECOND, 0);
		currentTime = cal.getTime() ;
		
		
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss:SS") ;
		
		// lista stanów aukcji
		
		if (allStatuses==null) {
			List<AuctionStatus> tl = auctionDAO.findAllStatuses() ;
			allStatuses = new AuctionStatus[tl.size()+1] ;
			
			for (AuctionStatus as:tl) {
				allStatuses[as.getCode()] = as ;
			}
		}
		
		
		// pobranie listy rozpoczêtych aukcji
		
		if (startedAuctions==null) {
		
			startedAuctions = auctionDAO.findByStartTime(currentTime);
		}
		
		
		// w³aœciwe sprawdzenie rozpoczêtych i zakoñczonych aukcji
		
		if (startedAuctions != null ) {
			
			for (Auction a:auctionDAO.findByStartTime(currentTime)) {
				
				if (!startedAuctions.contains(a)) {
					startedAuctions.add(a) ;
					a.setStatus(allStatuses[AuctionStatus.STATUS_STARTED]);
					auctionDAO.update(a);
					logger.info("StartedAuction id "+a.getId()+" @ "+df.format(currentTime));
				}
			}
			
			for (Iterator<Auction>it = startedAuctions.iterator();it.hasNext();) {
				
				Auction a = it.next() ;
				if (a.getEndDate().compareTo(currentTime)==0) {
					a.setStatus(allStatuses[AuctionStatus.STATUS_FINISHED]);
					auctionDAO.update(a);
					it.remove();
					logger.info("FinishedAuction id "+a.getId()+" @ "+df.format(currentTime));
				}
				
			}
			
			
		}
		
		
		
	}


	public AuctionDAO getAuctionDAO() {
		return auctionDAO;
	}


	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}
	
	
	
	
	
}
