package pl.krzaq.metalscrap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="lang_label")
@NamedQueries({
	@NamedQuery(name="LangLabel.findAll", query="from LangLabel l" ),
	@NamedQuery(name="LangLabel.findAllByLang", query="from LangLabel l where l.lang=:lang"),
	@NamedQuery(name="LangLabel.findByKeyAndLang", query="from LangLabel l where l.lkey=:lkey and l.lang=:lang"),
	@NamedQuery(name="LangLabel.findByKey", query="from LangLabel l where l.lkey=:lkey"),
	@NamedQuery(name="LangLabel.findLikeKey", query="from LangLabel l where l.lkey LIKE :lkey"),
	@NamedQuery(name="LangLabel.findLikeKeyUnique", query="from LangLabel l where l.lkey LIKE :lkey group by l.lkey"),
	@NamedQuery(name="LangLabel.findAllLangs", query="select l.lang from LangLabel l group by l.lang"),
	@NamedQuery(name="LangLabel.findById", query="from LangLabel l where l.id=:id")
	
})
public class LangLabel implements Serializable, Comparable<LangLabel>{
	
	
	public static final String LANG_PL = "pl_PL" ;
	public static final String LANG_EN = "en_US" ;
	public static final String LANG_DE = "de_DE" ;
	public static final String LANG_RU = "ru_RU" ;
	
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="l_key")
	private String lkey ;
	
	@Column(name="l_value")
	private String lvalue ;
	
	@Column(name="l_lang")
	private String lang ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLkey() {
		return lkey;
	}

	public void setLkey(String lkey) {
		this.lkey = lkey;
	}

	public String getLvalue() {
		return lvalue;
	}

	public void setLvalue(String lvalue) {
		this.lvalue = lvalue;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public int compareTo(LangLabel o) {
		return this.lang.compareTo(o.getLang()) ;
	}

	

	
	
	
}
