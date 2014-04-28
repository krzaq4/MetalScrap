package pl.krzaq.metalscrap.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="charge")
public class Charge {

	@Id
	@GeneratedValue
	private Long id ;
	
	public static Integer CHARGE_TYPE_AUCTIONPUBLISH = 1 ;
	public static Integer CHARGE_TYPE_PHOTO = 2 ;
	public static Integer CHARGE_TYPE_PHOTOTHUMB = 3 ;
	public static Integer CHARGE_TYPE_BUYNOW = 4 ;
	public static Integer CHARGE_TYPE_MINPRICE = 5 ;
	public static Integer CHARGE_TYPE_CATEGORYPROMO = 6 ;
	public static Integer CHARGE_TYPE_MAINPAGEPROMO = 7 ;
	public static Integer CHARGE_TYPE_AUCTIONDURATION = 8 ;
	
	@Column(name="value")
	private BigDecimal value ;
	
	@Column(name="")
	private Integer type ;
	
	@ManyToOne
	private AuctionCharge auctionCharge ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public AuctionCharge getAuctionCharge() {
		return auctionCharge;
	}

	public void setAuctionCharge(AuctionCharge auctionCharge) {
		this.auctionCharge = auctionCharge;
	}
	
	
}
