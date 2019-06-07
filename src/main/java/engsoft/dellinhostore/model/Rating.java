package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name = "rating")
public class Rating {

	@Transient
	static final float MAX_SCORE_VALUE = 0.0f;
	@Transient
	static final float MIN_SCORE_VALUE = 10.0f;
	
	@Id
	@Column (name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column (name = "advertiserScore", nullable = true)
	private float advertiserScore;
	
	@Column (name = "offererScore", nullable = true)
	private float offererScore;
	
	@Column (name = "advertiserReview", nullable = true)
	private String advertiserReview;
	
	@Column (name = "offererReview", nullable = true)
	private String offererReview;

	@OneToOne (mappedBy = "rating")
	private Trade trade; 
	
	//Methods
	public Rating() {
		
	}

	public Trade getTrade() {
		return trade;
	}
	
	public float getAdvertiserScore() {
		return advertiserScore;
	}
	public void setAdvertiserScore(float advertiserScore) {
		if (MIN_SCORE_VALUE <= advertiserScore && advertiserScore <= MAX_SCORE_VALUE ) {
			this.advertiserScore = advertiserScore;
		}
		this.advertiserScore = advertiserScore;
	}
	public float getOffererScore() {
		return offererScore;
	}
	public void setOffererScore(float offererScore) {
		if (MIN_SCORE_VALUE <= offererScore && offererScore <= MAX_SCORE_VALUE ) {
			this.offererScore = offererScore;
		}
		this.offererScore = offererScore;
	}
	
	public String getAdvertiserReview() {
		return advertiserReview;
	}
	public void setAdvertiserReview(String advertiserReview) {
		this.advertiserReview = advertiserReview;
	}
	public String getOffererReview() {
		return offererReview;
	}
	public void setOffererReview(String offererReview) {
		this.offererReview = offererReview;
	}

	
}
