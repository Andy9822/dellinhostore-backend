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
import engsoft.dellinhostore.util.ReturnMessage;


@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {
	
	private GameDAO gDao = new GameDAO();

	@RequestMapping("/insert")
	public ReturnMessage insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "minAge", defaultValue = "1") int minAge,
			@RequestParam(value = "genre_id") long genre_id){
		
		GenreDAO genreDao = new GenreDAO();
		Genre genre  = genreDao.getById(genre_id);
		//Teste if genre exists
		if (genre != null) {
			Game game = new Game(name, minAge, genre);
			gDao.save(game);
			return new ReturnMessage(true, game);
		} else {
			return new ReturnMessage(false,"Invalid genre_id");
		}
		
	}
	
	@RequestMapping("/list")
	public List<Game> getGameList() {
		List <Game> gameList = gDao.getGameList();
		return gameList;
	}
}
