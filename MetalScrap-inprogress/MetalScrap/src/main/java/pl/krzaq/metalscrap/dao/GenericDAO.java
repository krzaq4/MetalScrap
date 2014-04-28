package pl.krzaq.metalscrap.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class GenericDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public void save(Object o) {
		sessionFactory.getCurrentSession().saveOrUpdate(o);
	}
	
}
