package pl.krzaq.metalscrap.events;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class PageEvents {

	public void showSubMenu(String dest, Component c, AnnotateDataBinder binder) {
	
		Page page = (Page) Executions.getCurrent().getSession().getAttribute("page") ;
		
		if(page==null)
			page = c.getPage() ;
		
		
		
		
		Component toShow = page.getFellow("men").getFellow(dest+"SubMenu") ;
		
		
		
		
		if(page.getAttribute("subMenu")!=null) {
			Boolean subMenu = (Boolean) page.getAttribute("subMenu") ;
			
			if (page.getAttribute("prevSubMenu")!=null) {
				
				Component prev = (Component) page.getAttribute("prevSubMenu") ;
				if (prev!=toShow) {
					page.setAttribute("subMenu", false) ;
					binder.loadComponent(prev);
					page.setAttribute("subMenu", true) ;
					binder.loadComponent(toShow);
					page.setAttribute("subMenu", true) ;
				} else {
					
					page.setAttribute("subMenu", !subMenu) ;
					binder.loadComponent(toShow);
					//binder.loadComponent(prev);
					//page.setAttribute("subMenu", subMenu) ;
				}
				
			} else {
				
				
				page.setAttribute("subMenu", true) ;
				binder.loadComponent(toShow);
				page.setAttribute("subMenu", true) ;
			}
			
			
		}
		
		
		if (toShow instanceof Menubar) {
			if(((Menubar)toShow).getOrient().equalsIgnoreCase("vertical")){
			//	((Menubar)toShow).setLeft( ((Menuitem)c). );
			}
			
			
			
			page.setAttribute("prevSubMenu", toShow) ;
			
		}
		
		
		
	}
	
	public void showAuctionsSubeMenu(Component c, AnnotateDataBinder binder) {
		
		Page page = c.getPage() ;
		page.setAttribute("subMenuVisible", true) ;
		page.setAttribute("auctionsSubMenu", true) ;
		page.setAttribute("companiesSubMenu", false) ;
		binder.loadComponent(c);
		
	}
	
	public void showCompaniesSubMenu(Component c, AnnotateDataBinder binder) {
		Page page = c.getPage() ;
		page.setAttribute("subMenuVisible", true) ;
		page.setAttribute("auctionsSubMenu", false) ;
		page.setAttribute("companiesSubMenu", true) ;
		binder.loadComponent(c);
	}
	
	
	public void open(String url) {
		
		Executions.getCurrent().sendRedirect(url);
		
	}
	
	public void logout() {
		
		SecurityContextHolder.clearContext();
		HttpSession session = (HttpSession)Executions.getCurrent().getSession().getNativeSession();
		Executions.getCurrent().sendRedirect("home");
		if (session != null) {
		   session.invalidate();
		}
		
	}
	
	
	
	
	
}
