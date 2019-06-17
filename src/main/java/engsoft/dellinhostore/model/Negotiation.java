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
	
	@Column (name = "status")
	@Enumerated(EnumType.ORDINAL)
	private TransactionStatus transactionStatus;

	public Negotiation(Advert advert,Client offerer, String offer) {
		super(advert.getAdvertiser(), offerer, offer);
		this.advert = advert;
		this.transactionStatus = TransactionStatus.WAITING_ANSWER;
	}
	
	public Negotiation() {
		super();
	}
	
	public TransactionStatus getStatus() {
		return transactionStatus;
	}

	public void setStatus(TransactionStatus status) {
		this.transactionStatus = status;
	}

	public long getId() {
		return id;
	}
	
	public Advert getAdvert() {
		return advert;
	}

	public void close() {
		this.transactionStatus = TransactionStatus.CLOSED;
		
	}

	public void acceptTransaction() {
		setStatus(TransactionStatus.ACCEPTED);	
	}
	
	

}
