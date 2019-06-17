package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.util.ReturnMessage;
import engsoft.dellinhostore.dao.AdvertDAO;
import engsoft.dellinhostore.dao.ClientDAO;
import engsoft.dellinhostore.dao.GameDAO;
import engsoft.dellinhostore.dao.PlatformDAO;
import engsoft.dellinhostore.model.Advert;
import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.model.Platform;

@CrossOrigin
@RestController
@RequestMapping("/advert")
public class AdvertController {

	private AdvertDAO aDao = new AdvertDAO();
	
	@PostMapping
	public ReturnMessage insert(
			@RequestParam(value = "game_id") long game_id,
			@RequestParam(value = "advertiser_id") long advertiser_id,
			@RequestParam(value = "platform_id") long platform_id,
			@RequestParam(value = "description") String description) {
		//Se os ids recebidos corresponderem a elementos existentes cria o advert
		if (validInsertIds(game_id, advertiser_id, platform_id,description)) {
			ClientDAO cDao = new ClientDAO();
			Client client  = cDao.getById(advertiser_id);
			GameDAO gDao = new GameDAO();
			Game game = gDao.getById(game_id);
			PlatformDAO pDao = new PlatformDAO();
			Platform platform  = pDao.getById(platform_id);
			Advert advert = new Advert(game, client, description,platform);
			aDao.save(advert);
			return new ReturnMessage(true, advert);
		} else {

			return new ReturnMessage(false, "Invalid parameters");
		}
		
	}
	
	private boolean validInsertIds(long game_id,long advertiser_id,long platform_id,String description) {
		ClientDAO cDao = new ClientDAO();
		Client client  = cDao.getById(advertiser_id);
		GameDAO gDao = new  GameDAO();
		Game game = gDao.getById(game_id);
		PlatformDAO pDao = new PlatformDAO();
		Platform platform  = pDao.getById(platform_id);
		if (client != null && game != null && platform != null && !description.trim().equals("")) {
			return true;
		}
		else {
			return false;	
		}
	}
	@GetMapping
	public ReturnMessage getOpenAdvertList() {
		List <Advert> advertList = aDao.getOpenAdvertList();
		return new ReturnMessage(true,advertList); 
	}
	
	@GetMapping("/open")
	public ReturnMessage getEntireList() {
		List <Advert> advertList = aDao.getEntireList();
		return new ReturnMessage(true,advertList); 
	}
	
	
	
	@RequestMapping("/delete")
	public ReturnMessage delete(long advert_id) {
		Advert advert = aDao.getById(advert_id);
		if (advert != null) {
			aDao.delete(advert);
			return new ReturnMessage(true, "Advert deleted");
		}
		else {
			return new ReturnMessage(false, "Invalid advert_id received ");
		}
	}
	
	@RequestMapping("/getAdvertById")
	public Advert getAdvertById(@RequestParam(value = "advert_id") long advert_id) {
		Advert advert = aDao.getById(advert_id);
		return advert; 
	}
	
	@RequestMapping("/getAdvertisedGameById")
	public Game getAdvertisedGameById(@RequestParam(value = "advert_id") long advert_id) {
		Advert advert = getAdvertById(advert_id);
		return advert.getAdvertisedGame(); 
	}

	protected void closeById(long advert_id) {
		Advert advert = aDao.getById(advert_id);
		advert.close();
		aDao.update(advert);
	}
	
	/*
	 * Methods to make easier jUnit tests
	 */
	public long insertManually(long game_id, long advertiser_id, long platform_id, String description) {
		GameDAO gDao = new GameDAO();
		Game game = gDao.getById(game_id);
		PlatformDAO pDao = new PlatformDAO();
		Platform platform= pDao.getById(platform_id);
		ClientDAO cDao = new ClientDAO();
		Client client= cDao.getById(advertiser_id);
		Advert advert = new Advert(game,client, description,platform);
		aDao.save(advert);
		return advert.getId();
	}
	
	static public void deleteTestedAdvert(String description) {
		AdvertDAO aDao = new AdvertDAO();
		Advert advert = aDao.getByDescription(description);
		if (advert != null) {
			aDao.delete(advert);
		}
	}
}
