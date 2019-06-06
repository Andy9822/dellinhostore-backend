package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import engsoft.dellinhostore.abstraction.Offer;
@Entity
public class Trade extends Offer{
	
	public Trade(Client advertiseruser, Client offererUser,Game game, String description) {
		super(advertiseruser, offererUser, description);
		setTradedGame(game);
	}

	public Trade() {
		super();
	}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game tradedGame;
	
	@Column(nullable = true)
	@JoinColumn(name = "rating_id")
	private long rating;
	
	@Column(nullable = true)
	private String review;

	public Game getTradedGame() {
		return tradedGame;
	}

	public void setTradedGame(Game tradedGame) {
		this.tradedGame = tradedGame;
	}

}
