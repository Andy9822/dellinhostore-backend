package engsoft.dellinhostore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

//A entidade vai ser como a entidade Client 
//A utilidade de separar é que Manager vai precisar
//de um gerenciamento de seção mais complexo

@Entity
@PrimaryKeyJoinColumn(name="client_id")
public class Manager extends Client {
	
	public Manager(String name, String CPF, Date dateOfBirth) {
		super(name, CPF, dateOfBirth);
	}
}
