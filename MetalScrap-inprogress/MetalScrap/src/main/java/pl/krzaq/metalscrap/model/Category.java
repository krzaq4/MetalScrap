package pl.krzaq.metalscrap.model;

import java.io.Serializable;
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
	@NamedQuery(name="Category.findSubCategories", query="from Category c where c.parent=:parent" ),
	@NamedQuery(name="Category.findByName", query="from Category c where c.name=:name" )
	
	
	
	
})
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="description")
	private String description ;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="parent")
	private Category parent ;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Category> children ;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Auction> auctions ;

	
	
	public Category() {
		
		
		
	}
	
	
	
	public Category(String name, String description, Category parent) {
		
		this.name = name ;
		this.description = description ;
		this.parent = parent ;
	}
	
	
	//-----------------------------------------------------------------------
	
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
	
	
	
	
	
}
