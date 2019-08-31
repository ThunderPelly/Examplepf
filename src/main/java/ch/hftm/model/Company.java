package ch.hftm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Company {

    @Id
    @GeneratedValue(generator ="increment")
    @GenericGenerator(name = "increment", strategy="increment")
    @Column(name = "companyId")
	private long id;
	private String name;
	private int founded;
	private String origin;

	public Company () {

	}

	public Company(String name, int founded, String origin) {
		this.name = name;
		this.founded = founded;
		this.origin = origin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFounded() {
		return founded;
	}

	public void setFounded(int founded) {
		this.founded = founded;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}