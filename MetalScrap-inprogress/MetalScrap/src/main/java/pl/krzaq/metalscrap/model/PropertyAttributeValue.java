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


@Entity
@Table(name="property_attribute_value")
@NamedQueries({
	@NamedQuery(name="PropertyAttributeValue.findAll", query="from PropertyAttributeValue a" ),
	@NamedQuery(name="PropertyAttributeValue.findById", query="from PropertyAttributeValue a where a.id=:id")
	
})
public class PropertyAttributeValue {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="value")
	private String value ;
	
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
	
	
	
}
