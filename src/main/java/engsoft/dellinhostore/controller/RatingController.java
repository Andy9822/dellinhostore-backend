package engsoft.dellinhostore.controller;

import engsoft.dellinhostore.dao.RatingDAO;
import engsoft.dellinhostore.model.Rating;

public class RatingController {

	private RatingDAO rDao = new RatingDAO();
	
	protected Rating create() {
		Rating rating = new Rating();
		rDao.save(rating);
		return rating;
	}

}
