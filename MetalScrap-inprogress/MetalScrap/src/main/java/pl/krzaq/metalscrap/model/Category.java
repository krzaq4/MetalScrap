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


@Entity
@Table(name="category")
@NamedQueries({
	@NamedQuery(name="Category.findAll", query="from Category c" ),
	@NamedQuery(name="Category.findAllByLang", query="from Category c where c.lang=:lang" ),
	@NamedQuery(name="Category.findSubCategories", query="from Category c where c.parent=:parent" ),
	@NamedQuery(name="Category.findSubCategoriesByLang", query="from Category c where c.parent=:parent and c.lang=:lang" ),
	@NamedQuery(name="Category.findByName", query="from Category c where c.name=:name" ),
	@NamedQuery(name="Category.findByNameAndLang", query="from Category c where c.name=:name and c.lang=:lang" )
	
	
	
	
})
public class Category implements Serializable, Comparable {

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
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="parent")
	private Category parent ;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Category> children ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private List<Auction> auctions ;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private List<CategoryParameter> parameters ;
	
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
		this.children = category.getChildren()==null ? null : new ArrayList<Category>(category.getChildren()) ;
		this.description = new String(category.getDescription()) ;
		this.id = category.getId()==null ? null : new Long(category.getId()) ;
		this.name = new String(category.getName()) ;
		this.parent = category.getParent() ==null ? null :new Category(category.getParent()) ;
		this.position = new Integer(category.getPosition()).intValue() ;
		
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

	

	public String getLang() {
		return lang;
	}



	public void setLang(String lang) {
		this.lang = lang;
	}

	

	public List<CategoryParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<CategoryParameter> parameters) {
		this.parameters = parameters;
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
	
	
	
	
	
}
