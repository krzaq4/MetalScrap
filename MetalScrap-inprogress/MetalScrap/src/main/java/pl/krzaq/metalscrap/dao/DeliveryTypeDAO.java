package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.DeliveryType;

public class DeliveryTypeDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<DeliveryType> findAll() {
		List<DeliveryType> result = new ArrayList<DeliveryType>() ;
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		result =  (List<DeliveryType>) session.getNamedQuery("DeliveryType.findAll").list() ;
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
