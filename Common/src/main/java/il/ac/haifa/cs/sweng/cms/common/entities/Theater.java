package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Theater Entity
 */
@Entity
@Table(name = "theaters")

public class Theater implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String placeName; //todo: uneccecery
	private int seatsCapacity; //how many seats in the theater
	private int realSeatsCapacity; //after PB change

	@OneToMany(targetEntity = Screening.class ,mappedBy="theater", fetch = FetchType.LAZY)
	private List<Screening> screeningList;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="cinema_id")
	private Cinema cinema;




	/**
	 * constructors
	 */
//	public Theater(String placeName, int seatsCapacity, Cinema cinema)
//	{
//		this();
//		this.placeName = placeName;
//		this.seatsCapacity = seatsCapacity;
//		this.cinema=cinema;
//		this.setRealSeatsCapacity();
//	}
	public Theater(String placeName, int seatsCapacity, Cinema cinema)
	{
		this();
		this.placeName = placeName;
		this.seatsCapacity = seatsCapacity;
		this.cinema=cinema;
		this.setRealSeatsCapacity();
	}
	public Theater()
	{
		screeningList = new ArrayList<>();
	}

	/**
	 * Id set/get
	 */
	public int getId() { return id; }
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * theater location set/get
	 */
	public String getPlaceName() { return placeName; }
	public void getPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * Seats Capacity set/get
	 */
	public int getSeatsCapacity() { return seatsCapacity; }
	public void setSeatsCapacity(int seatsCapacity) {
		this.seatsCapacity = seatsCapacity;
		this.setRealSeatsCapacity();
		}

	/**
	 * theater Screenings set/get
	 */
	public List<Screening> getScreeningList() { return screeningList; }
	public void setScreeningList(List<Screening> s) { this.screeningList=s; }
	/**
	 * @return the realSeatsCapacity
	 */
	public int getRealSeatsCapacity() {
		return realSeatsCapacity;
	}
	/**
	 * set the realSeatsCapacity to set
	 */
	public void setRealSeatsCapacity() {
		PurpleBadge pb=PurpleBadge.getInstance();
		int y=pb.getY();
		if(pb.getStatus()) {
			if (this.seatsCapacity>1.2*y)
				this.realSeatsCapacity=y;
			else if(this.seatsCapacity>0.8*y)
				this.realSeatsCapacity=(int) Math.round(0.8*y);
			else
				this.realSeatsCapacity=this.seatsCapacity/2;

		}
		else
			this.realSeatsCapacity = this.seatsCapacity;
	}

	public List<Customer> coronaCheck(){
		List<Customer> cancel = new LinkedList<Customer>();
		for (Screening s: this.screeningList){
			if(PurpleBadge.getInstance().getClosingDates().contains(s.getDate()))
				s.setRealSeatsCapacity(realSeatsCapacity);

			//stack<-ticketList
			//pop * ticketList.size-real (send cancelation massage)
			//ticketList<-stack(change the seats No. , if not same customer space) (send changed seat massage)
			Stack<Ticket> stack = new Stack();
			stack.addAll(s.getTickets());

			/*cancel seats*/
			while(stack.size()>realSeatsCapacity){
				Ticket tic = stack.pop();
				// TODO: 25/06/2021 sand massege of cancelation
			}

			/*change taken seats*/
			int i =1;
			int j = 1;
			while(!stack.isEmpty()){
				Ticket tic = stack.pop();
				if (!stack.peek().getCustomer().equals(tic.getCustomer())){//skip seat
					s.addTicket(new Ticket(null,null,i,j));
					j++;
					j=j%10;
					if (i==0){
						j=1;
						i+=2;
					}
				}

				tic.setSeat(i,j);
				s.addTicket(tic);

				// TODO: 25/06/2021 sand massege of changing seats.

				j++;
				j=j%10;
				if (i==0){
					j=1;
					i+=2;
				}
			}
//				for(Ticket t: s.getTickets()) {
//TODO:					//notify(t.getCustomer());//Send alert to the customer about canceling
	//					if(t.getCustomer().getTicket().contains(t))
	//						t.getCustomer().removeTicket(t, true);
	//					else
	//						t.getCustomer().removeTicket(t, false);
	//					cancel.add(t.getCustomer());
				}
//		}
		return cancel;
	}

	public void setPurpleBadge() {
		this.setRealSeatsCapacity();
	}
	/**
	 * @return the cinema
	 */
	public Cinema getCinema() {
		return cinema;
	}
	/**
	 * @param cinema the cinema to set
	 */
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	public String getName(){return placeName;}

	@Override
	public String toString() {
		return this.getName();
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}



}
