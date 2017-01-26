
package ca2_Ronan_Duffy_Michael_Grinnell_Final;


public class Contractor extends Employee {
    private double hoursWorked;
    private double hourlyRate;
    //private boolean admin = false;

    public Contractor(String name, String phoneNum, String emailAddress) {
        super(name, phoneNum, emailAddress);
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
    public double getWages(){
        return hourlyRate * hoursWorked;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    @Override
    public String getSalary() {
        String roundSal= String.format("%.2f", (hoursWorked * hourlyRate));
        return roundSal;
    }
    
    
    @Override
    public String toString() {
        String roundHours = String.format("%.2f", hoursWorked);
        String roundRate = String.format("%.2f", hourlyRate);
        return "Contractor\n" + super.toString() + "\nHours Worked : " + roundHours + "\nHourly Rate : " + roundRate + "\nTotal Wages : " + (hoursWorked * hourlyRate);
    }
        
    
        
    @Override
     public String toListString() {
         return super.toListString() + "Contractor";
     }
     
    @Override
    public String getEmployeeType() {
        return "Contractor";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + hoursWorked + ";" + hourlyRate;
    }
    
    
}
