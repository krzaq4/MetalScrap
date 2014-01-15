package pl.krzaq.metalscrap.components;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import pl.krzaq.metalscrap.model.Category;

public class Breadcrumb extends Div {

	
	
	public void onCreate() {
		
		Page page = this.getPage() ;
		this.setSclass("breadCrumb");
		
		Category selectedCategory = (Category) page.getAttribute("selectedCategory") ;
		
		if(selectedCategory!=null) {
			
			List<Label> crumbs = new ArrayList<Label>() ;
			
			Category selectedParent = selectedCategory.getParent() ;
			
			Label nextCrumb = new Label(selectedCategory.getName()) ;
			nextCrumb.setTooltiptext(selectedCategory.getDescription());
			nextCrumb.setStyle("breadCrumbItem") ;
			nextCrumb.addEventListener("onClick", new BreadCrumbClickListener()) ;
			
			crumbs.add(nextCrumb) ;
			
			while(selectedParent!=null) {
				
				nextCrumb = new Label(selectedParent.getName()) ;
				nextCrumb.setTooltiptext(selectedParent.getDescription());
				nextCrumb.addEventListener("onClick", new BreadCrumbClickListener()) ;
				
				crumbs.add(nextCrumb) ;
				
			}
			
			for (int i=crumbs.size()-1;i>=0;i--){
				
				this.appendChild(crumbs.get(i)) ;
				if (i>0) {
					Label breadSeparator = new Label(" > ") ;
					breadSeparator.setSclass("breadCrumbSeparator");
					this.appendChild(breadSeparator) ;
				}
				
			}
			
		}
		
	}
	
	
	private class BreadCrumbClickListener implements EventListener<Event>{

		@Override
		public void onEvent(Event arg0) throws Exception {
			
			
		}
		
		
	}
	
	
}
