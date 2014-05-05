package pl.krzaq.metalscrap.bind;

import java.util.ArrayList;
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
	
	private Category parentCategory ;
	
	private Property newProperty ;
	
	private Property selectedProperty ;
	
	private List<Property> categoryProperties ;
	
	private PropertyAttribute selectedPropertyAttribute ;
	
	private List<PropertyAttribute> propertyAttributes ;
	
	private PropertyAttributeValue selectedAttributeValue ;
	
	private List<PropertyAttributeValue> attributeValues ;
	
	private List<Integer> propertyAttributeTypes ;
	
	@AfterCompose
	@NotifyChange({"selectedCategory", "categoryProperties", "propertyAttributeTypes"})
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
	
		 Selectors.wireComponents(view, this, false);
		 propertyAttributeTypes = new ArrayList<Integer>() ;
		 propertyAttributeTypes.add(PropertyAttribute.TYPE_DATE) ;
		 propertyAttributeTypes.add(PropertyAttribute.TYPE_DECIMAL) ;
		 propertyAttributeTypes.add(PropertyAttribute.TYPE_MULTISELECT) ;
		 propertyAttributeTypes.add(PropertyAttribute.TYPE_SELECT) ;
		 propertyAttributeTypes.add(PropertyAttribute.TYPE_TEXT) ;
		 
	}
	
	
	@Init
	public void init() {
		
		
	}
	
	
	@Command
	@NotifyChange({"selectedCategory", "categoryProperties", "parentCategory"})
	public void onWindowOpen() {
		selectedCategory = (Category) win.getAttribute("category") ;
		categoryProperties = selectedCategory.getProps() ;
		parentCategory = selectedCategory.getParent() ;
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


	public Grid getCategoryPropertiesGrid() {
		return categoryPropertiesGrid;
	}


	public void setCategoryPropertiesGrid(Grid categoryPropertiesGrid) {
		this.categoryPropertiesGrid = categoryPropertiesGrid;
	}


	public Grid getAttributeValuesGrid() {
		return attributeValuesGrid;
	}


	public void setAttributeValuesGrid(Grid attributeValuesGrid) {
		this.attributeValuesGrid = attributeValuesGrid;
	}


	public Window getWin() {
		return win;
	}


	public void setWin(Window win) {
		this.win = win;
	}


	public Category getSelectedCategory() {
		return selectedCategory;
	}


	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}


	public Category getParentCategory() {
		return parentCategory;
	}


	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}


	public Property getNewProperty() {
		return newProperty;
	}


	public void setNewProperty(Property newProperty) {
		this.newProperty = newProperty;
	}


	public Property getSelectedProperty() {
		return selectedProperty;
	}


	public void setSelectedProperty(Property selectedProperty) {
		this.selectedProperty = selectedProperty;
	}


	public List<Property> getCategoryProperties() {
		return categoryProperties;
	}


	public void setCategoryProperties(List<Property> categoryProperties) {
		this.categoryProperties = categoryProperties;
	}


	public PropertyAttribute getSelectedPropertyAttribute() {
		return selectedPropertyAttribute;
	}


	public void setSelectedPropertyAttribute(
			PropertyAttribute selectedPropertyAttribute) {
		this.selectedPropertyAttribute = selectedPropertyAttribute;
	}


	public List<PropertyAttribute> getPropertyAttributes() {
		return propertyAttributes;
	}


	public void setPropertyAttributes(List<PropertyAttribute> propertyAttributes) {
		this.propertyAttributes = propertyAttributes;
	}


	public PropertyAttributeValue getSelectedAttributeValue() {
		return selectedAttributeValue;
	}


	public void setSelectedAttributeValue(
			PropertyAttributeValue selectedAttributeValue) {
		this.selectedAttributeValue = selectedAttributeValue;
	}


	public List<PropertyAttributeValue> getAttributeValues() {
		return attributeValues;
	}


	public void setAttributeValues(List<PropertyAttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}


	public List<Integer> getPropertyAttributeTypes() {
		return propertyAttributeTypes;
	}


	public void setPropertyAttributeTypes(List<Integer> propertyAttributeTypes) {
		this.propertyAttributeTypes = propertyAttributeTypes;
	}
	
	//--------------------
	
	
	
}
