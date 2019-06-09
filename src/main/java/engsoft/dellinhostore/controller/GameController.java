package engsoft.dellinhostore.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.GameDAO;
import engsoft.dellinhostore.dao.GenreDAO;
import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.model.Genre;
import engsoft.dellinhostore.util.ReturnMessage;


@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {
	
	private GameDAO gDao = new GameDAO();

	/*
	 * HTTP Methods mapping
	 */
	@PostMapping
	public ReturnMessage insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "minimumAge") int minimumAge,
			@RequestParam(value = "genre_id") long genre_id){
		GenreDAO genreDao = new GenreDAO();
		Genre genre  = genreDao.getById(genre_id);
		//Tests if parameters are valid
		if (validParams(name, minimumAge, genre)) {
			Game game = new Game(name, minimumAge, genre);
			gDao.save(game);
			return new ReturnMessage(true, game);
		} else {
			return new ReturnMessage(false,"Invalid id, invalid minimumAge value or empty string");
		}
		
	}
	
	@GetMapping
	public ReturnMessage getGameList() {
		return new ReturnMessage(true,gDao.getGameList());
	}
	
	/*
	 * Private methods
	 */
	//Tests if existent genre, not empty string and valid age
	private boolean validParams(String name, int minimumAge, Genre genre) {
		return name != null 
				&& !name.trim().equals("")
				&& minimumAge > 0
				&& genre != null;
	}

	public void deleteTestedGame(long id) {
		GameDAO gDao = new GameDAO();
		Game game  = gDao.getById(id);
		if (game != null) {
			gDao.delete(game);
		}
		
	}

	/*
	 * Methods to make easier jUnit tests
	 */
	public long insertManually(String name, int minimumAge, long createdPlatformId) {
		GenreDAO genreDao = new GenreDAO();
		Genre genre  = genreDao.getById(createdPlatformId);
		if (genre != null) {
			Game game = new Game(name, minimumAge, genre);
			gDao.save(game);
			return game.getId();
		}
		return 0;
	}
	
	static public void deleteTestedGame(String name) {
		GameDAO gDao = new GameDAO();
		Game game = gDao.getByName(name);
		if (game != null) {
			gDao.delete(game);
		}
	}
	
}
