package pl.krzaq.metalscrap.pages;

import java.util.Map;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class HomePage implements Initiator {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
	
		page.setAttribute("auctionsSubMenu", false) ;
		page.setAttribute("companiesSubMenu", false) ;
		
		

	}

}
