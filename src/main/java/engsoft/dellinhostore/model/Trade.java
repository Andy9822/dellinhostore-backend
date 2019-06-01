package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import engsoft.dellinhostore.abstraction.Offer;
public class Trade extends Offer{
	
	@Id
	@Column (name = "approvedTransaction_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	@JoinColumn(name = "game_id")
	private long tradedGame;
	
	@Column(nullable = true)
	private float rating;
	
	@Column(nullable = true)
	private String review;


}
