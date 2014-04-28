package pl.krzaq.metalscrap.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

import pl.krzaq.metalscrap.model.LangLabel;

import pl.krzaq.metalscrap.utils.Utilities;

public class Translations extends HomePage {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doInit(page, arg1);
		
		List<LangLabel> aboutLabels = Utilities.getServices().getLangLabelService().findLikeKey("%about%") ;
		List<LangLabel> adminLabels = Utilities.getServices().getLangLabelService().findLikeKeyUnique("%admin%") ;
		List<LangLabel> adminLabelsAll = Utilities.getServices().getLangLabelService().findLikeKey("%admin%") ;
		List<LangLabel> auctionLabels = Utilities.getServices().getLangLabelService().findLikeKey("%auction%") ;
		List<LangLabel> cmsLabels = Utilities.getServices().getLangLabelService().findLikeKey("%cms%") ;
		List<LangLabel> regulationLabels = Utilities.getServices().getLangLabelService().findLikeKey("%regulation%") ;
		List<LangLabel> helpLabels = Utilities.getServices().getLangLabelService().findLikeKey("%help%") ;
		List<LangLabel> contactLabels = Utilities.getServices().getLangLabelService().findLikeKey("%contact%") ;
		List<String> langs = Utilities.getServices().getLangLabelService().findAllLangs() ;
		Collections.sort(langs);
		Collections.sort(adminLabels);
		Collections.sort(adminLabelsAll);
		
		
		List<String> availableLanguages = Utilities.getServices().getLangLabelService().findAllLangs() ;//Arrays.asList(Locale.getISOCountries()) ;
		List<String> allLanguages = new ArrayList<String>(Arrays.asList(Locale.getISOLanguages())) ;
		allLanguages.removeAll(availableLanguages) ;
		
		List<String> languages = new ArrayList<String>() ;
		languages.add(availableLanguages.get(0)) ;
		languages.add(availableLanguages.get(1)) ;
		
		page.setAttribute("languages", languages) ;
		
		page.setAttribute("lang1", availableLanguages.get(0)) ;
		page.setAttribute("lang2", availableLanguages.get(1)) ;
		page.setAttribute("availableLanguages", availableLanguages) ;
		page.setAttribute("allLanguages", allLanguages) ;
		page.setAttribute("langs", langs) ;
		page.setAttribute("aboutLabels", aboutLabels) ;
		page.setAttribute("adminLabels", adminLabels) ;
		page.setAttribute("adminLabelsAll", adminLabelsAll) ;
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
