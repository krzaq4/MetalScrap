package pl.krzaq.metalscrap.pages.common;

import java.util.Iterator;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Constants;

public class BasePage implements Initiator, InitiatorExt {

	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub

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

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		
		
		boolean isUser = false;
		boolean isAdmin = false;
		boolean isSuperAdmin = false;
		boolean isLoggedIn = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal())!=null ;
		
		if ( isLoggedIn ) {
			String login = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() ;
			System.out.println("Logged in user: "+login) ;
			
			// Aktualnie zalogowany użytkownik
			User currentUser = ServicesImpl.getUserService().getUserByLogin(login); 
			if (currentUser != null) {
			page.setAttribute("currentUser", currentUser);

			Iterator<Role> iterator = currentUser.getRoles().iterator() ; 
			
				while (iterator.hasNext()) {

					String roleName = iterator.next().getName();

					if (roleName.equalsIgnoreCase(Constants.ROLE_USER))
						isUser = true ;
					if(roleName.equalsIgnoreCase(Constants.ROLE_ADMIN))
						isAdmin = true ;
					if(roleName.equalsIgnoreCase(Constants.ROLE_SUPERADMIN))
						isSuperAdmin = true ;

				}

			}
			
		}
		
		

		// Uprawnienia użytkownika
		page.setAttribute("isLoggedIn", isLoggedIn) ;
		page.setAttribute("isUser", isUser);
		page.setAttribute("isAdmin", isAdmin);
		page.setAttribute("isSuperAdmin", isSuperAdmin);
		

	}

}
