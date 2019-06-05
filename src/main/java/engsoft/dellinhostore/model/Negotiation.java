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

import engsoft.dellinhostore.abstraction.Offer;
import engsoft.dellinhostore.enums.TransactionStatus;

@Entity
public class Negotiation extends Offer {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "advert_id")
	private Advert advert;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private TransactionStatus status;

	public Negotiation(Advert advert,Client offerer, String description) {
		super(advert.getAdvertiser(), offerer, description);
		this.advert = advert;
		this.status = TransactionStatus.WAITING_ANSWER;
	}
	
	public Negotiation() {
		super();
	}
	
	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}
	
	public Advert getAdvert() {
		return advert;
	}
	
	

}
