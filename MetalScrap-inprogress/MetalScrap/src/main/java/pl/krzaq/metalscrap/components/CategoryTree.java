package pl.krzaq.metalscrap.components;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import pl.krzaq.metalscrap.dao.CategoryDAO;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.utils.ApplicationContextProvider;

public class CategoryTree extends Tree {

	private CategoryDAO categoryDAO ;
	private AnnotateDataBinder binder ;
	
	public void onCreate() {
		
		System.out.println("onCreate") ;
		
		
		this.setModel(this.getCategoryTreeModel());
		
		this.setItemRenderer(new CategoryInfoRenderer());
		
	}
	
	public void refreshTree() {
		
		this.setModel(this.getCategoryTreeModel());
		try {
			this.redraw(new StringWriter());
		} catch (IOException e) {
			
		}
	}
	

	private TreeModel getCategoryTreeModel() {
		
		categoryDAO = (CategoryDAO) ApplicationContextProvider.getApplicationContext().getBean("categoryDAO") ;
		
		List<TreeNode<Category>> nodes = new ArrayList<TreeNode<Category>>() ;
		
				  List<Category> rootCategories = categoryDAO.findRootCategories() ;
				  Collections.sort(rootCategories);
				  
				  TreeNode<Category>[] nds = new DefaultTreeNode[rootCategories.size()] ;
				  int j=0 ;
				  for (Category root:rootCategories) {
					  
					  TreeNode<Category> rootNode ;
					  
					  if (categoryDAO.findSubCategories(root).size()>0) {
						  
						  rootNode = new DefaultTreeNode<Category>(root, getSubCategories(root) ) ;
						  nds[j] = rootNode ;
						  nodes.add(rootNode) ;
					  } else {
						  
						  rootNode = new DefaultTreeNode<Category>(root) ;
						  nds[j] = rootNode ;
						  nodes.add(rootNode) ;
					  }
					  
					  j++ ;
				  }
				  
				  
				  TreeNode<Category>[] nodesArray = new DefaultTreeNode[nodes.size()] ; 
				  
				  nodesArray = nodes.toArray(nodesArray) ;
				  
				  /*TreeModel<TreeNode<Category>> model = new DefaultTreeModel<Category>(
						  new DefaultTreeNode<Category>(null, nodesArray) ) ;	  
				  */
				  TreeModel<TreeNode<Category>> model = new DefaultTreeModel<Category>(
						  new DefaultTreeNode<Category>(null, nds) ) ;
				  
				  return model ;

	}
	
	
	private TreeNode<Category>[] getSubCategories(Category category) {
		
		DefaultTreeNode<Category>[] result  = new DefaultTreeNode[category.getChildren().size()] ;
		int i=0;
		List<Category> childCategories = category.getChildren() ;
		Collections.sort(childCategories);
		for (Category sub:childCategories) {
			
			if (categoryDAO.findSubCategories(sub).size()>0) {
				result[i] = new DefaultTreeNode<Category>(sub, getSubCategories(sub)) ;
			} else {
				
				result[i] = new DefaultTreeNode<Category>(sub) ;
			}
			
			
			i++ ;
			
			
		}
		
		return result ;
		
		
	}
	
	
	
	private class CategoryInfoRenderer implements TreeitemRenderer<DefaultTreeNode<Category>> {
	    public void render(Treeitem item, DefaultTreeNode<Category> data, int index) throws Exception {
	        Category fi = data.getData();
	        Treerow tr = new Treerow();
	        tr.setSclass("category");
	        
	        item.setAction("show: slideDown;hide: slideUp");
	        
	        item.appendChild(tr);
	        item.setValue(fi);
	        item.addEventListener("onClick", new CategoryClickListener()) ;
	        Treecell tc = new Treecell() ;
	        Div cellInside = new Div() ;
	        cellInside.setSclass("categoryDiv");
	        Label desc = new Label(fi.getDescription()) ;
	        
	        Label name = new Label(fi.getName()) ;
	        
	        desc.setSclass("categoryDesc") ;
	        name.setSclass("categoryName") ;
	        
	        cellInside.appendChild(name) ;
	        cellInside.appendChild(desc) ;
	        
	        tc.appendChild(cellInside) ;
	        tr.appendChild(tc);
	        
	    }
	}
	
	
	
	private class CategoryClickListener implements EventListener {

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equalsIgnoreCase("onClick")) {
				
				
				((Treeitem) event.getTarget()).setOpen(!((Treeitem) event.getTarget()).isOpen());
				
			}
			
		}
		
		
		
	}



	public AnnotateDataBinder getBinder() {
		return binder;
	}


	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}
	
	
	
	
}
