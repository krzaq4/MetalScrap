package pl.krzaq.metalscrap.quartz.jobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	private AuctionDAO auctionDAO ; 
	
	
	public void checkIfStarted() {
		
		Date currentTime = new Date() ;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss:SS") ;
		logger.info("Checking started/finished auctions "+df.format(currentTime));
		
		if (startedAuctions==null) {
		
			startedAuctions = auctionDAO.findByStartTime(currentTime);
		}
		
		if(allAuctions==null) {
			
			allAuctions = auctionDAO.findAll() ;
		}
		
		if (startedAuctions != null && allAuctions != null) {
			
			for (Auction a:auctionDAO.findByStartTime(currentTime)) {
				
				if (!startedAuctions.contains(a)) {
					startedAuctions.add(a) ;
					a.setStatus(auctionDAO.findStatusByCode(AuctionStatus.STATUS_STARTED));
					auctionDAO.update(a);
					logger.info("StartedAuction id "+a.getId());
				}
			}
			
			for (Auction a:auctionDAO.findByEndTime(currentTime)) {
				
				if (startedAuctions.contains(a)) {
					a.setStatus(auctionDAO.findStatusByCode(AuctionStatus.STATUS_FINISHED));
					auctionDAO.update(a);
					startedAuctions.remove(a) ;
					logger.info("FinishedAuction id "+a.getId());
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
