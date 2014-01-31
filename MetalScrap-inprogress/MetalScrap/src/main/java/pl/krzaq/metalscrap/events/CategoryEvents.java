package pl.krzaq.metalscrap.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.components.CategoryTree;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.CategoryParameter;
import pl.krzaq.metalscrap.model.CategoryParameterValue;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class CategoryEvents {

	
	public void admin_onSelectCategory(Grid grid, Category category, AnnotateDataBinder binder) {
		
		Page page = grid.getPage() ;
		
		List<CategoryParameter> params = new ArrayList<CategoryParameter>() ;
		
		if (category.getId()!=null){
			params = ServicesImpl.getCategoryParameterService().findAllParams(category, category.getLang()) ;
			category.setParameters(params);
		}
		page.setAttribute("categoryParameters", params) ;
		page.setAttribute("category", category) ;
		
		
		
		binder.loadComponent(page.getFellow("edit_category_button"));
		binder.loadComponent(page.getFellow("delete_category_button"));
		binder.loadComponent(page.getFellow("pos_up"));
		binder.loadComponent(page.getFellow("pos_down"));
		binder.loadComponent(page.getFellow("selectedGroupbox"));
		binder.loadComponent(page.getFellow("catParamsGrid"));
		
	}
	
	
	public void admin_onDoubleClickCategory(Grid grid, Category category, AnnotateDataBinder binder ) {
		
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		if (category.getId()!=null) {
		
		List<Category> subs = ServicesImpl.getCategoryService().findSubCategoriesByLang(category, locale.getLanguage()) ;
		
		if(subs != null && subs.size()>0) {
			
			Collections.sort(subs);
			
			if (category.getId()!=null) {
				Category back = new Category(Labels.getLabel("auction.auctioncategory.back"), Labels.getLabel("auction.auctioncategory.back"), category.getParent()) ;
				subs.add(0, back) ;
			}
			
			page.setAttribute("categories", subs) ;
			binder.loadComponent(grid);
			
		}
		
		}else {
			
			List<Category> subs = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(subs);
			
			
			if (category.getId()!=null) {
				subs = ServicesImpl.getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
				Collections.sort(subs);
				Category back = new Category(Labels.getLabel("auction.auctioncategory.back"), Labels.getLabel("auction.auctioncategory.back"), category.getParent()) ;
				subs.add(0, back) ;
			} else {
				subs = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
				Collections.sort(subs);				
			}
			
			if (subs!=null && subs.size()>0){
				page.setAttribute("categories", subs) ;
				binder.loadComponent(grid);
			}
			
		}
		
	}
	
	
	public void onSelectCategory() {
		
		
	}
	
	public void openEditCategoryWindow(Window win, AnnotateDataBinder binder) {
		
		Category category = (Category)win.getPage().getAttribute("category") ;
		win.setAttribute("category", category) ;
		win.setAttribute("parent", category.getParent()) ;
		binder.loadComponent(win);
		win.setVisible(true) ;
		
		
	}
	
	public void openNewCategoryWindow(Window win, AnnotateDataBinder binder) {
		
		Category selected = (Category) win.getPage().getAttribute("category") ;
		
		Listbox cmbx = (Listbox) win.getFellow("selectedCategory") ;
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		Category blank = new Category(Labels.getLabel("common.nocategory"), Labels.getLabel("common.nocategory.description"), null, locale.getLanguage()) ;
		Category newCat = new Category("", "", null, locale.getLanguage()) ;
		List<Category> root = ServicesImpl.getCategoryService().findAllByLang(locale.getLanguage()) ;
		root.add(0, blank) ;
		BindingListModel<Category> lml = new BindingListModelList<Category>(root, true) ;
		
		
		win.setAttribute("rootCategories", lml) ;
		win.setAttribute("category", newCat) ;
		win.setAttribute("selectedCat", selected) ;
		
		win.setVisible(true) ;
		
		binder.loadComponent(win);
		cmbx.setModel(lml);
		
		int pos = -1 ;
		if(selected!=null) {
			for (Category cc:root){
				if(cc.getName().equalsIgnoreCase(selected.getName())) {
					pos = root.indexOf(cc) ;
					cmbx.setSelectedItem(cmbx.getItemAtIndex(pos));
					break ;
				}
			
			}
		}
		
		cmbx.setSelectedIndex(pos) ;
		
		binder.saveComponent(cmbx);
		
	}
	
	public void moveCategoryUp(Category category, Grid grid, AnnotateDataBinder binder) {
		
		Category parent = category.getParent() ;
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		if(parent!=null) {
			
			List<Category> categories = ServicesImpl.getCategoryService().findSubCategoriesByLang(parent, locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition>1) {
				
				Category previous = categories.get(currentPosition-2) ;
				int previousPosition = previous.getPosition() ;
				
				category.setPosition(previousPosition);
				ServicesImpl.getCategoryService().update(category);
				
				previous.setPosition(currentPosition);
				ServicesImpl.getCategoryService().update(previous);
								
				categories  = ServicesImpl.getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
				
				binder.loadComponent(grid);
				
			}
			
			
		} else {
			
			List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition>1) {
				
				Category previous = categories.get(currentPosition-2) ;
				int previousPosition = previous.getPosition() ;
				category.setPosition(previousPosition);
				previous.setPosition(currentPosition);
				
				ServicesImpl.getCategoryService().update(category);
				ServicesImpl.getCategoryService().update(previous);
				
				categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
				
				binder.loadComponent(grid);
				
			}
			
		}
		
		
	}
	
	
public void moveCategoryDown(Category category, Grid grid, AnnotateDataBinder binder) {
		
		Category parent = category.getParent() ;
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		if(parent!=null) {
			
			List<Category> categories = ServicesImpl.getCategoryService().findSubCategoriesByLang(parent, locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition<categories.size()) {
				
				Category next = categories.get(currentPosition) ;
				int nextPosition = next.getPosition() ;
				
				category.setPosition(nextPosition);
				ServicesImpl.getCategoryService().update(category) ;
				
				next.setPosition(currentPosition);
				ServicesImpl.getCategoryService().update(next) ;
				
				categories  = ServicesImpl.getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
				
				binder.loadComponent(grid);
			}
			
			
		} else {
			
			List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition<categories.size()) {
				
				Category next = categories.get(currentPosition) ;
				int nextPosition = next.getPosition() ;
				category.setPosition(nextPosition);
				next.setPosition(currentPosition);
				
				ServicesImpl.getCategoryService().update(category) ;
				ServicesImpl.getCategoryService().update(next) ;
				
				categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
								
				binder.loadComponent(grid);
			}
			
		}
		
		
	}

	public void saveCategory(Category category, Window win, Grid grid, AnnotateDataBinder binder) {
	
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		Listbox cmbx = (Listbox) win.getFellow("selectedCategory") ;
		Page page = grid.getPage() ;
		Category parent = null ;
		
		if (cmbx.getSelectedIndex()>-1){
			if (((Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue()).getId()!=null)
				parent = (Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue() ;	
		}
		
		category.setParent(parent);
		
		//ServicesImpl.getCategoryService().merge(category);
		
		if(parent!=null) {
			parent.getChildren().add(category) ;
			ServicesImpl.getCategoryService().update(parent) ;
		}
		
		List<String> langs = ServicesImpl.getLangLabelService().findAllLangs() ;
		
		for (String lang:langs) {
			category.setLang(lang);
			//ServicesImpl.getCategoryService().save(category);
		}
		
		page.setAttribute("categories", ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage())) ;
		
		win.setVisible(false) ;
		binder.loadComponent(grid);
	}

	public void updateCategory(Category category, Window win, Grid grid, AnnotateDataBinder binder) {

		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		ServicesImpl.getCategoryService().update(category);
		
		win.setVisible(false) ;
		List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		Collections.sort(categories);
		page.setAttribute("categories", categories) ;
		binder.loadComponent(grid);
	}

	public void deleteCategory(final Category category,  Grid grid, final AnnotateDataBinder binder) {
	
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		Messagebox.show("Czy napewno usun¹æ kategoriê?", "Usuwanie kategorii", Messagebox.OK|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {

					if (event.getName().equalsIgnoreCase("onOK")){
						
						ServicesImpl.getCategoryService().delete(category);
						
						
						
					}
				
			}
			
			
		}) ;
		
		page.setAttribute("categories", ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage())) ;	
		binder.loadComponent(grid);
		
	}
	
	public void admin_onSelectCategoryParameter(CategoryParameter parameter, Grid paramValues, AnnotateDataBinder binder) {
		
		Page page = paramValues.getPage() ;
		
		if (parameter.getType().equals(CategoryParameter.PARAM_TYPE_CHOICE) || parameter.getType().equals(CategoryParameter.PARAM_TYPE_COMBO)) {
			
			List<CategoryParameterValue> paramValuesList = new ArrayList<CategoryParameterValue>() ;
			page.setAttribute("paramValues", paramValuesList) ;
			paramValues.setVisible(true) ;
			binder.loadComponent(paramValues);
		} else {
			paramValues.setVisible(false) ;
			
		}
		
	}
	
	
	public void admin_onClickExistingCategoryParameter(CategoryParameter param,Grid grid, Groupbox gbox, AnnotateDataBinder binder) {
		Page page = gbox.getPage();
		
		page.setAttribute("newParameter", param) ;
		gbox.setVisible(true) ;
		for(Object o:grid.getRows().getChildren()) {
			Row r = (Row) o ;
			for (Component c:r.getChildren()) {
				if (c instanceof Textbox || c instanceof Listbox || c instanceof Label) {
					
					if (binder.existsBindings(c)) {
						binder.loadComponent(c);
					}
				}
			}
			
		}
		
	}
	
	public void admin_onClickAddCategoryParameter(Groupbox gbox, Category category) {
		Page page = gbox.getPage();
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		
		CategoryParameter newParam = new CategoryParameter() ;
		
		newParam.setCategory(category);
		
		newParam.setValues(new ArrayList<CategoryParameterValue>()) ;
		page.setAttribute("newParameter", newParam) ;
		gbox.setVisible(true) ;
	}
	
	
	public void admin_onClickAddValue(CategoryParameter categoryParam, Grid grid, AnnotateDataBinder binder) {
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		ListModelList lm = (ListModelList) grid.getListModel() ;
		
		CategoryParameterValue pv = new CategoryParameterValue() ;
		pv.setCategoryParameter(categoryParam);
		pv.setLang(locale.getLanguage());
		lm.add(pv) ;
		
		binder.loadComponent(grid);
		
	}
	
	public void admin_onClickDeleteParamValue(CategoryParameterValue paramVal, Grid grid, AnnotateDataBinder binder) {
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		ListModelList lm = (ListModelList) grid.getListModel() ;
		
		lm.remove(paramVal) ;
		
		binder.loadComponent(grid);
		
	}
	
	
	public void admin_onClickSaveCategoryParameter(CategoryParameter catParam, Grid catParamsGrid, AnnotateDataBinder binder) {
		Page page = catParamsGrid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		//catParam.setValues(paramValues);
		List<String> langs = ServicesImpl.getLangLabelService().findAllLangs();
		if (catParam.getId()==null) {
		for (String lang:langs){
			catParam.setLang(lang);
			ServicesImpl.getCategoryParameterService().save(catParam);
		}
		} else {
			
			ServicesImpl.getCategoryParameterService().save(catParam);
		}
		
		Messagebox.show(Labels.getLabel("common.categoryparametersaved")) ;
		
		
	}
	
}
