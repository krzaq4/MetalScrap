package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="category_param")
@NamedQueries({
	@NamedQuery(name="CategoryParameter.findAll", query="from CategoryParameter c" ),
	@NamedQuery(name="CategoryParameter.findAllByLang", query="from CategoryParameter c where c.lang=:lang"),
	@NamedQuery(name="CategoryParameter.findByCategory", query="from CategoryParameter c where c.category=:category"),
	@NamedQuery(name="CategoryParameter.findByCategoryAndLang", query="from CategoryParameter c where c.lang=:lang and category=:category"),
	
	
})
public class CategoryParameter implements Serializable{

	
	public static Integer PARAM_TYPE_TEXT = 1 ;
	public static Integer PARAM_TYPE_COMBO = 2 ;
	public static Integer PARAM_TYPE_DATE = 3 ;
	public static Integer PARAM_TYPE_CHOICE = 4 ;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="description")
	private String description ;
	
	@Column(name="type")
	private Integer type ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="categoryParameter")
	private List<CategoryParameterValue> values ;
	
	@OneToOne
	@JoinColumn(name="category")
	private Category category ;
	
	@Column(name="lang")
	private String lang ;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<CategoryParameterValue> getValues() {
		return values;
	}

	public void setValues(List<CategoryParameterValue> values) {
		this.values = values;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
	
	
	
	
}
