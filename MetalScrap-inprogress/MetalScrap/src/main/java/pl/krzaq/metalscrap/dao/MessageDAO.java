package pl.krzaq.metalscrap.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Message;
import pl.krzaq.metalscrap.model.User;

@Component(value="messageDAO")
public class MessageDAO {

	
	@Autowired
	private SessionFactory sessionFactory  ;
	
	
	public Message findById(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "message") ;
		criteria.add(Restrictions.idEq(id)) ;
		return (Message) criteria.uniqueResult() ;
	}
	
	public List<Message> findByAuction(Auction auction) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "message") ;
		criteria.add(Restrictions.eq("auction", auction)) ;
		
		return criteria.list() ;
	}
	
	public List<Message> findByUser(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "message") ;
		criteria.add(Restrictions.eq("user", user)) ;
		
		return criteria.list() ;
	}
	
	public List<Message> findByParent(Message message) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "message") ;
		criteria.add(Restrictions.eq("parent", message)) ;
		
		return criteria.list() ;
	}
	
	public List<Message> findReadByUser(User user, Boolean read) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "msg") ;
		criteria.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("read", read)) ;
		
		return criteria.list() ;
				
	}
	
	public List<Message> findReadByUser(Auction auction, User user, Boolean read) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "msg") ;
		criteria.add(Restrictions.eq("user", user))
				.add(Restrictions.eq("auction", auction))
				.add(Restrictions.eq("read", read)) ;
		
		return criteria.list() ;
	}
	
	public List<Message> findByDateIssued(Date from, Date to, Auction auction, User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Message.class, "message") ;
		if(from!=null && to!=null) {
			criteria.add(Restrictions.between("issued", from, to)) ;
		} 
		else 
		if (from!=null){
			criteria.add(Restrictions.ge("issued", from)) ;
		} 
		else
		if(to!=null) {
			criteria.add(Restrictions.le("issued", to)) ;
		}
		
		if(auction!=null) {
			criteria.add(Restrictions.eq("auction", auction)) ;
		}
		
		if(user!=null) {
			criteria.add(Restrictions.eq("user", user)) ;
		}
		
		
		return criteria.list() ;
		
	}
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
