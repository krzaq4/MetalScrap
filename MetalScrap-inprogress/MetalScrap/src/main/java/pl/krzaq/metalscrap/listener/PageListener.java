package pl.krzaq.metalscrap.listener;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.PageSerializationListener;
import org.zkoss.zk.ui.util.RequestInterceptor;
import org.zkoss.zk.ui.util.UiLifeCycle;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

public class PageListener implements UiLifeCycle {

	@Override
	public void afterComponentAttached(Component arg0, Page arg1) {
		// TODO Auto-generated method stub
		//System.out.println(arg1.getTitle()+" | Component attached  -  "+arg0.getId()+" / "+arg0.getClass().getCanonicalName()) ;

	}

	@Override
	public void afterComponentDetached(Component arg0, Page arg1) {
		
	}

	@Override
	public void afterComponentMoved(Component arg0, Component arg1,
			Component arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPageAttached(Page arg0, Desktop arg1) {
	
		AnnotateDataBinder binder = (AnnotateDataBinder) arg0.getAttribute("binder") ;
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		ses.setAttribute("binder", binder);
		
		/*String oldPageId = (String) Executions.getCurrent().getSession().getAttribute("oldPage") ;
		Page oldPage = Executions.getCurrent().getDesktop().getPage(oldPageId) ;
		setPageData(oldPage) ;*/
		
	}

	@Override
	public void afterPageDetached(Page p, Desktop arg1) {
	

	}
	
	
	
private void setPageData(Page p) {
		
		AnnotateDataBinder b = new AnnotateDataBinder(p) ;
		b.saveAll();
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		for (String attribute:p.getAttributes().keySet()){
			if (attribute.substring(0, 2).equalsIgnoreCase("ms_")) {
			ses.setAttribute(attribute, p.getAttribute(attribute)) ;
			}
		}
		
	}




}
