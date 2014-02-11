package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Table(name="auction")
@NamedQueries({
	@NamedQuery(name="Auction.findAll", query="from Auction a" ),
	@NamedQuery(name="Auction.findById", query="from Auction a where a.id=:id"),
	@NamedQuery(name="Auction.findByName", query="from Auction a where a.name=:name"),
	@NamedQuery(name="Auction.findByNumber", query="from Auction a where a.number=:number"),
	@NamedQuery(name="Auction.findByStatus", query="from Auction a where a.status=:status"),
	@NamedQuery(name="Auction.findByCategory", query="from Auction a where a.category=:category"),
	@NamedQuery(name="Auction.findByCategoryAndStatus", query="from Auction a where a.category=:category and status=:status")
	
	
	
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
	
	@OneToMany( mappedBy="auction")
	@Cascade(value={CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	private List<AttachementFile> files = new ArrayList<AttachementFile>();
	
	@OneToOne
	@JoinColumn(name="winner")
	private Company winner ;
	
	@OneToOne
	@JoinColumn(name="winner_user")
	private User winnerUser ;
	
	@ManyToOne
	@JoinColumn(name="owner")
	private Company owner ;
	
	@Column(name="min_price")
	private Double minPrice ;
	
	@ManyToOne
	@JoinColumn(name="owner_user")
	private User ownerUser ;
	
	@OneToOne
	@JoinColumn(name="best_useroffer")
	private UserOffer bestUserOffer ;
	
	@ManyToMany(mappedBy="observed", cascade=javax.persistence.CascadeType.ALL)
	private List<User> obeservers ;
	
	@OneToMany(mappedBy="auction")
	@Cascade(value={CascadeType.ALL})
	private List<CompanyOffer> companyOffers = new ArrayList<CompanyOffer>() ;
	
	@OneToMany(mappedBy="auction", fetch=FetchType.EAGER)
	@Cascade(value={CascadeType.ALL})
	private List<UserOffer> userOffers = new ArrayList<UserOffer>() ;

	@OneToMany(mappedBy="auction")
	@Cascade(value={CascadeType.ALL})
	private List<Commodity> commodities = new ArrayList<Commodity>() ;
	
	@OneToOne
	@JoinColumn(name="status")
	private AuctionStatus status ;
	
	@ManyToOne
    @JoinColumn(name="delivery_type_id")
	private DeliveryType deliveryType ;
	
	@ManyToOne
	@JoinColumn(name="payment_method_id")
	private PaymentMethod paymentMethod ;
	

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category ;
	
	
	@OneToMany
	private List<Property> properties; 
	
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

	public List<UserOffer> getUserOffers() {
		return userOffers;
	}

	public void setUserOffers(List<UserOffer> userOffers) {
		this.userOffers = userOffers;
	}

	public User getWinnerUser() {
		return winnerUser;
	}

	public void setWinnerUser(User winnerUser) {
		this.winnerUser = winnerUser;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public UserOffer getBestUserOffer() {
		return bestUserOffer;
	}

	public void setBestUserOffer(UserOffer bestUserOffer) {
		this.bestUserOffer = bestUserOffer;
	}

	public List<User> getObeservers() {
		return obeservers;
	}

	public void setObeservers(List<User> obeservers) {
		this.obeservers = obeservers;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		Auction other = (Auction) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	
	
	
	
	// -------------------------------------------------------------------------------
	
	
	
	
}
