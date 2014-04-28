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
import pl.krzaq.metalscrap.utils.Utilities;


@Entity
@Table(name="delivery_type")
@NamedQueries({
	@NamedQuery(name="DeliveryType.findAll", query="from DeliveryType d" )
	
	
	
})
public class DeliveryType implements Serializable, Translatable {

	private Boolean isChild = false ;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="code")
	private Integer code ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="lang")
	private String lang ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="deliveryType")
	private List<Auction> auctions ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
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

	public DeliveryType clone(String lang, boolean save) {
		DeliveryType dt = new DeliveryType() ;
		if(this.getAuctions()!=null) {
			dt.setAuctions(new ArrayList<Auction>(this.getAuctions()));
		}
		dt.setCode(this.getCode());
		dt.setLang(lang);
		dt.setName(this.getName());
		
		if(save) {
			Utilities.getServices().getAuctionService().getAuctionDAO().save(dt);
		}
		return dt ;
	}
	
	//----------------------------------------------------------------------
	
	
	
	
}
