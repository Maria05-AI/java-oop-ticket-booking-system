package onlineticketbookingsystem;
import java.util.Scanner;

/**
 *
 * @author jolie
 */
public abstract class Ticket 
{
 private Event event; // Association with an Event
 protected boolean isVIP; // Indicator for VIP seating

 public Ticket(Event event, boolean isVIP)
 {
  this.event = event;
  this.isVIP = isVIP;
 }

 public Event getEvent() 
 {
   return event;
 }
 
 public void setEvent(Event event) 
 {
   this.event = event;
 }
 
 public abstract double getBasePrice(); 
 public abstract void setBasePrice(double newBasePrice); 
 

 public double calculatePrice() 
 {
   return 0.0; // Base price calculation, to be overridden
 }
 public double updateBasePrice(Scanner input)
 {
  return 0.0;   
 }
    
 public String displayDetails() 
 {
   return "\n\tEvent Type: " + event.getClass().getSimpleName() +
          "\n\tEvent Name: " + event.getEventName() +
          "\n\tLocation: " + event.getEventLocation() + 
          "\n\tDate: " + event.getEventDate();
 }
}
