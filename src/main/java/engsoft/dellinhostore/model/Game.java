package engsoft.dellinhostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Game {
	
	@Id
	@Column (name = "game_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column //TODO SET UNIQUE
	private String name;

	@Column
	private int minimumAge;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genre_id")
	private Genre genre;

	
	public Game(String name, int minAge, Genre genre) {
		setName(name);
		setMinimumAge(minAge);
		setGenre(genre);
	}
	
	public Game() {
		
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getMinimumAge() {
		return minimumAge;
	}

	private void setMinimumAge(int minimumAge) {
		this.minimumAge = minimumAge;
	}

	public Genre getGenre() {
		return genre;
	}

	private void setGenre(Genre genre) {
		this.genre = genre;
	}

}
