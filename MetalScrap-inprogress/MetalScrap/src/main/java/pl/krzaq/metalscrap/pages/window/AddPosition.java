package pl.krzaq.metalscrap.pages.window;

import java.util.Map;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AddPosition implements Initiator {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		page.setAttribute("commodityTypes", ServicesImpl.getCommodityTypeService().findAll()) ;
		page.setAttribute("commodity", new Commodity()) ;
	}

}
