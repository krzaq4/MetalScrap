
package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
	
	
	private Session session ;
	
	
	
	
	public List<Auction> findAll() {
		
		return (List<Auction>) sessionFactory.getCurrentSession().getNamedQuery("Auction.findAll").list() ;
		
	}
	
	public List<Auction> findByStatus(AuctionStatus status) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Auction.findByStatus").setParameter("status", status).list() ;
		
	}
	
	public void save(Object o) {
		sessionFactory.getCurrentSession().save(o);
	}
	
	public List<Long> findIds(AuctionStatus status, Date from, Date to) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Auction.class) ;
		if (status!=null)
			criteria.add(Restrictions.eq("status", status)) ;
		
		if (from!=null) 
			criteria.add(Restrictions.ge("startDate", from)) ;
		
		if (to!=null)
			criteria.add(Restrictions.le("endDate", to)) ;
		
		ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.property("id"));
	    
		criteria.setProjection(projList) ;
		
		return criteria.list() ;
		
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
		
		return sessionFactory.getCurrentSession().createCriteria(Auction.class).add(Restrictions.le("endDate", to)).list() ;
		
	}
	
	public List<Auction> findByObserver(User user) {
		return sessionFactory.getCurrentSession().createCriteria(Auction.class).createAlias("obeservers", "observer").add(Restrictions.eq("observer.id", user.getId())).list() ;
	}
	
	public List<Auction> findActiveByUser(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Auction.class, "auction") ;
		criteria.createCriteria("auction.userOffers", "offers", JoinType.LEFT_OUTER_JOIN)
		.createAlias("offers.user", "user")
		.add(Restrictions.eq("user", user)) ;
		
		return criteria.list() ;
	}
	
	public List<Auction> findLostByUser(User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Auction.class, "auction") ;
		criteria.add(Restrictions.ne("winnerUser", user))
				.createCriteria("auction.userOffers", "offers", JoinType.LEFT_OUTER_JOIN)
				.createAlias("offers.user", "user")
				.add(Restrictions.eq("user", user))
				;
		
		return criteria.list() ;
		
	}
	
	public List<Auction> findWonByUser(User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Auction.class, "auction") ;
		criteria.add(Restrictions.eq("winnerUser", user)) ;
		
		return criteria.list() ;
		
	}
	
public List<Auction> findOwnedByUser(User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Auction.class, "auction") ;
		criteria.add(Restrictions.eq("ownerUser", user)) ;
		
		return criteria.list() ;
		
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
		
		Criteria c1 = sessionFactory.getCurrentSession().createCriteria(Auction.class, "auction").setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).add(Restrictions.idEq(id)).createCriteria("auction.userOffers", "offers", JoinType.LEFT_OUTER_JOIN).createCriteria("auction.obeservers", "observers", JoinType.LEFT_OUTER_JOIN).createCriteria("auction.files", "files", JoinType.LEFT_OUTER_JOIN).createCriteria("auction.properties", "props", JoinType.LEFT_OUTER_JOIN).createCriteria("props.attributes", "attrs", JoinType.LEFT_OUTER_JOIN).createCriteria("attrs.values", JoinType.LEFT_OUTER_JOIN) ; //.createCriteria("properties", JoinType.LEFT_OUTER_JOIN);
		
		
		return (Auction) c1.uniqueResult() ;
	}
	
	

	public List<Commodity> findAuctionCommodities(Auction auction) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Commodity.findByAuction").setParameter("auction", auction).list() ;
	}
	
	public void update(Auction a) {
	
		sessionFactory.getCurrentSession().merge(a);
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
