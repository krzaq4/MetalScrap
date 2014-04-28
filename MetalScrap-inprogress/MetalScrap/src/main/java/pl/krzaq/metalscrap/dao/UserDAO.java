
package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;

@Transactional
@Component(value="userDAO")
public class UserDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public Role getRoleByName(String name){
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Role.class, "role");
		criteria.add(Restrictions.eq("name", name))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (Role) criteria.uniqueResult() ;
		
	}
	
	public List<User> getUsers() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class, "usr") ;
		criteria.createCriteria("usr.roles", "roles", JoinType.LEFT_OUTER_JOIN) ;
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY) ;
		//criteria.setProjection(Projections.)
		return criteria.list() ;
	}
	
	public User getUserbyFbId(String fbId) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class, "usr") 
				.add(Restrictions.eq("usr.fbId", fbId))
				.createCriteria("usr.auctions", "auctions", JoinType.LEFT_OUTER_JOIN)
				.createCriteria("usr.observed", "observed", JoinType.LEFT_OUTER_JOIN)
				.createCriteria("usr.userOffers", "offers", JoinType.LEFT_OUTER_JOIN)
				.createCriteria("usr.roles", "roles", JoinType.LEFT_OUTER_JOIN) ;
			
			return (User) criteria.uniqueResult() ;
			
		
	}
	
	public User getUserByLogin(String login) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class, "usr") 
			.add(Restrictions.eq("usr.login", login))
			.createCriteria("usr.auctions", "auctions", JoinType.LEFT_OUTER_JOIN)
			.createCriteria("usr.observed", "observed", JoinType.LEFT_OUTER_JOIN)
			.createCriteria("usr.userOffers", "offers", JoinType.LEFT_OUTER_JOIN)
			.createCriteria("usr.roles", "roles", JoinType.LEFT_OUTER_JOIN) ;
		
		return (User) criteria.uniqueResult() ;
		
		
	}
	
	public User getUserByEmail(String email) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class, "usr") 
				.add(Restrictions.eq("usr.email", email))
				.createCriteria("usr.auctions", "auctions", JoinType.LEFT_OUTER_JOIN)
				.createCriteria("usr.observed", "observed", JoinType.LEFT_OUTER_JOIN)
				.createCriteria("usr.userOffers", "offers", JoinType.LEFT_OUTER_JOIN) ;
			
			return (User) criteria.uniqueResult() ;
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