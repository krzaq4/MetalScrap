package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Table;

import pl.krzaq.metalscrap.model.generalization.Translatable;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;


@Entity
@Table(name="auction_status")
@NamedQueries({
	@NamedQuery(name="AuctionStatus.findAll", query="from AuctionStatus a" )
	
})
public class AuctionStatus implements Serializable, Translatable{

	
	public static final int STATUS_NEW =1 ;
	public static final int STATUS_STARTED =2 ;
	public static final int STATUS_FINISHED =3 ;
	
	private Boolean isChild = false ;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="code")
	private Integer code ;
	
	@Column(name="lang")
	private String lang ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="status")
	private Set<Auction> auctions ;

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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Set<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(Set<Auction> auctions) {
		this.auctions = auctions;
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
	
	public AuctionStatus clone(String lang, boolean save) {
		
		AuctionStatus as = new AuctionStatus() ;
		
		as.setCode(this.getCode());
		as.setLang(lang);
		as.setName(this.getName());
		
		if(this.getAuctions()!=null) {
		
			List<Auction> acts = new ArrayList<Auction>(this.getAuctions()) ;
			as.setAuctions(auctions);
		} else {
			as.setAuctions(null);
		}
		
		if(save) {
			ServicesImpl.getAuctionService().getAuctionDAO().save(as);
		}
		
		return as ;
	}
	
	
}
