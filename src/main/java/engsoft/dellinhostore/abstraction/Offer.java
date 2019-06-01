package engsoft.dellinhostore.abstraction;
import javax.persistence.Column;
import javax.persistence.JoinColumn;


public abstract class Offer {
	
	@Column
	@JoinColumn(name = "client_id")
	private long advertiseruser;
	
	@Column
	@JoinColumn(name = "client_id")
	private long offererUser;
	
	@Column
	private String description;
	
	public long getAdvertiseruser() {
		return advertiseruser;
	}
	
	public long getOffererUser() {
		return offererUser;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String offer) {
		this.description = offer;
	}


}
