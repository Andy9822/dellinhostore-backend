package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.PlatformDAO;
import engsoft.dellinhostore.model.Platform;

@CrossOrigin
@RestController
@RequestMapping("/platform")
public class PlatformController {

	private PlatformDAO gDao = new PlatformDAO();

	@RequestMapping("/insert")
	public Platform insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "company") String company){
		Platform platform = new Platform(name, company);
		gDao.save(platform);
		return platform;
	}

	@RequestMapping("/update")
	public Platform update(
			@RequestParam(value = "id") long id,
			@RequestParam(value = "name", defaultValue = "") String name) {
		Platform platform = gDao.getById(id);
		if (name != null && !name.trim().equals(""))
			platform.setName(name);
		gDao.update(platform);
		return platform;
	}

	@RequestMapping("/list")
	public List<Platform> getAll() {
		return gDao.getAll();
	}

}
