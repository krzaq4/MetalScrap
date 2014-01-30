package pl.krzaq.metalscrap.pages;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class Config extends HomePage{

	
	
	
	
	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		super.doAfterCompose(arg0, arg1);
		
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

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
	
		super.doInit(page, arg1);
		pl.krzaq.metalscrap.model.Config commoditiesVisible = ServicesImpl.getConfigService().findByKey("auction_commodities_visible") ;
		pl.krzaq.metalscrap.model.Config categoriesVisible = ServicesImpl.getConfigService().findByKey("auction_categories_visible") ;
		
		List<pl.krzaq.metalscrap.model.Config> configs = ServicesImpl.getConfigService().findAll() ;
		page.setAttribute("configs", configs) ;
		
		page.setAttribute("commoditiesVisible", Boolean.valueOf(commoditiesVisible.getValue()).booleanValue()) ;
		page.setAttribute("categoriesVisible", Boolean.valueOf(categoriesVisible.getValue()).booleanValue()) ;
		
		
	}






	

	
	
	
	
	
}
