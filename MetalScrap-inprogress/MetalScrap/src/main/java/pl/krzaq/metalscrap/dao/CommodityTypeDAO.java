package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.CommodityType;


@Transactional
@Component(value="commodityTypeDAO")
public class CommodityTypeDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<CommodityType> findAll() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("CommodityType.findAll").list() ;
		
	}

	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
