package pl.krzaq.metalscrap.utils;

import java.util.Locale;

import javax.servlet.http.Cookie;

import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.RequestInterceptor;
import javax.servlet.http.HttpServletRequest;


public class LocaleDetectorInterceptor implements RequestInterceptor {

	@Override
	public void request(Session session, Object request, Object response) {
		
		Locale locale = ((HttpServletRequest) request).getLocale();
        
        session.setAttribute(Attributes.PREFERRED_LOCALE, locale);
        
        

	}

}
