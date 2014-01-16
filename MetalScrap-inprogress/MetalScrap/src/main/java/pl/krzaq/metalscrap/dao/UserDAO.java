
package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.User;

@Transactional
public class UserDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public User getUserByLogin(String login) {
		
		User u =  (User)sessionFactory.getCurrentSession().getNamedQuery("User.findByLogin").setParameter("login", login).list().get(0) ;
		Hibernate.initialize(u.getRoles());
		return u ;
		
		
	}
	
	
	public void saveUser(User user) {
		
		
		sessionFactory.getCurrentSession().save(user) ;
		
	}
	
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}
	
	public void delete(User user) {
		sessionFactory.getCurrentSession().delete(user);
	}

public User getUserByLoginAndPass(String login, String pass) {
		
		return (User) sessionFactory.getCurrentSession().getNamedQuery("User.findByLoginAndPass").setParameter("login", login).setParameter("password", pass).list().get(0) ;
		
	}

public SessionFactory getSessionFactory() {
	return sessionFactory;
}

public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
}




}