package pl.krzaq.metalscrap.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.model.PaymentMethod;
import pl.krzaq.metalscrap.model.User;

public interface AuctionService {

	
	
	public List<Auction> findAll() ;
	
	public List<Auction> findByStatus(AuctionStatus status) ;
	
	public List<Auction> findByStartTime(Date start) ;
	
	public List<Auction> findByEndTime(Date end) ;
	
	public List<AuctionStatus> findAllStatuses() ;
	
	public AuctionStatus findStatusByCode(int code);

	public Auction findById(Long id) ;
	
	public Auction findWithCollection(Long id) ;
	
	public Auction findByName(String name) ;
	
	public Auction findByNumber(String number);
	
	public List<Auction> findByObserver(User user) ;
	
	public List<Auction> findByCategory(Category category) ;
	
	public List<Auction> findByCategoryDown(Category category, AuctionStatus status) ;
	
	public List<Auction> filter(String phrase, AuctionStatus status, PaymentMethod method, DeliveryType type, Date startDate, Date endDate) ;
	
	public void save(Auction a);

	public void update(Auction a) ;
	
	public void delete(Auction a) ;
	
	
}
