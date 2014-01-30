package pl.krzaq.metalscrap.quartz.jobs;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	
	private AuctionDAO auctionDAO ; 
	
	
	public void checkIfStarted() {
		
		//Aktualny czas serwera
		Date currentTime = new Date() ;
		
		// sp³aszczenie milisekund
		Calendar cal = new GregorianCalendar() ;
		cal.setTime(currentTime);
		cal.set(Calendar.MILLISECOND, 0);
		currentTime = cal.getTime() ;
		
		
		
		// nowy sposob
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss:SS") ;
		
		List<Auction> started = auctionDAO.findByStartTime(currentTime) ;
		List<Auction> ending = auctionDAO.findByEndTime(currentTime) ;
		
		for (Auction a:started){
			if(!a.getStatus().getCode().equals(AuctionStatus.STATUS_STARTED)) {
				a.setStatus(auctionDAO.findStatusByCode(AuctionStatus.STATUS_STARTED));
				auctionDAO.update(a);
				logger.info("StartedAuction id "+a.getId()+" @ "+df.format(currentTime));
			}
		}
		
		for(Auction a:ending){
			if(!a.getStatus().getCode().equals(AuctionStatus.STATUS_FINISHED)) {
				a.setStatus(auctionDAO.findStatusByCode(AuctionStatus.STATUS_FINISHED));
				auctionDAO.update(a);
				logger.info("FinishedAuction id "+a.getId()+" @ "+df.format(currentTime));
			}
		}
		
		// ---------------
		
		
		
				
		
	}


	public AuctionDAO getAuctionDAO() {
		return auctionDAO;
	}


	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}
	
	
	
	
	
}
