package pl.krzaq.metalscrap.pages;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;

public class HomePage implements Initiator, InitiatorExt {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
	
		page.setAttribute("auctionsSubMenu", false) ;
		page.setAttribute("companiesSubMenu", false) ;
		Executions.getCurrent().getSession().setAttribute("page", page) ;
		

	}	
	
	


@Override
public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
	// TODO Auto-generated method stub
	//Executions.getCurrent().getSession().setAttribute("oldPage", arg0.getId()) ;
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
		
		for (String attribute:p.getAttributes().keySet()){
			
			Executions.getCurrent().getSession().removeAttribute(attribute) ;
			
		}
		
	}
	
	
protected void setPageData(Page p) {
		
		
		for (String attribute:p.getAttributes().keySet()){
			
			Executions.getCurrent().getSession().setAttribute(attribute, p.getAttribute(attribute)) ;
			
		}
		
	}

protected void getPageData(Page p){
	
	for (String attribute:Executions.getCurrent().getSession().getAttributes().keySet()){
		
		p.setAttribute(attribute, Executions.getCurrent().getSession().getAttribute(attribute)) ;
		
	}
}
	

}
