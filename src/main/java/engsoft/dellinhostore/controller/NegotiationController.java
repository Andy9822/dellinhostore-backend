package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.AdvertDAO;
import engsoft.dellinhostore.dao.ClientDAO;
import engsoft.dellinhostore.dao.NegotiationDAO;
import engsoft.dellinhostore.enums.TransactionStatus;
import engsoft.dellinhostore.model.Advert;
import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.model.Negotiation;
import engsoft.dellinhostore.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/negotiation")
public class NegotiationController {

	private NegotiationDAO nDao = new NegotiationDAO();
	private AdvertDAO aDao = new AdvertDAO();
	private ClientDAO cDao = new ClientDAO();

	@RequestMapping("/insert")
	public Negotiation insert(
			@RequestParam(value = "advert_id") long advert_id,
			@RequestParam(value = "offerer_id") long offerer_id,
			@RequestParam(value = "description") String description) {
		Advert advert = aDao.getById(advert_id);
		Client offerer = cDao.getById(offerer_id);
		Negotiation negotiation = new Negotiation(advert, offerer, description);
		nDao.save(negotiation);
		return negotiation;
	}

	@RequestMapping("/delete")
	public ReturnMessage delete(
			@RequestParam(value = "negotiation_id") long negotiation_id) {
		Negotiation negotiation = nDao.getById(negotiation_id);
		if (negotiation != null) {
			nDao.delete(negotiation);
			return new ReturnMessage(true, "Deletado com sucesso!");
		} else {
			return new ReturnMessage(false, "Invalid Id!");
		}
	}

	@RequestMapping("/accept")
	public ReturnMessage accept(
			@RequestParam(value = "negotiation_id") long negotiation_id) {
		Negotiation negotiation = nDao.getById(negotiation_id);
		//We have to check if the advert_id received exists
		if (negotiation != null) {
			//We only accept if the transaction was properly waiting for an answer
			if (negotiation.getStatus() == TransactionStatus.WAITING_ANSWER) {
				negotiation.acceptTransaction();
				nDao.update(negotiation);
				closeAdvertAndNegotiations(negotiation.getAdvert().getId());
				createTrade(negotiation);
				return new ReturnMessage(true, "Trade processed!");
			} else {
				return new ReturnMessage(false,"The transaction wasn't waiting for an answer so it couldn't accept the request");
			}

		} else {
			return new ReturnMessage(false,"Invalid Id");
		}
	}

	private void createTrade(Negotiation negotiation) {
		TradeController trController = new TradeController();
		Client advertiser = negotiation.getAdvertiser();
		Client offerer = negotiation.getOfferer();
		String offer = negotiation.getOffer();
		Game game = negotiation.getAdvert().getAdvertisedGame();
		trController.create(advertiser, offerer, game, offer);

	}
	
	//We have to delete all negotiations for first because they have a reference to the advert and we need to guarantee integrity of the DB
	private void closeAdvertAndNegotiations(long advert_id) {
		//nDao.deleteNegotiationsByAdvertId(advert_id);
		nDao.closeNegotiationsByAdvertId(advert_id);
		AdvertController adController = new AdvertController();
		adController.closeById(advert_id);

	}
	
	@RequestMapping("/list")
	public List<Negotiation> getNegotiationList() {
		List <Negotiation> negotiationList = nDao.getNegotiationList();
		return negotiationList;
	}

}