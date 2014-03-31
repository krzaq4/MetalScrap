package pl.krzaq.metalscrap.listener;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class SecurityLogoutFilter extends LogoutFilter {

	
	public SecurityLogoutFilter(String logoutSuccessUrl,
			LogoutHandler[] handlers) {
		super(logoutSuccessUrl, handlers);
		// TODO Auto-generated constructor stub
	}
	

	public SecurityLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
			LogoutHandler[] handlers) {
		super(logoutSuccessHandler, handlers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		Calendar cal = new GregorianCalendar() ;
		cal.set(Calendar.MILLISECOND, 0);
		
		User user = ServicesImpl.getUserService().getLoggedinUser() ;
		Event event = new Event() ;
		event.setDate(cal.getTime());
		event.setName("Logged in to application");
		event.setType(Event.TYPE_LOGIN);
		if(user!=null) {
			event.setUser(user);
			
			ServicesImpl.getEventService().save(event);
		}
		
		
		super.doFilter(req, res, chain);
	}

}
