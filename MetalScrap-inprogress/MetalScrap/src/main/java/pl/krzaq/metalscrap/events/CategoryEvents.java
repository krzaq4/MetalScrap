package pl.krzaq.metalscrap.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.hibernate.Hibernate;
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.components.CategoryTree;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Utilities;

public class CategoryEvents {

	
	public void admin_onSelectCategory(Listbox grid, Listitem item, AnnotateDataBinder binder) {
		Category category = item.getValue() ;
		Page page = grid.getPage() ;
		
		List<Property> params = new ArrayList<Property>() ;
		
		
		if (category.getId()!=null){
		
			if(category.getProperties()!=null)
				params = category.getProperties() ;
			else
				category.setProperties(params);
		}
		
		
		page.setAttribute("categoryParameters", params) ;
		page.setAttribute("category", category) ;
		
		
		
		binder.loadComponent(page.getFellow("edit_category_button"));
		binder.loadComponent(page.getFellow("delete_category_button"));
		binder.loadComponent(page.getFellow("pos_up"));
		binder.loadComponent(page.getFellow("pos_down"));
		binder.loadComponent(page.getFellow("selected_cat_label"));
		binder.loadComponent(page.getFellow("selected_cat_lab"));
		binder.loadComponent(page.getFellow("selected_cat_dummy"));
		
		
		
	}
	
	
	public void admin_onDoubleClickCategory(Vbox vbox, Category category, final AnnotateDataBinder binder ) {
		
		Page page = vbox.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		
		
		//if (category.getId()!=null) {
		
			
			
			
		List<Category> subs = ServicesImpl.getCategoryService().findSubCategoriesByLang(category, locale.getLanguage()) ;
		
		//if(subs != null && subs.size()>0) {
			
		if(vbox.hasFellow(String.valueOf(category.getId()))){
			
			vbox.removeChild(vbox.getFellow(String.valueOf(category.getId()))) ;
			
		} else {
			Collections.sort(subs);
			final Listbox subList = new Listbox() ;
			subList.setPage(page);
			
			Listhead lhead = new Listhead() ;
			
			Listheader lh1 = new Listheader() ;
			lh1.setWidth("5%");
			Listheader lh2 = new Listheader() ;
			lh2.setWidth("95%");
			
			lhead.appendChild(lh1) ;
			lhead.appendChild(lh2) ;
			
			
			subList.appendChild(lhead) ;
			
			for (Category sub:subs){
				final Listitem litem = new Listitem() ;
				Button add = new Button();
				add.setSclass("addButton");
				final Category subFinal = sub ;
				Listcell lcell1 = new Listcell() ;
				Listcell lcell2 = new Listcell() ;
				final Vbox vb = new Vbox() ;
				
				Label l1 = new Label() ;
				Label l2 = new Label() ;
				l2.setSclass("smallLabel");
				
				l1.setValue(sub.getName());
				l2.setValue(sub.getDescription());
				
				l1.addEventListener("onClick", new EventListener<Event>(){

					@Override
					public void onEvent(Event arg0) throws Exception {
						admin_onSelectCategory(subList, litem, binder) ;
					}
					
				}) ;
				
				l1.addEventListener("onDoubleClick", new EventListener<Event>(){

					@Override
					public void onEvent(Event arg0) throws Exception {
						
						admin_onDoubleClickCategory(vb, subFinal, binder) ;
					}
					
				}) ;
				
				vb.appendChild(l1) ;
				vb.appendChild(l2) ;
				litem.setValue(sub);
				
				lcell1.appendChild(add) ;
				lcell2.appendChild(vb) ;
				litem.appendChild(lcell1) ;
				litem.appendChild(lcell2) ;
				subList.appendChild(litem) ;
			}
			
			
			
			
			//subList.setModel(new ListModelList<Category>(subs));
			subList.setId(String.valueOf(category.getId()) );
			
			vbox.appendChild(subList);
			
			/*
			if (category.getId()!=null) {
				Category back = new Category(Labels.getLabel("auction.auctioncategory.back"), Labels.getLabel("auction.auctioncategory.back"), category.getParent()) ;
				subs.add(0, back) ;
			}*/
			
			//page.setAttribute("categories", subs) ;
			//binder.loadComponent(grid);
			//binder.loadComponent(page.getFellow("backlink"));
			
		}
		
		/*}else {
			
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
			
		}*/
		
	}
	
	
	public void onSelectCategory() {
		
		
	}
	
	public void openEditCategoryWindow(Window win, AnnotateDataBinder binder) {
		
		Category category = (Category)win.getPage().getAttribute("category") ;
		win.setAttribute("category", category) ;
		win.setAttribute("existingAttribute", false) ;
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
	
	public void moveCategoryUp(Category category, Listbox grid, AnnotateDataBinder binder) {
		
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
	
	
public void moveCategoryDown(Category category, Listbox grid, AnnotateDataBinder binder) {
		
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

	public void saveCategory(Category category, Window win, Listbox grid, AnnotateDataBinder binder) {
	
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		Listbox cmbx = (Listbox) win.getFellow("selectedCategory") ;
		Page page = grid.getPage() ;
		Category parent = null ;
		
		if (cmbx.getSelectedIndex()>-1){
			if (((Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue()).getId()!=null)
				parent = ServicesImpl.getCategoryService().findById(((Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue()).getId()) ; //(Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue() ;	
		}
		
		category.setParent(parent);
		
		//ServicesImpl.getCategoryService().merge(category);
		
		if(parent!=null) {
			parent.getChildren().add(category) ;
		//	ServicesImpl.getCategoryService().update(parent) ;
		}
		
		/*List<String> langs = ServicesImpl.getLangLabelService().findAllLangs() ;
		
		for (String lang:langs) {*/
			
			ServicesImpl.getCategoryService().save(category);
		//}
		
			List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(categories);
		page.setAttribute("categories", categories ) ;
		
		win.setVisible(false) ;
		binder.loadComponent(grid);
	}

	public void updateCategory(Category category, Window win, Listbox grid, AnnotateDataBinder binder) {

		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		ServicesImpl.getCategoryService().update(category);
		
		win.setVisible(false) ;
		List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		Collections.sort(categories);
		page.setAttribute("categories", categories) ;
		binder.loadComponent(grid);
	}

	public void deleteCategory(final Category category,  Listbox grid, final AnnotateDataBinder binder) {
	
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
	
	public void admin_onSelectCategoryParameter(Property parameter, Grid paramValues, AnnotateDataBinder binder) {
		
		Page page = paramValues.getPage() ;
		/*
		if (parameter.get.getType().equals(CategoryParameter.PARAM_TYPE_CHOICE) || parameter.getType().equals(CategoryParameter.PARAM_TYPE_COMBO)) {
			
			List<CategoryParameterValue> paramValuesList = new ArrayList<CategoryParameterValue>() ;
			page.setAttribute("paramValues", paramValuesList) ;
			paramValues.setVisible(true) ;
			binder.loadComponent(paramValues);
		} else {
			paramValues.setVisible(false) ;
			
		}*/
		
	}
	
	
	public void admin_onClickDeleteCategoryParameter(Category category, Row row,  Window window, AnnotateDataBinder binder) {
		
		Property property = row.getValue() ;
		
		category.getProperties().remove(property) ;
		window.setAttribute("newParameter", new Property()) ;
		window.getFellow("attributes_div").setVisible(false) ;
		
		window.setAttribute("category", category) ;
		Clients.showBusy(null);
		Utilities.bind(window, binder);
		Clients.clearBusy();
	}
	
	public void admin_onClickDeletePropertyAttribute(Property property, Row row, Window window, AnnotateDataBinder binder) {
		
		PropertyAttribute attribute = row.getValue();
		property.getAttributes().remove(attribute) ;
		window.setAttribute("newParameter", property) ;
		Clients.showBusy(null);
		Utilities.bind(window, binder);
		Clients.clearBusy();
	}
	
	public void admin_onClickDeleteAttributeValue(Property property, PropertyAttribute attribute, Row row, Window window, AnnotateDataBinder binder) {
		PropertyAttributeValue value = row.getValue() ;
		
		int indexOfAttr = property.getAttributes().indexOf(attribute) ;
		property.getAttributes().remove(attribute);
		
		attribute.getValues().remove(value) ;
		property.getAttributes().add(indexOfAttr, attribute);
		
		window.setAttribute("newParameter", property) ;
		
		Clients.showBusy(null);
		Utilities.bind(window, binder);
		Clients.clearBusy();
		
	}
	
	public void admin_onClickExistingCategoryParameter(Row rr,Grid grid, Grid g, Div gbox, Window win,  AnnotateDataBinder binder) {
		Page page = gbox.getPage();
		Property param = (Property) rr.getValue() ;
		win.setAttribute("newParameter", param) ;
		win.setAttribute("existingAttribute", true) ;
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
		Utilities.bind(win, binder);
		
	}
	
	public void admin_onClickAddCategoryParameterAttribute(Property property, Grid grid, Window window, AnnotateDataBinder binder) {
		PropertyAttribute attr = new PropertyAttribute();
		attr.setProperty(property);
		attr.setType(1);
		//category.getProperties().add(property)
		property.getAttributes().add(attr) ;
		window.setAttribute("newParameter", property) ;
		window.setAttribute("attr", attr) ;
		binder.loadComponent(grid);
		
		
	}
	
	public void admin_onClickAddPropertyToCategory(Category category, Property property, Grid grid, Window win, AnnotateDataBinder binder) {
		Page page = grid.getPage() ;
		category.getProps().add(property) ;
		property = new Property() ;
		property.setAttributes(new ArrayList<PropertyAttribute>());
		
		win.setAttribute("newParameter", property) ;
		win.setAttribute("attr", new PropertyAttribute()) ;
		
		page.setAttribute("category", category) ;
		
		binder.loadComponent(grid);
		binder.loadComponent(win.getFellow("props"));
		binder.loadComponent(win.getFellow("attributes"));
		win.getFellow("attributes_div").setVisible(false) ;
		
	}
	
	public void admin_onClickUpdateCategoryProperty(Category category, Property property, Grid grid, Window win, AnnotateDataBinder binder) {
		Page page = grid.getPage() ;
		int indexOf = category.getProperties().indexOf(property) ;
		category.getProperties().remove(indexOf) ;
		
		category.getProperties().add(indexOf, property) ;
		property = new Property() ;
		property.setAttributes(new ArrayList<PropertyAttribute>());
		
		win.setAttribute("newParameter", property) ;
		win.setAttribute("attr", new PropertyAttribute()) ;
		win.setAttribute("existingAttribute", false) ;
		page.setAttribute("category", category) ;
		
		Utilities.bind(win, binder);
		
		/*binder.loadComponent(grid);
		binder.loadComponent(win.getFellow("props"));
		binder.loadComponent(win.getFellow("attributes"));*/
		win.getFellow("attributes_div").setVisible(false) ;
		
	}
	
	public void admin_onClickAddCategoryParameter(Window window, Div div, Category category, AnnotateDataBinder binder) {
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		List<Integer> types = new ArrayList<Integer>() ;
		types.add(1);
		types.add(2);
		types.add(3);
		types.add(4);
		types.add(5);
		
		Property property = new Property() ;
		
		property.setAttributes(new ArrayList<PropertyAttribute>()) ;
		window.setAttribute("newParameter", property) ;
		window.setAttribute("existingAttribute", false) ;
		window.setAttribute("types", types) ;
		div.setVisible(true) ;
		
		
		Utilities.bind(window, binder) ;
		
	}
	
	
	/*public void admin_onClickAddValue(Property categoryParam, Grid grid, AnnotateDataBinder binder) {
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		ListModelList lm = (ListModelList) grid.getListModel() ;
		
		CategoryParameterValue pv = new CategoryParameterValue() ;
		pv.setCategoryParameter(categoryParam);
		pv.setLang(locale.getLanguage());
		lm.add(pv) ;
		
		binder.loadComponent(grid);
		
	}*/
	
	/*public void admin_onClickDeleteParamValue(CategoryParameterValue paramVal, Grid grid, AnnotateDataBinder binder) {
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		ListModelList lm = (ListModelList) grid.getListModel() ;
		
		lm.remove(paramVal) ;
		
		binder.loadComponent(grid);
		
	}*/
	
	
	/*public void admin_onClickSaveCategoryParameter(CategoryParameter catParam, Grid catParamsGrid, AnnotateDataBinder binder) {
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
		
		
	}*/
	
	
	public void admin_onClickBackToParentCategory(Component c, Category category, AnnotateDataBinder binder) {
		
		Page page = c.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		List<Category> cats = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		if(category.getParent()!=null)
			cats = ServicesImpl.getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
		page.setAttribute("categories", cats) ;
		page.setAttribute("category", null) ;
		binder.loadAll();
		
	}
	
	public void admin_onSelectAttributeType(Component c, Window win, AnnotateDataBinder binder) {
		Row r = (Row) c.getParent() ;
		for(Component cc:r.getChildren()){
			if(cc instanceof Vbox) {
				Vbox vv = (Vbox) cc ;
				for (Component ccc:cc.getChildren()) {
					if(ccc instanceof Grid ) {
						Grid gr = (Grid) ccc;
						PropertyAttribute attribute = (PropertyAttribute) r.getValue() ;
						if(attribute.getType().equals(PropertyAttribute.TYPE_MULTISELECT) || attribute.getType().equals(PropertyAttribute.TYPE_SELECT)) {
							attribute.setValues(new ArrayList<PropertyAttributeValue>());
							gr.setVisible(true) ;
							binder.loadComponent(gr);
							break;					
						} else {
							gr.setVisible(false) ;
						}
					} 
				}
			}					
		}		
	}
	
	public void admin_onAddPropertyAttrValue(Component c, Window win, AnnotateDataBinder binder) {
		
		Row r = (Row) c.getParent() ;
		for(Component cc:r.getChildren()){
			if(cc instanceof Vbox) {
				Vbox vv = (Vbox) cc ;
				for (Component ccc:cc.getChildren()) {
					if(ccc instanceof Grid) {
				Grid gr = (Grid) ccc;
				PropertyAttribute attribute = (PropertyAttribute) r.getValue() ;
				PropertyAttributeValue v = new PropertyAttributeValue() ;
				v.setAttribute(attribute);
				attribute.getValues().add(v) ;
				win.setAttribute("val", v) ;
				
				binder.loadComponent(gr);
					}
				}
			}
					
		}
		
	}
	
}
