package ca2_Ronan_Duffy_Michael_Grinnell_Final;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Message {

    private String fromAddress;
    private ArrayList <String> toAddress;
    private String subject;
    private ZonedDateTime dateSent ;
    private String priority;
    private String messageBody;
    private static long IDSeed = 1000;
    private long messageID;

    public Message(String from, ArrayList <String> to, String sub, ZonedDateTime date, String pri, String body) {
        fromAddress = from;
        toAddress = to;
        subject = sub;
        dateSent = date;
        priority = pri;
        messageBody = body;
        messageID = IDSeed;
        IDSeed++;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public void setDateSent(ZonedDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public void setFromAddress(String f) {
        fromAddress = f;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public ZonedDateTime getDateSent() {
        return dateSent;
    }

    public int getDateDay() {
        return dateSent.getDayOfMonth();
    }

    public int getDateMonth() {
        return dateSent.getMonthValue();
    }

    public int getDateYear() {
        return dateSent.getYear();
    }
    public int getDateHour() {
        return dateSent.getHour();
    }
    public int getDateMin() {
        return dateSent.getMinute();
    }

    public void setToAddress(String to) {
        toAddress.add(to);
    }

    public ArrayList <String> getToAddress() {        
        return toAddress;
    }
    public String getToFirstAddress() {        
        return toAddress.get(0);
    }

    public void setSubject(String sub) {
        subject = sub;
    }

    public String getSubject() {
        return subject;
    }

    public void setPriority(String p) {
        priority = p;
    }

    public String getPriority() {
        return priority;
    }

    public void setMessageBody(String m) {
        messageBody = m;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public long getMessageID() {
        return messageID;
    }

    public String ViewMessage() {
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME ;
        String date = dtf.format(dateSent);
        String result = String.format("%-40s %-40s %-40s %-40s", "From : " + fromAddress, "To : " + toAddress, "\nPriority : " + priority, "Date : " + date + "\nSubject :" + subject + "\n\nMessage Content:\n" + messageBody + "\n");
        return result;
    }

    public String toInboxString() {
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME ;
        String date = dtf.format(dateSent);

        
        String result = String.format("%-40s %-40s %-10s", fromAddress, subject, date);
        return result;
    }

    public String toOutboxString() {
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME ;
        String date = dtf.format(dateSent);        

        String recipients = toAddress.toString();
        if (toAddress.size()> 2){
            recipients = toAddress.get(0)+ "," + toAddress.get(1) +"...";
        }
        
        String result = String.format("%-40s %-40s %-10s", recipients, subject, date);
        return result;
    }

    public String toAllString() {
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME ;
        String date = dtf.format(dateSent);       

                String recipients = toAddress.toString();
        if (toAddress.size()> 2){
            recipients = "[" +toAddress.get(0)+ "," + toAddress.get(1) +"...]";
        }
        String result = String.format("%-40s %-40s %-40s %-10s", fromAddress, recipients, subject, date);
        return result;
    }

    public String toFileString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < toAddress.size(); i++) {
            out.append(toAddress.get(i)).append("::");
        }

        String recipients = out.toString();
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME ;
        String date = dtf.format(dateSent);
        return messageID + ";" + fromAddress + ";" + recipients + ";" + subject + ";" + getDateDay() + ";" + getDateMonth()  + ";" + getDateYear() + ";" + getDateHour() + ";" + getDateMin() + ";" + priority + ";" + messageBody;
    }

}
