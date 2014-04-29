package pl.krzaq.metalscrap.events;

import java.security.NoSuchAlgorithmException;
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
		
			
			
			
		List<Category> subs = Utilities.getServices().getCategoryService().findSubCategoriesByLang(category, locale.getLanguage()) ;
		
		//if(subs != null && subs.size()>0) {
			
		if(vbox.hasFellow(String.valueOf(category.getId()))){
			
			vbox.removeChild(vbox.getFellow(String.valueOf(category.getId()))) ;
			
		} else {
			Collections.sort(subs);
			final Listbox subList = new Listbox() ;
			subList.setPage(page);
			
			Listhead lhead = new Listhead() ;
			
			
			Listheader lh = new Listheader() ;
			lh.setWidth("100%");
			
			lhead.appendChild(lh) ;
			
			
			subList.appendChild(lhead) ;
			
			for (Category sub:subs){
				final Listitem litem = new Listitem() ;
				
				final Category subFinal = sub ;
				
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
				
				l2.addEventListener("onDoubleClick", new EventListener<Event>(){

					@Override
					public void onEvent(Event arg0) throws Exception {
						
						admin_onDoubleClickCategory(vb, subFinal, binder) ;
					}
					
				}) ;
				
				vb.appendChild(l1) ;
				vb.appendChild(l2) ;
				litem.setValue(sub);
				
				
				lcell2.appendChild(vb) ;
				
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
			
			List<Category> subs = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(subs);
			
			
			if (category.getId()!=null) {
				subs = Utilities.getServices().getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
				Collections.sort(subs);
				Category back = new Category(Labels.getLabel("auction.auctioncategory.back"), Labels.getLabel("auction.auctioncategory.back"), category.getParent()) ;
				subs.add(0, back) ;
			} else {
				subs = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
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
		List<Category> root = Utilities.getServices().getCategoryService().findAllByLang(locale.getLanguage()) ;
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
			
			List<Category> categories = Utilities.getServices().getCategoryService().findSubCategoriesByLang(parent, locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition>1) {
				
				Category previous = categories.get(currentPosition-2) ;
				int previousPosition = previous.getPosition() ;
				
				category.setPosition(previousPosition);
				Utilities.getServices().getCategoryService().update(category);
				
				previous.setPosition(currentPosition);
				Utilities.getServices().getCategoryService().update(previous);
								
				categories  = Utilities.getServices().getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
				
				binder.loadComponent(grid);
				
			}
			
			
		} else {
			
			List<Category> categories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition>1) {
				
				Category previous = categories.get(currentPosition-2) ;
				int previousPosition = previous.getPosition() ;
				category.setPosition(previousPosition);
				previous.setPosition(currentPosition);
				
				Utilities.getServices().getCategoryService().update(category);
				Utilities.getServices().getCategoryService().update(previous);
				
				categories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
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
			
			List<Category> categories = Utilities.getServices().getCategoryService().findSubCategoriesByLang(parent, locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition<categories.size()) {
				
				Category next = categories.get(currentPosition) ;
				int nextPosition = next.getPosition() ;
				
				category.setPosition(nextPosition);
				Utilities.getServices().getCategoryService().update(category) ;
				
				next.setPosition(currentPosition);
				Utilities.getServices().getCategoryService().update(next) ;
				
				categories  = Utilities.getServices().getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
				
				binder.loadComponent(grid);
			}
			
			
		} else {
			
			List<Category> categories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition<categories.size()) {
				
				Category next = categories.get(currentPosition) ;
				int nextPosition = next.getPosition() ;
				category.setPosition(nextPosition);
				next.setPosition(currentPosition);
				
				Utilities.getServices().getCategoryService().update(category) ;
				Utilities.getServices().getCategoryService().update(next) ;
				
				categories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories) ;
				page.setAttribute("category", category) ;
								
				binder.loadComponent(grid);
			}
			
		}
		
		
	}

	public void saveCategory(Category category, Window win, Listbox grid, AnnotateDataBinder binder) {
	
		
		
				
				if (Utilities.validate(win, true)) {
				
				
				Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
				Listbox cmbx = (Listbox) win.getFellow("selectedCategory") ;
				Page page = grid.getPage() ;
				Category parent = null ;
		
				if (cmbx.getSelectedIndex()>-1){
					if (((Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue()).getId()!=null)
						parent = Utilities.getServices().getCategoryService().findById(((Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue()).getId()) ; //(Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue() ;	
				} 
		
				List<String> langs = Utilities.getServices().getLangLabelService().findAllLangs() ;
		
				category.setAuctions(new ArrayList<Auction>());
				
				for (String lang:langs) {
				
					/*if (parent!=null) {
						parent = Utilities.getServices().getCategoryService().getEqual( Utilities.hash(Utilities.HASH_METHOD_MD5, parent.getName().concat(parent.getDescription())) , lang) ;
					}
			
					category.setParent(parent);
			
					if(parent!=null) {
						parent.getChildren().add(category) ;					
					}
				
					category.setEqualIdentifier(equalIdent);*/
					
					category.setParent(parent);
					
					//category.setLang(lang);
					
					category = category.clone(lang, true); 
					
					//Utilities.getServices().getCategoryService().save(category);
				
				}
		
				List<Category> categories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
				Collections.sort(categories);
				page.setAttribute("categories", categories ) ;
		
				win.setVisible(false) ;
				binder.loadComponent(grid);
				
				}
				
		
		
	}

	public void updateCategory(Category category, Window win, Listbox grid, AnnotateDataBinder binder) {
try {
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		String equalIdent = category.getEqualIdentifier();
		
		
		for(Property prop:category.getProps()) {
			
			if(prop.getEqualIdentifier()==null) {
				// New Property in category	
				
				for(Category cat:Utilities.getServices().getCategoryService().getEquals(equalIdent)){
				
					Property newProp = new Property() ;
					
					newProp.setLang(cat.getLang());
					newProp.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, prop.getName().concat(prop.getDescription())));
					newProp.setName(prop.getName());
					newProp.setDescription(prop.getDescription());
					newProp.setExposed(prop.getExposed());
					newProp.setAttributes(new ArrayList<PropertyAttribute>());
					
					for(PropertyAttribute attr:prop.getAttributes()) {
						PropertyAttribute newAttr = new PropertyAttribute() ;
						newAttr.setProperty(newProp);
						newAttr.setLang(cat.getLang());
						newAttr.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, attr.getType().toString().concat(newProp.getName())));
						newAttr.setType(attr.getType());
						newAttr.setName(attr.getName());
						newAttr.setValues(new ArrayList<PropertyAttributeValue>());
						for(PropertyAttributeValue pval:attr.getValues()) {
							PropertyAttributeValue newVal = new PropertyAttributeValue() ;
							newVal.setAttribute(newAttr);
							newVal.setLang(cat.getLang());
							newVal.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, pval.getValue().concat(newProp.getName())));
							newVal.setValue(pval.getValue());
							newAttr.getValues().add(newVal);
						}
						
						newProp.getAttributes().add(newAttr) ;
					}
				
					cat.getProps().add(newProp);
					
				Utilities.getServices().getPropertyService().save(newProp);	
				
				Utilities.getServices().getCategoryService().update(cat);
				
				}	
					
				
			} else {
				// Property already exists
				String equalProp = prop.getEqualIdentifier() ;
				
				// iterate through other categories
				// check if propertyAttributes or propertyAttributeValues has changed
				for(Category cat:Utilities.getServices().getCategoryService().getEquals(equalIdent)){
					
					if(!cat.getLang().equals(category.getLang())){
						
						for(Property catProp:cat.getProps()) {
							
							if(catProp.getEqualIdentifier().equals(prop.getEqualIdentifier())) {
								
								//Found equal Property
								for (PropertyAttribute attr:prop.getAttributes()) {
									for(PropertyAttribute catAttr:catProp.getAttributes()) {
									
										if(attr.getEqualIdentifier()==null) {
											// PropertyAttribute is new
											PropertyAttribute newAttr = new PropertyAttribute() ;
											newAttr.setLang(cat.getLang());
											newAttr.setName(attr.getName());
											newAttr.setProperty(catProp);
											newAttr.setType(attr.getType());
											
											attr.setProperty(prop);
											attr.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, attr.getType().toString().concat(attr.getProperty().getName())));
											newAttr.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, attr.getType().toString().concat(attr.getProperty().getName())));
											
											newAttr.setValues(new ArrayList<PropertyAttributeValue>());
											for(PropertyAttributeValue val:attr.getValues()) {
												
														PropertyAttributeValue newVal = new PropertyAttributeValue() ;
														newVal.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, newVal.getValue().concat(newVal.getAttribute().getProperty().getName())));
														newVal.setAttribute(newAttr);
														newVal.setLang(cat.getLang());
														newVal.setValue(val.getValue());
														newAttr.getValues().add(newVal) ;
													
											}
											
											
											
										} else
										if(attr.getEqualIdentifier()!=null && attr.getEqualIdentifier().equals(catAttr.getEqualIdentifier()) ) {
											
											
											for(PropertyAttributeValue val:attr.getValues()) {
												for(PropertyAttributeValue catVal:catAttr.getValues()) {
													
													if(val.getEqualIdentifier()==null) {
														PropertyAttributeValue newVal = new PropertyAttributeValue() ;
														
														newVal.setAttribute(catAttr);
														newVal.setLang(cat.getLang());
														newVal.setValue(val.getValue());
														newVal.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, newVal.getValue().concat(newVal.getAttribute().getProperty().getName())));
														catAttr.getValues().add(newVal) ;
													}
													
												}
												
											}
											
											
										}
									
									}
								}
								
								Utilities.getServices().getPropertyService().update(catProp);
								Utilities.getServices().getPropertyService().update(prop);
								//continue;
							}
							
							/*if(toDelete) {
								if(cat.getProps().contains(catProp)) {
									cat.getProps().remove(catProp) ;
								}
								//Utilities.getServices().getPropertyService().delete(prop);
								Utilities.getServices().getPropertyService().delete(catProp);
						}*/
						
						
							
						}
					}
					
					Utilities.getServices().getCategoryService().update(cat);
				}
				
			}
			
			
			
		}
		
		
		
		//Utilities.getServices().getCategoryService().update(category);
		
		/*for(Category cat:Utilities.getServices().getCategoryService().getEquals(equalIdent)){
				
				if(!cat.getLang().equals(category.getLang())){
				Iterator<Property> itProp = category.getProps().iterator() ;
				
				int propNo = 0 ;
				while(itProp.hasNext()) {
					Property property = itProp.next() ;
					
					
					
					
					
				Property newProp = new Property() ;
				List<PropertyAttribute> newAttrs = new ArrayList<PropertyAttribute>() ;
				List<PropertyAttributeValue> newVals = new ArrayList<PropertyAttributeValue>() ;
				if(property.getAttributes()!=null)
					for (PropertyAttribute pa:property.getAttributes()) {
						PropertyAttribute newPa = new PropertyAttribute() ;
						newPa.setName(pa.getName());
						newPa.setProperty(newProp);
						newPa.setType(pa.getType());
				
						if(pa.getValues()!=null)
							for (PropertyAttributeValue pva:pa.getValues()) {
								PropertyAttributeValue newPva = new PropertyAttributeValue() ;
								newPva.setAttribute(newPa);
								newPva.setValue(pva.getValue());
								newVals.add(newPva) ;
							}
						newPa.setValues(newVals);
						newAttrs.add(newPa) ;
				
					}
				newProp.setAttributes(newAttrs);
				newProp.setLang(cat.getLang());
				
				newProp.setDescription(property.getDescription());
				newProp.setExposed(property.getExposed());
				newProp.setName(property.getName());
				
				
				//if(!cat.getLang().equals(locale.getLanguage())) {
					
					boolean contains=false;
					for(Property p:cat.getProps()){
						String toCompare = p.getEqualIdentifier() ;
						String toCompare2 = property.getEqualIdentifier() ;
						if(toCompare.equals(toCompare2)){
							contains=true ;
							
						}
					}
					
					if(!contains){
						newProp.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, newProp.getName().concat(newProp.getDescription())));
						cat.getProps().add(newProp) ;
					}
				//} 
				//else
				//if(propNo>=cat.getProps().size()){
				//	cat.getProps().add(newProp) ;
				//}
				
				
				
			}
				
			//if(lang.equals(locale.getLanguage())) {
			//	cat.setName(category.getName());
			//	cat.setDescription(category.getDescription());
			//}
				}
			Utilities.getServices().getCategoryService().update(cat);
			
			
		}*/
		
		
		deleteUnusedProperty(category) ;
		
		win.setVisible(false) ;
		List<Category> categories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		Collections.sort(categories);
		page.setAttribute("categories", categories) ;
		binder.loadComponent(grid);
		
} catch(NoSuchAlgorithmException nsa) {
	
}
	}
	
	
	
	private void deleteUnusedProperty(Category category) {
		
		for(Category cat:Utilities.getServices().getCategoryService().getEquals(category.getEqualIdentifier())){
			
			Iterator<Property> it = cat.getProps().iterator() ;
			
			while(it.hasNext()) {
				Property p = it.next();
				boolean toDelete = true ;
				
				for(Property pp:category.getProps()) {
					
					if(pp.getEqualIdentifier()==null || (pp.getEqualIdentifier()!=null && pp.getEqualIdentifier().equals(p.getEqualIdentifier()))) {
						toDelete = false ;
					}
					
				}
				
				if(toDelete) {
					it.remove();
					Utilities.getServices().getCategoryService().update(cat);
					Utilities.getServices().getPropertyService().delete(p);
					
				}
				
			}
			
			
		}
		
		
	}
	

	public void deleteCategory(final Category category,  Listbox grid, final AnnotateDataBinder binder) {
	
		Page page = grid.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		Messagebox.show("Czy napewno usunąć kategorię?", "Usuwanie kategorii", Messagebox.OK|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {

					if (event.getName().equalsIgnoreCase("onOK")){
						String equalIdent = category.getEqualIdentifier();
						for (String lang:Utilities.getServices().getLangLabelService().findAllLangs()) {
						
							Category cat = Utilities.getServices().getCategoryService().getEqual(equalIdent, lang) ;
							Utilities.getServices().getCategoryService().delete(cat);
						
						}
						
					}
				
			}
			
			
		}) ;
		
		page.setAttribute("categories", Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage())) ;	
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
		
		category.getProps().remove(property) ;
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
		try{
		
		String equalIdent = category.getEqualIdentifier();
		//List<Category> equals = Utilities.getServices().getCategoryService().getEquals(equalIdent) ;
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		Category cat ;
		for (String lang:Utilities.getServices().getLangLabelService().findAllLangs()) {
			cat = Utilities.getServices().getCategoryService().getEqual(equalIdent, lang) ;
			String propEqualIdent = Utilities.hash(Utilities.HASH_METHOD_MD5,property.getName().concat(property.getDescription())) ;
			Property newProp = new Property() ;
			List<PropertyAttribute> newAttrs = new ArrayList<PropertyAttribute>() ;
			List<PropertyAttributeValue> newVals = new ArrayList<PropertyAttributeValue>() ;
			if(property.getAttributes()!=null)
			for (PropertyAttribute pa:property.getAttributes()) {
				PropertyAttribute newPa = new PropertyAttribute() ;
				newPa.setName(pa.getName());
				newPa.setProperty(newProp);
				newPa.setType(pa.getType());
				
				if(pa.getValues()!=null)
				for (PropertyAttributeValue pva:pa.getValues()) {
					PropertyAttributeValue newPva = new PropertyAttributeValue() ;
					newPva.setAttribute(newPa);
					newPva.setValue(pva.getValue());
					newVals.add(newPva) ;
				}
				newPa.setValues(newVals);
				newAttrs.add(newPa) ;
				
			}
			newProp.setAttributes(newAttrs);
			newProp.setLang(lang);
			
			
			newProp.setDescription(property.getDescription());
			newProp.setExposed(property.getExposed());
			newProp.setName(property.getName());
			//newProp.setEqualIdentifier(propEqualIdent);
			property.setLang(lang);
			cat.getProps().add(newProp) ;
			if (lang.equals(locale.getLanguage())) {
				category.getProps().add(newProp) ;
			}
		}
		
		} catch(NoSuchAlgorithmException nsa) {
			
		}
		
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
		Property oldProperty = category.getProperties().get(indexOf) ;
		String equalIdent = oldProperty.getEqualIdentifier() ;
		category.getProperties().remove(indexOf) ;
		property.setEqualIdentifier(equalIdent);
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
		List<String> langs = Utilities.getServices().getLangLabelService().findAllLangs();
		if (catParam.getId()==null) {
		for (String lang:langs){
			catParam.setLang(lang);
			Utilities.getServices().getCategoryParameterService().save(catParam);
		}
		} else {
			
			Utilities.getServices().getCategoryParameterService().save(catParam);
		}
		
		Messagebox.show(Labels.getLabel("common.categoryparametersaved")) ;
		
		
	}*/
	
	
	public void admin_onClickBackToParentCategory(Component c, Category category, AnnotateDataBinder binder) {
		
		Page page = c.getPage() ;
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		List<Category> cats = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		if(category.getParent()!=null)
			cats = Utilities.getServices().getCategoryService().findSubCategoriesByLang(category.getParent(), locale.getLanguage()) ;
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
