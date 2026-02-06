package onlineticketbookingsystem;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author jolie
 */
public class TravelEvent extends Event
{
 private String destination;
 private String transportType;
 private static final ArrayList<String> additionalDestinations = new ArrayList<>();
 private static ArrayList<String> travelDestinations = new ArrayList<>();
 
 static {
    travelDestinations.add("Saudi Arabia");
    travelDestinations.add("Qatar");
    travelDestinations.add("Kuwait");
    travelDestinations.add("Dubai");
    travelDestinations.add("Bahrain");
    travelDestinations.add("Turkey");
    travelDestinations.add("Italy");
    travelDestinations.add("korea");
    travelDestinations.add("Canada");
    travelDestinations.add("India");
 }
 

 // Constructor
 public TravelEvent(String eventName, String eventLocation, Date eventDate, String destination)
 {
  super(eventName, eventLocation, eventDate);
  this.destination = destination;
  this.transportType = "Unknown";
 }
 public TravelEvent()
 {
  super("eventName", "Dammam", new Date());
  this.destination = "destination";
  this.transportType = "Unknown";
 }
 
  // Getters
 public String getDestination() 
 {
  return destination;
 }

 public String getTransportType() 
 {
  return transportType;
 }
 
 //Setter
 public void setDestination(String destination)
 {
    this.destination = destination; 
 }
 
 public void setTransportType(String transportType)
 {
     this.transportType = transportType; 
 }

 public void chooseTravelDetails(Scanner input) 
 {
  // Select Transport Type 
  System.out.println("\nChoose your mode of transport(1-3):");
  System.out.println("1- Bus");
  System.out.println("2- Train");
  System.out.println("3- Airplane");
  
  int transportChoice = -1;
    while (transportChoice < 1 || transportChoice > 3) {
        String transportChoiceStr = input.nextLine(); 
        try {
            transportChoice = Integer.parseInt(transportChoiceStr); 
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid input.");
            continue; 
        }
        if (transportChoice < 1 || transportChoice > 3) {
            System.out.println("Invalid choice. Please enter a valid input.");
        }
    }

  
  switch (transportChoice)
  {
   case 1:
    this.transportType = "Bus";
   break;
   case 2:
    this.transportType = "Train";
   break;
   case 3:
    this.transportType = "Airplane";
   break;
   default:
    System.out.println("Invalid choice , please select option 1,2,3 ");
  }
    //Select Destination        
    System.out.println("\nPlease choose a destination by entering its number:");
    printTravelDestination();
    System.out.println("0. Add a new destination");

    int destinationChoice = -1; 

    while (true) { 
        String destinationChoiceStr = input.nextLine(); 
        try {
            destinationChoice = Integer.parseInt(destinationChoiceStr); 
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            continue; 
        }

        if (destinationChoice == 0) {
            System.out.println("\nEnter the new destination:");
            String destination = input.nextLine();
            addDestination(destination); 
            break;
        } else if (destinationChoice > 0 && destinationChoice <= travelDestinations.size() + additionalDestinations.size()) {
            this.destination = getDestination(destinationChoice); 
            break; 
        } else {
            System.out.println("Invalid choice. Please select a valid input.");
        }
}

  }
 
     public static void printTravelDestination()
 {
  if (!travelDestinations.isEmpty())
  {
   for (int i = 0; i < travelDestinations.size(); i++)
   {
    System.out.println((i + 1) + ". " + travelDestinations.get(i));
   }
  }
   // Display dynamically added destinations
  if (!additionalDestinations.isEmpty())
  {
   for (int i = 0; i < additionalDestinations.size(); i++) 
   {
     System.out.println((travelDestinations.size() + i + 1) + "- " + additionalDestinations.get(i));
    }
   } 
 }
  
 public static void addDestination(String newDestination)
 {
   if (newDestination != null && !newDestination.isEmpty()) 
   {
     // Check in travelDestinations
     for (int i = 0; i < travelDestinations.size(); i++)
     {
       if (travelDestinations.get(i).equalsIgnoreCase(newDestination)) 
       {
        travelDestinations.set(i, newDestination);
         System.out.println("\n\t~Destination updated successfully!~");
         return;
        }
       } 
     
    // Check if the destination exists in additionalDestinations
    for (int i = 0; i < additionalDestinations.size(); i++) 
    {
     if (additionalDestinations.get(i).equalsIgnoreCase(newDestination))
     {
      System.out.println("\n\t~Destination updated successfully!~");
      return;
      }
     }
   additionalDestinations.add(newDestination);
  }
   else
   {
    System.out.println("Invalid destination input!");
   }
 }
 
 //overloaded method to set destination
public void setDestination() {
    Scanner input = new Scanner(System.in);
    int choice = -1;

    while (true) {
        try {
            System.out.println("\nAvailable destinations:");
            printTravelDestination(); 
            System.out.println("Enter the number of your chosen destination:");
            choice = Integer.parseInt(input.nextLine());

            if (choice >= 1 && choice <= travelDestinations.size() + additionalDestinations.size()) {
                this.destination = getDestination(choice); 
                System.out.println("Destination set to " + this.destination +
                        " and take off will be from " + getEventLocation());
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}


  
 // Get a destination by choice
 public static String getDestination(int choice)
 {
  String[] predefinedDestinations = {"Saudi Arabia", "Qatar", "Kuwait", "Dubai", "Bahrain", "Turkey", "Italy", "Korea", "Canada", "India"};
  if (choice >= 1 && choice <= 10)
  {
   return predefinedDestinations[choice - 1];
  }
  if (choice > 10 && choice <= 10 + additionalDestinations.size())
  {
   return additionalDestinations.get(choice - 11);
  }
  return null;
 }
 
 // set event location, date, and name
public void setTravelDetails() {
    Scanner scanner = new Scanner(System.in);
    setEventLocation("Dammam");
    int dateChoice = -1;
    int transportChoice = -1;
    while (true) {
        try {
            System.out.println("\nChoose your mode of transport:");
            System.out.println("1- Bus");
            System.out.println("2- Train");
            System.out.println("3- Airplane");
            transportChoice = Integer.parseInt(scanner.nextLine());
            if (transportChoice >= 1 && transportChoice <= 3) break;
            else System.out.println("Invalid choice. Please select 1, 2, or 3.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid input.");
        }
    }
    
    switch (transportChoice) {
        case 1:
            this.transportType = "Bus";
            break;
        case 2:
            this.transportType = "Train";
            break;
        case 3:
            this.transportType = "Airplane";
            break;
        default:
            this.transportType = "";
            break;
    }

    System.out.println("Transport type set to: " + this.transportType);

    while (true) {
        try {
            System.out.println("Choose the event date:");
            System.out.println("1) Tomorrow");
            System.out.println("2) In 2 days");
            System.out.println("3) In 3 days");
            dateChoice = Integer.parseInt(scanner.nextLine());

            if (dateChoice >= 1 && dateChoice <= 3) break;
            else System.out.println("Invalid choice. Please choose between 1 and 3.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
        }
    }

    Calendar calendar = Calendar.getInstance();
    switch (dateChoice) {
        case 1 -> calendar.add(Calendar.DAY_OF_MONTH, 1);
        case 2 -> calendar.add(Calendar.DAY_OF_MONTH, 2);
        case 3 -> calendar.add(Calendar.DAY_OF_MONTH, 3);
    }

    Date eventDate = calendar.getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String formattedDate = dateFormat.format(eventDate);
    setEventDate(eventDate);
    System.out.println("Event date is: " + formattedDate);

    String eventName = getDestination() + " trip";
    setEventName(eventName);
    System.out.println("Your event will be: " + eventName + " by " + getTransportType());
}


}