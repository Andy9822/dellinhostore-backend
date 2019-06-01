package engsoft.dellinhostore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Client {

	//Database Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "client_id")
	private long id;
	
	private String name;
	
	private String CPF;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	private float rating;
	
	private int numNegotiations;
	
	//Methods 
	public Client(String name, String CPF, Date dateOfBirth) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.CPF = CPF;
		this.rating = 0;
		this.numNegotiations = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getNegotiations() {
		return numNegotiations;
	}

	public void setNegotiations(int negotiations) {
		this.numNegotiations = negotiations;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}
}
