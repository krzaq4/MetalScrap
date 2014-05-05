package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import pl.krzaq.metalscrap.model.generalization.Translatable;


@Entity
@Table(name="property")
@NamedQueries({
	@NamedQuery(name="Property.findAll", query="from Property a" ),
	@NamedQuery(name="Property.findById", query="from Property a where a.id=:id")
	
})
public class Property implements Serializable, Translatable {

	private Boolean isChild = false ;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="description")
	private String description ;
	
	@Column(name="lang")
	private String lang ;
	
	@Column(name="exposed")
	private Boolean exposed ;
	
	@Column(name="required")
	private Boolean required ;
	
	@Column(name="equal_ident")
	private String equalIdentifier ;
	
	@OneToMany(mappedBy="property", cascade=CascadeType.ALL)
	@IndexColumn(name="INDEX_COL3")
	private List<PropertyAttribute> attributes ;

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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<PropertyAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<PropertyAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public Boolean getExposed() {
		return exposed;
	}

	public void setExposed(Boolean exposed) {
		this.exposed = exposed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Property other = (Property) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getEqualIdentifier() {
		
		
		return equalIdentifier;
	}

	public void setEqualIdentifier(String equalIdentifier) {
		this.equalIdentifier = equalIdentifier;
	}

	@Override
	public Boolean isChild() {
		return this.isChild ;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	
	
	
	
	
}
