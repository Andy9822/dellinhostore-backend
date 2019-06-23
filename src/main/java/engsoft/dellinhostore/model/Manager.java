package engsoft.dellinhostore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

//A entidade vai ser como a entidade pessoa, a utilidade de separar é que Manager vai precisar
//de um gerenciamento de seção mais complexo
@Entity
@PrimaryKeyJoinColumn(name = "client_id")
public class Manager extends Client {

	private String jobTitle;
	public Manager() {
	}

	public Manager(String name, String CPF,Date dateOfBirth, String email, String password,String jobTitle, String phone) {
		super(name, CPF,dateOfBirth, email, password, phone);
		setJobTitle(jobTitle);	
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

}