/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca2_Ronan_Duffy_Michael_Grinnell_Final;

/**
 *
 * @author Michael
 */
public class Agent extends Employee {

    double salery;
    //private boolean admin = true;
    public Agent(String name, String phoneNum, String emailAddress) {
        super(name, phoneNum, emailAddress);
        salery = 0;
    }
    
    public Agent(String name, String phoneNum, String emailAddress, double sal) {
        super(name, phoneNum, emailAddress);
        salery = sal;
    }

    public double getWages() {
        return salery;
    }

    public void setSalery(double salery) {
        this.salery = salery;
    }
    @Override
    public String getSalary() {
        String roundSalery = String.format("%.2f", salery);
        return roundSalery;
    }
    @Override
    public String toString() {
        String roundSalery = String.format("%.2f", salery);
        return "Agent\n" + super.toString() + "\nSalery : " + roundSalery;
    }
        

    
    @Override
    public String toListString() {
        return super.toListString() + "Agent";
    }
     
    @Override
    public String getEmployeeType() {
        return "Agent";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + ";" + salery;
    }
    
    
}
