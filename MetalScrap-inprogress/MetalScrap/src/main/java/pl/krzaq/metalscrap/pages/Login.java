package pl.krzaq.metalscrap.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;

import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;

public class Login implements Initiator, InitiatorExt {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		// TODO Auto-generated method stub
		//super.doInit(page, arg1);
		
		
		Address main = new Address() ;
		Address additional = new Address() ;
		Company company = new Company() ;
		User user = new User() ;
		List<User> users = new ArrayList<User>() ;
		List<Role> roles = new ArrayList<Role>() ;
		user.setRoles(roles);
		company.setUsers(users);
		company.setAddressMain(main);
		company.setAddressAdditional(additional);
		page.setAttribute("user", user) ;
		page.setAttribute("company", company) ;
		
		boolean isUser = false;
		boolean isAdmin = false;
		boolean isSuperAdmin = false;
		
		page.setAttribute("isUser", isUser);
		page.setAttribute("isAdmin", isAdmin);
		page.setAttribute("isSuperAdmin", isSuperAdmin);
		
	}

	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		//super.doAfterCompose(arg0, arg1);
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
	//	super.doFinally();
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
}
