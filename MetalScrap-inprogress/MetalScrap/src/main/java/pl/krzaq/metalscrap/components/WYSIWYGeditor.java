package pl.krzaq.metalscrap.components;


import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.Binding;

public class WYSIWYGeditor extends CKeditor {

	
	private AnnotateDataBinder binder ;
	
	public void onCreate() {
		
		this.binder = (AnnotateDataBinder) this.getPage().getAttribute("binder") ;
		
		this.addEventListener("onBlur", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (!binder.existBinding(event.getTarget(), "value")) {
					
					binder.addBinding(event.getTarget(), "value", "auction.description");
					
				}
				
				Binding binding = binder.getBinding(event.getTarget(), "value") ;
				binding.saveAttribute(event.getTarget());
				
			}
			
			
		}) ;
		
	}
	
	
}
