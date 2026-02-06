package onlineticketbookingsystem;
import java.util.Scanner;


/**
 *
 * @author jolie
 */
public class OnlineTicketBookingSystem {
    
static String main_menue=
 "\t+---------------------------------------------------------------+\n" +
 "\t|          Welcome to the Online Ticket Booking System          |\n" +
 "\t+---------------------------------------------------------------+\n" +
 "\t|  1. Browse the system                                          |\n" +
 "\t|  2. Login as Admin                                              |\n" +
 "\t|  3. Login as Customer                                           |\n" +
 "\t|  4. Exit                                                       |\n" +
 "\t+---------------------------------------------------------------+\n" +
 "\tEnter your choice: ";

static String user_menu =
"\n\t---------------------------------\n"+       
"\t|        Guest Dashboard         |\n"+
"\t---------------------------------\n"+
"\t|1|    View Available Movies     |\n" +
"\t---------------------------------\n"+
"\t|2| View Available Destinations  |\n" +
"\t---------------------------------\n"+ 
"\t|3|     View New Events          |\n" +
"\t---------------------------------\n"+        
"\t|4|   Go back to log in menue    |\n"+
"\t---------------------------------\n"+
"\tYour choice: ";
   
static Scanner input = new Scanner(System.in);
    
static void userSystem() {
    User user = new User("Guest", "Guest@gmail.com", "Guest#1234");
    int choice = -1;

    do {
        System.out.print(user_menu);

        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n\tInvalid input. Please enter a valid choice.");
            continue;
        }

        switch (choice) {
            case 1:
                user.availableMovies();
                break;

            case 2:
                user.availableDestinations();
                break;

            case 3:
                System.out.println("\tListing all events...");
                user.viewNewEvents();
                break;

            case 4:
                System.out.println("\n\t~End of Browsing.~");
                break;

            default:
                System.out.println("\n\t^Wrong Entry. Please choose one from the options.^\n");
        }
    } while (choice != 4);
}


static void adminSystem() {
    Admin admin = null;
    boolean isAdminLoggedIn = false; // Tracks if the admin is logged in
    int choice = -1; 
    Scanner input = new Scanner(System.in);

    do {
        // Generate the admin menu based on login status
        String adminMenu;
        if (!isAdminLoggedIn) {
            adminMenu =
                    "\n\t---------------------------------\n" +
                    "\t|         Admin Dashboard        |\n" +
                    "\t---------------------------------\n" +
                    "\t|1|          Login               |\n" +
                    "\t---------------------------------\n" +
                    "\t|2|       Create Event           |\n" +
                    "\t---------------------------------\n" +
                    "\t|3|       Update Event           |\n" +
                    "\t---------------------------------\n" +
                    "\t|4|       Delete Event           |\n" +
                    "\t---------------------------------\n" +
                    "\t|5|    Return to the Main menu   |\n" +
                    "\t---------------------------------\n" +
                    "\tYour choice: ";
        } else {
            // After login
            adminMenu =
                    "\n\t---------------------------------\n" +
                    "\t|         Admin Dashboard        |\n" +
                    "\t---------------------------------\n" +
                    "\t|1|       Create Event           |\n" +
                    "\t---------------------------------\n" +
                    "\t|2|       Update Event           |\n" +
                    "\t---------------------------------\n" +
                    "\t|3|       Delete Event           |\n" +
                    "\t---------------------------------\n" +
                    "\t|4|         Logout               |\n" +
                    "\t---------------------------------\n" +
                    "\tYour choice: ";
        }
        System.out.print(adminMenu);

        String userInput = input.nextLine(); 
        try {
            choice = Integer.parseInt(userInput); 
        } catch (NumberFormatException e) {
            System.out.println("\n\tInvalid input. Please enter a valid option.");
            continue; 
        }

        if (!isAdminLoggedIn) {
            switch (choice) {
                case 1: // Login
                    System.out.print("\nEnter Admin Name: ");
                    String adminName = input.nextLine();
                    System.out.print("\nEnter Admin Email: ");
                    String adminEmail = input.nextLine();
                    System.out.print("\nEnter Admin Password: ");
                    String adminPassword = input.nextLine();

                    // Create the Admin instance with the provided details
                    admin = new Admin(adminName, adminEmail, adminPassword);
                    admin.login(adminEmail, adminPassword);

                    if (adminEmail.equals("OOPgroup3@iau.edu.sa") && adminPassword.equals("OOPgroup3@")) {
                        isAdminLoggedIn = true; // Update login status
                        System.out.println("\n\t<<Login Successful!>>");
                    } else {
                        System.out.println("\n\t<<Invalid email or password.>>");
                    }
                    break;

                case 5:
                    System.out.println("\n\tReturning to the main menu...");
                    break;

                default:
                    System.out.println("\n\t<<You need to log in first!>>");
            }
        } else {
            switch (choice) {
                case 1: // Create Event
                    admin.createEvent();
                    break;

                case 2: // Update Event
                    admin.updateEvent();
                    break;

                case 3: // Delete Event
                    admin.deleteEvent();
                    break;

                case 4: // Logout
                    if (admin != null) {
                        admin.logout();
                        isAdminLoggedIn = false; // Log out
                        System.out.println("\n\t<<Logged out successfully.>>");
                    }
                    break;

                default:
                    System.out.println("\n\tWrong Entry. Please choose one from the options.\n");
            }
        }
    } while (choice != 5);
}

static void customerSystem() {
    Customer customer = null;
    Event selectedEvent = null;
    boolean isCustomerLoggedIn = false;
    int choice = -1;

    do {
        String customerMenu = !isCustomerLoggedIn ? 
            "\n\t---------------------------------\n" +
            "\t|        Customer Dashboard      |\n" +
            "\t---------------------------------\n" +
            "\t|1|          Login               |\n" +
            "\t---------------------------------\n" +
            "\t|2|       Search Events          |\n" +
            "\t---------------------------------\n" +
            "\t|3|       Book Tickets           |\n" +
            "\t---------------------------------\n" +
            "\t|4|     View Booking Details     |\n" +
            "\t---------------------------------\n" +
            "\t|5|      Cancel Booking          |\n" +
            "\t---------------------------------\n" +
            "\t|6|    Return to the Main Menu   |\n" +
            "\t---------------------------------\n" +
            "\tYour choice: "
            :
            "\n\t---------------------------------\n" +
            "\t|        Customer Dashboard      |\n" +
            "\t---------------------------------\n" +
            "\t|1|       Search Events          |\n" +
            "\t---------------------------------\n" +
            "\t|2|       Book Tickets           |\n" +
            "\t---------------------------------\n" +
            "\t|3|     View Booking Details     |\n" +
            "\t---------------------------------\n" +
            "\t|4|      Cancel Booking          |\n" +
            "\t---------------------------------\n" +
            "\t|5|          Logout              |\n" +
            "\t---------------------------------\n" +
            "\tYour choice: ";

        System.out.print(customerMenu);

        try {
            choice = Integer.parseInt(input.nextLine()); 
        } catch (NumberFormatException e) {
            System.out.println("\n\tInvalid input. Please enter a valid choice.");
            continue;
        }

        if (!isCustomerLoggedIn) {
            switch (choice) {
                case 1:
                    System.out.print("\nEnter Customer Name: ");
                    String customerName = input.nextLine();
                    System.out.print("\nEnter Customer Email: ");
                    String customerEmail = input.nextLine();
                    System.out.print("\nEnter Customer Password: ");
                    String customerPassword = input.nextLine();

                    customer = new Customer(customerName, customerEmail, customerPassword);
                    customer.login(customerEmail, customerPassword);

                    if (customerEmail.equals(customer.getEmail()) && customerPassword.equals(customer.getPassword())) {
                        isCustomerLoggedIn = true;
                    }
                    break;

                case 6:
                    System.out.println("\n\tReturning to the main menu...");
                    break;

                default:
                    System.out.println("\n\t<<You need to log in first!>>");
            }
        } else {
            switch (choice) {
                case 1:
                    selectedEvent = customer.searchEvent();
                    break;

                case 2:
                    if (selectedEvent == null) {
                        System.out.println("\n\t^No event selected. Please search for an event first.^");
                    } else {
                        customer.bookTickets(selectedEvent);
                    }
                    break;

                case 3:
                    if (!customer.currentTickets.isEmpty()) {
                        customer.viewEventDetails();
                    } else {
                        System.out.println("\n\t^You have no bookings to view.^");
                    }
                    break;

                case 4:
                    if (!customer.currentTickets.isEmpty()) {
                        customer.cancelBooking();
                    } else {
                        System.out.println("\n\t^You have no bookings to cancel.^");
                    }
                    break;

                case 5:
                    customer.logout();
                    isCustomerLoggedIn = false;
                    break;

                default:
                    System.out.println("\t^Wrong Entry.^ Please select a valid option.");
            }
        }
    } while (choice != 6);
}

      
public static void main(String[] args) {
    int choice = -1;
    do {
        System.out.print(main_menue);

        try {
            choice = Integer.parseInt(input.nextLine()); 
        } catch (NumberFormatException e) {
            System.out.println("\n\tInvalid input. Please enter a valid choice.");
            continue;
        }

        switch (choice) {
            case 1:
                userSystem();
                break;

            case 2:
                adminSystem();
                break;

            case 3:
                customerSystem();
                break;

            case 4:
                System.out.println("\n\t<< Thank YOU For Using Our System <3 >>");
                System.out.println("\t\t~ Good Bye!~");
                break;

            default:
                System.out.println("\n\t^Wrong Entry. Please choose one from the options.^\n");
        }
    } while (choice != 4);
}


}