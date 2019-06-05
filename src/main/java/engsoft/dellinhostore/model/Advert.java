package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import engsoft.dellinhostore.dao.GameDAO;

@Entity
public class Advert {

	@Id
	@Column (name = "advert_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game advertisedGame;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "platform_id")
	private Platform platform;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client advertiser;
	
	private String description;
	
	public Advert(long advertisedGame_id, Client advertiser, String description, Platform platform) {
		GameDAO gDao = new GameDAO();
		setAdvertisedGame(gDao.getById(advertisedGame_id));
		setAdvertiser(advertiser);
		setDescription(description);
		setPlatform(platform);
	}
	
	public Advert() {
		
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Game getAdvertisedGame() {
		return advertisedGame;
	}
	
	public void setAdvertisedGame(Game advertisedGame) {
		this.advertisedGame = advertisedGame;
	}

	public Client getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Client advertiser) {
		this.advertiser = advertiser;
	}
	
	public long getId() {
		return id;
	}

	private void setPlatform(Platform platform) {
		this.platform = platform;
	}

	
}
