package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.CommodityType;

public class CommodityTypeDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<CommodityType> findAll() {
		List<CommodityType> result = new ArrayList<CommodityType>() ;
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		result =  (List<CommodityType>) session.getNamedQuery("CommodityType.findAll").list() ;
		session.getTransaction().commit();
		session.close() ;
		return result ;
	}

	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
