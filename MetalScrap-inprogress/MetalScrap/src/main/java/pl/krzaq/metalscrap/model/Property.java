package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="property")
@NamedQueries({
	@NamedQuery(name="Property.findAll", query="from Property a" ),
	@NamedQuery(name="Property.findById", query="from Property a where a.id=:id")
	
})
public class Property implements Serializable {

	
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
	
	@OneToMany(mappedBy="property")
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
	
	
	
	
}
