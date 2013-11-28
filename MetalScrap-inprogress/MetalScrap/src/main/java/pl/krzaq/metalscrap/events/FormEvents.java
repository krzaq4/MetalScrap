package pl.krzaq.metalscrap.events;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.model.CommodityType;



public class FormEvents {

	
	
	public void showAuctionPositions(Component a, Component b) {
		
		a.setVisible(false) ;
		b.setVisible(true) ;
		
	}
	
	public void showAuctionParameters(Component a, Component b) {
		
		b.setVisible(false) ;
		a.setVisible(true) ;
	}
	
	
	public void openAddPositionWindow(Component c) {
		
		
		Window window = (Window)Executions.createComponents(
                "/secured/auctions/windows/add_position.zul", null,null);
       
		window.setPage(c.getPage());
        window.doPopup();
		
	}
	
	public void clearForms(Component c) {
		
		
		if (c.getChildren().size()>0) {
			for (Component chk:c.getChildren()) {
				
				if(chk instanceof Textbox) {
					((Textbox)chk).setValue("");
				} else {
					clearForms(chk) ;
				}
				
			}
			
			
			
		}
		
		
	}
	
}
