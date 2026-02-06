package onlineticketbookingsystem;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author jolie
 */

public class Admin extends User
{
        
 public Admin(String name, String email, String password)
 {        
  super(name, email, password);
 }
        
 @Override
 public void login(String email, String password) 
 {
  if (!checkPassword(password)) 
  {
   System.out.println("\n\tPassword does not meet the required criteria:");
   System.out.println("\t- At least 8 characters");
   System.out.println("\t- At least one uppercase letter");
   System.out.println("\t- At least one lowercase letter");
   System.out.println("\t- At least one digit");
   System.out.println("\t- At least one special character");
   return;
  }
        
  if (getEmail().equals("OOPgroup3@iau.edu.sa") && getPassword().equals("OOPgroup3@"))
  {
   System.out.println("\n\t~"+getName() + " logged in as admin successfully.~");
  } 
  else 
  {
   System.out.println("\n\t^Login failed. Please check your credentials.^");   
  }
 }

 public void createEvent() 
 {
  Scanner input = new Scanner(System.in);
  
// Collect Event Type
  System.out.print("\nEnter event type (movie/travel):");
  String eventType = input.nextLine();
       
  // Collect Event Name
  System.out.print("\nEnter event name: ");
  String eventName=input.nextLine();
      
  // Collect Event Location
  System.out.print("\nEnter event location: ");
  String eventLocation=input.nextLine();
        
  // Collect Event Date 
  Date eventDate = null;
  while (eventDate == null) 
  {
   System.out.print("\nEnter event date (DD/MM/YYYY): ");
   String eventDateString = input.nextLine();
   eventDate = parseDate(eventDateString);
   if (eventDate == null) 
   {
    System.out.println("\n\t^Invalid date format. Please enter the date in DD/MM/YYYY format.^");
   }
  }
  Event event = null;
  //Movie Details
  if (eventType.equalsIgnoreCase("movie")) 
  {
   System.out.print("\nEnter movie title:");
   String movieTitle = input.nextLine();
      
    // Use chooseAudience method to determine the target audience
    MovieEvent audiencet = new MovieEvent();
    int audienceChoice = audiencet.chooseAudience();

   // Add movie to the appropriate list
    if (audienceChoice == 1) {
        System.out.println("Adding movie to Children's list...");
        MovieEvent.addToChildrensMovies(movieTitle);
    } else if (audienceChoice == 2) {
        System.out.println("Adding movie to Adults' list...");
        MovieEvent.addToAdultsMovies(movieTitle);
    } else {
        System.out.println("\n\t^Invalid audience type. Event creation aborted.^");
        return;
    }
   // Create MovieEvent
   MovieEvent movieEvent = new MovieEvent(eventName, eventLocation, eventDate, movieTitle);
   Event.allEvents.add(movieEvent);
   System.out.println("\n\t~Movie event created successfully!~");
   event = movieEvent;
  } 
  // Create TravelEvent
  else if(eventType.equalsIgnoreCase("travel"))
  {
   TravelEvent travelEvent = new TravelEvent(eventName, eventLocation, eventDate, ""); 
   travelEvent.chooseTravelDetails(input);
   Event.allEvents.add(travelEvent);
   System.out.println("\n\t~ Travel Event created successfully!~");
   event = travelEvent;
  }  
  else
  {
   System.out.println("\n\t^Invalid event type.^"); 
  }
     
  if (event == null)
  {
   System.out.println("\n\t^Event creation failed. Cannot create tickets.^");
   return;
  }
  
  //create Ticket  
System.out.println("\nEnter ticket type (standard/vip):");
    String ticketType;

    while (true) { 
        ticketType = input.nextLine().trim(); 

        if (ticketType.equalsIgnoreCase("standard")) {
            StandardTicket standardTicket = new StandardTicket(event, false); // Associate ticket with event
            standardTicket.updateBasePrice(input); 
            event.addTicket(standardTicket); // Add ticket to the event
            double finalPrice = standardTicket.calculatePrice();
            System.out.println("Price after extra charge: $" + finalPrice);
            break; 
        } else if (ticketType.equalsIgnoreCase("vip")) {
            VIPTicket vipTicket = new VIPTicket(event, true); 
            vipTicket.updateBasePrice(input); 
            event.addTicket(vipTicket); // Add ticket to the event
            double finalPrice = vipTicket.calculatePrice();
            System.out.println("Price after extra charge: $" + finalPrice);
            break; 
        } else {
            
            System.out.println("Invalid ticket type. Please enter standard or vip.");
        }
    }

    System.out.println("\n\t~Ticket created and added successfully!~");

 }
 
 public void updateEvent() 
 {
  if (Event.allEvents.isEmpty()) 
  {
   System.out.println("\n\t^No events have been created yet.^");
   return;
  }
    
  // Display list of all events
  System.out.println("\n\t~ List of All Created Events ~");
  for (int i = 0; i < Event.allEvents.size(); i++) 
  {
   System.out.println((i + 1) + ". " + Event.allEvents.get(i).getEventName());
  }
   
  Scanner input = new Scanner(System.in);
  System.out.print("\nEnter the number of the event you want to update: ");
  int index;
  if(input.hasNextInt())
  {
   index = input.nextInt(); 
   input.nextLine();
   if (index < 1 || index > Event.allEvents.size()) 
   {
    System.out.println("\n\t^Invalid event number. Please select a number between 1 and" + Event.allEvents.size() + ".^");
    return;
   }
   index -= 1; 
  }
  else 
  {
   System.out.println("\n\t^Invalid input. Please enter a valid number.^");
   input.nextLine(); 
   return;
  }
   
  if (index >=0 && index < Event.allEvents.size())
  {
   Event event = Event.allEvents.get(index);
   System.out.println("\nUpdating event: " + event.getEventName());
          
   // Update event name
   System.out.print("Enter new event name (leave blank for no change):");
   String newName = input.nextLine();
   if (!newName.isEmpty()) 
   {
    event.setEventName(newName);
   }
          
   // Update event location
   System.out.println("\nthe old location is: "+event.getEventLocation());
   System.out.print("Enter new event location (leave blank for no change):");
   String newLocation = input.nextLine();
   if (!newLocation.isEmpty()) 
   {
    event.setEventLocation(newLocation);
   }
           
   // Update event date
   System.out.println("\nthe old date is: "+event.getEventDate());
   System.out.print("Enter new event date (DD/MM/YYYY, leave blank for no change):");
   String newDateString = input.nextLine();
   if (!newDateString.isEmpty()) 
   {
    Date newDate = null;
    while (newDate == null) 
    {
     newDate = parseDate(newDateString);
     if (newDate == null) 
     {
      System.out.println("\n\t^Invalid date format.^\n Please enter the date in DD/MM/YYYY format:");
      newDateString = input.nextLine();
      if (newDateString.isEmpty())
      {
       break;
      }
     } 
     else 
     {
      event.setEventDate(newDate);
     }
    }
   }
     
   // Update movie title
   if (event instanceof MovieEvent)
   {
    MovieEvent movieEvent = (MovieEvent) event;    
    System.out.println("\nthe old movie title is: "+movieEvent.getMovieTitle());
    System.out.print("Enter new movie title (leave blank for no change):");
    String newMovieTitle = input.nextLine();
    if (!newMovieTitle.isEmpty()) 
    {
     movieEvent.setMovieTitle(newMovieTitle);
    }
   }
      
   // Update TravelEvent details
   if (event instanceof TravelEvent) 
   {
    TravelEvent travelEvent = (TravelEvent) event; //down casting
    
    // Update Travel destination
    System.out.println("\nThe old destination is: " + travelEvent.getDestination());
    System.out.print("Enter new destination (leave blank for no change): ");
    
    String newDestination = input.nextLine();
    if (!newDestination.isEmpty()) 
    {
     travelEvent.setDestination(newDestination);
     TravelEvent.addDestination(newDestination);   
    }
       
    // Update Travel transport type
    System.out.println("\nThe old transport type is: " + travelEvent.getTransportType());
    System.out.print("Enter new transport type (Bus/Train/Airplane, leave blank for no change): ");
    String newTransportType = input.nextLine();
    if (!newTransportType.isEmpty()) 
    {
      
     travelEvent.setTransportType(newTransportType);
    }
   }
    
   // Update ticket details  
   if (!event.getTickets().isEmpty()) 
   {
    Ticket ticket = event.getTickets().get(0); // Assume single ticket for simplicity
    System.out.println("\nThe current ticket type is: " + (ticket instanceof VIPTicket ? "VIP" : "Standard"));
    System.out.print("Enter new ticket type (standard/vip, leave blank for no change):");
    String newType = input.nextLine().toLowerCase();
    if (!newType.isEmpty())
    {
     if (newType.equalsIgnoreCase("standard") && ticket instanceof VIPTicket) 
     {
      // Replace VIP with Standard
      event.getTickets().remove(ticket);
      StandardTicket newStandardTicket = new StandardTicket(event, false);
      event.addTicket(newStandardTicket); // Add new Standard ticket
      ticket = newStandardTicket;
     }
     else if (newType.equalsIgnoreCase("vip") && ticket instanceof StandardTicket) 
     {
      // Replace Standard with VIP
      event.getTickets().remove(ticket);
      VIPTicket newVIPTicket = new VIPTicket(event, true);
      event.addTicket(newVIPTicket); // Add new VIP ticket
      ticket = newVIPTicket;
     }
    }

    // Update ticket price
    System.out.println("\nThe base price for " +newType+ " is: $" + ticket.getBasePrice());
    System.out.print("Enter new price (leave blank for no change):");
    String newPriceString = input.nextLine();
    if (!newPriceString.isEmpty()) 
    {
     double newPrice = Double.parseDouble(newPriceString);
     ticket.setBasePrice(newPrice);
    }
    // Recalculate price and update ticket details
    double updatedPrice = ticket.calculatePrice();
    System.out.println("\nThe new price after extra charge is set to: $" + updatedPrice);
   } 
   else 
   {
   System.out.println("\n\t^No tickets have been created for this event.^");
   }
    
   System.out.println("\n\t~Event updated successfully!~");  
   }      
 }
              
 public void deleteEvent()
 {
  if (Event.allEvents.isEmpty()) 
  {
   System.out.println("\n\t^No events have been created yet.^");
   return;
  }
    
  // Display list of all events
  System.out.println("\n\t~ List of All Created Events ~");
  for (int i = 0; i < Event.allEvents.size(); i++) 
  {
   System.out.println((i + 1) + ". " + Event.allEvents.get(i).getEventName());
  }
  
  // Prompt user for the event number to delete
  Scanner input = new Scanner(System.in);
  System.out.print("\nEnter the number of the event you want to delete: ");
  int index;
  if (input.hasNextInt())
  {
   index = input.nextInt();
   input.nextLine(); 
   if (index < 1 || index > Event.allEvents.size()) 
   {
    System.out.println("\n\t^Invalid event number. Please select a number between 1 and " + Event.allEvents.size() + ".^");
    return;
   }
   
   // Delete event
   index -= 1;
   Event.allEvents.remove(index);
   System.out.println("\n\t~Event deleted successfully!~");
   } 
  else
  {
   System.out.println("\n\t^Invalid input. Please enter a valid number.^");
   input.nextLine(); // Consume invalid input
  }
 }
        
 private Date parseDate(String dateString)
 {
  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
  if (dateString == null || dateString.isEmpty())
  {
   return null;
  }
    
  Date parsedDate = formatter.parse(dateString, new ParsePosition(0));
  if (parsedDate != null)
  {
   return parsedDate;
  }
  else
  {
   return null;
  }
 }
               
 @Override
 public void logout() 
 {
  System.out.println("\n\t~"+getName() + " logged out successfully.~");
 }
 
}