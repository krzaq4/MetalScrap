package pl.krzaq.metalscrap.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Treeitem;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AuctionCategories extends HomePage{
	
	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		super.doAfterCompose(arg0, arg1);
		
	}


	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return super.doCatch(arg0) ;
	}


	@Override
	public void doFinally() throws Exception {
		super.doFinally(); 
		
	}

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
	
		super.doInit(page, arg1);
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		Collections.sort(categories);
		
		
		page.setAttribute("parametr", new Property()) ;
		page.setAttribute("attr", new PropertyAttribute()) ;
		page.setAttribute("attrValuesVisible", false) ;
		page.setAttribute("category", null) ;
		page.setAttribute("categories", categories) ;

	}

}
