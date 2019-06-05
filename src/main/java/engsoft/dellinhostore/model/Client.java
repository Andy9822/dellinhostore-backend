package engsoft.dellinhostore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import engsoft.dellinhostore.util.ReturnMessage;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Client {

	//Database Attributes
	@Id
	@Column (name = "client_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;
	
	@Column (unique = true)
	private String CPF;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column
	private float rating;
	
	@Column
	private int numNegotiations;
	
	@Column (unique = true)
	private String email;
	
	@Column
	private String password;
	
	//Methods 
	public Client(String name, String CPF, Date dateOfBirth, String email, String password) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.CPF = CPF;
		this.email = email;
		this.password = password;
		this.rating = 0;
		this.numNegotiations = 0;		
	}
	
	public String getEmail() {
		return email;
	}

	public ReturnMessage changeEmail(String newEmail, String password) {
		if (password.equals(this.password)) {
			this.email = newEmail;
			return new ReturnMessage(true,"E-mail modificado com sucesso");
		} else {
			return new ReturnMessage(false,"Senha inválida. Não foram feitas modificações");
		}
	}

	public boolean comparePassword(String passwordTry) {
		return passwordTry.equals(this.password);
	}

	public boolean changePassword(String oldPassword, String newPassword) {
		if (oldPassword.equals(this.password)) {
			this.password = newPassword;
			return true;
		} else {
			return false;
		}
	}

	public Client() {
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
	
	public long getId() {
		return id;
	}

}
