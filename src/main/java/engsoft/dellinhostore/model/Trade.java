package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import engsoft.dellinhostore.abstraction.Offer;

@Entity
@Table (name = "trade")
public class Trade extends Offer{

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game tradedGame;

	@OneToOne
	@JoinColumn(name = "rating_id")
	private Rating rating;

	//Methods
	
	public Trade(Client advertiseruser, Client offererUser,Game game, String description, Rating rating) {
		super(advertiseruser, offererUser, description);
		setTradedGame(game);
		setRating(rating);
	}

	public Trade() {
		super();
	}
	
	public Game getTradedGame() {
		return tradedGame;
	}

	public void setTradedGame(Game tradedGame) {
		this.tradedGame = tradedGame;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public long getId() {
		return id;
	}
	
	

}
