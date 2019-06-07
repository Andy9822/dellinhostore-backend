package engsoft.dellinhostore.controller;

import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.model.Rating;
import engsoft.dellinhostore.model.Trade;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.TradeDAO;

@CrossOrigin
@RestController
@RequestMapping("/trade")
public class TradeController {
	
	private TradeDAO tDao = new TradeDAO();
	
	protected Trade create(Client advertiser, Client offerer, Game game, String description) {
		RatingController ratController = new RatingController();
		Rating rating = new Rating();
		rating = ratController.create();
		Trade trade =  new Trade(advertiser,offerer,game,description,rating);
		tDao.save(trade);
		return trade;
	}
	
	@RequestMapping("/list")
	public List<Trade> getTradeList() {
		List <Trade> tradeList = tDao.getTradeList();
		return tradeList;
	}
	
	@RequestMapping("/listByAdvertiserId")
	public List<Trade> getTradeListByAdvertiserId(
			@RequestParam(value = "advert_Id") long advertiser_id) {
		List <Trade> tradeList = tDao.getTradeList();
		return tradeList;
	}
	
}
