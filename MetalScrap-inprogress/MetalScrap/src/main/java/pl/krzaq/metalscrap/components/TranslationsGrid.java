package pl.krzaq.metalscrap.components;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class TranslationsGrid extends Grid {

	public void onCreate(){
		
		String prefix = this.getId();
		
		List<String> langs = ServicesImpl.getLangLabelService().findAllLangs() ;
		
		Columns cols = new Columns() ;
		
		for(String lang:langs){
			cols.appendChild(new Column(lang)) ;
		}
		
		this.appendChild(cols) ;
		
		Rows rows = new Rows() ;
		
		List<LangLabel> labelsUnique = ServicesImpl.getLangLabelService().findLikeKeyUnique("%"+prefix+"%");
		
		for (LangLabel l:labelsUnique){
			Row nextRow = new Row() ;
			for(String lang:langs){
				
				LangLabel current = ServicesImpl.getLangLabelService().findByKey(l.getLkey(), lang) ;
				Textbox keyBox = new Textbox() ;
				keyBox.setName(String.valueOf(current.getId() )) ;
				keyBox.setValue(current.getLvalue());
				
				nextRow.appendChild(keyBox) ;
			}
			rows.appendChild(nextRow) ;
		}
		
		this.appendChild(rows) ;
		
		
	}
	
}
