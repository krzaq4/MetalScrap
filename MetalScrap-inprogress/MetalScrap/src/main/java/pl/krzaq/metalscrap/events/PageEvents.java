package pl.krzaq.metalscrap.events;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;

public class PageEvents {

	public void showSubMenu(String dest, Component c, AnnotateDataBinder binder) {
	
		Page page = (Page) Executions.getCurrent().getSession().getAttribute("page") ;
		
		//Page page = c.getPage() ;
		
		if (page.getAttribute("prevSubMenu")!=null) {
			
			Component prev = (Component) page.getAttribute("prevSubMenu") ;
			prev.setVisible(false) ;
			binder.loadComponent(prev);
		}
		
		
		Component toShow = page.getFellow("men").getFellow(dest+"SubMenu") ;
		if (toShow instanceof Menubar) {
			if(((Menubar)toShow).getOrient().equalsIgnoreCase("vertical")){
			//	((Menubar)toShow).setLeft( ((Menuitem)c). );
			}
		}
		
		toShow.setVisible(true) ;
		binder.loadComponent(toShow);
		
		page.setAttribute("prevSubMenu", toShow) ;
		
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
		if (session != null) {
		   session.invalidate();
		}
		Executions.getCurrent().sendRedirect("/app/home");
	}
	
	
}
