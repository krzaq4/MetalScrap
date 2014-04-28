package pl.krzaq.metalscrap.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.components.TranslationsGrid;
import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.utils.Utilities;

public class LangEvents {

	
	public void updateLabels(Grid grid, AnnotateDataBinder binder){
		
		List<Component> rows =grid.getRows().getChildren() ;
		
		for (Component c:rows) {
			Row row = (Row) c ;
			
			List<Component> rowChildren = row.getChildren() ;
			
			for (Component child:rowChildren) {
				if(child instanceof Textbox) {
					Long id = Long.valueOf(((Textbox)child).getName()) ;
					LangLabel toUpdate = Utilities.getServices().getLangLabelService().findById(id) ;
					if (!toUpdate.getLvalue().equals(((Textbox)child).getValue())) {
						toUpdate.setLvalue( ((Textbox)child).getValue() );
						Utilities.getServices().getLangLabelService().update(toUpdate);
					}
				}
			}
		}
		
		
		
		
		
		Labels.reset();
		
	}
	
	public void showInLanguages(String lang1, String lang2, Tabpanels panels, AnnotateDataBinder binder) {
	
		List<String> languages = new ArrayList<String>() ;
		languages.add(lang1) ;
		languages.add(lang2) ;
		
		panels.getPage().setAttribute("languages", languages) ;
		
		List<Component> tabPanels = panels.getChildren() ;
		for (Component c:tabPanels) {
			if (c instanceof Tabpanel) {
				
				List<Component> tabs = ((Tabpanel)c).getChildren() ;
				for (Component cc:tabs){
					if (cc instanceof TranslationsGrid) {
						binder.loadComponent(cc);
					}
				}
				
			}
		}
		
		
	}

	public void addNewLanguage(String lang, Combobox cmbx1,Combobox cmbx2, Combobox cmbx3 ,Combobox cmbx4, AnnotateDataBinder binder) {
		
		Page page = cmbx1.getPage() ;
		List<String> allLangs = Utilities.getServices().getLangLabelService().findAllLangs() ;
		if (!allLangs.contains(lang)) {
			
			List<String> allKeys = Utilities.getServices().getLangLabelService().findAllKeysUnique() ;
			
			for (String key:allKeys){
				
				Utilities.getServices().getLangLabelService().save(new LangLabel(key,lang,""));
				
			}
			
			
			List<String> availableLanguages = Utilities.getServices().getLangLabelService().findAllLangs() ;//Arrays.asList(Locale.getISOCountries()) ;
			List<String> allLanguages = new ArrayList<String>(Arrays.asList(Locale.getISOLanguages())) ;
			allLanguages.removeAll(availableLanguages) ;
			
			page.setAttribute("availableLanguages", availableLanguages) ;
			page.setAttribute("allLanguages", allLanguages) ;
			
			binder.loadComponent(cmbx1);
			binder.loadComponent(cmbx2);
			binder.loadComponent(cmbx3);
			binder.loadComponent(cmbx4);
			
		}
		
	}
	
	
public void delLanguage(String lang, Combobox cmbx1,Combobox cmbx2, Combobox cmbx3 ,Combobox cmbx4, AnnotateDataBinder binder) {
		
		Page page = cmbx1.getPage() ;
		List<String> allLangs = Utilities.getServices().getLangLabelService().findAllLangs() ;
		if (allLangs.contains(lang)) {
			
			List<String> allKeys = Utilities.getServices().getLangLabelService().findAllKeysUnique() ;
			
			for (String key:allKeys){
				
				Utilities.getServices().getLangLabelService().delete(Utilities.getServices().getLangLabelService().findByKey(key, lang));
				
			}
			
			
			List<String> availableLanguages = Utilities.getServices().getLangLabelService().findAllLangs() ;//Arrays.asList(Locale.getISOCountries()) ;
			List<String> allLanguages = new ArrayList<String>(Arrays.asList(Locale.getISOLanguages())) ;
			allLanguages.removeAll(availableLanguages) ;
			
			page.setAttribute("availableLanguages", availableLanguages) ;
			page.setAttribute("allLanguages", allLanguages) ;
			
			binder.loadComponent(cmbx1);
			binder.loadComponent(cmbx2);
			binder.loadComponent(cmbx3);
			binder.loadComponent(cmbx4);
			
		}
		
	}

	
	}