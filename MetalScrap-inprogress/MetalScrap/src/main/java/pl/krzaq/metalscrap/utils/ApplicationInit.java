package pl.krzaq.metalscrap.utils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

import pl.krzaq.metalscrap.lang.DBLabelLocator;

public class ApplicationInit implements WebAppInit {

	@Override
	public void init(WebApp arg0) throws Exception {
	
		
		Labels.register(new DBLabelLocator(""));

	}

}
