package pl.krzaq.metalscrap.events;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;

public class PageEvents {

	
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
	
	
}
