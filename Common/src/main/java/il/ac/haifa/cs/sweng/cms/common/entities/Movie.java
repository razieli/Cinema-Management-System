package il.ac.haifa.cs.sweng.cms.common.entities;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "movies")

public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String engName;
	private String hebName;
	private Integer year;
	private String castList;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="movie")
	private List<Screening> screening;
	private Integer length;
	private Integer ageRestriction;
	private String description;
	private URI posterUrl;
	private URI trailerUrl;

	public Movie() {
	}
	
	public Movie(String engName,String hebName,Integer year,
				 String castList, Integer length, Integer ageRestriction,
				 String description, URI inputStream, URI inputStream2) {
		this();
		this.engName = engName;
		this.hebName=hebName;
		this.year=year;
		this.castList = castList;
		this.length = length;
		this.ageRestriction = ageRestriction;
		this.posterUrl = inputStream;
		this.trailerUrl = inputStream2;
		this.description = description;
	}
	
	public int getId() { return id; }
	public void setId(int id) {
		this.id = id;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getHebName() {
		return hebName;
	}
	public void setHebName(String hebName) {
		this.hebName = hebName;
	}
	public String getCastList() { return castList; }
	public void setCastList(String castList) { this.castList = castList; }
	public Integer getLength() { return length; }
	public void setLength(Integer length) { this.length = length; }
	public Integer getAgeRestriction() { return ageRestriction; }
	public void setAgeRestriction(Integer ageRestriction) { this.ageRestriction = ageRestriction; }
	public URI getPosterUrl() { return posterUrl; }
	public void setPosterUrl(URI trailerUrl) { this.posterUrl = trailerUrl; }
	public URI getTrailerUrl() { return trailerUrl; }
	public void setTrailerUrl(URI trailerUrl) { this.trailerUrl = trailerUrl; }
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<Screening> getScreening() {
		return screening;
	}
	public void setScreening(List<Screening> screening) {
		this.screening = screening;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
