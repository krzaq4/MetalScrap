package pl.krzaq.metalscrap.events;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.model.CommodityType;



public class FormEvents {

	
	
	public void showAuctionPositions(Panel a, Panel b) {
		
		a.setVisible(false) ;
		b.setVisible(true) ;
		
	}
	
	public void showAuctionParameters(Panel a, Panel b) {
		
		b.setVisible(false) ;
		a.setVisible(true) ;
	}
	
	
	public void openAddPositionWindow(Component c) {
		
		
		Window window = (Window)Executions.createComponents(
                "/secured/auctions/windows/add_position.zul", null,null);
       
		window.setPage(c.getPage());
        window.doPopup();
		
	}
	
}
