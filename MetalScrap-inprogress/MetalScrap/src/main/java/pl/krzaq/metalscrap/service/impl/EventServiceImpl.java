package pl.krzaq.metalscrap.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.dao.EventDAO;
import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.EventService;

@Component(value="eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDAO eventDAO ;
	
	@Override
	public List<Event> findEvents() {
		// TODO Auto-generated method stub
		return eventDAO.findEvents();
	}

	@Override
	public List<Event> findEvents(User user) {
		// TODO Auto-generated method stub
		return eventDAO.findEvents(user);
	}

	@Override
	public List<Event> findEvents(User user, Date from, Date to) {
		// TODO Auto-generated method stub
		return eventDAO.findEvents(user, from, to);
	}

	@Override
	public void save(Event event) {
		eventDAO.save(event);
		
	}

	@Override
	public void delete(Event event) {
		eventDAO.delete(event);
		
	}

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	
}
