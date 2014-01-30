package pl.krzaq.metalscrap.components;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class TranslationsGrid extends Grid {

	private List<String> languages ;
	
	
	public void onCreate(){
		/*this.setMold("paging");
		this.setPageSize(10);*/
		String prefix = this.getId();
		
		Columns cols = new Columns() ;
		
		if(this.getChildren().size()==0 || this.getChildren()==null) {
		
		for(String lang:languages){
			Locale lloc = new Locale(lang,lang) ;
			HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
			Locale inLocale = (Locale) ses.getAttribute(Attributes.PREFERRED_LOCALE) ;
			Column lcol = new Column(lloc.getDisplayLanguage(inLocale)) ;
			//lcol.setImage("/resources/images/flags_iso/16/"+lang+".png");
			if(this.getColumns()!=null) {
				this.getColumns().appendChild(lcol) ;
			} else {
				cols.appendChild(lcol) ;
				
			}
			
			
			
		}
		
		if (this.getColumns()==null)
			this.appendChild(cols) ;
		
		Rows rows = new Rows() ;
		
		List<LangLabel> labelsUnique = ServicesImpl.getLangLabelService().findLikeKeyUnique("%"+prefix+"%");
		
		for (LangLabel l:labelsUnique){
			Row nextRow = new Row() ;
			for(String lang:languages){
				
				LangLabel current = ServicesImpl.getLangLabelService().findByKey(l.getLkey(), lang) ;
				Textbox keyBox = new Textbox() ;
				keyBox.setName(String.valueOf(current.getId() )) ;
				keyBox.setValue(current.getLvalue());
				
				nextRow.appendChild(keyBox) ;
			}
			if (this.getRows()!=null) {
				this.getRows().appendChild(nextRow) ;
			} else {
				rows.appendChild(nextRow) ;
			}
			
			
			
			
		}
		
		if(this.getRows()==null)
			this.appendChild(rows) ;
		
		
		}
		
	}

	
	private void redrawGrid() {
		
		if (this.getColumns()!=null && this.getRows()!=null) {
			this.getColumns().getChildren().clear() ;
			this.getRows().getChildren().clear() ;
		}
		this.onCreate();
		
	}


	public List<String> getLanguages() {
		return languages;
	}


	public void setLanguages(List<String> languages) {
		this.languages = languages;
		
		this.getChildren().clear();
		this.onCreate();
		
	}

	

	/*public void setLang1(String lang1) {
		this.lang1 = lang1;
		
		if(this.lang2!=null && this.lang1!=null){
			this.getChildren().clear();
			this.onCreate();
		}
	}


	


	public void setLang2(String lang2) {
		this.lang2 = lang2;
		
		if(this.lang1!=null && this.lang2!=null){
			this.getChildren().clear();
			this.onCreate();
		}
	}*/
	
	
	
	
	
}
