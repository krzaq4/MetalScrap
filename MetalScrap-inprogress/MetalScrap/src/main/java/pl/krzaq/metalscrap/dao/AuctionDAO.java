package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.model.Auction;

public class AuctionDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Auction> findAll() {
		
		return (List<Auction>) sessionFactory.getCurrentSession().getNamedQuery("Auction.findAll").list() ;
		
	}
	
	
	
	
}
