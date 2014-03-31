package pl.krzaq.metalscrap.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.User;

public interface EventService {
	
	public List<Event> findEvents() ;
	
	public List<Event> findEvents(User user);
	
	public List<Event> findEvents(User user, Date from, Date to) ;
	
	public void save(Event event) ;
	
	public void delete(Event event) ;
}
