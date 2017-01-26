package ca2_Ronan_Duffy_Michael_Grinnell_Final;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Michael
 */
public class Employee {

    private String name;
    private static long uniqueId = 1;
    private long id;
    private String phoneNum;
    private final int birthDate[] = new int[3];
    private String emailAddress;
    private String password;
    private boolean admin;

    public Employee(String name, String phoneNum, String emailAddress) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.emailAddress = emailAddress;
        id = uniqueId;
        uniqueId++;
        admin = true;

    }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean a) {
        admin = a;
    }

    public long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmpId(long id) {
        this.id = id;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setBirthDate(int i, int date) {
        birthDate[i] = date;
    }

    public int[] getBirthDate() {
        return birthDate;
    }

    public int getDateDay() {
        return birthDate[0];
    }

    public int getDateMonth() {
        return birthDate[1];
    }

    public int getDateYear() {
        return birthDate[2];
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    
    public String getEmployeeType() {
        return "Employee";
    }
    public String getSalary() {
        return "Don't use this";
    }
    @Override
    public String toString() {

        String birthString = birthDate[0] + "//" + birthDate[1] + "//" + birthDate[2];
        return "\n\nEmployee ID :\t " + id + "\nName :\t" + name + " \nBirthdate :\t" + birthString + "\nEmail Address :\t" + emailAddress + "\nPhone Number :\t" + phoneNum;
    }

    public String toStringNoSal() {

        String birthString = birthDate[0] + "//" + birthDate[1] + "//" + birthDate[2];
        return "\n\nEmployee ID :\t " + id + "\nName :\t" + name + " \nBirthdate :\t" + birthString + "\nEmail Address :\t" + emailAddress + "\nPhone Number :\t" + phoneNum;
    }
    
    public String toFileString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            out.append(birthDate[i]).append(";");
        }
        String birthString = out.toString();
        String finalString = id + ";" + name + ";" + phoneNum + ";" + emailAddress + ";" + birthString + password + ";" + admin;
        return finalString;
    }

    public String toListString() {

        String birthString = birthDate[0] + "//" + birthDate[1] + "//" + birthDate[2];

        String result = String.format("%-6s %-30s %-20s %-40s %-40s", id, name, birthString, emailAddress, phoneNum);
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.phoneNum);
        hash = 89 * hash + Arrays.hashCode(this.birthDate);
        hash = 89 * hash + Objects.hashCode(this.emailAddress);
        hash = 89 * hash + Objects.hashCode(this.password);
        hash = 89 * hash + (this.admin ? 1 : 0);
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
        final Employee other = (Employee) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.admin != other.admin) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phoneNum, other.phoneNum)) {
            return false;
        }
        if (!Objects.equals(this.emailAddress, other.emailAddress)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Arrays.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        return true;
    }

}
