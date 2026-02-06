package onlineticketbookingsystem;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author jolie
 */
public class MovieEvent extends Event 
{
 private String movieTitle;
 private boolean isAdult;
 private static ArrayList<String> adultsMovies = new ArrayList<>();
 private static ArrayList<String> childrensMovies = new ArrayList<>();
 
  static {
        childrensMovies.add("Frozen");
        childrensMovies.add("Toy Story");
        childrensMovies.add("Inside Out");
        childrensMovies.add("Minions");
        childrensMovies.add("The Lion King");

        adultsMovies.add("Bad Boys");
        adultsMovies.add("Harry Potter");
        adultsMovies.add("Titanic");
        adultsMovies.add("Mrs. Chatterjee vs Norway");
        adultsMovies.add("The Impossible");
    }
 
 
 // Constructor
 public MovieEvent(String eventName, String eventLocation, Date eventDate, String movieTitle) 
 {
  super(eventName, eventLocation, eventDate);
  this.movieTitle = movieTitle;
 }
 public MovieEvent() 
 {
  super("eventName", "eventLocation", new Date());
  this.movieTitle = "movieTitle";
 }
 
 // Getters
 public String getMovieTitle() 
 {
  return movieTitle;
 }
 public boolean getIsAdult()
 {
  return isAdult;
 }
 
 //Setter
 public void setMovieTitle(String movieTitle)
 {
  this.movieTitle = movieTitle;   
 }

 //overloaded method to set the title of the movie
public void setMovieTitle() {
    Scanner scanner = new Scanner(System.in);
    int audienceType = chooseAudience();
    int choice = -1;

    while (true) {
        try {
            System.out.println("Please enter the number of your choice:");
            choice = Integer.parseInt(scanner.nextLine());

            if ((audienceType == 1 && choice >= 1 && choice <= childrensMovies.size()) ||
                (audienceType == 2 && choice >= 1 && choice <= adultsMovies.size())) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    String chosenMovie;
    if (audienceType == 1) {
        chosenMovie = childrensMovies.get(choice - 1);
    } else {
        chosenMovie = adultsMovies.get(choice - 1);
    }

    setMovieTitle(chosenMovie);
    System.out.println("Your chosen movie is: " + chosenMovie);
}


 // Method to choose movie category
 public int chooseAudience() {
    Scanner input = new Scanner(System.in);

    int choice = -1; 
    while (true) { 
        System.out.println("\nChoose the movie audience:");
        System.out.println("1. Movie for Children");
        System.out.println("2. Movie for Adults");

        String userInput = input.nextLine(); 

        try {
            choice = Integer.parseInt(userInput); 
            if (choice == 1 || choice == 2) { 
                break; 
            } else {
                System.out.println("\tInvalid choice. Please select (1 or 2).");
            }
        } catch (NumberFormatException e) {
            System.out.println("\tInvalid input. Please enter a number (1 or 2).");
        }
    }

    switch (choice) {
        case 1:
            System.out.println("\tYou chose a movie for Children.");
            displayMovies("Children");
            isAdult = false; 
            break;
        case 2:
            System.out.println("\tYou chose a movie for Adults.");
            displayMovies("Adults");
            isAdult = true;
            break;
    }
    return choice; 
}

 
 // method to print children or adult movies 
 public static void displayMovies(String audience) 
 {
  System.out.println("Available movies for " + audience + ":");
  if (audience.equalsIgnoreCase("children"))
  {
   for (int i = 0; i < childrensMovies.size(); i++) 
   {
    System.out.println((i + 1) + ". " + childrensMovies.get(i));
   }
  }
  else if (audience.equalsIgnoreCase("adults")) 
  {
   for (int i = 0; i < adultsMovies.size(); i++) 
   {
    System.out.println((i + 1) + ". " + adultsMovies.get(i));
   }
  }
  else
  {
   System.out.println("Invalid input, please choose a proper audience.");
  }
 }
 
   // set event location, date, and name
public void setMovieDetails() {
    Scanner scanner = new Scanner(System.in);
    int locationChoice = -1;

    while (true) {
        try {
            System.out.println("\nPlease proceed with the location.");
            System.out.println("\nChoose event location:\n1) Dammam\n2) Riyadh\n3) Jeddah");
            locationChoice = Integer.parseInt(scanner.nextLine());

            if (locationChoice >= 1 && locationChoice <= 3) break;
            else System.out.println("Invalid location choice. Please try again.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
        }
    }
  
    String eventLocation = "";
    switch (locationChoice) {
        case 1:
            eventLocation = "Dammam";
            break;
        case 2:
            eventLocation = "Riyadh";
            break;
        case 3:
            eventLocation = "Jeddah";
            break;
        default:
            eventLocation = "";
            break;
    }

    setEventLocation(eventLocation);
    System.out.println("Event location will be: " + eventLocation);

    int dateChoice = -1;

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

    String eventName = getMovieTitle() + " screening ";
    setEventName(eventName);
    System.out.println("Your event will be: " + eventName);
    System.out.println("\nPlease proceed with the booking!");
}

   
   // Add movie to Children's Movies list
   public static void addToChildrensMovies(String movieTitle) 
   {
    if (movieTitle!=null && !movieTitle.isEmpty()&& !childrensMovies.contains(movieTitle))
    {
     childrensMovies.add(movieTitle);
     System.out.println("\t" +movieTitle + " has been added to Children's movie list successfully!.");
     displayMovies("children");
    }
   }
    
   // Add movie to Adults' Movies list
   public static void addToAdultsMovies(String movieTitle) 
   {
    if (movieTitle!=null && !movieTitle.isEmpty()&& !adultsMovies.contains(movieTitle)) 
    {
     adultsMovies.add(movieTitle);
     System.out.println("\t" +movieTitle + " has been added to Adults' movie list successfully!.");
     displayMovies("adults");
    }
   }   
}