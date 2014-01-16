
package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.User;

@Transactional
public class AuctionDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Auction> findAll() {
		
		return (List<Auction>) sessionFactory.getCurrentSession().getNamedQuery("Auction.findAll").list() ;
		
	}
	
	public List<Auction> findByStatus(AuctionStatus status) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Auction.findByStatus").setParameter("status", status).list() ;
		
	}
	
	public List<Auction> findByStartTime(Date start) {
		Calendar c = new GregorianCalendar() ;
		c.setTime(start);
		c.set(Calendar.MILLISECOND, 0);
		Date from = c.getTime() ;
		
		return sessionFactory.getCurrentSession().createCriteria(Auction.class).add(Restrictions.eq("startDate", from)).list() ;
		
	}
	
	public List<Auction> findByEndTime(Date end) {
		Calendar c = new GregorianCalendar() ;
		c.setTime(end);
		c.set(Calendar.MILLISECOND, 0);
		
		Date to = c.getTime() ;
		
		return sessionFactory.getCurrentSession().createCriteria(Auction.class).add(Restrictions.eq("endDate", to)).list() ;
		
	}
	
	public List<Auction> findByObserver(User user) {
		return sessionFactory.getCurrentSession().createCriteria(Auction.class).createAlias("obeservers", "observer").add(Restrictions.eq("observer.id", user.getId())).list() ;
	}
	
	public List<Auction> findByCategory(Category category) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Auction.findByCategory").setParameter("category", category).list() ;
	}
	
	public List<Auction> findByCategoryAndStatus(Category category, AuctionStatus status) {
		return sessionFactory.getCurrentSession().getNamedQuery("Auction.findByCategoryAndStatus").setParameter("category", category).setParameter("status", status).list() ;
	}
	
	public Auction findByName(String name) {
	
		return sessionFactory.getCurrentSession().getNamedQuery("Auction.findByName").setParameter("name", name).list().size()>0 ? (Auction)sessionFactory.getCurrentSession().getNamedQuery("Auction.findByName").setParameter("name", name).list().get(0): null ;
		
	}
	
	public Auction findByNumber(String number){
	
		return sessionFactory.getCurrentSession().getNamedQuery("Auction.findByNumber").setParameter("number", number).list().size()>0 ? (Auction) sessionFactory.getCurrentSession().getNamedQuery("Auction.findByNumber").setParameter("number", number).list().get(0) : null ;
		
	}
	
	public List<AuctionStatus> findAllStatuses() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("AuctionStatus.findAll").list();
	}
	
	public AuctionStatus findStatusByCode(int code) {
		
		return (AuctionStatus) sessionFactory.getCurrentSession().createCriteria(AuctionStatus.class).add(Restrictions.eq("code", code)).list().get(0) ;
	}

	public Auction findById(Long id) {
		
		return (Auction) sessionFactory.getCurrentSession().getNamedQuery("Auction.findById").setParameter("id", id).list().get(0) ; 
	}
	
	public void save(Auction a){
		
		sessionFactory.getCurrentSession().save(a) ;
		
		
	}

	public List<Commodity> findAuctionCommodities(Auction auction) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Commodity.findByAuction").setParameter("auction", auction).list() ;
	}
	
	public void update(Auction a) {
	
		sessionFactory.getCurrentSession().update(a);
	}
	
	public void delete(Auction a) {
		
		sessionFactory.getCurrentSession().delete(a);
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
}
