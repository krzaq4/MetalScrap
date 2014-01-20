package pl.krzaq.metalscrap.events;

import java.util.Collections;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class LangEvents {

	
	public void updateLabels(Grid grid, AnnotateDataBinder binder){
		
		List<Component> rows =grid.getRows().getChildren() ;
		
		for (Component c:rows) {
			Row row = (Row) c ;
			
			List<Component> rowChildren = row.getChildren() ;
			
			for (Component child:rowChildren) {
				if(child instanceof Textbox) {
					Long id = Long.valueOf(((Textbox)child).getId()) ;
					LangLabel toUpdate = ServicesImpl.getLangLabelService().findById(id) ;
					if (!toUpdate.getLvalue().equals(((Textbox)child).getValue())) {
						toUpdate.setLvalue( ((Textbox)child).getValue() );
						ServicesImpl.getLangLabelService().update(toUpdate);
					}
				}
			}
		}
		
		
		
		List<LangLabel> theLabels = ServicesImpl.getLangLabelService().findLikeKeyUnique("%"+grid.getId()+"%") ;
		List<LangLabel> theLabelsAll = ServicesImpl.getLangLabelService().findLikeKey("%"+grid.getId()+"%") ;
		List<String> langs = ServicesImpl.getLangLabelService().findAllLangs() ;
		
		
		
		Collections.sort(langs);
		Collections.sort(theLabels);
		Collections.sort(theLabelsAll);
		
		grid.getPage().setAttribute(grid.getId()+"Labels", theLabels) ;
		grid.getPage().setAttribute(grid.getId()+"LabelsAll", theLabelsAll) ;
		grid.getPage().setAttribute("langs", langs) ;
		
		Labels.reset();
		binder.loadComponent(grid);
	}
	
}
