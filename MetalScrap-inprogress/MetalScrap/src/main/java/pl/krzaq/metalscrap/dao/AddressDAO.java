package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Company;

@Transactional
public class AddressDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	public List<Address> findAll() {
		
		return (List<Address>) sessionFactory.getCurrentSession().getNamedQuery("Address.findAll").list() ;
		
	}
	
	public List<Address> findByCompany(Company company) {
		
		Query result = sessionFactory.getCurrentSession().getNamedQuery("Address.findByCompany") ;
		result.setParameter("company", company) ;
		return (List<Address>) result.list() ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
}
