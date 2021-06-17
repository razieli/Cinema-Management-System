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

/**
 * Movie Entity
 */
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String engName;
	private String hebName;
	private Integer year;
	private String castList;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="movie")
	private List<Screening> screening;
	private Integer length;
	private Integer ageRestriction;
	private String description;
	private URI posterUrl;
	private URI trailerUrl;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="movie")
	private List<Link> links;

	/**
	 * constructors
	 */
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

	/**
	 * id set/get
	 */
	public int getId() { return id; }
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * EngName set/get
	 */
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * hebName set/get
	 */
	public String getHebName() {
		return hebName;
	}
	public void setHebName(String hebName) {
		this.hebName = hebName;
	}

	/**
	 * cast list set/get
	 */
	public String getCastList() { return castList; }
	public void setCastList(String castList) { this.castList = castList; }

	/**
	 * Length set/get
	 */
	public Integer getLength() { return length; }
	public void setLength(Integer length) { this.length = length; }

	/**
	 * Restrictions set/get
	 */
	public Integer getAgeRestriction() { return ageRestriction; }
	public void setAgeRestriction(Integer ageRestriction) { this.ageRestriction = ageRestriction; }

	/**
	 * PosterUrl set/get
	 */
	public URI getPosterUrl() { return posterUrl; }
	public void setPosterUrl(URI trailerUrl) { this.posterUrl = trailerUrl; }

	/**
	 * trailer link set/get
	 */
	public URI getTrailerUrl() { return trailerUrl; }
	public void setTrailerUrl(URI trailerUrl) { this.trailerUrl = trailerUrl; }

	/**
	 * year set/get
	 */
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * screening list set/get
	 */
	public List<Screening> getScreening() { return screening; }
	public void setScreening(List<Screening> screening) { this.screening = screening; }

	/**
	 * Description list set/get
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * links list set/get
	 */
	public List<Link> getLinks() { return links; }
	public void setLinks(List<Link> links) { this.links = links; }
}
