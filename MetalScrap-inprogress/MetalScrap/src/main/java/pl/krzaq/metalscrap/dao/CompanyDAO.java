package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Company;

public class CompanyDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Company> findAll() {
		List<Company> result = new ArrayList<Company>() ;
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		result =  (List<Company>) session.getNamedQuery("Company.findAll").list() ;
		session.getTransaction().commit();
		session.close() ;
		return result ;
	}

	
	public void saveCompany(Company company) {
		
		Session session = sessionFactory.openSession() ;
		session.beginTransaction().begin(); ;
		session.save(company) ;
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
