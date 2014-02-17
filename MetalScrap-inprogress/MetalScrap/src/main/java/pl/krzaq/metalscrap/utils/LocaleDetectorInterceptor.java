package pl.krzaq.metalscrap.utils;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.RequestInterceptor;

import javax.servlet.http.HttpServletRequest;


public class LocaleDetectorInterceptor implements RequestInterceptor {

	@Override
	public void request(Session session, Object request, Object response) {
		
		Locale locale = ((HttpServletRequest) request).getLocale();
        String lang = locale.getLanguage() ;
        
        
        HttpServletRequest req = (HttpServletRequest) request ;
		HttpServletResponse res = (HttpServletResponse) response ;
		
		Cookie[] cookies = req.getCookies() ;
		
		boolean found = false ;
		if(cookies!=null)
		for (Cookie cook:cookies){
			if (cook.getName().equalsIgnoreCase(Attributes.PREFERRED_LOCALE)) {
				lang = cook.getValue() ;
				found = true ;
				locale = new Locale(lang, lang) ;
			}
		}
		
		
		
		if (!found) {
			res.addCookie(new Cookie(Attributes.PREFERRED_LOCALE, lang));
		}
		
		session.setAttribute(Attributes.PREFERRED_LOCALE, locale);
	}

}
