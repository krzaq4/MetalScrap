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
		
		final Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null) {
            for (int j = cookies.length; --j >= 0;) {
               if (cookies[j].getName().equals("my.locale")) {
                    //determine the locale
                    String val = cookies[j].getValue();
                    Locale locale = org.zkoss.util.Locales.getLocale(val);
                    session.setAttribute(Attributes.PREFERRED_LOCALE, locale);
                    System.out.println("Locale "+locale) ;
                    return;
                }
            }
        }

	}

}
