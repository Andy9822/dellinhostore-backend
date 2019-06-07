package engsoft.dellinhostore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engsoft.dellinhostore.dao.DisputeDAO;
import engsoft.dellinhostore.dao.TradeDAO;
import engsoft.dellinhostore.model.Dispute;
import engsoft.dellinhostore.model.Trade;
import engsoft.dellinhostore.util.ReturnMessage;

@CrossOrigin
@RestController
@RequestMapping("/dispute")
public class DisputeController {

	private DisputeDAO dDao = new DisputeDAO();

	@RequestMapping("/insert")
	public ReturnMessage insert(
			@RequestParam(value = "trade_id") long trade_id,
			@RequestParam(value = "complaint") String complaint) {
		TradeDAO tDao = new TradeDAO();
		Trade trade = tDao.getById(trade_id);
		if (trade != null && complaint.trim() != "") {
			Dispute dispute = new Dispute(trade,complaint);
			dDao.save(dispute);
			return new ReturnMessage(true,dispute);
		} else {
			return new ReturnMessage(false,"Invalid parameteres");
		}
	}

	@RequestMapping("/list")
	public List<Dispute> getAll() {
		return dDao.getAll();
	}

}
