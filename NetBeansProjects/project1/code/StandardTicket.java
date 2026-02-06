package onlineticketbookingsystem;
import java.util.Scanner;

/**
 *
 * @author jolie
 */
public class StandardTicket extends Ticket 
{
    private double basePrice = 50.0; // Base price for standard ticket

 public StandardTicket(Event event, boolean isVIP) 
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
   finalPrice += 5.0; // Extra charge for MovieEvent
  }
  
  if (getEvent() instanceof TravelEvent)
  {
   finalPrice *= 1.3; // Extra charge for travel events
  }
  return finalPrice;
 }
 
@Override
public double updateBasePrice(Scanner input) {
    System.out.println("\nThe default base price for Standard Ticket is: $" + basePrice);
    System.out.println("Enter a new price if you want to change it, or press Enter to keep the default price:");

    String newPrice = input.nextLine();
    if (newPrice.isEmpty()) {
        System.out.println("The price remains at the default: $" + basePrice);
        return 0; 
    }

    try {
        double parsedPrice = Double.parseDouble(newPrice); 
        this.basePrice = parsedPrice;
        System.out.println("The new price for Standard Ticket is: $" + basePrice);
        return parsedPrice;
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        return updateBasePrice(input); 
    }
}

 @Override
 public String displayDetails()
 {
  return "\tStandard Ticket  " + super.displayDetails() + "\n\tPrice: $" + calculatePrice();
 }  
}