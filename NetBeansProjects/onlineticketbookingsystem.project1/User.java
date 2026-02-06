package onlineticketbookingsystem;

/**
 *
 * @author jolie
 */
public class User 
{
  private String name;
  private String email;
  private String password;
    
  //Constructor
  public User(String name, String email, String password) 
  {
   this.name = name;
   this.email = email;
   if (checkPassword(password))
   {
    this.password = password;
   }  
  }
  
  //Getter 
  public String getName()
  {
   return name; 
  }
   
  public String getEmail()
  {
   return email; 
  }
  
  public String getPassword()
  {
   return password; 
  }
   
  //Settet
  public void setName(String name)
  {
   this.name = name;
  }
   
  public void setEmail(String email)
  {
   this.email = email;
  }
   
  public void setPassword(String password)
  {
   if (checkPassword(password))
   {
    this.password = password;
   } 
  }
  
  //Methods for User Class
   public void login(String email, String password){
   
   } 
    
   public static boolean checkPassword(String password)
   {   
    if (password.length() < 8) 
    {
     return false;
    }
    boolean hasUpper = false , hasLower = false , hasNum = false , hasSpecial = false;

    for (int i = 0; i < password.length(); i++) 
    {
     char checkChar = password.charAt(i);
     
     if (Character.isUpperCase(checkChar)) 
     {
      hasUpper = true;
     }
     else if (Character.isLowerCase(checkChar)) 
     {
      hasLower = true;
     }
     else if (Character.isDigit(checkChar)) 
     {
      hasNum = true;
     }
     else if (!Character.isLetterOrDigit(checkChar)) 
     {
      hasSpecial = true;
     }
    }
    return hasUpper && hasLower && hasNum && hasSpecial;
   }
   
   public void availableMovies()
   {
    System.out.println("\nAll available movies:");
    MovieEvent.displayMovies("children"); // Reusing method exist in movieEvent class;
    System.out.println("============================");
    MovieEvent.displayMovies("adults"); // Reusing method exist in movieEvent class;
   }
    
   public void availableDestinations()
   {
    System.out.println("\nAvailable destinations for travel:");
    TravelEvent.printTravelDestination();
   }
   
   public void viewNewEvents() 
   {
    if (Event.allEvents.isEmpty()) 
    {
     System.out.println("\n\t^The Admin hasn't created events yet.^");
     return;
    }
    System.out.println("\n\tList of all new available events:");
    System.out.println("\n\t-----------------------------------");
    for (Event event : Event.allEvents) 
    {
     // Display ticket details
     if (!event.getTickets().isEmpty())
     {
      for (Ticket ticket : event.getTickets())
      {
       System.out.println(ticket.displayDetails());
      }
     }
      
     else
     {
      System.out.println("\n\tNo tickets available for this event.");
     }
      
     // Specific details for MovieEvent
     if (event instanceof MovieEvent) 
     {
      MovieEvent movieEvent = (MovieEvent) event;
      System.out.println("\tMovie Title: " + movieEvent.getMovieTitle());
     }

     // Specific details for TravelEvent
     if (event instanceof TravelEvent)
     {
      TravelEvent travelEvent = (TravelEvent) event;
      System.out.println("\tDestination: " + travelEvent.getDestination());
      System.out.println("\tTransport Type: " + travelEvent.getTransportType());
     }
     
     System.out.println("\n\t-----------------------------------");
    }  
   }
   
   public void logout() {
   
   }
  }