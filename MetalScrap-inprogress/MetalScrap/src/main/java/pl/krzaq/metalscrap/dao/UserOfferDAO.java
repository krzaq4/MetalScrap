package pl.krzaq.metalscrap.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.UserOffer;

@Transactional
public class UserOfferDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	public List<UserOffer> findAll() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("UserOffer.findAll").list() ;
	}
	
	public List<UserOffer> findByAuction(Auction auction) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("UserOffer.findByAuction").setParameter("auction", auction).list() ;
		
	}
	
	public UserOffer findById(Long id) {
		
		return (UserOffer) sessionFactory.getCurrentSession().getNamedQuery("UserOffer.findById").setParameter("id", id).list().get(0) ;
	}
	
	public List<UserOffer> findByDate(Date start, Date end){
		
		return sessionFactory.getCurrentSession().createCriteria(UserOffer.class).add(Restrictions.between("issueDate", start, end)).list() ;
		
	}
	
	public void save(UserOffer userOffer) {
		
		sessionFactory.getCurrentSession().save(userOffer) ;
		
	}
	
	public void update(UserOffer userOffer){
		sessionFactory.getCurrentSession().update(userOffer);
		
	}
	
	public void delete(UserOffer userOffer) {
		sessionFactory.getCurrentSession().delete(userOffer);
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
}
