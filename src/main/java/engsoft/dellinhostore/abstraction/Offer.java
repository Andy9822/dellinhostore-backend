package engsoft.dellinhostore.abstraction;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import engsoft.dellinhostore.model.Client;

@MappedSuperclass
public abstract class Offer {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "advertiser_id")
	private Client advertiser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "offerer_id")
	private Client offerer;
	
	@Column
	private String description;
	
	
	public Offer(Client advertiseruser, Client offererUser, String description) {
		super();
		this.advertiser = advertiseruser;
		this.offerer= offererUser;
		this.description = description;
	}
	
	public Offer() {
		
	}
	
	public Client getAdvertiser() {
		return advertiser;
	}

	public Client getOfferer() {
		return offerer;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String offer) {
		this.description = offer;
	}


}
