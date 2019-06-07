package engsoft.dellinhostore.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.ManagerDAO;
import engsoft.dellinhostore.model.Manager;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {

	private ManagerDAO mDao = new ManagerDAO();
	
	@RequestMapping("/insert")
	public Manager insert(
			@RequestParam(value = "name") String name, 
			@RequestParam(value = "CPF") String CPF,
			@RequestParam(value = "dateOfBirth") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam(value = "email") String email, 
			@RequestParam(value = "password") String password,
			@RequestParam(value = "jobTitle") String jobTitle) {
		Manager manager = new Manager(name, CPF, dateOfBirth,email, password,jobTitle);
		mDao.save(manager);
		return manager;
	}
	
	@RequestMapping("/getbyid")
	public Manager getById(@RequestParam(value = "id") long id) {
		Manager manager = mDao.getById(id);
		return manager;
	}
	@RequestMapping("/delete")
	public Manager delete(@RequestParam(value = "id") long id) {
		Manager manager = mDao.getById(id);
		mDao.delete(manager);
		return manager;
	}
	
}
