package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="auction")
@NamedQueries({
	@NamedQuery(name="Auction.findAll", query="from Auction a" ),
	@NamedQuery(name="Auction.findById", query="from Auction a where a.id=:id"),
	@NamedQuery(name="Auction.findByName", query="from Auction a where a.name=:name"),
	@NamedQuery(name="Auction.findByNumber", query="from Auction a where a.number=:number"),
	@NamedQuery(name="Auction.findByStatus", query="from Auction a where a.status=:status")
	
	
	
})
public class Auction implements Serializable {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="number")
	private String number ;
	
	@Column(name = "start_date", columnDefinition="DATETIME NOT NULL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate ;
	
	@Column(name = "end_date", columnDefinition="DATETIME NOT NULL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate ;
	
	@Column(name="start_price")
	private Double startPrice ;
	
	@Column(name="best_price")
	private Double bestPrice ;
	
	@Column(name="description")
	private String description ;
	
	@Column(name="invoice")
	private Boolean invoice ;
	
	@Column(name="delivery_time")
	private String deliveryTime ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="auction")
	private List<AttachementFile> files = new ArrayList<AttachementFile>();
	
	@OneToOne
	@JoinColumn(name="winner")
	private Company winner ;
	
	@OneToOne
	@JoinColumn(name="owner")
	private Company owner ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="auction")
	private List<CompanyOffer> companyOffers = new ArrayList<CompanyOffer>() ;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="auction")
	private List<Commodity> commodities = new ArrayList<Commodity>() ;
	
	@OneToOne
	@JoinColumn(name="status")
	private AuctionStatus status ;
	
	@OneToOne
    @JoinColumn(name="delivery_type")
	private DeliveryType deliveryType ;
	
	@OneToOne
	@JoinColumn(name="payment_method")
	private PaymentMethod paymentMethod ;
	

	@ManyToOne
	@JoinColumn(name="category")
	private Category category ;
	
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

	public List<CompanyOffer> getCompanyOffers() {
		return companyOffers;
	}

	public void setCompanyOffers(List<CompanyOffer> companyOffers) {
		this.companyOffers = companyOffers;
	}

	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	public Boolean getInvoice() {
		return invoice;
	}

	public void setInvoice(Boolean invoice) {
		this.invoice = invoice;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;

	}

	public List<AttachementFile> getFiles() {
		return files;
	}

	public void setFiles(List<AttachementFile> files) {
		this.files = files;
	}
	
	
	
	// -------------------------------------------------------------------------------
	
	
	
	
}
