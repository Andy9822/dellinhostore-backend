package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.AdvertDAO;
import engsoft.dellinhostore.dao.ClientDAO;
import engsoft.dellinhostore.dao.NegotiationDAO;
import engsoft.dellinhostore.model.Advert;
import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.model.Negotiation;
import engsoft.dellinhostore.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/negotiation")
public class NegotiationController {

	private NegotiationDAO nDao = new NegotiationDAO();

	@PostMapping
	public ReturnMessage insert(@RequestParam(value = "advert_id") long advert_id,
			@RequestParam(value = "offerer_id") long offerer_id, @RequestParam(value = "offer") String offer) {
		if (validInsertIds(advert_id, offerer_id, offer)) {
			Negotiation negotiation = createNegotiation(advert_id, offerer_id, offer);
			nDao.save(negotiation);
			return new ReturnMessage(true, negotiation);
		} else {
			return new ReturnMessage(false, "Invalid Ids");
		}
	}

	@GetMapping
	public ReturnMessage getNegotiationList() {
		List<Negotiation> negotiationList = nDao.getNegotiationList();
		return new ReturnMessage(true, negotiationList);
	}

	//TODO test
	@RequestMapping("/delete")
	public ReturnMessage delete(@RequestParam(value = "negotiation_id") long negotiation_id) {
		Negotiation negotiation = nDao.getById(negotiation_id);
		if (negotiation != null) {
			nDao.delete(negotiation);
			return new ReturnMessage(true, "Deletado com sucesso!");
		} else {
			return new ReturnMessage(false, "Invalid Id!");
		}
	}

	private Negotiation createNegotiation(long advert_id, long offerer_id, String offer) {
		AdvertDAO aDao = new AdvertDAO();
		ClientDAO cDao = new ClientDAO();
		Advert advert = aDao.getById(advert_id);
		Client offerer = cDao.getById(offerer_id);
		Negotiation negotiation = new Negotiation(advert, offerer, offer);
		return negotiation;
	}

	private boolean validInsertIds(long advert_id, long offerer_id, String offer) {
		AdvertDAO aDao = new AdvertDAO();
		ClientDAO cDao = new ClientDAO();
		Advert advert = aDao.getById(advert_id);
		Client offerer = cDao.getById(offerer_id);
		if (advert != null && offerer != null && !offer.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Methods to make easier jUnit tests
	 */
	public long insertManually(long advert_id, long offerer_id, String offer) {
		Negotiation negotiation = createNegotiation(advert_id, offerer_id, offer);
		nDao.save(negotiation);
		return negotiation.getId();
	}

	static public void deleteTestedNegotiation(String offer) {
		NegotiationDAO nDao = new NegotiationDAO();
		Negotiation negotiation = nDao.getByDescription(offer);
		if (negotiation != null) {
			nDao.delete(negotiation);
		}
	}

}