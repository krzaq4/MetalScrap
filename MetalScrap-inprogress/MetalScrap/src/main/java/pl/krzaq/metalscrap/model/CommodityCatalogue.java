package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="commodity_catalogue")
@NamedQueries({
	@NamedQuery(name="CommodityCatalogue.findAll", query="from CommodityCatalogue" )
	
	
})
public class CommodityCatalogue implements Serializable{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	
	@ManyToMany
	@JoinTable(name="catalogue_commodities")
	private Set<Commodity> commodities ;
	
	@ManyToOne
	@JoinColumn(name="company")
	private Company company ;

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

	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
