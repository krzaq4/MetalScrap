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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import pl.krzaq.metalscrap.dao.CategoryDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.ApplicationContextProvider;

public class CategoryTree extends Tree {

	
	private AnnotateDataBinder binder ;
	
	private boolean showDescription ;
	private boolean showParent ;
	private boolean showAllAuctions ;
	private boolean showAuctionCount ;
	private boolean showCountInSelectedOnly ;
	
	private String caption ;
	private Grid auctionList ;
	private Breadcrumb breadcrumb ;
	private List<Category> categoryModel ;
	
	
	public void onCreate() {
		
		this.binder = (AnnotateDataBinder) this.getPage().getAttribute("binder") ;
		this.setModel(this.getCategoryTreeModel());
		
		this.setItemRenderer(new CategoryInfoRenderer());
		if (caption!=null) {
			
			Treecols cols = new Treecols() ;
			Treecol tc = new Treecol() ;
			tc.setLabel(caption);
			cols.appendChild(tc) ;
			this.appendChild(cols) ;
		}
		
	}
	
	public void refreshTree() {
		
		this.setModel(this.getCategoryTreeModel());
		try {
			this.redraw(new StringWriter());
		} catch (IOException e) {
			
		}
	}
	

	private TreeModel getCategoryTreeModel() {
		
		
		
		List<TreeNode<Category>> nodes = new ArrayList<TreeNode<Category>>() ;
		
				 
				  Collections.sort(categoryModel);
				  
				  TreeNode<Category>[] nds = new DefaultTreeNode[categoryModel.size()] ;
				  int j=0 ;
				  for (Category root:categoryModel) {
					  
					  TreeNode<Category> rootNode ;
					  
					  if (ServicesImpl.getCategoryService().findSubCategories(root).size()>0) {
						  
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
		
		DefaultTreeNode<Category>[] result  = new DefaultTreeNode[ServicesImpl.getCategoryService().findSubCategories(category).size()] ;
		int i=0;
		List<Category> childCategories = ServicesImpl.getCategoryService().findSubCategories(category) ;
		Collections.sort(childCategories);
		for (Category sub:childCategories) {
			
			if (ServicesImpl.getCategoryService().findSubCategories(sub).size()>0) {
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
	        AuctionStatus status = ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_STARTED) ;
	        
	        Treerow tr = new Treerow();
	        tr.setSclass("category");
	        
	        item.setAction("show: slideDown;hide: slideUp");
	        
	        item.appendChild(tr);
	        item.setValue(fi);
	        item.addEventListener("onClick", new CategoryClickListener(status)) ;
	        Treecell tc = new Treecell() ;
	        Div cellInside = new Div() ;
	        cellInside.setSclass("categoryDiv");
	        
	        Label name = new Label(fi.getName()) ;
	        name.setSclass("categoryName") ;
	        
	        if (showAuctionCount) {
	        	if (showCountInSelectedOnly){
	        		if(item.getTree().getSelectedItem().equals(item)) {
	        			
	        			String qty = "("+ServicesImpl.getAuctionService().findByCategoryDown(fi, ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_STARTED)).size()+")" ;
	    	        	Label qtyLabel = new Label(qty) ;
	    	        	Hbox hbox = new Hbox() ;
	    	        	hbox.appendChild(name) ;
	    	        	hbox.appendChild(qtyLabel) ;
	    	        	cellInside.appendChild(hbox) ;
	        			
	        		} else {
	        			cellInside.appendChild(name) ;
	        		}
	        	} else {
	        	
	        		String qty = "("+ServicesImpl.getAuctionService().findByCategoryDown(fi, ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_STARTED)).size()+")" ;
	        		Label qtyLabel = new Label(qty) ;
	        		Hbox hbox = new Hbox() ;
	        		hbox.appendChild(name) ;
	        		hbox.appendChild(qtyLabel) ;
	        		cellInside.appendChild(hbox) ;
	        	}
	        	
	        } else {
	        	cellInside.appendChild(name) ;
	        }
	        
	        
	        
	        if(showDescription){
	        	Label desc = new Label(fi.getDescription()) ;
	        	desc.setSclass("categoryDesc") ;
	        
	        	cellInside.appendChild(desc) ;
	        }
	        
	        tc.appendChild(cellInside) ;
	        tr.appendChild(tc);
	        
	    }
	}
	
	
	
	private class CategoryClickListener implements EventListener {

		
		private AuctionStatus status ;
		
		public CategoryClickListener() {
			
		}
		
		public CategoryClickListener(AuctionStatus status){
			this.status = status ;
		}
		
		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equalsIgnoreCase("onClick")) {
				
				Category selectedCategory = ((Treeitem) event.getTarget()).getValue() ;
				
				((Treeitem) event.getTarget()).setOpen(!((Treeitem) event.getTarget()).isOpen());
				event.getTarget().getPage().setAttribute("selectedCategory", selectedCategory) ;
				
				List<Auction> auctions = ServicesImpl.getAuctionService().findByCategoryDown(selectedCategory, status) ;
				
				if (showAllAuctions)
					auctions = ServicesImpl.getAuctionService().findByCategoryDown(selectedCategory) ;
				
				((ListModelList) auctionList.getListModel()).clear();
				((ListModelList) auctionList.getListModel()).addAll(auctions) ;
				binder.loadComponent(auctionList);
				
				if (breadcrumb!=null)
					binder.loadComponent(breadcrumb);
				
				
			}
			
		}
		
		
		
	}



	public AnnotateDataBinder getBinder() {
		return binder;
	}


	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public boolean isShowDescription() {
		return showDescription;
	}

	public void setShowDescription(boolean showDescription) {
		this.showDescription = showDescription;
	}

	public boolean isShowParent() {
		return showParent;
	}

	public void setShowParent(boolean showParent) {
		this.showParent = showParent;
	}

	

	public Grid getAuctionList() {
		return auctionList;
	}

	public void setAuctionList(Grid auctionList) {
		this.auctionList = auctionList;
	}

	public Breadcrumb getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(Breadcrumb breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public List<Category> getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(List<Category> categoryModel) {
		this.categoryModel = categoryModel;
	}

	public boolean isShowAllAuctions() {
		return showAllAuctions;
	}

	public void setShowAllAuctions(boolean showAllAuctions) {
		this.showAllAuctions = showAllAuctions;
	}

	public boolean isShowAuctionCount() {
		return showAuctionCount;
	}

	public void setShowAuctionCount(boolean showAuctionCount) {
		this.showAuctionCount = showAuctionCount;
	}

	public boolean isShowCountInSelectedOnly() {
		return showCountInSelectedOnly;
	}

	public void setShowCountInSelectedOnly(boolean showCountInSelectedOnly) {
		this.showCountInSelectedOnly = showCountInSelectedOnly;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		//this.getItems().iterator().next().setLabel(caption);
	}
	
	
	
	
}
