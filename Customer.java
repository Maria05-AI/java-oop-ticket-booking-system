package onlineticketbookingsystem;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jolie
 */
public class Customer extends User
{ 
 protected ArrayList<Ticket> currentTickets; 

 // Constructor
 public Customer(String name, String email, String password) 
 {
  super(name, email, password); 
  this.currentTickets = new ArrayList<>();
 }
        
 // method for logging in
 @ Override
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
  
  if (this.getEmail().equals(email) && this.getPassword().equals(password)) 
  {
   System.out.println("\n\t~" + getName() + " logged in successfully!~") ;
  }
  else 
  {
   System.out.println("\n\t^Login failed. Please check your credentials.^") ;
  }
 }
 
 // method for searching events
 public Event searchEvent() {
    Scanner scanner = new Scanner(System.in);
    Event selectedEvent = null;
    int eventChoice = -1;

    while (true) {
        try {
            System.out.println("\nWhich event would you like to search? (please choose 1-3)");
            System.out.println("1- Movie Event");
            System.out.println("2- Travel Event");
            System.out.println("3- Registered Event");
            eventChoice = Integer.parseInt(scanner.nextLine());

            if (eventChoice >= 1 && eventChoice <= 3) break;
            else System.out.println("Invalid event type. Please enter a number between 1 and 3.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
        }
    }

    if (eventChoice == 1) {
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setMovieTitle();
        movieEvent.setMovieDetails();
        selectedEvent = movieEvent;
    } else if (eventChoice == 2) {
        TravelEvent travelEvent = new TravelEvent();
        travelEvent.setDestination();
        travelEvent.setTravelDetails();
        selectedEvent = travelEvent;
    } else if (eventChoice == 3) {
        viewNewEvents(); 

        System.out.println("\nEnter the event number to book:");

        try {
            int selectedNumber = Integer.parseInt(scanner.nextLine());

            if (selectedNumber < 1 || selectedNumber > Event.allEvents.size()) {
                System.out.println("Invalid choice. Returning to the dashboard.");
                return null;
            }

            Event selectedRegisteredEvent = Event.allEvents.get(selectedNumber - 1);

            if (selectedRegisteredEvent.getTickets().isEmpty()) {
                System.out.println("No tickets available for this event.");
                return null;
            }

            Ticket selectedTicket = selectedRegisteredEvent.getTickets().get(selectedNumber - 1);

            System.out.println("\nYou have selected:");
            System.out.println(selectedTicket.displayDetails());
            System.out.println("Would you like to book this ticket? (yes/no):");

            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes")) {
                currentTickets.add(selectedTicket);
                System.out.println("Ticket booked successfully!");
            } else {
                System.out.println("Booking canceled.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to the dashboard.");
        }

    }

    return selectedEvent;
}


 //method for booking tickets
public void bookTickets(Event selectedEvent) {
    if (selectedEvent == null) {
        System.out.println("No event selected. Please search for an event first.");
        return;
    }

    Scanner scanner = new Scanner(System.in);
    int userTicketChoice = -1;

    while (true) {
        try {
            System.out.println(" ~ Booking a Ticket ~ ");
            System.out.println("Please choose ticket type:");
            System.out.println("1- Standard ticket");
            System.out.println("2- VIP ticket");
            userTicketChoice = Integer.parseInt(scanner.nextLine());

            if (userTicketChoice == 1 || userTicketChoice == 2) break;
            else System.out.println("Invalid ticket type. Please type 1 or 2.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter 1 or 2.");
        }
    }

    Ticket ticket = null;
    double ticketPrice = 0.0;

    if (userTicketChoice == 1) {
        ticket = new StandardTicket(selectedEvent, false);
        ticketPrice = ticket.calculatePrice();
    } else if (userTicketChoice == 2) {
        ticket = new VIPTicket(selectedEvent, true);
        ticketPrice = ticket.calculatePrice();
    }

    while (true) {
        System.out.println("The price for this ticket is " + ticketPrice + "$. Do you want to proceed? (yes/no):");
        String userResponse = scanner.nextLine().trim().toLowerCase();

        if (userResponse.equals("yes")) {
            currentTickets.add(ticket);
            System.out.println("Ticket booked successfully!");
            break;
        } else if (userResponse.equals("no")) {
            System.out.println("Booking not fulfilled.");
            break;
        } else {
            System.out.println("Invalid input. Please type yes or no.");
        }
    }
}


       
 //method for viewing details of a booking
 public void viewEventDetails() {
    if (currentTickets.isEmpty()) {
        System.out.println("You have no bookings to view.");
        return;
    }

    System.out.println("\n~ Displaying Booking Details ~");
    for (Ticket ticket : currentTickets) {
        System.out.println(ticket.displayDetails());
        System.out.println("---------------------------------");
    }
}

        
 //method for cancelling a booking
 public void cancelBooking() {
    if (currentTickets.isEmpty()) {
        System.out.println("You have no bookings to cancel.");
        return;
    }

    Scanner scanner = new Scanner(System.in);

    System.out.println("\n~ Your Booked Tickets ~");
    int index = 1; 
    for (Ticket ticket : currentTickets) {
        System.out.println(index + ") " + ticket.displayDetails());
        index++;
    }

    int ticketChoice = -1;
    while (true) {
        try {
            System.out.println("\nPlease choose the ticket number to cancel (1-" + currentTickets.size() + "):");
            ticketChoice = Integer.parseInt(scanner.nextLine());
            if (ticketChoice >= 1 && ticketChoice <= currentTickets.size()) {
                break; 
            } else {
                System.out.println("Invalid choice. Please select a number between 1 and " + currentTickets.size() + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    Ticket selectedTicket = currentTickets.get(ticketChoice - 1);

    while (true) {
        System.out.println("\nYou selected:");
        System.out.println(selectedTicket.displayDetails());
        System.out.println("Would you like to cancel this booking? (yes/no):");
        String userAns = scanner.nextLine().trim().toLowerCase();

        if (userAns.equals("yes")) {
            currentTickets.remove(selectedTicket);
            System.out.println("Ticket canceled successfully!");
            break;
        } else if (userAns.equals("no")) {
            System.out.println("Your booking is still active!");
            break;
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        }
    }
}


 
 // method for logging out
 @ Override
 public void logout() 
 {
  System.out.println("\n\t~"+getName() + " logged out successfully.~");
 }
}