package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.PaymentMethod;

public class PaymentMethodDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<PaymentMethod> findAll() {
		List<PaymentMethod> result = new ArrayList<PaymentMethod>() ;
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		result =  (List<PaymentMethod>) session.getNamedQuery("PaymentMethod.findAll").list() ;
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
