package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.DeliveryType;

@Transactional
@Component(value="deliveryTypeDAO")
public class DeliveryTypeDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<DeliveryType> findAll() {
		List<DeliveryType> result = new ArrayList<DeliveryType>() ;
		
		
		return sessionFactory.getCurrentSession().getNamedQuery("DeliveryType.findAll").list() ;
		
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
