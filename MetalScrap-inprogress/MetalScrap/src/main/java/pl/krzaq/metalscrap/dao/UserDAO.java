
package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.User;

@Transactional
public class UserDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public User getUserByLogin(String login) {
		
		User u =  (User)sessionFactory.getCurrentSession().getNamedQuery("User.findByLogin").setParameter("login", login).list().get(0) ;
		if (u!=null)
			Hibernate.initialize(u.getRoles());
		return u ;
		
		
	}
	
	public User getUserByEmail(String email) {
		
		User u =  (User)sessionFactory.getCurrentSession().getNamedQuery("User.findByEmail").setParameter("email", email).list().get(0) ;
		if (u!=null)
			Hibernate.initialize(u.getRoles());
		return u ;
	}
	
	public User getUserById(Long id) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class, "user").add(Restrictions.idEq(id)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).createCriteria("user.observed","observed", JoinType.LEFT_OUTER_JOIN).createCriteria("user.auctions", "auctions", JoinType.LEFT_OUTER_JOIN).createCriteria("user.userOffers", "offers", JoinType.LEFT_OUTER_JOIN).createCriteria("user.roles", "roles", JoinType.LEFT_OUTER_JOIN) ;
		//User u =  (User)sessionFactory.getCurrentSession().getNamedQuery("User.findById").setParameter("id", id).list().get(0) ;
		/*if (u!=null)
			Hibernate.initialize(u.getRoles());
		return u ;*/
		
		return (User)crit.uniqueResult() ;
		
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