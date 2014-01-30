package pl.krzaq.metalscrap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="category_param_value")
@NamedQueries({
	@NamedQuery(name="CategoryParameterValue.findAll", query="from CategoryParameterValue c" ),
	@NamedQuery(name="CategoryParameterValue.findAllByLang", query="from CategoryParameterValue c where c.lang=:lang"),
	@NamedQuery(name="CategoryParameterValue.findByCategoryParameter", query="from CategoryParameterValue c where c.categoryParameter=:categoryParameter"),
	@NamedQuery(name="CategoryParameterValue.findByCategoryParameterAndLang", query="from CategoryParameterValue c where c.lang=:lang and c.categoryParameter=:categoryParameter")
	
})
public class CategoryParameterValue implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@ManyToOne
	@JoinColumn(name="category_parameter")
	private CategoryParameter categoryParameter ;
	
	@Column(name="lang")
	private String lang ;
	
	@Column(name="value")
	private String value ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoryParameter getCategoryParameter() {
		return categoryParameter;
	}

	public void setCategoryParameter(CategoryParameter categoryParameter) {
		this.categoryParameter = categoryParameter;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
