package pl.krzaq.metalscrap.pages;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;

public class Login extends HomePage {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doInit(page, arg1);
		
		
		Address main = new Address() ;
		Address additional = new Address() ;
		Company company = new Company() ;
		User user = new User() ;
		Set<User> users = new HashSet<User>() ;
		Set<Role> roles = new HashSet<Role>() ;
		user.setRoles(roles);
		company.setUsers(users);
		company.setAddressMain(main);
		company.setAddressAdditional(additional);
		page.setAttribute("user", user) ;
		page.setAttribute("company", company) ;
		
	}

	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(arg0, arg1);
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
		super.doFinally();
	}

	
	
	
	
	
	
}
