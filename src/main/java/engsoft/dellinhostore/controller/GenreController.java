package engsoft.dellinhostore.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.GenreDAO;
import engsoft.dellinhostore.model.Genre;
import engsoft.dellinhostore.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/genre")
public class GenreController {

	private GenreDAO gDao = new GenreDAO();
	
	/*
	 * HTTP Methods mapping
	 */
	@PostMapping
	public ReturnMessage insert(
			@RequestParam(value = "name") String name) {
		//Test if valid name before processing the insert
		if (validParams(name)) {
			Genre genre = new Genre(name);
			gDao.save(genre);
			return new ReturnMessage(true,genre);	
		}
		else {
			return new ReturnMessage(false,"Invalid id");
		}
	} 

	@PutMapping
	public ReturnMessage update(
			@RequestParam(value = "genre_id") long id,
			@RequestParam(value = "name") String name) {
		Genre genre = gDao.getById(id);
		//Test if valid genre_id and valid name before processing the update
		if (validParams(name, genre)) {
			genre.setName(name);
			gDao.update(genre);
			return new ReturnMessage(true,genre);	
		}
		else {
			return new ReturnMessage(false,"Invalid id");
		}
	}

	@GetMapping
	public ReturnMessage getAll() {
		return new ReturnMessage(true,gDao.getAll());
	}
	
	/*
	 * Private methods
	 */
	private boolean validParams(String name, Genre genre) {
		return (name != null && !name.trim().equals(""));
	}
	
	private boolean validParams(String name) {
		return (name != null && !name.trim().equals(""));
	}

	/*
	 * Methods to make easier jUnit tests
	 */
	public long insertManually(String name) {
		Genre genre = new Genre(name);
		gDao.save(genre);
		return genre.getId();	
	}
	
	static public void deleteTestedGenre(String name) {
		GenreDAO gDao = new GenreDAO();
		Genre genre = gDao.getByName(name);
		if (genre != null) {
			gDao.delete(genre);
		}
	}

}
