package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


public class Advert {

	@Id
	@Column (name = "advert_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JoinColumn(name = "game_id")
	private long advertisedGame;
	
	@JoinColumn(name = "client_id")
	private long advertiser;
	
	private String description;
	
	public Advert(long advertisedGame, long advertiser, String description) {
		super();
		this.advertisedGame = advertisedGame;
		this.advertiser = advertiser;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
