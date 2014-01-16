package pl.krzaq.metalscrap.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Treeitem;

import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class Breadcrumb extends Div {

	private AnnotateDataBinder binder ;
	private Page page ;
	
	public void onCreate() {
		
		this.page = this.getPage() ;
		
		this.binder = (AnnotateDataBinder) page.getAttribute("binder") ;
		
		
		
		this.setSclass("breadCrumb");
		refresh() ;
		
		
	}
	
	public void refresh() {
		
		Category selectedCategory = (Category) page.getAttribute("selectedCategory") ;
		
		List<Label> crumbs = new ArrayList<Label>() ;
		AuctionStatus status = ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_STARTED) ;
		
		
		if(selectedCategory!=null) {
			
			
			
			Category selectedParent = selectedCategory.getParent() ;
			
			Label nextCrumb = new Label(selectedCategory.getName()) ;
			nextCrumb.setTooltiptext(selectedCategory.getDescription());
			nextCrumb.setSclass("breadCrumbItem") ;
			nextCrumb.addEventListener("onClick", new BreadCrumbClickListener(selectedCategory, status)) ;
			
			crumbs.add(nextCrumb) ;
			
			while(selectedParent!=null) {
				
				nextCrumb = new Label(selectedParent.getName()) ;
				nextCrumb.setTooltiptext(selectedParent.getDescription());
				nextCrumb.addEventListener("onClick", new BreadCrumbClickListener(selectedParent, status)) ;
				nextCrumb.setSclass("breadCrumbItem");
				crumbs.add(nextCrumb) ;
				selectedParent = selectedParent.getParent() ;
				
			}
			
			nextCrumb = new Label("Home") ;
			nextCrumb.setTooltiptext("");
			nextCrumb.setSclass("breadCrumbItem") ;
			nextCrumb.addEventListener("onClick", new BreadCrumbClickListener(null, status)) ;
			
			crumbs.add(nextCrumb) ;
			
			this.getChildren().clear(); 
			for (int i=crumbs.size()-1;i>=0;i--){
				if(i==0) {
					crumbs.get(i).setSclass("breadCrumbItemActive"); ;
					this.appendChild(crumbs.get(i)) ;
				} else {
					this.appendChild(crumbs.get(i)) ;
				}
				
				
				if (i>0) {
					Label breadSeparator = new Label(" > ") ;
					breadSeparator.setSclass("breadCrumbSeparator");
					this.appendChild(breadSeparator) ;
				}
				
			}
			
		} else {
			this.getChildren().clear();
			Label nextCrumb = new Label("Home") ;
			nextCrumb.setTooltiptext("");
			nextCrumb.setSclass("breadCrumbItem") ;
			nextCrumb.addEventListener("onClick", new BreadCrumbClickListener(null, status)) ;
			
			crumbs.add(nextCrumb) ;
			
			this.appendChild(nextCrumb) ;
			
		}
		
		
		
	}
	
	private class BreadCrumbClickListener implements EventListener<Event>{

		private Category category ;
		private AuctionStatus status ;
		
		public BreadCrumbClickListener(Category category, AuctionStatus status) {
			this.category = category ;
		}
		
		public BreadCrumbClickListener(){
			
		}
		
		@Override
		public void onEvent(Event event) throws Exception {
			Page p = event.getTarget().getPage() ;
			p.setAttribute("selectedCategory", this.category) ;
			p.setAttribute("categoryAuctions", ServicesImpl.getAuctionService().findByCategoryDown(this.category, this.status)) ;
			if (p.hasFellow("allAuctions"))
				binder.loadComponent(p.getFellow("allAuctions"));
			if (p.hasFellow("cat_tree")) {
				CategoryTree tree = (CategoryTree) p.getFellow("cat_tree") ;
				Iterator<Treeitem> it = tree.getItems().iterator() ;
				if(this.category!=null) {
				
					while(it.hasNext()) {
						Treeitem titem = it.next() ;
						if( ((Category)titem.getValue()).getId()==this.category.getId() && ((Category)titem.getValue()).getName().equalsIgnoreCase(this.category.getName())) {
					
							p.setAttribute("selItem", titem) ;
							break ;
						}
				
					}
			
			}
		} else {
			
			if(p.hasAttribute("selItem")) {
				p.removeAttribute("selItem") ;
			}
		}
			if(p.hasFellow("cat_tree"))
				binder.loadComponent(p.getFellow("cat_tree"));
			refresh() ;
			
		}
		
		
	}
	
	
}
