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

import engsoft.dellinhostore.enums.DisputeStatus;

@Entity
public class Dispute {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column (name = "status")
	@Enumerated(EnumType.ORDINAL)
	private DisputeStatus status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trade_id")
	private Trade denouncedTrade;
	
	@Column (nullable = false)
	String complaint;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn (name = "condemner_id", nullable = true)
	Manager condemner;
	
	@Column (nullable = true)
	String verdict;

	public Dispute() {	
	}

	public Dispute(Trade trade, String complaint) {
		setStatus(status);
		setDenouncedTrade(trade);
	}
	
	private void setDenouncedTrade(Trade denouncedTrade) {
		this.denouncedTrade = denouncedTrade;
	}

	private void setStatus(DisputeStatus status) {
		this.status = status;
	}

	public DisputeStatus getStatus() {
		return status;
	}
	
	public String getVerdict() {
		return verdict;
	}

	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}

	public Trade getDenouncedTrade() {
		return denouncedTrade;
	}
	
	public String getComplaint() {
		return complaint;
	}

	public Manager getCondemner() {
		return condemner;
	}
}
