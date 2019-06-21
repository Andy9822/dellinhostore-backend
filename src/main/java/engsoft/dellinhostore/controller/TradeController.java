package engsoft.dellinhostore.controller;

import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.model.Negotiation;
import engsoft.dellinhostore.model.Rating;
import engsoft.dellinhostore.model.Trade;
import engsoft.dellinhostore.util.ReturnMessage;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.NegotiationDAO;
import engsoft.dellinhostore.dao.TradeDAO;
import engsoft.dellinhostore.enums.AdvertStatus;
import engsoft.dellinhostore.enums.TransactionStatus;

@CrossOrigin
@RestController
@RequestMapping("/trade")
public class TradeController {
	
	private TradeDAO tDao = new TradeDAO();
	
	@PostMapping
	public ReturnMessage insert(@RequestParam(value = "negotiation_id") long negotiation_id) {
		if (validInsertParams(negotiation_id)) {
			Trade trade = createTrade(negotiation_id);	
			closeAdvertAndNegotiations(negotiation_id);
			tDao.save(trade);
			return new ReturnMessage(true, trade);
		}
		else {
			return new ReturnMessage(false, "Invalid negotiation");
		}
	}

	@GetMapping
	public ReturnMessage getTradeList() {
		List <Trade> tradeList = tDao.getTradeList();
		return new ReturnMessage(true, tradeList);
	}
	
	@RequestMapping("/listByAdvertiserId")
	public List<Trade> getTradeListByAdvertiserId(
			@RequestParam(value = "advert_Id") long advertiser_id) {
		List <Trade> tradeList = tDao.getTradeList();
		return tradeList;
	}
	
	private Trade createTrade(long negotiation_id) {
		NegotiationDAO nDao = new NegotiationDAO();
		Negotiation negotiation  = nDao.getById(negotiation_id);
		Client advertiser = negotiation.getAdvert().getAdvertiser();
		Client offerer = negotiation.getOfferer();
		Game advertisedGame = negotiation.getAdvert().getAdvertisedGame();
		RatingController ratController = new RatingController();
		Rating rating = ratController.create();
		Trade trade =  new Trade(advertiser, offerer, advertisedGame, negotiation.getOffer(), rating);
		return trade;
	}
	
	private void closeAdvertAndNegotiations(long negotiation_id) {
		NegotiationDAO nDao = new NegotiationDAO();
		Negotiation negotiation = nDao.getById(negotiation_id);
		long advert_id = negotiation.getAdvert().getId();
		nDao.closeNegotiationsByAdvertId(advert_id);
		AdvertController adController = new AdvertController();
		adController.closeById(advert_id);
	}
	
	private boolean validInsertParams(long negotiation_id) {
		NegotiationDAO nDao = new NegotiationDAO();
		Negotiation negotiation  = nDao.getById(negotiation_id);
		if (negotiation != null && negotiation.getStatus() == TransactionStatus.WAITING_ANSWER && negotiation.getAdvert().getStatus() == AdvertStatus.OPEN) {
			return true;
		}
		else {
			return false;	
		}
	}
	
	/*
	 * Methods to make jUnit tests easier
	 */
	public long insertManually(long negotiation_id) {
		Trade trade = createTrade(negotiation_id);
		tDao.save(trade);
		return trade.getId();
	}
	
	static public void deleteTestedTrade(String offer) {
		TradeDAO tDao = new TradeDAO();
		Trade trade = tDao.getByDescription(offer);
		if (trade  != null) {
			tDao.delete(trade);
		}
	}
	
}
