package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne ;
import javax.persistence.OneToMany ;
import javax.persistence.Table;

@Entity
@Table(name="commodity")
@NamedQueries({
	@NamedQuery(name="Commodity.findAll", query="from Commodity" )
	
	
})
public class Commodity implements Serializable {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	
	@Column(name="name")
	private String name ;
	
	@Column(name="description")
	private String description ;
		
	@Column(name="cpv")
	private String cpv ;
	
	@Column(name="quantity")
	private Double quantity ;
	
	@ManyToOne
	@JoinColumn(name="commodity_type")
	private CommodityType commodityType ;
	
	@ManyToMany
	@JoinTable(name="catalogue_commodities")
	private Set<CommodityCatalogue> catalogues ;
	
	@ManyToOne
	@JoinColumn(name="auction")
	private Auction auction ;

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

	public String getCpv() {
		return cpv;
	}

	public void setCpv(String cpv) {
		this.cpv = cpv;
	}

	public CommodityType getCommodityType() {
		return commodityType;
	}

	public void setType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}

	public Set<CommodityCatalogue> getCatalogues() {
		return catalogues;
	}

	public void setCatalogues(Set<CommodityCatalogue> catalogues) {
		this.catalogues = catalogues;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public void setCommodityType(CommodityType commodityType) {
		this.commodityType = commodityType;
	}
	
	
	
	
	
}
