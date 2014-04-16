package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import pl.krzaq.metalscrap.model.generalization.Translatable;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;


@Entity
@Table(name="category")
@NamedQueries({
	@NamedQuery(name="Category.findAll", query="from Category c" ),
	@NamedQuery(name="Category.findAllByLang", query="from Category c where c.lang=:lang" ),
	@NamedQuery(name="Category.findSubCategories", query="from Category c where c.parent=:parent" ),
	@NamedQuery(name="Category.findSubCategoriesByLang", query="from Category c where c.parent=:parent and c.lang=:lang" ),
	@NamedQuery(name="Category.findByName", query="from Category c where c.name=:name" ),
	@NamedQuery(name="Category.findByNameAndLang", query="from Category c where c.name=:name and c.lang=:lang" ),
	@NamedQuery(name="Category.findById", query="from Category c where c.id=:id")
	
	
	
	
})
public class Category implements Serializable, Comparable, Translatable {

	
	private Boolean isChild = false ;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="description")
	private String description ;
	
	@Column(name="position")
	private int position ;
	
	@Column(name="lang")
	private String lang ;
	
	@Column(name="eq_ident")
	private String equalIdentifier ;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="parent")
	private Category parent ;
	
	@OneToMany(cascade=CascadeType.ALL)
	@IndexColumn(name="INDEX_COL2")
	private List<Category> children ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private List<Auction> auctions ;

	@OneToMany(cascade=CascadeType.ALL)
	@IndexColumn(name="INDEX_COL")
	private List<Property> properties = new ArrayList<Property>();
	
	public Category() {
		
		
		
	}
	
public Category(String name, String description, Category parent, String lang) {
		
		this.name = name ;
		this.description = description ;
		this.parent = parent ;
		this.auctions = new ArrayList<Auction>() ;
		this.children = new ArrayList<Category>() ;
		this.lang = lang ;
	}
	
	
	public Category(String name, String description, Category parent) {
		
		this.name = name ;
		this.description = description ;
		this.parent = parent ;
		this.auctions = new ArrayList<Auction>() ;
		this.children = new ArrayList<Category>() ;
	}
	
	public Category(Category category) {
		
		this.auctions = new ArrayList<Auction>() ;
		
		
		if (category.getChildren()!=null) Collections.sort(category.getChildren()) ;
		this.children = category.getChildren()==null ? new ArrayList<Category>() : new ArrayList<Category>(category.getChildren()) ;
		this.description = new String(category.getDescription()) ;
		this.id = category.getId()==null ? null : new Long(category.getId()) ;
		this.name = new String(category.getName()) ;
		this.parent = category.getParent() ==null ? null : category.getParent() ;
		this.position = new Integer(category.getPosition()).intValue() ;
		this.properties = category.getProperties()==null ? new ArrayList<Property>() : new ArrayList<Property>(category.getProperties()) ;
		this.lang = category.getLang() ;
	}
	
	
	
	
	//-----------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + position;
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
		Category other = (Category) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position != other.position)
			return false;
		return true;
	}



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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}


	public List<Category> getChildren() {
		return children;
	}


	public void setChildren(List<Category> children) {
		this.children = children;
	}



	public int getPosition() {
		return position;
	}



	public void setPosition(int position) {
		this.position = position;
	}

	
	@Override
	public String getLang() {
		return lang;
	}



	public void setLang(String lang) {
		this.lang = lang;
	}

	
	public List<Property> getProperties() {
		Category par = this.getParent() ;
		List<Property> props = new ArrayList<Property>() ;
		while(par!=null) {
			List<Property> newProps = ServicesImpl.getCategoryService().findById(par.getId()).getProperties() ;
			if(newProps!=null && newProps.size()>0) {
				props.addAll(newProps) ;
			}
			par = par.getParent() ;
		}
		props.addAll(properties) ;
		return props;
	}

	public List<Property> getProps() {
		return properties ;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	

	public String getEqualIdentifier() {
		return equalIdentifier;
	}

	public void setEqualIdentifier(String equalIdentifier) {
		this.equalIdentifier = equalIdentifier;
	}

	@Override
	public int compareTo(Object o) {
		
		Category toCompare = (Category) o ;
		int cmp = 0 ;
		if (this.getPosition()<toCompare.getPosition()) cmp=-1 ;
		if (this.getPosition()>toCompare.getPosition()) cmp=1 ;
		if (this.getPosition()==toCompare.getPosition()) cmp=0 ;
		return cmp ;
	}

	@Override
	public Boolean isChild() {
		return this.isChild ;
	}
	
	
	public Category clone(String lang, boolean save) {
		
		Category ca = new Category() ;
		ca.setDescription(this.getDescription());
		ca.setLang(lang);
		ca.setName(this.getName());
		
		if(this.getParent()!=null) {
			ca.setParent(this);getParent().clone(lang, save) ;
		} else {
			ca.setParent(null);
		}
		
		ca.setPosition(this.getPosition());
		
		if (this.getAuctions()!=null) {
			List<Auction> auctions = new ArrayList<Auction>(this.getAuctions()) ;
			ca.setAuctions(auctions);
		} else {
			ca.setAuctions(null);
		}
		
		if(this.getChildren()!=null) {
			List<Category> childs = new ArrayList<Category>() ;
			for (Category c:this.getChildren()) {
				childs.add(c.clone(lang, save)) ;
			}
			ca.setChildren(childs);
		} else {
			ca.setChildren(null);
		}
		
		if(this.getProps()!=null) {
			List<Property> props = new ArrayList<Property>() ;
			for (Property p:this.getProps()) {
				Property newP = new Property() ;
				newP.setDescription(p.getDescription());
				newP.setExposed(p.getExposed());
				newP.setLang(lang);
				newP.setName(p.getName());
				if(p.getAttributes()!=null) {
					List<PropertyAttribute> patts = new ArrayList<PropertyAttribute>() ;
					for (PropertyAttribute pa:p.getAttributes()) {
						PropertyAttribute newPa = new PropertyAttribute() ;
						newPa.setLang(lang);
						newPa.setName(pa.getName());
						newPa.setProperty(newP);
						newPa.setType(pa.getType());
						
						if(pa.getValues()!=null) {
							List<PropertyAttributeValue> pvals = new ArrayList<PropertyAttributeValue>() ;
							
							for (PropertyAttributeValue pav:pa.getValues()) {
								PropertyAttributeValue newPav = new PropertyAttributeValue() ;
								newPav.setAttribute(newPa);
								newPav.setLang(lang);
								newPav.setValue(pav.getValue());
								pvals.add(newPav) ;
							}
							
							newPa.setValues(pvals);
						} else {
							newPa.setValues(null);
						}
						
					}
					
					
				} else {
					newP.setAttributes(null);
				}
				
				
				props.add(newP) ;
			}
			
			ca.setProperties(props);
			
		} else {
			ca.setProperties(null);
		}
		
		if(save) {
			ServicesImpl.getCategoryService().save(ca);
		}
		
		return ca ;
	}
	
	
}
