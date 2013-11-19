package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.User;


public class UserDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public User getUserByLogin(String login) {
		Session session = sessionFactory.openSession(); 
		session.beginTransaction().begin();
		List<User> users = session.getNamedQuery("User.findByLogin").setParameter("login", login).list() ;
		
		if (users.size()>0) {
			User u = (User)users.get(0) ;
			session.getTransaction().commit();
			
			return u;
		} else {
			session.getTransaction().commit();
			return null ;
		}
		
		
		
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
