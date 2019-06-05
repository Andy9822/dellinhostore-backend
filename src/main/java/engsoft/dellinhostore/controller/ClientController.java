package engsoft.dellinhostore.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.model.Admin;
import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.util.LoginReturnMessage;
import engsoft.dellinhostore.util.ReturnMessage;
import engsoft.dellinhostore.dao.AdminDAO;
import engsoft.dellinhostore.dao.ClientDAO;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

	ClientDAO cDao = new ClientDAO();
	AdminDAO aDao = new AdminDAO();

	@RequestMapping("/insert")
	public Client insert(
			@RequestParam(value = "name") String name, 
			@RequestParam(value = "CPF") String CPF,
			@RequestParam(value = "dateOfBirth") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam(value = "email") String email, 
			@RequestParam(value = "password") String password) {
		Client client = new Client(name, CPF, dateOfBirth, email, password);
		cDao.save(client);
		return client;
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
		// Se email e senha sao corretos precisa verificar se usuario é simples cliente ou é admin
		else {
			Admin admin = aDao.getById(client.getId());
			//Caso tenha achado admin com esse id de cliente é pq é admin
			if (admin != null)
				return new LoginReturnMessage(true, true, admin);
			else 
				//Senao é mero cliente
				return new LoginReturnMessage(true, false, client);
			}
	}
	
	@RequestMapping("/list")
	public List<Client> getGameList() {
		List <Client> clientList = cDao.getClientList();
		return clientList;
	}

}
