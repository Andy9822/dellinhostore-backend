package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.util.ReturnMessage;
import engsoft.dellinhostore.dao.AdvertDAO;
import engsoft.dellinhostore.dao.ClientDAO;
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
	
	@RequestMapping("/insert")
	public Advert insert(@RequestParam(value = "game_id") long game_id,
			@RequestParam(value = "advertiser_Id") long advertiser_Id,
			@RequestParam(value = "platform_id") long platform_id,
			@RequestParam(value = "description") String description) {
		ClientDAO cDao = new ClientDAO();
		Client client  = cDao.getById(advertiser_Id);
		PlatformDAO pDao = new PlatformDAO();
		Platform platform  = pDao.getById(platform_id);
		Advert advert = new Advert(game_id, client, description,platform);
		aDao.save(advert);
		return advert;
	}
	
	@RequestMapping("/list")
	public List<Advert> getAdvertList() {
		List <Advert> advertList = aDao.getAdvertList();
		return advertList; 
	}
	
	
	@RequestMapping("/delete")
	public ReturnMessage delete(long advert_id) {
		Advert advert = aDao.getById(advert_id);
		if (advert != null) {
			aDao.delete(advert);
			return new ReturnMessage(true, "Advert deleted");
		}
		else {
			return new ReturnMessage(false, "Invalid advert_Id received ");
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
}
