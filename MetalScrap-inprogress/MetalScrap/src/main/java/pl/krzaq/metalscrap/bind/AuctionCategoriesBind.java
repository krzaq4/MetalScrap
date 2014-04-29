package pl.krzaq.metalscrap.bind;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;

public class AuctionCategoriesBind {

	@Wire("#catParamsGrid")
	private Grid categoryPropertiesGrid ;
	
	@Wire("#valsGrid")
	private Grid attributeValuesGrid ;
	
	@Wire("#edit_category_popup")
	private Window win ;
	
	private Category selectedCategory ;
	
	private Property newProperty ;
	
	private Property selectedProperty ;
	
	private List<Property> categoryProperties ;
	
	private PropertyAttribute selectedPropertyAttribute ;
	
	private List<PropertyAttribute> propertyAttributes ;
	
	private PropertyAttributeValue selectedAttributeValue ;
	
	private List<PropertyAttributeValue> attributeValues ;
	
	
	@AfterCompose
	@NotifyChange({"selectedCategory", "categoryProperties"})
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
	
		 Selectors.wireComponents(view, this, false);
		 
		 selectedCategory = (Category) win.getAttribute("category") ;
		 
		 categoryProperties = selectedCategory.getProps() ;
		 
		 
	}
	
	
	@Init
	public void init() {
		
		
	}
	
	
	// Property
	
	@Command
	public void addCategoryProperty() {
		
	}
	
	@Command
	public void selectCategoryProperty() {
		
	}
	
	@Command
	public void deleteCategoryProperty() {
		
	}
	
	// ----------------
	
	
	// Attribute
	
	@Command
	public void addPropertyAttribute() {
		
	}
	
	@Command 
	public void deletePropertyAttribute() {
		
	}
	
	@Command
	public void selectPropertyAttribute() {
		
	}
	
	@Command
	public void selectPropertyAttributeType() {
		
	}
	
	// -----------------
	
	// Values
	
	@Command
	public void addAttributeValue() {
		
	}
	
	@Command
	public void deleteAttributeValue() {
		
	}
	
	//--------------------
	
	
	
}
