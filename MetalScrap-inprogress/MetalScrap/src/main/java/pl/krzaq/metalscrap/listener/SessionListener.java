package pl.krzaq.metalscrap.listener;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.zkoss.zk.ui.http.HttpSessionListener;

import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class SessionListener extends HttpSessionListener {

	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		super.attributeAdded(arg0);
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		super.attributeAdded(arg0);
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		super.attributeRemoved(arg0);
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		super.attributeRemoved(arg0);
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		super.attributeReplaced(arg0);
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		super.attributeReplaced(arg0);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		super.contextDestroyed(arg0);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		super.contextInitialized(event);
	}

	@Override
	public void sessionCreated(HttpSessionEvent evt) {
		// TODO Auto-generated method stub
		super.sessionCreated(evt);
		if(ServicesImpl.getUserService().getLoggedinUser()!=null) {
			Event event = new Event() ;
			Calendar cal = new GregorianCalendar() ;
			cal.set(Calendar.MILLISECOND, 0);
				
			event.setName("Logowanie do systemu") ;
			event.setType(Event.TYPE_LOGIN);
			event.setUser(ServicesImpl.getUserService().getLoggedinUser());
			event.setDate(cal.getTime());
			
			ServicesImpl.getEventService().save(event) ;
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent evt) {
		// TODO Auto-generated method stub
		super.sessionDestroyed(evt);
		Event event = new Event() ;
		Calendar cal = new GregorianCalendar() ;
		cal.set(Calendar.MILLISECOND, 0);
		if(ServicesImpl.getUserService().getLoggedinUser()!=null) {
			event.setName("Wylogowanie z systemu") ;
			event.setType(Event.TYPE_LOGOUT);
			event.setUser(ServicesImpl.getUserService().getLoggedinUser());
			event.setDate(cal.getTime());
		
			ServicesImpl.getEventService().save(event) ;
		}
	}

}
