package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import engsoft.dellinhostore.enums.AdvertStatus;

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
	
	@Column (name = "status")
	@Enumerated(EnumType.ORDINAL)
	private AdvertStatus transactionStatus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client advertiser;
	
	@Column
	private String description;
	
	public Advert(Game game, Client advertiser, String description, Platform platform) {
		setAdvertisedGame(game);
		setAdvertiser(advertiser);
		setDescription(description);
		setPlatform(platform);
		setStatus(AdvertStatus.OPEN);
	}
	
	private void setStatus(AdvertStatus status) {
		this.transactionStatus = status;
		
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
	
	public AdvertStatus getStatus() {
		return transactionStatus;
	}

	public void close() {
		this.transactionStatus = AdvertStatus.CLOSED;
	}

	public Platform getPlatform() {
		return platform;
	}
	
	
	
}
