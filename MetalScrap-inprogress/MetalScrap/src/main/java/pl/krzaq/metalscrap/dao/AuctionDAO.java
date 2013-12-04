package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;

@Transactional
public class AuctionDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Auction> findAll() {
		
		return (List<Auction>) sessionFactory.getCurrentSession().getNamedQuery("Auction.findAll").list() ;
		
	}
	
	public List<Auction> findByStatus(int status) {
		
		return sessionFactory.getCurrentSession().createCriteria(Auction.class).add(Restrictions.eq("status", status)).list() ;
		
	}
	
	public int findCountByStatus(int status) {
		
		return ((Number)sessionFactory.getCurrentSession().createCriteria(AuctionStatus.class).add(Restrictions.eq("status", status)).setProjection(Projections.rowCount()).uniqueResult()).intValue() ;
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
	
	public AuctionStatus findStatusByCode(int code) {
		
		return (AuctionStatus) sessionFactory.getCurrentSession().createCriteria(AuctionStatus.class).add(Restrictions.eq("code", code)).list().get(0) ;
	}

	
	public List<AuctionStatus> findAllStatuses() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("AuctionStatus.findAll").list() ;
		
	}
	
	public void save(Auction a){
		
		sessionFactory.getCurrentSession().save(a) ;
		
		
	}

	
	public void update(Auction a) {
	
		sessionFactory.getCurrentSession().update(a);
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
}
