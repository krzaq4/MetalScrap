package pl.krzaq.metalscrap.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.User;

@Transactional
@Component(value="eventDAO")
public class EventDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	public void save(Event event) {
		sessionFactory.getCurrentSession().saveOrUpdate(event);
	}
	
	public void delete(Event event) {
		sessionFactory.getCurrentSession().delete(event);
	}
	
	public List<Event> findEvents() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class) ;
		return criteria.list() ;
	}
	
	public List<Event> findEvents(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class) ;
		criteria.add(Restrictions.eq("user", user)) ;
		return criteria.list() ;
	}
	
	public List<Event> findEvents(User user, Date from, Date to) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class) ;
		criteria.add(Restrictions.eq("user", user)) ;
		
		if(from!=null) {
			criteria.add(Restrictions.ge("date", from));
		}
		
		if(to!=null) {
			criteria.add(Restrictions.le("date", to));
		}
		return criteria.list() ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
