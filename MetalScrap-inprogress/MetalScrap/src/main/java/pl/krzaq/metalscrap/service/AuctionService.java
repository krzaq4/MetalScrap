package pl.krzaq.metalscrap.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;

public interface AuctionService {

	
	
	public List<Auction> findAll() ;
	
	public List<Auction> findByStatus(int status) ;
	
	public List<Auction> findByStartTime(Date start) ;
	
	public List<Auction> findByEndTime(Date end) ;
	
	public List<AuctionStatus> findAllStatuses() ;
	
	public AuctionStatus findStatusByCode(int code);

	public Auction findById(Long id) ;
	
	public void save(Auction a);

	
	public void update(Auction a) ;
	
	
}
