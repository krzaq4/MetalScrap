package pl.krzaq.metalscrap.listener;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.utils.Utilities;


public class SecurityLogoutFilter implements LogoutSuccessHandler {

	


	/*@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		Calendar cal = new GregorianCalendar() ;
		cal.set(Calendar.MILLISECOND, 0);
		
		User user = Utilities.getServices().getUserService().getLoggedinUser() ;
		Event event = new Event() ;
		event.setDate(cal.getTime());
		event.setName("Logged out from application");
		event.setType(Event.TYPE_LOGOUT);
		if(user!=null) {
			event.setUser(user);
			
			Utilities.getServices().getEventService().save(event);
		}
		
	}*/


	@Override
	public void onLogoutSuccess(HttpServletRequest arg0,
			HttpServletResponse arg1, Authentication arg2) throws IOException,
			ServletException {
		Calendar cal = new GregorianCalendar() ;
		cal.set(Calendar.MILLISECOND, 0);
		
		User user = Utilities.getServices().getUserService().getUserByLogin(arg2.getName()) ;
		Event event = new Event() ;
		event.setDate(cal.getTime());
		event.setName("Logged out from application");
		event.setType(Event.TYPE_LOGOUT);
		if(user!=null) {
			event.setUser(user);
			
			Utilities.getServices().getEventService().save(event);
		}
		
	}

}
