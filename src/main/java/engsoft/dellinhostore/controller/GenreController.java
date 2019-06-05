package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.GenreDAO;
import engsoft.dellinhostore.model.Genre;

@CrossOrigin
@RestController
@RequestMapping("/genre")
public class GenreController {

	private GenreDAO gDao = new GenreDAO();

	@RequestMapping("/insert")
	public Genre insert(
			@RequestParam(value = "name") String name) {
		Genre genre = new Genre(name);
		gDao.save(genre);
		return genre;
	}

	@RequestMapping("/update")
	public Genre update(
			@RequestParam(value = "id") long id,
			@RequestParam(value = "name", defaultValue = "") String name) {
		Genre genre = gDao.getById(id);
		if (name != null && !name.trim().equals(""))
			genre.setName(name);
		gDao.update(genre);
		return genre;
	}

	@RequestMapping("/list")
	public List<Genre> getAll() {
		return gDao.getAll();
	}

}
