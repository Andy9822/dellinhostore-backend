package engsoft.dellinhostore.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.PlatformDAO;
import engsoft.dellinhostore.model.Platform;
import engsoft.dellinhostore.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/platform")
public class PlatformController {

	private PlatformDAO gDao = new PlatformDAO();

	/*
	 * HTTP Methods mapping
	 */
	@PostMapping
	public ReturnMessage insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "company") String company){
		//Test if valid name and company strings before processing the insert
		if (validParams(name,company)) {
			Platform platform = new Platform(name, company);
			gDao.save(platform);
			return new ReturnMessage(true,platform);	
		}
		else {
			return new ReturnMessage(false,"Forbidden empty strings");
		}
	}

	@PutMapping
	public ReturnMessage update(
			@RequestParam(value = "id") long id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "company") String company) {
		Platform platform = gDao.getById(id);
		if (validParams(platform,name, company)) {
			gDao.update(platform);
			return new ReturnMessage(true,platform);	
		}
		else {
			return new ReturnMessage(false,"Invalid id or forbidden empty strings");
		}
	}

	@GetMapping
	public ReturnMessage getAll() {
		return new ReturnMessage(true,gDao.getAll());
	}
	
	/*
	 * Private methods
	 */
	//Test if not empty strings
	private boolean validParams(String name, String company) {
		return (name != null && !name.trim().equals("")) 
				&& (company != null && !company.trim().equals(""));
	}

	//Test if not empty strings and not null retrieved platform 
	private boolean validParams(Platform platform, String name, String company) {
		return platform != null && validParams(name, company);
	}

	/*
	 * Methods to make easier jUnit tests
	 */
	public long insertManually(String name, String company) {
		Platform platform = new Platform(name, company);
		gDao.save(platform);	
		return platform.getId();
	}
	
	static public void deleteTestedPlatform(String name) {
		PlatformDAO pDao = new PlatformDAO();
		Platform platform = pDao.getByName(name);
		if (platform != null) {
			pDao.delete(platform);
		}
	}
	
}
