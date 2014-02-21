package pl.krzaq.metalscrap.bind;

import org.zkoss.bind.annotation.Command;

public class AuctionViewBind {

	
	private String timeLeft ;
	
	@Command("update")
	public void update() {
		
		System.out.println("Bind update") ;
	}
	
}
