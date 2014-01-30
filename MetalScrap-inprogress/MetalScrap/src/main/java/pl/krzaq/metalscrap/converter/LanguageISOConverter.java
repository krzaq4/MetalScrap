package pl.krzaq.metalscrap.converter;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.TypeConverter;

public class LanguageISOConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		String localeCode = (String) arg0 ;
		Locale loc = new Locale(localeCode,localeCode) ;
		HttpSession session = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		Locale currentLocale = (Locale) session.getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		String country = loc.getDisplayLanguage(currentLocale) ;
		return country ;
	}

}
