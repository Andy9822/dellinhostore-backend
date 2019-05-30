package engsoft.dellinhostore.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.GameDAO;
import engsoft.dellinhostore.model.Game;


@CrossOrigin
@RestController
@RequestMapping("/game")
public class GameController {
	
	private GameDAO gDao = new GameDAO();
	
	@GetMapping
	public void teste() {
		System.out.println("A \n A A A \nA A A A\n \n  A ");
	}
	@RequestMapping("/insert")
	public Game insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "minAge", defaultValue = "1") int minAge,
			@RequestParam(value = "genre") String genre) {
		Game product = new Game(name, minAge, genre);
		gDao.save(product);
		return product;
	}
}
