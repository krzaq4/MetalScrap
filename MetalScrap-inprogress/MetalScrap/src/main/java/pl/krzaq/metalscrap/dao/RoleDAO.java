package pl.krzaq.metalscrap.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Role;

@Transactional
public class RoleDAO {

	@Autowired
	private SessionFactory sessionFactory ;

	
	public Role findRoleByName(String name){
		
		return (Role) sessionFactory.getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("name", name)).list().get(0) ;
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
}
