package pl.krzaq.metalscrap.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Company;

@Transactional
@Component(value="companyDAO")
public class CompanyDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Company> findAll() {
		return sessionFactory.getCurrentSession().getNamedQuery("Company.findAll").list() ;
		
	}

	
	public void saveCompany(Company company) {
		
		sessionFactory.getCurrentSession().save(company) ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
