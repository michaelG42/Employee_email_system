package ca2_Ronan_Duffy_Michael_Grinnell_Final;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TheFarmLtd_Main {

    public static void main(String[] args) throws FileNotFoundException {
        mainMenu("\nPlease Log in.");
    }
    
    //--------------------------------------------Main Menu (Login)------------------------------------------------
    public static void mainMenu(String customMessege) throws FileNotFoundException {
        Employee_store employeeStoreList = new Employee_store();
        messageStore mailServer = new messageStore();
        employeeStoreList.loadNewFile();
        mailServer.loadNewMessageFile();

        Employee currentUser;
        boolean idExists;
        String warMessege = "";

        Scanner in = new Scanner(System.in);
        System.out.println("*******************************************"
                + "\n_  _ ____ _ _  _    _  _ ____ _  _ _  _ "
                + "\n|\\/| |__| | |\\ |    |\\/| |___ |\\ | |  |"
                + "\n|  | |  | | | \\|    |  | |___ | \\| |__|"
                + "\n                                       "
                + "\n*******************************************\n"
                + customMessege
        );
        
        System.out.print("ID : ");
        if (!in.hasNextLong()) {
            mainMenu("Not a valid ID");
        } 
        else {
            long id = in.nextLong();
            System.out.print("Password : ");
            String password = in.next();
            idExists = employeeStoreList.checkIdExists(id);
            
            if (idExists) {
                currentUser = employeeStoreList.FindEmpById(id);
                
                if (password.equals(currentUser.getPassword())) {

                    if (password.equals("Cheese")) {
                        warMessege = "Warning, You are using the default password. Please change your Password. ";
                    }
                    if (currentUser.isAdmin()) {
                        adminMenu("\n\nWelcome " + currentUser.getName() + ".\nWhat would you like to do today?\n" + warMessege, employeeStoreList, currentUser, mailServer);
                    } else {
                        userMenu("\n\nWelcome " + currentUser.getName() + ".\nWhat would you like to do today?\n" + warMessege, employeeStoreList, currentUser, mailServer);
                    }
                } else {
                    mainMenu("\nLogin failed - Password Incorrect.");
                }
            } else {
                mainMenu("\nLogin failed - Id not valid.");
            }
        }
    }

    //--------------------------------------------Administrator Menu------------------------------------------------
    public static void adminMenu(String customMessege, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        employeeStoreList.saveNewFile();
        Scanner in = new Scanner(System.in);
        boolean valid = true;
        
        System.out.println("********************************************"
                + "\n____ ___  _  _ _ _  _    _  _ ____ _  _ _  _ "
                + "\n|__| |  \\ |\\/| | |\\ |    |\\/| |___ |\\ | |  | "
                + "\n|  | |__/ |  | | | \\|    |  | |___ | \\| |__| "
                + "\n                                             "
                + "\n********************************************\n"
                + customMessege
                + "\n\n(1)Add Employee\n(2)Search Employee\n(3)See Salery for All Employees\n(4)Show Mailing List\n(5)Search All Mail\n(6)User Menu\n(7)Exit\n");
        
        int choice;
        if (!in.hasNextInt()) {
            adminMenu("Not A Valid Choice", employeeStoreList, currentUser, mailServer);
        } 
        else {
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    customMessege = employeeStoreList.addEmployee();
                    employeeStoreList.saveNewFile();
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    searchEmp(employeeStoreList, currentUser, mailServer);
                    break;
                case 7:
                    employeeStoreList.saveNewFile();
                    confirmExit(employeeStoreList, currentUser, mailServer);
                    break;
                case 4:
                    customMessege = "\n Current Mailing list " + employeeStoreList.getMailList();
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 6:
                    customMessege = "\nAdministrator";
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 5:
                    searchMail(employeeStoreList, currentUser, mailServer);
                    break;
                case 3:
                    customMessege = employeeStoreList.listSalaries() + "\nTotal Salery for all Employees : " + employeeStoreList.getTotalsalery();
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                default:
                    customMessege = "\nI'm sorry, I didn't quite get that, please try again";
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
            }
        }
    }
    //--------------------------------------------User Menu------------------------------------------------

    public static void userMenu(String customMessege, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        boolean valid = true;
        System.out.println("********************************************"
                + "\n_  _ ____ ____ ____    _  _ ____ _  _ _  _ "
                + "\n|  | [__  |___ |__/    |\\/| |___ |\\ | |  | "
                + "\n|__| ___] |___ |  \\    |  | |___ | \\| |__| "
                + "\n                                           "
                + "\n********************************************\n"
                + customMessege);
        
        if (currentUser.isAdmin()) {
            System.out.print("\n(1)New messege\n(2)Inbox\n(3)Outbox\n(4)Search\n(5)Show Mailing List\n(6)Change Password\n(7)Exit\n(8)Admin Menu\n");
        } 
        else {
            System.out.println("\n(1)New messege\n(2)Inbox\n(3)Outbox\n(4)Search\n(5)Show Mailing List\n(6)Change Password\n(7)Exit\n");
        }
        if (!in.hasNextInt()) {
            userMenu("Not A Valid Choice", employeeStoreList, currentUser, mailServer);
        } 
        else {
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    getRecipient(employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    int displayOption = 1;
                    int sort = 0;
                    mailFound(mailServer.getMessagesTo(currentUser.getEmailAddress()), "Inbox", displayOption, employeeStoreList, mailServer, currentUser, sort);
                    break;
                case 3:
                    displayOption = 2;
                    sort = 0;
                    mailFound(mailServer.getMessagesFrom(currentUser.getEmailAddress()), "OutBox", displayOption, employeeStoreList, mailServer, currentUser, sort);
                    break;
                case 4:
                    searchEmp(employeeStoreList, currentUser, mailServer);
                    break;
                case 5:
                    customMessege = "\n Current Mailing list " + employeeStoreList.getMailList();
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 7:
                    confirmExit(employeeStoreList, currentUser, mailServer);
                    break;
                case 6:
                    customMessege = employeeStoreList.setPassword(currentUser.getId());
                    employeeStoreList.saveNewFile();
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 8:
                    if (currentUser.isAdmin()) {
                        adminMenu("\n\nWelcome " + currentUser.getName(), employeeStoreList, currentUser, mailServer);
                    } 
                    else {
                        customMessege = "\n'Im sorry, I didn't quite get that, please try again";
                        userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    }
                    break;
                default:
                    customMessege = "\nI'm sorry, I didn't quite get that, please try again";
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
            }
        }
    }

    //--------------------------------------------Search Employee Menu------------------------------------------------
    public static void searchEmp(Employee_store list, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("How would you like to search for employee"
                + "\n(1)Name, (2)Id, (3)Phone number, (4)E-mail, (5)Display All Employees, (6)Display Agents only, (7) Go Back");
        
        if (!in.hasNextInt()) {
            System.out.println("Not A Valid Choice");
            searchEmp(list, currentUser, mailServer);
        } 
        else {
            switch (in.nextInt()) {
                case 1:
                    searchEmpName(list, currentUser, mailServer);
                    break;
                case 2:
                    searchEmpId(list, currentUser, mailServer);
                    break;
                case 3:
                    searchEmpPhone(list, currentUser, mailServer);
                    break;
                case 4:
                    searchEmpEmail(list, currentUser, mailServer);
                    break;
                case 5:
                    manyEmpsFound(list.getAllEmployees(), list, currentUser, mailServer, 0, "Employees Found");
                    break;
                case 6:
                    manyEmpsFound(list.getAgents(), list, currentUser, mailServer, 0, "Agents Found");
                    break;
                case 7:
                    if (currentUser.isAdmin()) {
                        adminMenu("\nTotal Salery for all Employees : ", list, currentUser, mailServer);
                    } else {
                        userMenu("\nYou have been returned to the User menu", list, currentUser, mailServer);
                    }
                    break;
                default:
                    System.out.println("\nIm sorry, I didnt quite get that, please try again");
                    break;
            }
        }
    }
    //--------------------------------------------Employee found Menu------------------------------------------------

    public static void empFound(Employee e, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String customMessege;
        
        if (currentUser.isAdmin()) {
            System.out.println("\nEmployee found,"
                    + "\t\t" + e.toString() + "\n\nWhat would you like to do? \n(1)Send Messege," + " (2)Add to Mail List,"
                    + " (3)Edit," + " (4)Delete," + " (5)See Messeges" + " (6)Go Back");
            
            if (!in.hasNextInt()) {
                System.out.println("Not a valid option");
                empFound(e, employeeStoreList, currentUser, mailServer);
            }            
            switch (in.nextInt()) {
                case 1:
                    customMessege = createNewMessage(currentUser, e, mailServer);
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    customMessege = employeeStoreList.addToMailList(e.getEmailAddress());
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 3:
                    editEmp(e, employeeStoreList, currentUser, mailServer);
                    break;
                case 4:
                    customMessege = employeeStoreList.delEmployee(e.getId());
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 5:
                    ArrayList<Message> found = mailServer.getMessagesFrom(e.getEmailAddress());
                    found.addAll(mailServer.getMessagesTo(e.getEmailAddress()));
                    mailFound(found, "All Messages involving " + e.getEmailAddress(), 3, employeeStoreList, mailServer, currentUser, 0);
                    break;
                case 6:
                    adminMenu("\nYou have been returned to the admin menu", employeeStoreList, currentUser, mailServer);
                    break;
                default:
                    adminMenu("\nYou have been returned to the admin menu", employeeStoreList, currentUser, mailServer);
                    break;
            }
        } 
        else {
            System.out.println("\nEmployee found,"
                    + "\t\t" + e.toStringNoSal() + "\n\nWhat would you like to do? \n(1)Send Messege," + " (2)Add to Mail List,"
                    + " (3)Go Back");
            switch (in.nextInt()) {
                case 1:
                    customMessege = createNewMessage(currentUser, e, mailServer);
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    customMessege = employeeStoreList.addToMailList(e.getEmailAddress());
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 3:
                    userMenu("\nYou have been returned to the user menu", employeeStoreList, currentUser, mailServer);
                    break;
                default:
                    userMenu("\nYou have been returned to the user menu", employeeStoreList, currentUser, mailServer);
                    break;
            }
        }
    }
    //--------------------------------------------Many Employees found Menu------------------------------------------------

    public static void manyEmpsFound(ArrayList<Employee> employeesFound, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer, int sort, String customMessage) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("\n " + customMessage + "\n");
        ArrayList<Long> foundIds = new ArrayList<>();
        int option = 1;
        String heading = String.format("%-6s %-30s %-20s %-40s %-40s %-20s", "\tID", "Name", "BirthDate", "E-mail", "Phone Number", "Type");
        System.out.println(heading);
        
        for (int i = 0; i < employeesFound.size(); i++) {
            System.out.println("\n(" + option + ")\t" + employeesFound.get(i).toListString());
            foundIds.add(employeesFound.get(i).getId());
            option++;
        }
        System.out.println("\nEnter option number of message you wish to select or press (" + (option) + ") to exit, Press (0) to change the sort.");
        int choice;
        boolean valid = false;
        if (!in.hasNextInt()) {
            System.out.println("Not a valid option");
            manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, customMessage);
        } 
        else {
            choice = in.nextInt();
            if (choice == 0) {
                sort++;
                switch (sort) {
                    case 1:
                        Collections.sort(employeesFound, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by name Ascending");
                        break;
                    case 2:
                        Collections.sort(employeesFound, (a, b) -> b.getName().compareToIgnoreCase(a.getName()));
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by name Decending");
                        break;
                    case 3:
                        Collections.sort(employeesFound, (a, b) -> Long.valueOf(a.getId()).compareTo(b.getId()));
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by ID Ascending");
                        break;
                    case 4:
                        Collections.sort(employeesFound, (a, b) -> Long.valueOf(b.getId()).compareTo(a.getId()));
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by ID Decending");
                        break;
                    case 5:
                        Collections.sort(employeesFound, (a, b) -> a.getEmailAddress().compareToIgnoreCase(b.getEmailAddress()));
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by Email Ascending");
                        break;
                    case 6:
                        Collections.sort(employeesFound, (a, b) -> b.getEmailAddress().compareToIgnoreCase(a.getEmailAddress()));
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by Email Decending");
                        break;
                    case 7:
                        Collections.sort(employeesFound, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
                        sort = 1;
                        manyEmpsFound(employeesFound, employeeStoreList, currentUser, mailServer, sort, "sorted by name Ascending");
                        break;
                    default:
                        break;
                }
            }
            while (choice < 0 || choice > option) {
                System.out.println("\nNot a valid choice, Please choose the option number from the list");
                choice = in.nextInt();
            }
            if (choice == (option)) {
                if (currentUser.isAdmin()) {
                    adminMenu("\nYou have been returned to the admin menu", employeeStoreList, currentUser, mailServer);
                } 
                else {
                    userMenu("\nYou have been returned to the user menu", employeeStoreList, currentUser, mailServer);
                }
            }
            if (choice > 0 && choice < option) {
                choice = choice - 1;
                Long choiceId = foundIds.get(choice);
                empFound(employeeStoreList.FindEmpById(choiceId), employeeStoreList, currentUser, mailServer);
            }
        }
    }

    //--------------------------------------------Edit Employee Menu------------------------------------------------
    public static void editEmp(Employee e, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String customMessege;
        System.out.println(" Edit Employee Menu"
                + "\n(Employee details)\t" + e.toString()
                + "\nOptions" + "\t(1)Edit name," + " (2)Edit phone,"
                + " (3)Edit Email," + " (4)Edit Birth date,(5) Go Back (6) Update Wages");

        long id = e.getId();
        if (in.hasNextInt()) {
            
            switch (in.nextInt()) {
                case 1:
                    customMessege = employeeStoreList.editEmployeeName(id);
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    customMessege = employeeStoreList.editEmployeePhone(id);
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 3:
                    customMessege = employeeStoreList.editEmployeeEmail(id);
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 4:
                    customMessege = employeeStoreList.editEmployeeBirthDate(id);
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                    break;
                case 5:
                    empFound(e, employeeStoreList, currentUser, mailServer);
                    break;
                case 6:
                    editwages(e, employeeStoreList, currentUser, mailServer);
                    break;
                default:
                    break;
            }
        } 
        else {
            System.out.println("Not a valid option");
            editEmp(e, employeeStoreList, currentUser, mailServer);
        }
    }

    public static void editwages(Employee e, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String customMessege;
        String customMessege2;
        long id = e.getId();
        
        if (e.getEmployeeType().equals("Agent")) {
            customMessege = employeeStoreList.editAgentSalery(id);
            adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
        } 
        else {
            customMessege = employeeStoreList.editContractorHours(id);
            customMessege2 = employeeStoreList.editContractorRate(id);
            adminMenu(customMessege + customMessege2, employeeStoreList, currentUser, mailServer);
        }
    }

    //--------------------------------------------Confirm program exit------------------------------------------------
    public static void confirmExit(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Are you sure you want to exit? (Y/N)"
                + "\nAll details from this session will be added to a text file.");
        String input;
        input = in.next();
        
        if (input.equalsIgnoreCase("n")) {
            String customMessege = "\nNot done yet, You have been returned to the Menu";
            if (currentUser.isAdmin()) {
                adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
            } 
            else {
                userMenu(customMessege, employeeStoreList, currentUser, mailServer);
            }
        } 
        else if (input.equalsIgnoreCase("y")) {
            employeeStoreList.saveNewFile();
            mailServer.saveNewMessageFile();
            System.out.println("Goodbye :)");
        } 
        else {
            String customMessege = "\nSorry I didnt quite get that, You have been returned to the Menu";
            if (currentUser.isAdmin()) {
                adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
            } 
            else {
                userMenu(customMessege, employeeStoreList, currentUser, mailServer);
            }
        }
    }

    //--------------------------------------------Search Employee Methods------------------------------------------------
    public static void searchEmpName(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        String customMessege;
        Scanner in = new Scanner(System.in);
        System.out.println("\nPlease enter Employee name");
        String searchName = in.nextLine().trim();
        ArrayList<Employee> found = employeeStoreList.FindName(searchName);
        
        if (found.size() > 1) {
            manyEmpsFound(found, employeeStoreList, currentUser, mailServer, 0, "Employees Found");
        } 
        else if (found.size() == 1) {
            empFound(found.get(0), employeeStoreList, currentUser, mailServer);
        } 
        else {
            customMessege = "\nNo Employees found";
            if (currentUser.isAdmin()) {
                adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
            } 
            else {
                userMenu(customMessege, employeeStoreList, currentUser, mailServer);
            }
        }
    }

    public static void searchEmpEmail(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        String customMessege;
        Scanner in = new Scanner(System.in);
        System.out.println("\nPlease enter Employee E-mail Address");
        String search = in.next();

        if (!employeeStoreList.checkEmailUnique(search)) {
            Employee found = employeeStoreList.FindEmpByEmail(search);
            empFound(found, employeeStoreList, currentUser, mailServer);
        } 
        else {
            customMessege = "\nNo Employees found";
            if (currentUser.isAdmin()) {
                adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
            } 
            else {
                userMenu(customMessege, employeeStoreList, currentUser, mailServer);
            }
        }
    }

    public static void searchEmpId(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);
        System.out.println("\nPlease enter Employee ID");
        if (in.hasNextInt()) {
            long search = in.nextInt();
            boolean idExists = employeeStoreList.checkIdExists(search);
            
            if (idExists) {
                empFound(employeeStoreList.FindEmpById(search), employeeStoreList, currentUser, mailServer);
            } 
            else {
                String customMessege = "\nNo such ID has been found";
                if (currentUser.isAdmin()) {
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                } 
                else {
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                }
            }
        } 
        else {
            System.out.println("Not a valid option");
            searchEmpId(employeeStoreList, currentUser, mailServer);
        }
    }

    public static void searchEmpPhone(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("\nPlease enter Employee Phone number");
        String customMessege;
        if (in.hasNextInt()) {
            String search = in.next();
            ArrayList<Employee> found = employeeStoreList.FindEmpByPhone(search);
            if (found.size() > 1) {
                manyEmpsFound(found, employeeStoreList, currentUser, mailServer, 0, "Employees Found");
            } 
            else if (found.size() == 1) {
                empFound(found.get(0), employeeStoreList, currentUser, mailServer);
            } 
            else {
                customMessege = "\nNo Employees found";
                if (currentUser.isAdmin()) {
                    adminMenu(customMessege, employeeStoreList, currentUser, mailServer);
                } 
                else {
                    userMenu(customMessege, employeeStoreList, currentUser, mailServer);
                }
            }
        } else {
            System.out.println("Not a valid option");
            searchEmpPhone(employeeStoreList, currentUser, mailServer);
        }
    }

    //--------------------------------------------Get recipient for new messege------------------------------------------------
    public static void getRecipient(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("please choose a message recipient"
                + "\n (1)Search, (2) Enter Email, (3)Send To Mail List, (4)Exit");
        
        if (in.hasNextInt()) {
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    searchEmp(employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    System.out.println("Please enter email address");
                    Boolean addressNotExist = true;
                    while (addressNotExist) {
                        String recipientEmail = in.next();
                        addressNotExist = employeeStoreList.checkEmailUnique(recipientEmail);
                        if (addressNotExist) {
                            System.out.println("Sorry, I couldnt find that address please try again.");
                        } 
                        else {
                            String customMessage = createNewMessage(currentUser, employeeStoreList.FindEmpByEmail(recipientEmail), mailServer);
                            mailServer.saveNewMessageFile();
                            userMenu(customMessage, employeeStoreList, currentUser, mailServer);
                        }
                    }
                    break;
                case 3:
                    String customMessage = createNewMessageMany(currentUser, employeeStoreList.getMailList(), mailServer);
                    mailServer.saveNewMessageFile();
                    userMenu(customMessage, employeeStoreList, currentUser, mailServer);
                    break;
                case 4:
                    userMenu("Returned To User Menu", employeeStoreList, currentUser, mailServer);
                    break;
                default:
                    System.out.println("\nI'm sorry, I didn't quite get that, please try again");
                    getRecipient(employeeStoreList, currentUser, mailServer);
                    break;
            }
        } 
        else {
            System.out.println("\nI'm sorry, I didn't quite get that, please try again");
            getRecipient(employeeStoreList, currentUser, mailServer);
        }
    }

    public static ZonedDateTime getDate() {
        Scanner in = new Scanner(System.in);
        int date;
        boolean valid;
        int[] fullDate = new int[3];
        for (int i = 0; i < 3; i++) {

            do {
                if (i == 0) {
                    System.out.print("Day :");
                }
                if (i == 1) {
                    System.out.print("Month :");
                }
                if (i == 2) {
                    System.out.print("Year :");
                }
                if (!in.hasNextInt()) {
                    System.out.println("Not A Valid Choice");
                    getDate();
                }
                date = in.nextInt();
                valid = valiDate(i, date);
            } while (!valid);
            fullDate[i] = date;
        }
        return ZonedDateTime.of(fullDate[2], fullDate[1], fullDate[0], 0, 0, 0, 0, ZoneId.of("Europe/Dublin"));
    }

    public static  ZonedDateTime currentDate() {
        ZonedDateTime timestamp = ZonedDateTime.now();
        return timestamp;
    }

    public static ZonedDateTime currentDateLessX(int x) {
        ZonedDateTime timestamp = ZonedDateTime.now().minusDays(x);
        return timestamp;
    }

    public static String formatDate(ZonedDateTime d) {
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME ;
        String date = dtf.format(d); 
        return date;
    }

    public static boolean valiDate(int i, int date) {
        boolean valid = true;
        
        if (i == 0 && (date < 1 || date > 31)) {
            valid = false;
            System.out.println("Please enter a valid date");
        } 
        else if (i == 1 && (date < 1 || date > 12)) {
            valid = false;
            System.out.println("Please enter a valid date");
        } 
        else if (i == 2 && (date < 1900 || date > 2017)) {
            valid = false;
            System.out.println("Please enter a valid date");
        }
        return valid;
    }

    public static String createNewMessage(Employee currentUser, Employee recipient, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String sender = currentUser.getEmailAddress();
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(recipient.getEmailAddress());
        ZonedDateTime date = currentDate();
        ZonedDateTime timestamp = ZonedDateTime.now();
        String strTimestamp = DateTimeFormatter.RFC_1123_DATE_TIME.format(timestamp);

        String priority = selectPriority();
        System.out.println("Please Enter the subject");
        String subject = in.nextLine();
        System.out.println("\nNew E-mail\n");
        String head = String.format("%-40s %-25s %-10s", "To : " + recipient.getEmailAddress(), "Priority : " + priority, strTimestamp);
        System.out.println(head + "\nSubject : " + subject);

        System.out.println("\nPlease type your message below, then press enter to send");
        String body = in.nextLine();

        Message m = new Message(sender, recipients, subject, date, priority, body);
        mailServer.addMessage(m);
        mailServer.saveNewMessageFile();

        return "\n***Message Sent***";
    }

    public static String createNewMessageMany(Employee currentUser, ArrayList<String> recipients, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String sender = currentUser.getEmailAddress();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < recipients.size(); i++) {
            out.append(recipients.get(i)).append(",");
        }
        ZonedDateTime date = currentDate();
        String priority = selectPriority();
        System.out.println("Please Enter the subject");
        String subject = in.nextLine();
        ZonedDateTime timestamp = ZonedDateTime.now();
        String strTimestamp = DateTimeFormatter.RFC_1123_DATE_TIME.format(timestamp);

        System.out.println("\nNew E-mail\n");
        String head = String.format("%-40s %-25s %-10s", "To : " + out, "Priority : " + priority, strTimestamp);
        System.out.println(head + "\nSubject : " + subject);

        System.out.println("\nPlease type your message below then press enter to send");
        String body = in.nextLine();

        Message m = new Message(sender, recipients, subject, date, priority, body);
        mailServer.addMessage(m);
        mailServer.saveNewMessageFile();

        return "\n***Message Sent***";
    }

    public static String selectPriority() {
        Scanner in = new Scanner(System.in);
        String priority = null;
        System.out.println("please select the Priority"
                + "\n(1)low, (2)Medium, (3)High");
        if (!in.hasNextInt()) {
            System.out.println("Not A Valid Choice");
            selectPriority();
        } else {
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    priority = "Low";
                    break;
                case 2:
                    priority = "Medium";
                    break;
                case 3:
                    priority = "High";
                    break;
                default:
                    System.out.println("Not a valid choice, Please try again");
                    selectPriority();
                    break;
            }
        }
        return priority;
    }

    //--------------------------------------------List files in directery------------------------------------------------
    public static void listFiles() {
        File dir = new File(".");
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith((".txt"))) {
                System.out.println("Files : " + file.getName());
            }
        }
    }

    public static void searchMail(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Search mail by \n(1)Sender, (2)Reciever, (3)date, (4)Keyword, (5)ID, (6)Priority, (7)Show All, (8)Advance search (9)Exit");
        
        if (!in.hasNextInt()) {
            System.out.println("Not A Valid Choice");
            searchMail(employeeStoreList, currentUser, mailServer);
        } 
        else {
            int choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    //search mail sender
                    System.out.println("What's the sender's email address?");
                    String sender = in.next();
                    mailFound(mailServer.getMessagesFrom(sender), "Displaying all Employee Mail From : " + sender, 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 2:
                    //search reciever
                    System.out.println("What's the email address?");
                    String receiver = in.next();
                    mailFound(mailServer.getMessagesTo(receiver), "Displaying all Employee Mail To : " + receiver, 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 3:
                    System.out.println("Please enter the date");
                    ZonedDateTime date = getDate();
                    mailFound(mailServer.getMessagesDate(date), "Displaying all Employee Mail Sent On : " + date.getDayOfMonth() + "//" + date.getMonthValue() + "//" + date.getYear(), 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 4:
                    //search by subject
                    System.out.println("Please enter the word you wish to search");
                    String Search;
                    Search = in.nextLine();
                    mailFound(mailServer.listMessageByKeyword(Search), "Displaying all Employee Mail That Contains : " + Search, 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 5:
                    //search by Id
                    long id;
                    System.out.println("\nPlease enter Message ID");
                    id = in.nextLong();
                    viewMessage(mailServer.getAllMessages(), mailServer.returnMessageFromID(id), employeeStoreList, currentUser, mailServer, 3);//3 is display option
                    break;
                case 6:
                    String priority = selectPriority();
                    mailFound(mailServer.getMessagesPriority(priority), "Displaying all Employee Mail With Priority : " + priority, 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 7:
                    mailFound(mailServer.getAllMessages(), "Displaying all Employee Mail", 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 8:
                    advanceSearchMail(employeeStoreList, currentUser, mailServer);
                    break;
                default:
                    adminMenu("Returned to Admin menu", employeeStoreList, currentUser, mailServer);
                    break;
            }
        }
    }

    public static void advanceSearchMail(Employee_store employeeStoreList, Employee currentUser, messageStore mailServer) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Search mail by \n(1)Sent Between Dates, (2)Sent From Between Two Dates , (3)Show All Sent In the Last X Days (4)Exit");
        if (!in.hasNextInt()) {
            System.out.println("Not A Valid Choice");
            advanceSearchMail(employeeStoreList, currentUser, mailServer);
        } else {
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    //search mail sender
                    System.out.println("Please enter the date sent after");
                    ZonedDateTime afterDate = getDate();
                    System.out.println("Please enter the date sent before");
                    ZonedDateTime beforeDate = getDate();
                    String customMessage = "Displaying all Employee Mail between : " + formatDate(afterDate) + " and " + formatDate(beforeDate);
                    mailFound(mailServer.getMessagesBetweenDate(afterDate, beforeDate), customMessage, 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                case 2:
                    //search reciever
                    System.out.println("What's the email address?");
                    String sender = in.next();
                    System.out.println("Please enter the date sent after");
                    afterDate = getDate();
                    System.out.println("Please enter the date sent before");
                    beforeDate = getDate();
                    customMessage = "Displaying all Employee Mail Sent by : " + sender + "\nBetween" + formatDate(afterDate) + " and " + formatDate(beforeDate);
                    ArrayList<Message> between = mailServer.getMessagesBetweenDate(afterDate, beforeDate);
                    mailFound(mailServer.getMessagesFromBetween(sender, between), customMessage, 3, employeeStoreList, mailServer, currentUser, 3);

                    break;
                case 3:
                    //mail sent in the last x days
                    System.out.println("Please enter the amount of days to view");
                    int days = in.nextInt();
                    afterDate = currentDateLessX(days);
                    beforeDate = currentDate();
                    System.out.println(formatDate(afterDate) + " and " + formatDate(beforeDate));
                    customMessage = "Displaying all Employee Mail Sent in the last " + days + " days";
                    mailFound(mailServer.getMessagesBetweenDate(afterDate, beforeDate), customMessage, 3, employeeStoreList, mailServer, currentUser, 3);
                    break;
                default:
                    adminMenu("Returned to Admin menu", employeeStoreList, currentUser, mailServer);
                    break;
            }
        }
    }

    //--------------------------------------------mail found Menu------------------------------------------------
    public static void mailFound(ArrayList<Message> mesagesFound, String customMessage, int displayOption, Employee_store employeeStoreList, messageStore mailServer, Employee currentUser, int sort) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        if (mesagesFound.size() < 1) {
            System.out.println("No mail found :(");
        }
        ArrayList<Long> foundIds = new ArrayList<>();
        int option = 1;
        switch (displayOption) {
            case 1:

                customMessage = String.format("\n" + customMessage + "\n   \t%-40s %-40s %-10s", "From", "Subject", "Date");
                System.out.println(customMessage);
                break;
            case 2:

                customMessage = String.format("\n" + customMessage + "\n   \t%-40s %-40s %-10s", "To", "Subject", "Date");
                System.out.println(customMessage);
                break;
            default:

                customMessage = String.format("\n" + customMessage + "\n   \t%-40s %-40s %-40s %-10s", "From", "To", "Subject", "Date");
                System.out.println(customMessage);
                break;
        }
        for (int i = 0; i < mesagesFound.size(); i++) {
            switch (displayOption) {
                case 1:

                    System.out.println("\n(" + (option) + ")\t" + mesagesFound.get(i).toInboxString());
                    break;
                case 2:
                    System.out.println("\n(" + (option) + ")\t" + mesagesFound.get(i).toOutboxString());
                    break;
                default:
                    System.out.println("\n(" + (option) + ")\t" + mesagesFound.get(i).toAllString());
                    break;
            }
            foundIds.add(mesagesFound.get(i).getMessageID());
            option++;
        }
        System.out.println("\nEnter option number of message you wish to select or press (" + (option) + ") to exit, Press (0) to change the sort.");
        int count = 0;
        int choice;
        boolean valid = false;
        if (!in.hasNextInt()) {
            mailFound(mesagesFound, "Not A Valid Choice", displayOption, employeeStoreList, mailServer, currentUser, sort);
        } 
        else {
            choice = in.nextInt();
            while (choice < -1 || choice > option + 1) {
                System.out.println("\nNot a valid choice, Please choose the option number from the list");
                choice = in.nextInt();
            }

            // The folowing code will check if the user is in the inbox, outbox or view all mode and cycle through the sort order differently for each displayoption
            if (choice == 0) {
                sort++;
                switch (sort) {
                    case 1:
                        switch (displayOption) {
                            case 1:
                                Collections.sort(mesagesFound, (a, b) -> a.getFromAddress().compareToIgnoreCase(b.getFromAddress()));
                                customMessage = "\nSorted by sender Acending";
                                break;
                            case 2:
                                Collections.sort(mesagesFound, (a, b) -> a.getToFirstAddress().compareToIgnoreCase(b.getToFirstAddress()));
                                customMessage = "\nSorted by recipient Acending";
                                break;
                            default:
                                Collections.sort(mesagesFound, (a, b) -> a.getFromAddress().compareToIgnoreCase(b.getFromAddress()));
                                customMessage = "\nSorted by sender Acending";
                                break;
                        }
                        mailFound(mesagesFound, customMessage, displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 2:
                        switch (displayOption) {
                            case 1:
                                Collections.sort(mesagesFound, (a, b) -> b.getFromAddress().compareToIgnoreCase(a.getFromAddress()));
                                customMessage = "\nSorted by sender Decending";
                                break;
                            case 2:
                                Collections.sort(mesagesFound, (a, b) -> b.getToFirstAddress().compareToIgnoreCase(a.getToFirstAddress()));
                                customMessage = "\nSorted by recipient Decending";
                                break;
                            default:
                                Collections.sort(mesagesFound, (a, b) -> b.getFromAddress().compareToIgnoreCase(a.getFromAddress()));
                                sort = 7;
                                customMessage = "\nSorted by sender Decending";
                                break;
                        }
                        mailFound(mesagesFound, customMessage, displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 3:
                        Collections.sort(mesagesFound, (a, b) -> b.getDateDay() - a.getDateDay());
                        Collections.sort(mesagesFound, (a, b) -> b.getDateMonth() - a.getDateMonth());
                        Collections.sort(mesagesFound, (a, b) -> b.getDateYear() - a.getDateYear());
                        mailFound(mesagesFound, "\nSorted by Date Decending", displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 4:
                        Collections.sort(mesagesFound, (a, b) -> a.getDateDay() - b.getDateDay());
                        Collections.sort(mesagesFound, (a, b) -> a.getDateMonth() - b.getDateMonth());
                        Collections.sort(mesagesFound, (a, b) -> a.getDateYear() - b.getDateYear());

                        mailFound(mesagesFound, "\nSorted by Date Acending", displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 5:
                        Collections.sort(mesagesFound, (a, b) -> Long.valueOf(a.getMessageID()).compareTo(b.getMessageID()));
                        mailFound(mesagesFound, "\nSorted by ID Acending", displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 6:
                        Collections.sort(mesagesFound, (a, b) -> Long.valueOf(b.getMessageID()).compareTo(a.getMessageID()));
                        mailFound(mesagesFound, "\nSorted by ID Decending", displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 7:
                        switch (displayOption) {
                            case 1:
                                Collections.sort(mesagesFound, (a, b) -> a.getFromAddress().compareToIgnoreCase(b.getFromAddress()));
                                customMessage = "\nSorted by sender Acending";
                                break;
                            case 2:
                                Collections.sort(mesagesFound, (a, b) -> a.getToFirstAddress().compareToIgnoreCase(b.getToFirstAddress()));
                                customMessage = "\nSorted by recipient Acending";
                                break;
                            default:
                                Collections.sort(mesagesFound, (a, b) -> a.getFromAddress().compareToIgnoreCase(b.getFromAddress()));
                                customMessage = "\nSorted by sender Acending";
                                break;
                        }
                        sort = 1;
                        mailFound(mesagesFound, customMessage, displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 8:
                        Collections.sort(mesagesFound, (a, b) -> a.getToFirstAddress().compareToIgnoreCase(b.getToFirstAddress()));
                        customMessage = "\nSorted by recipient Acending";
                        mailFound(mesagesFound, customMessage, displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    case 9:
                        Collections.sort(mesagesFound, (a, b) -> b.getToFirstAddress().compareToIgnoreCase(a.getToFirstAddress()));
                        customMessage = "\nSorted by recipient Decending";
                        sort = 2;
                        mailFound(mesagesFound, customMessage, displayOption, employeeStoreList, mailServer, currentUser, sort);
                        break;
                    default:
                        break;
                }
            }
            if (choice == (option)) {
                if (currentUser.isAdmin()) {
                    adminMenu("\nYou have been returned to the admin menu", employeeStoreList, currentUser, mailServer);
                } 
                else {
                    userMenu("\nYou have been returned to the user menu", employeeStoreList, currentUser, mailServer);
                }
            } 
            else if ((choice >= 1 && choice < option)) {
                choice = choice - 1;
                Long choiceId = foundIds.get(choice);
                viewMessage(mesagesFound, mailServer.returnMessageFromID(choiceId), employeeStoreList, currentUser, mailServer, displayOption);
            }
        }
    }

    public static void viewMessage(ArrayList<Message> mesagesFound, Message m, Employee_store employeeStoreList, Employee currentUser, messageStore mailServer, int displayOption) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println(m.ViewMessage());
        System.out.println("(1)Reply, (2)Go Back");
        
        if (!in.hasNextInt()) {
            System.out.println("Not A Valid Choice");
            viewMessage(mesagesFound, m, employeeStoreList, currentUser, mailServer, displayOption);
        } 
        else {
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    String customMessage = createNewMessage(currentUser, employeeStoreList.FindEmpByEmail(m.getFromAddress()), mailServer);
                    userMenu(customMessage, employeeStoreList, currentUser, mailServer);
                    break;
                case 2:
                    mailFound(mesagesFound, "gone back", displayOption, employeeStoreList, mailServer, currentUser, 1);
                    break;
                default:
                    System.out.println("\nSorry I didnt quite get that, Please try again");
                    viewMessage(mesagesFound, m, employeeStoreList, currentUser, mailServer, displayOption);
                    break;
            }
        }
    }
}
