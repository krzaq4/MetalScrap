package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.krzaq.metalscrap.model.generalization.Translatable;


@Entity
@Table(name="commodity_type")
@NamedQueries({
	@NamedQuery(name="CommodityType.findAll", query="from CommodityType c" )
	
	
})
public class CommodityType implements Serializable, Translatable {

	private Boolean isChild = false ;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="lang")
	private String lang ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="commodityType")
	private List<Commodity> commodities ;

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

	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	@Override
	public String getLang() {
		return this.lang ;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public Boolean isChild() {
		return this.isChild ;
	}
	
	
	
}
