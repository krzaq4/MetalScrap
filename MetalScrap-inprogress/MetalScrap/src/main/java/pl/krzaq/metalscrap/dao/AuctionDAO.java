package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;

@Transactional
public class AuctionDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Auction> findAll() {
		List<Auction> result = new ArrayList<Auction>() ;
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		result =  (List<Auction>) session.getNamedQuery("Auction.findAll").list() ;
		session.getTransaction().commit();
		session.close() ;
		return result ;
	}

	public void save(Auction a){
		
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		session.save(a) ;
		session.getTransaction().commit();
		session.close() ;
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
}
