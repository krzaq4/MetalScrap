package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=pl.krzaq.metalscrap.model.Company.class)
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
	
	@OneToMany(fetch=FetchType.EAGER)
	private Set<Role> roles ;
	
	
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
	
	
	
	// ------------------------------------------------------------------------------
	
	
	
}
