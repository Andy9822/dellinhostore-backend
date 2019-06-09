package engsoft.dellinhostore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.model.Manager;
import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.util.LoginReturnMessage;
import engsoft.dellinhostore.util.ReturnMessage;
import engsoft.dellinhostore.dao.ManagerDAO;
import engsoft.dellinhostore.dao.ClientDAO;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

	ClientDAO cDao = new ClientDAO();
	ManagerDAO mDao = new ManagerDAO();

	/*
	 * HTTP Methods mapping
	 */
	@PostMapping
	public ReturnMessage insert(
			@RequestParam(value = "name") String name, 
			@RequestParam(value = "cpf") String cpf,
			@RequestParam(value = "dateOfBirth") String dateOfBirth ,
			@RequestParam(value = "email") String email, 
			@RequestParam(value = "password") String password) {
		if (validParameters(name, cpf, email, password, dateOfBirth)) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(dateOfBirth);
				Client client = new Client(name, cpf, date, email, password);
				cDao.save(client);	
				return new ReturnMessage(true,client);
			} catch (ParseException e) {
				return new ReturnMessage(false,"Invalid date format, doesn't match dd/MM/yyyy");
			} 	
		} else {
			String errorMessage = getInvalidInfo(name, cpf, email, password, dateOfBirth);
			return new ReturnMessage(false,errorMessage);
		}
	}

	@RequestMapping("/login")
	public ReturnMessage login(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		Client client = cDao.getByEmail(email);
		// Testar se existe cliente com esse email
		if (client == null)
			return new ReturnMessage(false, "Email not found");
		//Testa se senha está correta
		if (client.comparePassword(password) == false)
			return new ReturnMessage(false, "Wrong password");
		// Se email e senha sao corretos precisa verificar se usuario é simples cliente ou é manager
		else {
			Manager manager = mDao.getById(client.getId());
			//Caso tenha achado manager com esse id de cliente é pq é manager
			if (manager != null)
				return new LoginReturnMessage(true, true, manager);
			else 
				//Senao é mero cliente
				return new LoginReturnMessage(true, false, client);
			}
	}
	
	@GetMapping
	public ReturnMessage getClientList() {
		return new ReturnMessage(true,cDao.getClientList());
	}
	
	@GetMapping("/email")
	public ReturnMessage getClientByEmail(
			@RequestParam(value = "email") String email) {
		if (email != null && !email.trim().equals("")) {
			Client client = cDao.getByEmail(email);
			if (client != null) {
				return new ReturnMessage(true, client);
			}
		}
		return new ReturnMessage(false, "Invalid or inexistant email");		
	}
	
	
	/*
	 * Private methods
	 */
	private boolean validParameters(String name, String cpf, String email, String password, String dateOfBirth) {
		if (validStrings(name, cpf, email, password) && availableEmail(email)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean availableEmail(String email) {
		Client client = cDao.getByEmail(email);
		if (client != null) {
			return false;
		}
		else {
			return true;
		}
	}

	private boolean validStrings(String name, String cpf, String email, String password) {
		return name != null && !name.trim().equals("")
				&& cpf != null && !cpf.trim().equals("")
				&& email != null && !email.trim().equals("")
				&& password != null && !password.trim().equals("");
	}
	
	private String getInvalidInfo(String name, String cpf, String email, String password, String dateOfBirth) {
		if (name == null || name.trim().equals("")) {
			return "Empty name";
		}
		if (cpf == null || cpf.trim().equals("")) {
			return "Empty cpf";
		}
		if (email == null || email.trim().equals("")) {
			return "Empty email";
		}
		if (password == null || password.trim().equals("")) {
			return "Empty password";
		}
		if (dateOfBirth == null || dateOfBirth.trim().equals("")) {
			return "Empty dateOfBirth";
		}
		if (!availableEmail(email)) {
			return "Email already in use";
		}
		return "Fatal error";
	}

	/*
	 * Methods to make easier jUnit tests
	 */
	public long insertManually(String name, String cpf, String date, String email, String password) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateOfBirth = formatter.parse(date);
			Client client = new Client(name, cpf, dateOfBirth, email, password);
			cDao.save(client);
			return client.getId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	static public void deleteTestedClient(String email) {
		ClientDAO cDao = new ClientDAO();
		Client client = cDao.getByEmail(email);
		if (client != null) {
			cDao.delete(client);
		}
	}
}

