package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.JoinColumns;
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









import org.hibernate.annotations.IndexColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.krzaq.metalscrap.utils.Utilities;

@SuppressWarnings("serial")
@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="from User" ),
	@NamedQuery(name="User.findByLogin", query="from User u where u.login=:login"),
	@NamedQuery(name="User.findByEmail", query="from User u where u.email=:email"),
	@NamedQuery(name="User.findById", query="from User u where u.id=:id"),
	@NamedQuery(name="User.findByLoginAndPass", query="from User u where u.login=:login and u.password=:password")
	
	
})
@XmlRootElement(name = "userDetails")
public class User implements Serializable {

	public static Integer STATUS_NEW = 1 ;
	public static Integer STATUS_CONFIRMED = 2 ;
	public static Integer STATUS_VERIFIED = 3 ;
	public static Integer STATUS_PENDING_VERIFICATION = 4 ;
	public static Integer STATUS_VERIFICATION_SENT = 5 ;
	
	
	
	public User() {
		
		this.email = "" ;
		this.login = "" ;
		this.firstName = "" ;
		this.lastName = "" ;
		this.secondName = "" ;
		
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="completed")
	private Boolean completed ;
	
	@ManyToOne
	private Company company ;
	
	@NotNull
	@Column(name="login",unique=true)
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
	@Column(name="email", unique=true)
	private String email ;
	
	@Column(name="phone")
	private String phone ;
	
	@Column(name="mobile")
	private String mobile ;
	
	@Column(name="avatar_file")
	private String avatarFileName ;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address_main")
	private Address mainAddress ;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="address_contact")
	private Address contactAddress ;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="users_roles", joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
	@IndexColumn(name="rls", nullable=false )
	private Set<Role> roles ;
	
	@OneToMany
	@IndexColumn(name="cmnts", nullable=false)
	private List<Comment> comments ;
	
	@OneToMany
	@IndexColumn(name="msgs", nullable=false)
	private List<Message> messages ;
	
	@OneToMany(cascade=CascadeType.MERGE)
	@IndexColumn(name="obsvr", nullable=false)
	private List<Auction> observed ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="ownerUser")
	@IndexColumn(name="actns", nullable=false)
	private List<Auction> auctions ;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	@IndexColumn(name="offrs", nullable=false)
	private List<UserOffer> userOffers ;
	
	@Column(name="password_change")
	private Boolean passwordChange ;
	
	@Column(name="registered_on")
	private Date createdOn ;
	
	@Column(name="status")
	private Integer status ;
	
	
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
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

	public Address getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(Address mainAddress) {
		this.mainAddress = mainAddress;
	}

	public Address getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(Address contactAddress) {
		this.contactAddress = contactAddress;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}
	
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getToken() {
		String res = "" ;
		try {
			res = Utilities.hash(Utilities.HASH_METHOD_MD5, this.getLogin().concat(this.getEmail().concat(String.valueOf(this.getCreatedOn().getTime())))) ;
			
			
		} catch(NoSuchAlgorithmException ex) {
			
		}
		
		return res ;
	}
	
	public String getRemindToken() {
		String res = "" ;
		
		try {
			res = Utilities.hash(Utilities.HASH_METHOD_MD5, this.getLogin().concat(this.getEmail())) ;
			
			
		} catch(NoSuchAlgorithmException ex) {
			
		}
		
		return res ;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public Boolean getPasswordChange() {
		return passwordChange;
	}

	public void setPasswordChange(Boolean passwordChange) {
		this.passwordChange = passwordChange;
	}
	
	
	
	// ------------------------------------------------------------------------------
	
	
	
}
