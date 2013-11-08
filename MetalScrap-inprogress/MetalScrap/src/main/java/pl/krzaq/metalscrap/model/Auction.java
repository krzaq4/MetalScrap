package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="auction")
@NamedQueries({
	@NamedQuery(name="Auction.findAll", query="from Auction a" )
	
	
	
})
public class Auction implements Serializable {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	
	@Column(name="start_date")
	private Date startDate ;
	
	@Column(name="end_date")
	private Date endDate ;
	
	@Column(name="start_price")
	private Double startPrice ;
	
	@Column(name="best_price")
	private Double bestPrice ;
	
	@Column(name="description")
	private String description ;
	
	@OneToOne
	@JoinColumn(name="winner")
	private Company winner ;
	
	@OneToOne
	@JoinColumn(name="owner")
	private Company owner ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="auction")
	private Set<CompanyOffer> companyOffers ;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="auction")
	private Set<Commodity> commodities ;
	
	@OneToOne
	@JoinColumn(name="status")
	private AuctionStatus status ;
	
	
	// -------------------------------------------------------------------------------
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getBestPrice() {
		return bestPrice;
	}

	public void setBestPrice(Double bestPrice) {
		this.bestPrice = bestPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Company getWinner() {
		return winner;
	}

	public void setWinner(Company winner) {
		this.winner = winner;
	}

	public Company getOwner() {
		return owner;
	}

	public void setOwner(Company owner) {
		this.owner = owner;
	}

	public AuctionStatus getStatus() {
		return status;
	}

	public void setStatus(AuctionStatus status) {
		this.status = status;
	}

	public Set<CompanyOffer> getCompanyOffers() {
		return companyOffers;
	}

	public void setCompanyOffers(Set<CompanyOffer> companyOffers) {
		this.companyOffers = companyOffers;
	}

	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}
	
	
	
	// -------------------------------------------------------------------------------
	
	
	
	
}
