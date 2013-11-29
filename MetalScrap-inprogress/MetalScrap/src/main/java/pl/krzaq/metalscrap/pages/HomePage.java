package pl.krzaq.metalscrap.pages;

import java.util.Iterator;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.springframework.security.core.userdetails.UserDetails;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.UserService;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.service.impl.UserServiceImpl;
import pl.krzaq.metalscrap.utils.ApplicationContextProvider;
import pl.krzaq.metalscrap.utils.Constants;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class HomePage implements Initiator, InitiatorExt {

	
	private UserServiceImpl userService ;
	
	
	
	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {

		// wyœwietlanie sub menu
		page.setAttribute("auctionsSubMenu", false);
		page.setAttribute("companiesSubMenu", false);

		Executions.getCurrent().getSession().setAttribute("page", page);

		userService = (UserServiceImpl) ApplicationContextProvider.getApplicationContext().getBean("userService") ;
		
		boolean isUser = false;
		boolean isAdmin = false;
		boolean isSuperAdmin = false;
		
		
		String login = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() ;
		System.out.println("Logged in user: "+login) ;
		
		
		// Aktualnie zalogowany u¿ytkownik
		User currentUser =userService.getUserByLogin(login); //ServicesImpl.getUserService().getUserByLogin(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()) ;
		if (currentUser != null) {
		page.setAttribute("currentUser", currentUser);

		Iterator<Role> iterator = currentUser.getRoles().iterator() ; 
		
			while (iterator.hasNext()) {

				String roleName = iterator.next().getName();

				isUser = roleName.equalsIgnoreCase(Constants.ROLE_USER);
				isAdmin = roleName.equalsIgnoreCase(Constants.ROLE_ADMIN);
				isSuperAdmin = roleName
						.equalsIgnoreCase(Constants.ROLE_SUPERADMIN);

			}

		}

		// Uprawnienia u¿ytkownika

		page.setAttribute("isUser", isUser);
		page.setAttribute("isAdmin", isAdmin);
		page.setAttribute("isSuperAdmin", isSuperAdmin);
		

	}

	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {
		
		
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub

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

	
	
	

}
