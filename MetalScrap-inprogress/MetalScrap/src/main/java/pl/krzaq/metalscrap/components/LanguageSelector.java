package pl.krzaq.metalscrap.components;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.utils.Utilities;


public class LanguageSelector extends Div {

	// Attributes
	private String title ;
	
	//-------------------
	
	
	private Locale locale ;	
	private Listbox langLbx ;
	
	
	public void onCreate() {
	
		locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		List<String> langs = Utilities.getServices().getLangLabelService().findAllLangs() ;
	
		langLbx = new Listbox() ;
		langLbx.setMold("select");
		langLbx.setWidth("auto");
		
		langLbx.setModel(new ListModelList<String>(langs));
		
		langLbx.setSelectedIndex( ( (ListModelList) langLbx.getModel() ).indexOf(locale.getLanguage()) );
		
		langLbx.addEventListener("onSelect", new EventListener<SelectEvent>(){

			@Override
			public void onEvent(SelectEvent arg0) throws Exception {
				
				onSelectLanguage() ;
				
			}
			
			
		}) ;
		
		this.appendChild(langLbx) ;
		
	}
	
	
	private void onSelectLanguage() {
		
		Page page = this.getPage() ;
		AnnotateDataBinder binder = (AnnotateDataBinder) page.getAttribute("binder") ;
		String lang = (String) langLbx.getSelectedItem().getValue() ;
		Locale locale = new Locale(lang, lang) ;
		Executions.getCurrent().getSession().setAttribute(Attributes.PREFERRED_LOCALE, locale) ;
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ;
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse() ;
		
		Cookie prefLoc = new Cookie(Attributes.PREFERRED_LOCALE, lang) ;
		
		Cookie[] cookies = request.getCookies() ;
		
		for (Cookie cook:cookies){
			if (cook.getName().equalsIgnoreCase(prefLoc.getName())) {
				cook.setMaxAge(0);
			}
		}
		
		response.addCookie(prefLoc);
		
		Labels.reset();
		Executions.getCurrent().sendRedirect(null);
		
	}
	
	
}
