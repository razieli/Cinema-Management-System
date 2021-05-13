package il.ac.haifa.cs.sweng.cms.common.entities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "movies")

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String engName;
	private String hebName;
	private int year;
	private List<String> castList;
	@OneToMany(mappedBy="movie")
	private List<Screening> screening;
	private int length;
	private int ageRestriction;
	private InputStream posterUrl;
	
	public Movie() {
		castList = new ArrayList<>();
	}
	
	public Movie(String engName,String hebName,int year, List<String> castList, int length,
			int ageRestriction, InputStream inputStream) {
		this();
		this.engName = engName;
		this.hebName=hebName;
		this.year=year;
		this.castList = castList;
		this.length = length;
		this.ageRestriction = ageRestriction;
		this.posterUrl = inputStream;
	}
	
	public int getId() { return id; }
    
	public String getengName() { return engName; }
    
	public void setName(String name) { this.engName = name; }
    
	public List<String> getCastList() { return castList; }
    
	public void setCastList(List<String> castList) { this.castList = castList; }
    
	public int getLength() { return length; }
    
	public void setLength(int length) { this.length = length; }
    
	public int getAgeRestriction() { return ageRestriction; }
    
	public void setAgeRestriction(int ageRestriction) { this.ageRestriction = ageRestriction; }
    
	public String getPosterUrl() { return posterUrl; }
    
	public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

	public String getHebName() {
		return hebName;
	}

	public void setHebName(String hebName) {
		this.hebName = hebName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
