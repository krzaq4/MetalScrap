package pl.krzaq.metalscrap.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.components.CategoryTree;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class CategoryEvents {

	
	public void admin_onSelectCategory(Treeitem item, Tree tree, AnnotateDataBinder binder) {
		
		Page page = tree.getPage() ;
		Category category = (Category) item.getValue() ;
		page.setAttribute("category", category) ;
		binder.loadComponent(page.getFellow("edit_category_button"));
		binder.loadComponent(page.getFellow("delete_category_button"));
		binder.loadComponent(page.getFellow("pos_up"));
		binder.loadComponent(page.getFellow("pos_down"));
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
		
		Listbox cmbx = (Listbox) win.getFellow("selectedCategory") ;
		
		
		
		Category newCat = new Category("", "", null) ;
		List<Category> root = ServicesImpl.getCategoryService().findAll() ;
		BindingListModel<Category> lml = new BindingListModelList<Category>(root, true) ;
		Category selected = (Category) win.getPage().getAttribute("category") ;
		
		win.setAttribute("rootCategories", lml) ;
		win.setAttribute("category", newCat) ;
		win.setAttribute("selectedCat", selected) ;
		
		win.setVisible(true) ;
		
		binder.loadComponent(win);
		cmbx.setModel(lml);
		
		int pos = -1 ;
		for (Category cc:root){
			if(cc.getName().equalsIgnoreCase(selected.getName())) {
				pos = root.indexOf(cc) ;
				cmbx.setSelectedItem(cmbx.getItemAtIndex(pos));
				break ;
			}
			
		}
		
		
		cmbx.setSelectedIndex(pos) ;
		
		binder.saveComponent(cmbx);
		
	}
	
	public void moveCategoryUp(Category category, CategoryTree tree, AnnotateDataBinder binder) {
		
		Category parent = category.getParent() ;
		
		if(parent!=null) {
			
			List<Category> categories = ServicesImpl.getCategoryService().findSubCategories(parent) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition>1) {
				
				Category previous = categories.get(currentPosition-2) ;
				int previousPosition = previous.getPosition() ;
				
				category.setPosition(previousPosition);
				ServicesImpl.getCategoryService().update(category);
				
				previous.setPosition(currentPosition);
				ServicesImpl.getCategoryService().update(previous);
				
				
				tree.refreshTree();
			}
			
			
		} else {
			
			List<Category> categories = ServicesImpl.getCategoryService().findRootCategories() ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition>1) {
				
				Category previous = categories.get(currentPosition-2) ;
				int previousPosition = previous.getPosition() ;
				category.setPosition(previousPosition);
				previous.setPosition(currentPosition);
				
				ServicesImpl.getCategoryService().update(category);
				ServicesImpl.getCategoryService().update(previous);
				
				tree.refreshTree();
			}
			
		}
		
		
	}
	
	
public void moveCategoryDown(Category category, CategoryTree tree, AnnotateDataBinder binder) {
		
		Category parent = category.getParent() ;
		
		if(parent!=null) {
			
			List<Category> categories = ServicesImpl.getCategoryService().findSubCategories(parent) ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition<categories.size()) {
				
				Category next = categories.get(currentPosition) ;
				int nextPosition = next.getPosition() ;
				
				category.setPosition(nextPosition);
				ServicesImpl.getCategoryService().update(category) ;
				
				next.setPosition(currentPosition);
				ServicesImpl.getCategoryService().update(next) ;
				
				tree.refreshTree();
			}
			
			
		} else {
			
			List<Category> categories = ServicesImpl.getCategoryService().findRootCategories() ;
			Collections.sort(categories);
			int currentPosition = category.getPosition() ;
			
			if(currentPosition<categories.size()) {
				
				Category next = categories.get(currentPosition) ;
				int nextPosition = next.getPosition() ;
				category.setPosition(nextPosition);
				next.setPosition(currentPosition);
				
				ServicesImpl.getCategoryService().update(category) ;
				ServicesImpl.getCategoryService().update(next) ;
				
				tree.refreshTree();
			}
			
		}
		
		
	}

	public void saveCategory(Category category, Window win, CategoryTree tree, AnnotateDataBinder binder) {
	
		Listbox cmbx = (Listbox) win.getFellow("selectedCategory") ;
		Category parent = null ;
		
		if (cmbx.getSelectedIndex()>-1)
		parent = (Category) cmbx.getItemAtIndex(cmbx.getSelectedIndex()).getValue() ;
		category.setParent(parent);
		
		//ServicesImpl.getCategoryService().merge(category);
		
		if(parent!=null) {
			parent.getChildren().add(category) ;
			ServicesImpl.getCategoryService().update(parent) ;
		}
		
		
		
		win.setVisible(false) ;
		tree.refreshTree();
	}

	public void updateCategory(Category category, Window win, CategoryTree tree, AnnotateDataBinder binder) {
	

		ServicesImpl.getCategoryService().update(category);
		
		win.setVisible(false) ;
		tree.refreshTree();
	}

	public void deleteCategory(final Category category,  final CategoryTree tree, final AnnotateDataBinder binder) {
	
		Messagebox.show("Czy napewno usun¹æ kategoriê?", "Usuwanie kategorii", Messagebox.OK|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {

					if (event.getName().equalsIgnoreCase("onOK")){
						
						ServicesImpl.getCategoryService().delete(category);
						
						
						
					}
				
			}
			
			
		}) ;
		
		tree.refreshTree();
	
		
	}
	
}
