package pl.krzaq.metalscrap.bind;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

public class ConfigBind {

	@Wire("#tilesInside")
	private Div tilesInside ;
	
	@Wire("#tilesInsideUp")
	private Div tilesInsideUp ;
	
	@Wire("#integrationDiv")
	private Div integrationDiv ;
	
	@Wire("#sysConfigDiv")
	private Div sysConfigDiv ;
	
	@Wire("#auctionConfigDiv")
	private Div auctionConfigDiv ;
	
	@Wire("#lookConfigDiv")
	private Div lookConfigDiv ;
	
	private boolean menuIntegration = false ;
	private boolean menuSysConfig = false ;
	private boolean menuAuctionConfig = false ;
	private boolean menuLookConfig = false ;
	
	
	private boolean menuVisible = true ;
	
	
	
	@Command
	@NotifyChange({"menuVisible", "menuIntegration"})
	public void showIntegrationMenu() {
		
		integrationDiv.setSclass("configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
		
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuSysConfig"})
	public void showSysConfigMenu() {
		
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuAuctionConfig"})
	public void showAuctionConfigMenu() {
		
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuLookConfig"})
	public void showLookConfigMenu() {
		
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
	}
	
	@Command
	public void backToMainMenu() {
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("wrapped tilesInsideUp");
		tilesInside.setSclass("tilesInside");
	}
	
	
	public boolean isMenuIntegration() {
		return menuIntegration;
	}
	public void setMenuIntegration(boolean menuIntegration) {
		this.menuIntegration = menuIntegration;
	}
	public boolean isMenuSysConfig() {
		return menuSysConfig;
	}
	public void setMenuSysConfig(boolean menuSysConfig) {
		this.menuSysConfig = menuSysConfig;
	}
	public boolean isMenuAuctionConfig() {
		return menuAuctionConfig;
	}
	public void setMenuAuctionConfig(boolean menuAuctionConfig) {
		this.menuAuctionConfig = menuAuctionConfig;
	}
	public boolean isMenuLookConfig() {
		return menuLookConfig;
	}
	public void setMenuLookConfig(boolean menuLookConfig) {
		this.menuLookConfig = menuLookConfig;
	}

	public Div getTilesInside() {
		return tilesInside;
	}

	public void setTilesInside(Div tilesInside) {
		this.tilesInside = tilesInside;
	}

	public Div getTilesInsideUp() {
		return tilesInsideUp;
	}

	public void setTilesInsideUp(Div tilesInsideUp) {
		this.tilesInsideUp = tilesInsideUp;
	}

	public boolean isMenuVisible() {
		return menuVisible;
	}

	public void setMenuVisible(boolean menuVisible) {
		this.menuVisible = menuVisible;
	}
	
	
	
}
