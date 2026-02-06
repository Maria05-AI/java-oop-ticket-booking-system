package onlineticketbookingsystem;
import java.util.Scanner;

/**
 *
 * @author jolie
 */
public class VIPTicket extends Ticket
{
  private double basePrice = 100.0; // Base price for VIP ticket
 
 public VIPTicket(Event event, boolean isVIP) 
 {
  super(event, isVIP);
 }
 //Getter
  @Override
 public double getBasePrice()
 {
  return basePrice;
 }
 //Setter
  @Override
 public void setBasePrice(double newBasePrice) 
 {
  this.basePrice = newBasePrice;
 }
 @Override
 public double calculatePrice() 
 {
  double finalPrice = basePrice;
        
  if (getEvent() instanceof MovieEvent)
  {
   finalPrice += 10.0; // Extra charge for for movie event
  }
  
  if (getEvent() instanceof TravelEvent) 
  {
   finalPrice *= 1.5; // Extra charge for TravelEvent
  }
  return finalPrice;
 }
 
 @Override
public double updateBasePrice(Scanner input) {
    System.out.println("\nThe default base price for VIP Ticket is: $" + basePrice);
    System.out.println("Enter a new price if you want to change it, or press Enter to keep the default price:");

    String newPrice = input.nextLine(); 
    if (newPrice.isEmpty()) {
        System.out.println("The price remains at the default: $" + basePrice);
        return 0; 
    }

    try {
        double parsedPrice = Double.parseDouble(newPrice);
        this.basePrice = parsedPrice; 
        System.out.println("The new price for VIP Ticket is set to: $" + basePrice);
        return parsedPrice; 
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        return updateBasePrice(input); 
    }
}


 @Override
 public String displayDetails() 
 {
   return "\tVIP Ticket " + super.displayDetails() + "\n\tPrice: $" + calculatePrice();
 }  
}