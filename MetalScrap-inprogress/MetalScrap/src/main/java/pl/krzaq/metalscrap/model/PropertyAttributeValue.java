package pl.krzaq.metalscrap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pl.krzaq.metalscrap.model.generalization.Translatable;


@Entity
@Table(name="property_attribute_value")
@NamedQueries({
	@NamedQuery(name="PropertyAttributeValue.findAll", query="from PropertyAttributeValue a" ),
	@NamedQuery(name="PropertyAttributeValue.findById", query="from PropertyAttributeValue a where a.id=:id")
	
})
public class PropertyAttributeValue implements Translatable {

	Boolean isChild = true ;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="value")
	private String value ;
	
	@Column(name="lang")
	private String lang ;
	
	@Column(name="equal_ident")
	private String equalIdentifier ;
	
	@ManyToOne
	private PropertyAttribute attribute ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PropertyAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(PropertyAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyAttributeValue other = (PropertyAttributeValue) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String getLang() {
		return this.lang ;
	}

	@Override
	public Boolean isChild() {
		return this.isChild ;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getEqualIdentifier() {
		return equalIdentifier;
	}

	public void setEqualIdentifier(String equalIdentifier) {
		this.equalIdentifier = equalIdentifier;
	}
	
	/*public PropertyAttributeValue clone(String lang, boolean save) {
		
		PropertyAttributeValue pav = new PropertyAttributeValue() ;
		pav.setLang(lang);
		pav.set
	}*/
	
}
