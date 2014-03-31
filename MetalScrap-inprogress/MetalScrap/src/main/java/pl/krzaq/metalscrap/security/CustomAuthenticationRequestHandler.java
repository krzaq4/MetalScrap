package pl.krzaq.metalscrap.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class CustomAuthenticationRequestHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        
		Calendar cal = new GregorianCalendar() ;
		cal.set(Calendar.MILLISECOND, 0);
		
		User user = ServicesImpl.getUserService().getUserByLogin(authentication.getName()) ;
		Event event = new Event() ;
		event.setDate(cal.getTime());
		event.setName("Logged in to application");
		event.setType(Event.TYPE_LOGIN);
		if(user!=null) {
			event.setUser(user);
			
			ServicesImpl.getEventService().save(event);
		}
		
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
        if( defaultSavedRequest != null  ) {
            String requestUrl = defaultSavedRequest.getRequestURL() + "?" + defaultSavedRequest.getQueryString();
            getRedirectStrategy().sendRedirect(request, response, requestUrl);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
	
}
