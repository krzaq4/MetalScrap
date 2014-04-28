package pl.krzaq.metalscrap.bind;

import java.math.BigDecimal;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

import pl.krzaq.metalscrap.model.Config;
import pl.krzaq.metalscrap.utils.Utilities;


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
	
	
	
	// system configuration
	
	private boolean auctionCategoriesVisible ;
	private String currencySymbol ;
	private String hostAddress ;
	private BigDecimal autobidderStep ;
	private boolean userVerificationMode ;
	private String userVerificationType ;
	
	@Command
	@NotifyChange({"auctionCategoriesVisible"})
	public void selectAuctionCategoriesVisible(@BindingParam("val") boolean val) {
		
		Config cfg = Utilities.getServices().getConfigService().findByKey("auction_categories_visible") ;
		cfg.setValue(String.valueOf(val));
		Utilities.getServices().getConfigService().update(cfg);
		auctionCategoriesVisible = val ;
		
	}
	
	@Command
	@NotifyChange({"currencySymbol"})
	public void updateCurrencySymbol(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		Config cfg = Utilities.getServices().getConfigService().findByKey("auction.currency.symbol");
		cfg.setValue(event.getValue()) ;
		Utilities.getServices().getConfigService().update(cfg);
		currencySymbol = event.getValue() ;
	}
	
	@Command
	@NotifyChange({"hostAddress"})
	public void updateHostAddress(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		Config cfg = Utilities.getServices().getConfigService().findByKey("host.address");
		cfg.setValue(event.getValue()) ;
		Utilities.getServices().getConfigService().update(cfg);
		hostAddress = event.getValue() ;
	}
	
	@Command
	@NotifyChange({"autobidderStep"})
	public void updateAutobidderStep(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		autobidderStep = new BigDecimal(event.getValue()) ;
		Config cfg = Utilities.getServices().getConfigService().findByKey("auction.autobidder.step");
		cfg.setValue(event.getValue()) ;
		Utilities.getServices().getConfigService().update(cfg);
		
	}
	
	@Command
	@NotifyChange({"userVerificationMode"})
	public void selectUserVerificationMode(@BindingParam("val") boolean val) {
		
		Config cfg = Utilities.getServices().getConfigService().findByKey("user.verification.mode.auto") ;
		cfg.setValue(String.valueOf(val));
		Utilities.getServices().getConfigService().update(cfg);
		userVerificationMode = val ;
		
	}
	
	@Command
	@NotifyChange({"userVerificationType"})
	public void selectUserVerificationType(@BindingParam("val") String val) {
		
		Config cfg = Utilities.getServices().getConfigService().findByKey("user.verification.mode.type") ;
		cfg.setValue(val);
		Utilities.getServices().getConfigService().update(cfg);
		userVerificationType = val ;
		
	}
	
	//---------------------
	
	
	//Auction Config
	
	
	
	//-------------------------
	
	@AfterCompose
	@NotifyChange({"auctionCategoriesVisible", "currencySymbol", "hostAddress", "autobidderStep", "userVerificationMode", "userVerificationType"})
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		
		Selectors.wireComponents(view, this, false);
		// System config
		auctionCategoriesVisible = Boolean.valueOf(Utilities.getServices().getConfigService().findByKey("auction_categories_visible").getValue());
		currencySymbol = Utilities.getServices().getConfigService().findByKey("auction.currency.symbol").getValue();
		hostAddress = Utilities.getServices().getConfigService().findByKey("host.address").getValue();
		autobidderStep = new BigDecimal(Utilities.getServices().getConfigService().findByKey("auction.autobidder.step").getValue());
		userVerificationMode = Boolean.valueOf(Utilities.getServices().getConfigService().findByKey("user.verification.mode.auto").getValue());
		userVerificationType = Utilities.getServices().getConfigService().findByKey("user.verification.mode.type").getValue();
		
		// Auction config
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuSysConfig", "menuAuctionConfig", "menuLookConfig", "menuIntegration"})
	public void showIntegrationMenu() {
		
		integrationDiv.setSclass("configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
		menuIntegration = true ;
		menuSysConfig = false ;
		menuAuctionConfig = false ;
		menuLookConfig = false ;
		
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuSysConfig", "menuAuctionConfig", "menuLookConfig", "menuIntegration"})
	public void showSysConfigMenu() {
		
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
		menuIntegration = false ;
		menuSysConfig = true ;
		menuAuctionConfig = false ;
		menuLookConfig = false ;
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuSysConfig", "menuAuctionConfig", "menuLookConfig", "menuIntegration"})
	public void showAuctionConfigMenu() {
		
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("configDiv");
		lookConfigDiv.setSclass("wrapped configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
		menuIntegration = false ;
		menuSysConfig = false ;
		menuAuctionConfig = true ;
		menuLookConfig = false ;
	}
	
	@Command
	@NotifyChange({"menuVisible", "menuSysConfig", "menuAuctionConfig", "menuLookConfig", "menuIntegration"})
	public void showLookConfigMenu() {
		
		integrationDiv.setSclass("wrapped configDiv");
		sysConfigDiv.setSclass("wrapped configDiv");
		auctionConfigDiv.setSclass("wrapped configDiv");
		lookConfigDiv.setSclass("configDiv");
		
		tilesInsideUp.setSclass("tilesInsideUp");
		tilesInside.setSclass("wrapped tilesInside");
		
		menuIntegration = false ;
		menuSysConfig = false ;
		menuAuctionConfig = false ;
		menuLookConfig = true ;
		
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

	public boolean getAuctionCategoriesVisible() {
		return auctionCategoriesVisible;
	}

	public void setAuctionCategoriesVisible(boolean auctionCategoriesVisible) {
		this.auctionCategoriesVisible = auctionCategoriesVisible;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public BigDecimal getAutobidderStep() {
		return autobidderStep;
	}

	public void setAutobidderStep(BigDecimal autobidderStep) {
		this.autobidderStep = autobidderStep;
	}

	public boolean isUserVerificationMode() {
		return userVerificationMode;
	}

	public void setUserVerificationMode(boolean userVerificationMode) {
		this.userVerificationMode = userVerificationMode;
	}

	public String getUserVerificationType() {
		return userVerificationType;
	}

	public void setUserVerificationType(String userVerificationType) {
		this.userVerificationType = userVerificationType;
	}
	
	
	
}
