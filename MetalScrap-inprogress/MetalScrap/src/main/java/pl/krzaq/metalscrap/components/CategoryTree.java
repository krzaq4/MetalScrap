package pl.krzaq.metalscrap.components;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
	
	
	public void onCreate() {
		
		System.out.println("onCreate") ;
		
		
		this.setModel(this.getCategoryTreeModel());
		
		this.setItemRenderer(new CategoryInfoRenderer());
		
	}
	

	private TreeModel getCategoryTreeModel() {
		
		categoryDAO = (CategoryDAO) ApplicationContextProvider.getApplicationContext().getBean("categoryDAO") ;
		
		List<TreeNode<Category>> nodes = new ArrayList<TreeNode<Category>>() ;
		
		
				  
				  
				  
				  List<Category> rootCategories = categoryDAO.findRootCategories() ;
				  
				  
				  for (Category root:rootCategories) {
					  
					  TreeNode<Category> rootNode ;
					  
					  if (categoryDAO.findSubCategories(root).size()>0) {
						  
						  rootNode = new DefaultTreeNode<Category>(root, getSubCategories(root) ) ;
						  nodes.add(rootNode) ;
					  } else {
						  
						  rootNode = new DefaultTreeNode<Category>(root) ;
						  nodes.add(rootNode) ;
					  }
					  
					  
				  }
				  
				  
				  TreeNode<Category>[] nodesArray = new DefaultTreeNode[nodes.size()] ; 
				  
				  nodesArray = nodes.toArray(nodesArray) ;
				  
				  TreeModel<TreeNode<Category>> model = new DefaultTreeModel<Category>(
						  new DefaultTreeNode<Category>(null, nodesArray) ) ;	  
				  
				  return model ;

	}
	
	
	private TreeNode<Category>[] getSubCategories(Category category) {
		
		DefaultTreeNode<Category>[] result  = new DefaultTreeNode[category.getChildren().size()] ;
		int i=0;
		for (Category sub:category.getChildren()) {
			
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
	
}
