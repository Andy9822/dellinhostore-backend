package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.GameDAO;
import engsoft.dellinhostore.dao.GenreDAO;
import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.model.Genre;


@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {
	
	private GameDAO gDao = new GameDAO();

	@RequestMapping("/insert")
	public Game insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "minAge", defaultValue = "1") int minAge,
			@RequestParam(value = "genre_id") long genre_id){
		
		GenreDAO genreDao = new GenreDAO();
		Genre genre  = genreDao.getById(genre_id);
		Game game = new Game(name, minAge, genre);
		gDao.save(game);
		return game;
	}
	
	@RequestMapping("/list")
	public List<Game> getGameList() {
		List <Game> gameList = gDao.getGameList();
		return gameList;
	}
}
