package pl.krzaq.metalscrap.pages;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zk.ui.util.PageSerializationListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;
import org.springframework.security.core.userdetails.UserDetails;

import pl.krzaq.metalscrap.listener.ComponentEventInterceptor;
import pl.krzaq.metalscrap.model.Event;
import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.UserService;

import pl.krzaq.metalscrap.service.impl.UserServiceImpl;
import pl.krzaq.metalscrap.utils.ApplicationContextProvider;
import pl.krzaq.metalscrap.utils.Constants;
import pl.krzaq.metalscrap.utils.Utilities;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HomePage implements Initiator, InitiatorExt {

	
	private UserServiceImpl userService ;
	User currentUser = null ;
	
	
	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		
		
		Executions.getCurrent().getDesktop().addListener(new ComponentEventInterceptor());
		
		
		// wyświetlanie sub menu
		page.setAttribute("auctionsSubMenu", false);
		page.setAttribute("companiesSubMenu", false);

		
		
		userService = (UserServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("userService") ;
		
		boolean isUser = false;
		boolean isAdmin = false;
		boolean isSuperAdmin = false;
		boolean isLoggedIn = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal())!=null ;
		
		
		Boolean isCategoriesVisible = Boolean.valueOf(Utilities.getServices().getConfigService().findByKey("auction_categories_visible").getValue()) ;
		Boolean isCommoditiesVisible = Boolean.valueOf(Utilities.getServices().getConfigService().findByKey("auction_commodities_visible").getValue());
		boolean userVerificationModeAuto = Boolean.valueOf(Utilities.getServices().getConfigService().findByKey("user.verification.mode.auto").getValue()).booleanValue() ;
		
		
		if ( isLoggedIn ) {
			String login = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() ;
			System.out.println("Logged in user: "+login) ;
			
			// Aktualnie zalogowany użytkownik
			currentUser =userService.getUserByLogin(login); 
			if (currentUser != null) {
				
				currentUser = Utilities.getServices().getUserService().getUserById(currentUser.getId()) ;
				page.setAttribute("currentUser", currentUser);
			Set<Role> roles = currentUser.getRoles() ;
			//Iterator<Role> iterator = .iterator() ; 
			
				for(Role role:roles) {
					if(role!=null) {
						String roleName = role.getName();

						if (roleName.equalsIgnoreCase(Constants.ROLE_USER))
							isUser = true ;
						if(roleName.equalsIgnoreCase(Constants.ROLE_ADMIN))
							isAdmin = true ;
						if(roleName.equalsIgnoreCase(Constants.ROLE_SUPERADMIN))
							isSuperAdmin = true ;
					}
				}

			}
			
		}
		
		

		// Uprawnienia użytkownika

		page.setAttribute("isUser", isUser);
		page.setAttribute("isAdmin", isAdmin);
		page.setAttribute("isSuperAdmin", isSuperAdmin);
		
		page.setAttribute("isCommoditiesVisible", isCommoditiesVisible) ;
		page.setAttribute("isCategoriesVisible", isCategoriesVisible) ;
		page.setAttribute("userVerificationModeAuto", userVerificationModeAuto) ;
		page.setAttribute("subMenu", false) ;

	}

	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {
		
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		String pref = page.getId() ;
		
		Enumeration<String> keys = ses.getAttributeNames();
		
		while(keys.hasMoreElements()) {
			
			String key = keys.nextElement() ;
			
			if (key.substring(0, pref.length()).equalsIgnoreCase(pref) && pref.length()>0) {
				
				page.setAttribute(key.substring(pref.length(), key.length()), ses.getAttribute(key)) ;
				
			}
			
		}
		
		/*
		AnnotateDataBinder binder = (AnnotateDataBinder) ses.getAttribute("binder") ;
		
		if (ses.getAttribute(pref+"values")!=null && binder!=null) {
			
			Map<Component, Object> values = (Map<Component, Object>) ses.getAttribute(pref+"values") ;
			
			Iterator<Component> it = values.keySet().iterator() ;
			
			while(it.hasNext()) {
			
				Component cmp = it.next() ;
				String cmpId = cmp.getId() ;
				
				if (cmp instanceof Textbox) {
					String exp = binder.getBinding(cmp, "value").getExpression() ; 
					page.setAttribute(exp.substring(0, exp.indexOf(".")), ses.getAttribute(pref+exp.substring(0, exp.indexOf(".")))) ;
					
					((Textbox)cmp).setValue((String)values.get(cmp)) ;
					
				} else if (cmp instanceof Combobox) {
					
					((Combobox)cmp).setSelectedItem((Comboitem)values.get(cmp)) ;
					
				} else if (cmp instanceof Checkbox) {
					
					((Checkbox)cmp).setChecked(((Boolean)values.get(cmp)).booleanValue()) ;
					
				}
				
			}
			
			
		}
*/		
		
		
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doFinally() throws Exception {
		

	}

	protected void clearPageData(Page p) {

		for (String attribute : p.getAttributes().keySet()) {

			Executions.getCurrent().getSession().removeAttribute(attribute);

		}

	}

	protected void setPageData(Page p) {

		for (String attribute : p.getAttributes().keySet()) {

			Executions.getCurrent().getSession()
					.setAttribute(attribute, p.getAttribute(attribute));

		}

	}

	protected void getPageData(Page p) {

		for (String attribute : Executions.getCurrent().getSession()
				.getAttributes().keySet()) {

			p.setAttribute(attribute, Executions.getCurrent().getSession()
					.getAttribute(attribute));

		}
	}

	

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	

}
