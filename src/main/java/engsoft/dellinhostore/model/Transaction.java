package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import engsoft.dellinhostore.abstraction.Offer;
import engsoft.dellinhostore.enums.TransactionStatus;

public class Transaction extends Offer {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	@JoinColumn(name = "rating_id")
	private long advert;
	
	@Enumerated(EnumType.ORDINAL)
	private TransactionStatus status;

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}


}
