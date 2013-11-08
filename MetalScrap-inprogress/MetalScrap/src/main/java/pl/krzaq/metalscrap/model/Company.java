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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@SuppressWarnings("serial")
@Entity
@Table(name="company")
@NamedQueries({
	@NamedQuery(name="Company.findAll", query="from Company" )
	
	
})
public class Company implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@NotNull
	@Column(name="company_name")
	private String name ;
	
	@NotNull
	@Column(name="nip")
	private String nip ;
	
	@Column(name="regon")
	private String regon ;
	
	@OneToOne
	@JoinColumn(name="address_main")
	private Address addressMain ;
	
	@OneToOne
	@JoinColumn(name="address_additional")
	private Address addressAdditional ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="company")
	private Set<PkdClassification> pkdClassification ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="company")
	private Set<User> users ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
	private Set<Auction> auctions ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="company")
	private Set<CommodityCatalogue> commodityCatalogues ;

	
	// -------------------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getRegon() {
		return regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public Address getAddressMain() {
		return addressMain;
	}

	public void setAddressMain(Address addressMain) {
		this.addressMain = addressMain;
	}

	public Address getAddressAdditional() {
		return addressAdditional;
	}

	public void setAddressAdditional(Address addressAdditional) {
		this.addressAdditional = addressAdditional;
	}

	public Set<PkdClassification> getPkdClassification() {
		return pkdClassification;
	}

	public void setPkdClassification(Set<PkdClassification> pkdClassification) {
		this.pkdClassification = pkdClassification;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(Set<Auction> auctions) {
		this.auctions = auctions;
	}

	public Set<CommodityCatalogue> getCommodityCatalogues() {
		return commodityCatalogues;
	}

	public void setCommodityCatalogues(Set<CommodityCatalogue> commodityCatalogues) {
		this.commodityCatalogues = commodityCatalogues;
	}

	public Long getId() {
		return id;
	}
	
	
	
	// ------------------------------------------------------------------
	
	
	
}
