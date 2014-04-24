package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import pl.krzaq.metalscrap.model.generalization.Translatable;

@Entity
@Table(name="property_attribute")
@NamedQueries({
	@NamedQuery(name="PropertyAttribute.findAll", query="from PropertyAttribute a" ),
	@NamedQuery(name="PropertyAttribute.findById", query="from PropertyAttribute a where a.id=:id")
	
})
public class PropertyAttribute implements Serializable, Translatable{

	public static Integer TYPE_TEXT = 1 ;
	public static Integer TYPE_DECIMAL = 2 ;
	public static Integer TYPE_DATE = 3 ;
	public static Integer TYPE_SELECT = 4 ;
	public static Integer TYPE_MULTISELECT = 5 ;
	
	private Boolean isChild = true ;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
		
	@Column(name="type")
	private Integer type ;
	
	@Column(name="lang")
	private String lang ;
	
	@ManyToOne
	private Property property ;
	
	@OneToMany(mappedBy="attribute", cascade=CascadeType.ALL)
	@IndexColumn(name="INDEX_COL4")
	private List<PropertyAttributeValue> values ;

	@Column(name="equal_ident")
	private String equalIdentifier ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public List<PropertyAttributeValue> getValues() {
		return values;
	}

	public void setValues(List<PropertyAttributeValue> values) {
		this.values = values;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PropertyAttribute other = (PropertyAttribute) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
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

	
	
	
	
}
