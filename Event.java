package onlineticketbookingsystem;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jolie
 */
public class Event
{
 private String eventName;
 private String eventLocation;
 private Date eventDate;
 private List<Ticket> tickets = new ArrayList<>();
 public static ArrayList<Event> allEvents = new ArrayList<>();

 // Constructor
 public Event(String eventName, String eventLocation, Date eventDate)
 {
  this.eventName = eventName;
  this.eventLocation = eventLocation;
  this.eventDate = eventDate;
 }
 // Getters and Setters
 public String getEventName() 
 {
  return eventName;
 }
 public void setEventName(String eventName)
 {
  this.eventName = eventName;
 }
 public String getEventLocation() 
 {
  return eventLocation;
 }
 public void setEventLocation(String eventLocation) 
 {
  this.eventLocation = eventLocation;
 }
 public Date getEventDate()
 {
  return eventDate;
 }
 public void setEventDate(Date eventDate)
 {
  this.eventDate = eventDate;
 }
 
 public List<Ticket> getTickets()
 {
  return tickets;
 }
 
 public void addTicket(Ticket ticket)
 {
  this.tickets.add(ticket);
 }
}