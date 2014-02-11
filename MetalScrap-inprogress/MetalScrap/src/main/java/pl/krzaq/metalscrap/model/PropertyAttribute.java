package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.List;

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

@Entity
@Table(name="property_attribute")
@NamedQueries({
	@NamedQuery(name="PropertyAttribute.findAll", query="from PropertyAttribute a" ),
	@NamedQuery(name="PropertyAttribute.findById", query="from PropertyAttribute a where a.id=:id")
	
})
public class PropertyAttribute implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	
	@Column(name="name")
	private String name ;
	
	@Column(name="description")
	private String description ;
	
	@ManyToOne
	private Property property ;
	
	@OneToMany(mappedBy="attribute")
	private List<PropertyAttributeValue> values ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
	
	
}
