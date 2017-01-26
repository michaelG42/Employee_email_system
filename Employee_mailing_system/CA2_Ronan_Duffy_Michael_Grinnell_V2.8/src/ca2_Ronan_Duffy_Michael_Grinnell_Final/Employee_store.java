package ca2_Ronan_Duffy_Michael_Grinnell_Final;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Employee_store {

    private final ArrayList<Employee> employeeList;
    private final ArrayList<Agent> agentList;
    private final ArrayList<Contractor> contractorList;
    
   // temparary string array to store email addresses for messages with multiple recipients
    private final ArrayList<String> mailList;

    public Employee_store() {
        employeeList = new ArrayList<>();
        mailList = new ArrayList<>();
        agentList = new ArrayList<>();
        contractorList = new ArrayList<>();
        
    }
    //--------------------------------------------Return Details------------------------------------------------

    public String displayEmployees() {
        return employeeList.toString();
    }

    public ArrayList<Employee> getAllEmployees() {
        return employeeList;
    }

    public String displayEmployee(Employee e) {
        return e.toString();
    }

    public String addToMailList(String address) {
        mailList.add(address);
        return address + " has been added to mailing list";
    }

    public ArrayList<String> getMailList() {
        return mailList;
    }

    //--------------------------------------------Add Details------------------------------------------------
    // Add Employee From user Input Method  
    public String addEmployee() {
        Scanner in = new Scanner(System.in);       
        boolean agent = addEmpType();//Determine the type of employee
        String name = addName();
        String phoneNum = addPhone();
        String emailAddress = addEmail(employeeList);
        String Password = "Cheese";
        String newEmpDetails;
                                    
        if(agent) {
                Agent a = new Agent(name, phoneNum, emailAddress);
                double salary = addSalary();
                a.setSalery(salary);
                addBirthDate(a);
                a.setPassword(Password);
                employeeList.add(a);
                agentList.add(a);
                newEmpDetails = a.toString();
                
            } else {
                Contractor c = new Contractor(name, phoneNum, emailAddress);
                double hourWork = addHoursWorked();
                c.setHoursWorked(hourWork);
                double rate = addHourlyRate();
                c.setHourlyRate(rate);
                addBirthDate(c);
                c.setPassword(Password);
                c.setAdmin(false);
                employeeList.add(c);
                contractorList.add(c);
                newEmpDetails = c.toString();
                               
            }    
        System.out.println("Employee succsessfully added, You will be returned to the admin menu.");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(TheFarmLtd_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newEmpDetails;
    }

    
    public boolean addEmpType(){
        System.out.println("(1)Agent (2)Contractor");
        boolean isAgent = true;
        Scanner in = new Scanner(System.in);
        if (!in.hasNextInt()){
            System.out.println("Not a valid option");
            addEmpType();
        }
        else{
            switch(in.nextInt()){
            case 1 :
            isAgent = true;
            break;
            case 2 :
            isAgent = false;
            break;
            default :
            System.out.println("Not a valid choice");
            addEmpType();
            break;
        }
        }
        return isAgent;
    }
    
    public ArrayList<Employee> getAgents(){
        ArrayList<Employee> found = new ArrayList<>();
                for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmployeeType().equals("Agent")) // employeeList.get(i)  instanceof Agent
            {               
                found.add(employeeList.get(i));
            }
            
        }
                return found;
    }

    public String addEmail(ArrayList<Employee> employeeList) {
        Scanner in = new Scanner(System.in);
        boolean unique;
        String finalEmail;
        System.out.println("Please enter Employee Email wich will then be appended with @theFarm.org");
        do {
            String emailAddress = in.next();
            finalEmail = appendEmail(emailAddress);
            unique = checkEmailUnique(finalEmail);

            if (!unique) {
                System.out.println("Email address already in use, Please try a different email");
            }
        } while (!unique);

        return finalEmail;
    }

    public String addName() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        String name = "";
        System.out.println("Please enter Employee name");

        while (valid == false) {
            name = in.nextLine();
            valid = isOnlyLetters(name);

            if (!valid) {
                System.out.println("Sorry the field 'Name' can only contain letters, please try again");
            }
        }

        return name;
    }

    public String addPhone() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        String phoneNum = "";
        System.out.println("Please enter Employee Phone");

        while (valid == false) {
            phoneNum = in.next();
            valid = isOnlyNumbers(phoneNum);

            if (!valid) {
                System.out.println("Sorry A phone number can only contain numbers with a positive value");
            }
        }

        return phoneNum;
    }

    // Adds birth date to an array of 3 ints for day/month/year
    public void addBirthDate(Employee e) {
        Scanner in = new Scanner(System.in);
        int date = 0;
        boolean valid;
        System.out.println("Please enter Employee BirthDate.\n");
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
                    System.out.println("Not A Valid Date");
                    in.next();
                    valid = valiDate(i, 999);
                } else {
                    date = in.nextInt();
                    valid = valiDate(i, date);
                }

            } while (!valid);

            e.setBirthDate(i, date);
        }
    }

    public double addSalary() {
        Scanner in = new Scanner(System.in);        
        double salary = 0;      
            while (true) {
            System.out.println("What's this agent's salary?");
            try {
                salary = Double.parseDouble(in.next());
                if (salary < 0) {
                System.out.println("You can't have negative salary");
                addSalary();
                break;
                }
                else{
                break;} 
            } catch (NumberFormatException ignore) {
                System.out.println("Not a valid option");
            }
        }                       
        return salary;
    }

    public double addHoursWorked() {
        Scanner in = new Scanner(System.in);
        double hours = 0;
        while (true) {
        System.out.println("How many hours has this contractor worked?");
        try {
                hours = Double.parseDouble(in.next());
                if (hours < 0) {
                System.out.println("You can't have negative hours");
                addHoursWorked();
                break;
                }
                else{
                break;} 
            } catch (NumberFormatException ignore) {
                System.out.println("Not a valid option");
            }
        }
        return hours;
    }

    public double addHourlyRate() {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        double rate = 0;
        
                while (true) {
        System.out.println("What is this contractor hourly rate?");
        try {
                rate = Double.parseDouble(in.next());
                if (rate < 0) {
                System.out.println("You can't have negative rate");
               addHourlyRate();
                break;
                }
                else{
                break;} 
            } catch (NumberFormatException ignore) {
                System.out.println("Not a valid option");
            }
                }
        return rate;
    }

    public String setPassword(Long id) {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        String password;
        String customMessege = "";

        while (valid == false) {
            System.out.println("Please enter your new Password, your new password must be longer than five characters and less than ten.");
            password = in.next();

            if (password.length() < 5 || password.length() > 10) {
                System.out.println("Sorry, your password must be longer than five characters and less than ten.");
            } else {
                System.out.println("Please confirm your new password");
                String passwordConfirm = in.next();

                if (passwordConfirm.equals(password)) {
                    FindEmpById(id).setPassword(password);
                    customMessege = "Your password has been changed to " + FindEmpById(id).getPassword();
                    valid = true;
                } else {
                    System.out.println("Your passwords did not match please try again");
                }
            }
        }
        return customMessege;
    }

    //--------------------------------------------Search Details------------------------------------------------       
    // Search for employee by name
    public ArrayList<Employee> FindName(String empName) {
        ArrayList<Employee> employeesFoundByName = new ArrayList<>();
        String checkName;
        String searchName = empName.toLowerCase().replaceAll("[']", "");
        for (int i = 0; i < employeeList.size(); i++) {

            checkName = employeeList.get(i).getName().toLowerCase().replaceAll("[\\s\\']", "");

            if (checkName.equals(searchName)) {
                employeesFoundByName.add(employeeList.get(i));
            } else if (!checkName.equals(searchName)) {
                int searchSize;
                int foundLetters = 0;
                if (checkName.length() >= searchName.length()) {
                    searchSize = searchName.length();
                } else {
                    searchSize = checkName.length();
                }
                for (int j = 0; j < searchSize; j++) {
                    if (searchName.charAt(j) == checkName.charAt(j)) {
                        foundLetters++;
                    } else {
                        foundLetters = 0;
                    }
                    if (foundLetters >= 4) {
                        employeesFoundByName.add(employeeList.get(i));
                        break;
                    }
                }
            }
        }
        return employeesFoundByName;
    }

    public Employee FindEmpByEmail(String email) {
        int found = -1;
        for (int i = 0; i < employeeList.size(); i++) {

            if (employeeList.get(i).getEmailAddress().equals(email)) {
            }
            found = i;
        }
        return employeeList.get(found);
    }

    public Employee FindEmpById(long id) {
        int found = -1;
        for (int i = 0; i < employeeList.size(); i++) {

            if (employeeList.get(i).getId() == (id)) {
                found = i;
            }

        }
        return employeeList.get(found);
    }

    public ArrayList<Employee> FindEmpByPhone(String phone) {
        ArrayList<Employee> employeesFound = new ArrayList<>();

        employeeList.stream().filter((e) -> (e.getPhoneNum().equals(phone))).forEach((e) -> {
            employeesFound.add((e));
        });

        return employeesFound;
    }
    
    public String listSalaries() {
        System.out.println("Now displaying monthly salaries for each employee");
        String list = "";
        
        for (int i = 0; i < employeeList.size(); i++) {
            list = list + "\n" + String.format("%-20s %-8s", employeeList.get(i).getName(), "€" + employeeList.get(i).getSalary());//employeeList.get(i).getName() + "\t\t" + employeeList.get(i).getSalary();
            
        }
        return list;
    }
        
    public String getTotalsalery(){
    double total = 0;
    
        for (Agent a : agentList) {
            total += a.getWages();
        }
        
        for (Contractor c : contractorList) {
            total += c.getWages();
        }
        String roundTotal = String.format("%.2f", total);
        return "€" +roundTotal;
    }

    //--------------------------------------------Edit Details------------------------------------------------
    public String editEmployeeName(long id) {
        String newName = addName();
        String oldName = FindEmpById(id).getName();
        String customMessege = "\nEmployee : " + oldName + " has been updated to : " + newName;
        FindEmpById(id).setName(newName);
        return customMessege;
    }

    public String editEmployeePhone(long id) {
        String newPhone = addPhone();
        String oldPhone = FindEmpById(id).getPhoneNum();
        String customMessege = "\nEmployee Phone number : " + oldPhone + " has been updated to : " + newPhone;
        FindEmpById(id).setPhoneNum(newPhone);
        return customMessege;
    }

    public String editEmployeeEmail(long id) {
        String newEmail = addEmail(employeeList);
        String old = FindEmpById(id).getPhoneNum();
        String customMessege = "\nEmployee Email Address : " + old + " has been updated to " + newEmail;
        FindEmpById(id).setEmailAddress(newEmail);
        return customMessege;
    }

    public String editEmployeeBirthDate(long id) {
        int[] old = FindEmpById(id).getBirthDate();
        String oldDate = old[0] + "/" + old[1] + "/" + old[2];

        addBirthDate(FindEmpById(id));

        int[] newBirthdate = FindEmpById(id).getBirthDate();

        String customMessege = "\nEmployee birthday : " + oldDate
                + " has been updated to " + newBirthdate[0] + "/" + newBirthdate[1] + "/" + newBirthdate[2];

        return customMessege;
    }
        public String editAgentSalery(long id) {
        System.out.println("Please Enter Agents New Salery");
        double newSal = addSalary();
        double oldSal= 0;
        Agent b = null;
        for (Agent a : agentList) {
            if (a.getId() == id){
                oldSal = a.getWages();
                a.setSalery(newSal);
                b=a;               
            }
        }
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId() == id){
                 employeeList.set(i,b);
            }
        }       
        String customMessege = "\nEmployee Salery : " + String.format("%.2f", oldSal) + " has been updated to " + String.format("%.2f", newSal);

        return customMessege;
    }
        
    public String editContractorRate(long id) {
        System.out.println("Please Enter Contractors New Rate");
        Contractor b = null;
        double newRate = addHourlyRate();
        double oldRate= 0;
        for (Contractor c : contractorList) {
            if (c.getId() == id){
                oldRate = c.getHourlyRate();
                c.setHourlyRate(newRate);
                b=c;
            }
        }
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId() == id){
                 employeeList.set(i,b);
            }
        }
        
        String customMessege = "\nEmployee Hourly Rate : " + String.format("%.2f", oldRate) + " has been updated to " + String.format("%.2f", newRate);

        return customMessege;
    }
    public String editContractorHours(long id) {
        System.out.println("Please Enter Contractors New Hours");
        Contractor b = null;
        double newHours = addHoursWorked();
        double oldHours= 0;
        for (Contractor c : contractorList) {
            if (c.getId() == id){
                oldHours = c.getHourlyRate();
                c.setHourlyRate(newHours);
                b=c;
            }
        }
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId() == id){
                 employeeList.set(i,b);
            }
        }
          
        String customMessege = "\nEmployee Hours Worked : " + String.format("%.2f", oldHours) + " has been updated to " + String.format("%.2f", newHours);

        return customMessege;
    }

    public String delEmployee(long id) {
        Scanner in = new Scanner(System.in);
        String customMessege = "";
        for (int i = 0; i < employeeList.size(); i++) {

            if (employeeList.get(i).getId() == id) {
                System.out.println("Are you sure you wish to remove " + employeeList.get(i).getName() + "\nId #" + employeeList.get(i).getId() + "\t Y/N");

                if (in.next().equalsIgnoreCase("y")) {
                    customMessege = "\nemployee : " + employeeList.get(i).getName() + "\nId #" + employeeList.get(i).getId()
                            + "\nHas been removed";
                    employeeList.remove(i);

                } else {
                    customMessege = "\nemployee : " + employeeList.get(i).getName() + "\nId #" + employeeList.get(i).getId()
                            + "\nHas NOT been removed";
                }
            }
        }
        return customMessege;
    }

    //--------------------------------------------Validate------------------------------------------------
    public static String appendEmail(String email) {
        StringBuilder out = new StringBuilder();
        out.append(email).append("@theFarm.org");
        return out.toString();
    }

    public boolean isOnlyNumbers(String word) {
        char[] chars = word.toCharArray();

        for (char c : chars) {
            if (!Character.isDigit(c) || Character.getNumericValue(c) < 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isOnlyLetters(String w) {
        String word = w.replaceAll("[\\s\\']", "");

        char[] chars = word.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean checkIdExists(long id) {
        boolean exists = false;

        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId() == (id)) {
                exists = true;
            }

        }
        return exists;
    }

    public boolean checkEmailUnique(String eMAddress) {
        boolean unique = true;
        for (Employee e : employeeList) {
            if (e.getEmailAddress().equals(eMAddress)) {
                unique = !unique;
            }
        }

        return unique;
    }

    //Validate date
    public boolean valiDate(int i, int date) {
        boolean valid = true;
        if (i == 0 && (date < 1 || date > 31)) {
            valid = false;
            System.out.println("please enter a valid date");
        } else if (i == 1 && (date < 1 || date > 12)) {
            valid = false;
            System.out.println("please enter a valid date");
        } else if (i == 2 && (date < 1900 || date > 2017)) {
            valid = false;
            System.out.println("please enter a valid date");
        }

        return valid;
    }

    //--------------------------------------------save and load to file------------------------------------------------
    public void saveNewFile() throws FileNotFoundException {

        //File file = new File("empData/empData.txt");
        File fileAgent = new File("empData/agentData.txt");
        File fileContract = new File("empData/conData.txt");
        try {

            FileWriter fwA = new FileWriter(fileAgent.getAbsoluteFile());
            try (BufferedWriter bwA = new BufferedWriter(fwA)) {
                for (Employee e : employeeList) {
                    if (e.getEmployeeType().equals("Agent")) {
                        bwA.write(e.toFileString());
                        bwA.newLine();
                    }

                }
            }

            System.out.println("File " + fileAgent.getName() + " saved");

        } catch (IOException e) {
        }
        try {

            FileWriter fwC = new FileWriter(fileContract.getAbsoluteFile());
            try (BufferedWriter bwC = new BufferedWriter(fwC)) {
                for (Employee e : employeeList) {
                    if (e.getEmployeeType().equals("Contractor")) {
                        bwC.write(e.toFileString());
                        bwC.newLine();
                    }
                }
            }

            System.out.println("File " + fileContract.getName() + " saved");

        } catch (IOException e) {
        }

    }

    public void loadNewFile() throws FileNotFoundException {

        
        File fileAgent = new File("empData/agentData.txt");
        File fileContract = new File("empData/conData.txt");
        String SplitBy = ";";
        //Check if both files exists. If not, create them
        if (!fileAgent.exists()) {
            System.out.println(fileAgent + " not found. Creating file.");
            try {
                FileWriter fwA = new FileWriter(fileAgent.getAbsoluteFile());
                try (BufferedWriter bwA = new BufferedWriter(fwA)) {
                    bwA.write("1;Dummy;08452856;dummy@theFarm.org;12;6;1924;Cheese;true;200");
                }
                System.out.println("File successfully created");
            } catch (IOException e) {

            }
        }
        if (!fileContract.exists()) {
            System.out.println(fileContract + " not found. Creating file.");
            try {
                FileWriter fwC = new FileWriter(fileContract.getAbsoluteFile());
                try (BufferedWriter bwC = new BufferedWriter(fwC)) {
                    bwC.write("2;Dummy2;08451924;dummy@theFarm.org;12;6;1924;Cheese;false;13;50");
                }
                System.out.println("File successfully created");
            } catch (IOException e) {

            }
        }
        try (Scanner in = new Scanner(fileAgent)) {

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] EmployeeDetails = line.split(SplitBy);
                Agent e = new Agent(EmployeeDetails[1], EmployeeDetails[2], EmployeeDetails[3]);
                e.setEmpId(Long.parseLong(EmployeeDetails[0]));
                e.setBirthDate(0, Integer.parseInt(EmployeeDetails[4]));
                e.setBirthDate(1, Integer.parseInt(EmployeeDetails[5]));
                e.setBirthDate(2, Integer.parseInt(EmployeeDetails[6]));
                e.setPassword(EmployeeDetails[7]);
                e.setAdmin(Boolean.parseBoolean(EmployeeDetails[8]));
                e.setSalery(Double.parseDouble(EmployeeDetails[9]));
                employeeList.add(e);
                agentList.add(e);               

            }
            System.out.println("File " + fileAgent.getName() + " read");
            in.close();

        }
        try (Scanner in = new Scanner(fileContract)) {

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] EmployeeDetails = line.split(SplitBy);
                Contractor e = new Contractor(EmployeeDetails[1], EmployeeDetails[2], EmployeeDetails[3]);
                e.setEmpId(Long.parseLong(EmployeeDetails[0]));
                e.setBirthDate(0, Integer.parseInt(EmployeeDetails[4]));
                e.setBirthDate(1, Integer.parseInt(EmployeeDetails[5]));
                e.setBirthDate(2, Integer.parseInt(EmployeeDetails[6]));
                e.setPassword(EmployeeDetails[7]);
                e.setAdmin(Boolean.parseBoolean(EmployeeDetails[8]));
                e.setHoursWorked(Double.parseDouble(EmployeeDetails[9]));
                e.setHourlyRate(Double.parseDouble(EmployeeDetails[10]));
                employeeList.add(e);                
                contractorList.add(e);

            }
            System.out.println("File " + fileContract.getName() + " read");
            in.close();

        }

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.employeeList);
        hash = 29 * hash + Objects.hashCode(this.agentList);
        hash = 29 * hash + Objects.hashCode(this.contractorList);
        hash = 29 * hash + Objects.hashCode(this.mailList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee_store other = (Employee_store) obj;
        if (!Objects.equals(this.employeeList, other.employeeList)) {
            return false;
        }
        if (!Objects.equals(this.agentList, other.agentList)) {
            return false;
        }
        if (!Objects.equals(this.contractorList, other.contractorList)) {
            return false;
        }
        return Objects.equals(this.mailList, other.mailList);
    }

}
