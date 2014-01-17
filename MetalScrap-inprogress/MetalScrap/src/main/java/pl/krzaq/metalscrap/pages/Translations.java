package pl.krzaq.metalscrap.pages;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class Translations extends HomePage {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doInit(page, arg1);
		
		List<LangLabel> aboutLabels = ServicesImpl.getLangLabelService().findLikeKey("about") ;
		List<LangLabel> adminLabels = ServicesImpl.getLangLabelService().findLikeKey("admin") ;
		List<LangLabel> auctionLabels = ServicesImpl.getLangLabelService().findLikeKey("auction") ;
		List<LangLabel> cmsLabels = ServicesImpl.getLangLabelService().findLikeKey("cms") ;
		List<LangLabel> regulationLabels = ServicesImpl.getLangLabelService().findLikeKey("regulation") ;
		List<LangLabel> helpLabels = ServicesImpl.getLangLabelService().findLikeKey("help") ;
		List<LangLabel> contactLabels = ServicesImpl.getLangLabelService().findLikeKey("contact") ;
		
		page.setAttribute("aboutLabels", aboutLabels) ;
		page.setAttribute("adminLabels", adminLabels) ;
		page.setAttribute("auctionLabels", auctionLabels) ;
		page.setAttribute("cmsLabels", cmsLabels) ;
		page.setAttribute("regulationLabels", regulationLabels) ;
		page.setAttribute("helpLabels", helpLabels) ;
		page.setAttribute("contactLabels", contactLabels) ;
	}

	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(page, arg1);
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return super.doCatch(arg0);
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
		super.doFinally();
	}

	
	
}
