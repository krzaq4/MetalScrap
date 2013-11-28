package pl.krzaq.metalscrap.listener;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.UiLifeCycle;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

public class PageListener implements UiLifeCycle {

	@Override
	public void afterComponentAttached(Component arg0, Page arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterComponentDetached(Component arg0, Page arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterComponentMoved(Component arg0, Component arg1,
			Component arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPageAttached(Page arg0, Desktop arg1) {
		/*// TODO Auto-generated method stub
		System.out.println("afterPageAttached") ;
		String oldPageId = (String) Executions.getCurrent().getSession().getAttribute("oldPage") ;
		Page oldPage = Executions.getCurrent().getDesktop().getPage(oldPageId) ;
		setPageData(oldPage) ;*/
		
	}

	@Override
	public void afterPageDetached(Page p, Desktop arg1) {
		/*System.out.println("afterPageDetached") ;
		setPageData(p) ;*/

	}
	
	
	
private void setPageData(Page p) {
		
		AnnotateDataBinder b = new AnnotateDataBinder(p) ;
		b.saveAll();
		for (String attribute:p.getAttributes().keySet()){
			if (!attribute.equalsIgnoreCase("binder")) {
			Executions.getCurrent().getSession().setAttribute(attribute, p.getAttribute(attribute)) ;
			}
		}
		
	}
	

}
