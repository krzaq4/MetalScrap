package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="from User" ),
	@NamedQuery(name="User.findByLogin", query="from User u where u.login=:login"),
	@NamedQuery(name="User.findByLoginAndPass", query="from User u where u.login=:login and u.password=:password")
	
	
})
@XmlRootElement(name = "userDetails")
public class User implements Serializable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	
	@ManyToOne
	private Company company ;
	
	@NotNull
	@Column(name="login")
	private String login ;
	
	@NotNull
	@Column(name="password")
	private String password ;
	
	@NotNull
	@Column(name="first_name")
	private String firstName ;
	
	@Column(name="second_name")
	private String secondName ;
	
	@NotNull
	@Column(name="last_name")
	private String lastName ;
	
	@NotNull
	@Column(name="email")
	private String email ;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="user_roles", joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
								inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
	private List<Role> roles ;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="user_observed_auctions", joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
											  inverseJoinColumns=@JoinColumn(name="auction_id", referencedColumnName="id"))
	private List<Auction> observed = new ArrayList<Auction>();
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="ownerUser")
	private List<Auction> auctions ;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<UserOffer> userOffers ;
	
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getLogin() {
		return login;
	}

	@XmlElement
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	@XmlElement
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Auction> getObserved() {
		return observed;
	}

	public void setObserved(List<Auction> observed) {
		this.observed = observed;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}

	public List<UserOffer> getUserOffers() {
		return userOffers;
	}

	public void setUserOffers(List<UserOffer> userOffers) {
		this.userOffers = userOffers;
	}

	
	
	
	
	
	// ------------------------------------------------------------------------------
	
	
	
}
