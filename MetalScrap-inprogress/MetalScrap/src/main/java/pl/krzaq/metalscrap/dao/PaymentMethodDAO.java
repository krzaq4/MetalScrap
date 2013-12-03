package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.PaymentMethod;

@Transactional
public class PaymentMethodDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<PaymentMethod> findAll() {
		return sessionFactory.getCurrentSession().getNamedQuery("PaymentMethod.findAll").list() ;
		
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
