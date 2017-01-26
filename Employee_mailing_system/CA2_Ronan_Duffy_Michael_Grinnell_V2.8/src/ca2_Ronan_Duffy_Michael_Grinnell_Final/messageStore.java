package ca2_Ronan_Duffy_Michael_Grinnell_Final;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class messageStore {

    private final ArrayList<Message> list;

    public messageStore() {
        list = new ArrayList<>();
    }

    public void addMessage(Message m) {
        list.add(m);
    }

    public ArrayList<Message> listMessageByKeyword(String key) {
        ArrayList<Message> found = new ArrayList<>();
        list.stream().filter((mail) -> (mail.getSubject().contains(key) || mail.getMessageBody().contains(key))).forEach((mail) -> {
            found.add(mail);
        });
        return found;
    }

    public ArrayList<Message> getMessagesPriority(String priority) {
        ArrayList<Message> found = new ArrayList<>();
        list.stream().filter((m) -> (m.getPriority().equals(priority))).forEach((m) -> {
            found.add(m);
        });
        return found;
    }

    public ArrayList<Message> getAllMessages() {
        return list;

    }

    public ArrayList<Message> getMessagesTo(String userEmail) {
        ArrayList<Message> found = new ArrayList<>();
        list.stream().filter((m) -> (m.getToAddress().contains(userEmail))).forEach((m) -> {
            found.add(m);
        });
        return found;
    }

    public ArrayList<Message> getMessagesDate(ZonedDateTime date) {
        ArrayList<Message> found = new ArrayList<>();
        for (Message m : list) {
            if ((m.getDateDay() == date.getDayOfMonth()) && (m.getDateMonth() == date.getMonthValue()) && (m.getDateYear() == date.getYear()) ) {
                found.add(m);
            }
        }
        return found;
    }

    public ArrayList<Message> getMessagesBetweenDate(ZonedDateTime after, ZonedDateTime before) {
        ArrayList<Message> found = new ArrayList<>();

        for (Message m : list) {
            if (m.getDateSent().isAfter(after) && m.getDateSent().isBefore(before)){
                found.add(m);
            }
        }
        return found;
    }

    public ArrayList<Message> getMessagesFromBetween(String email, ArrayList<Message> f) {
        ArrayList<Message> found = new ArrayList<>();
        f.stream().filter((m) -> (m.getFromAddress().equals(email))).forEach((m) -> {
            found.add(m);
        });
        return found;
    }

    public ArrayList<Message> getMessagesFrom(String userEmail) {
        ArrayList<Message> found = new ArrayList<>();
        list.stream().filter((m) -> (m.getFromAddress().equals(userEmail))).forEach((m) -> {
            found.add(m);
        });
        return found;
    }

    public Message returnMessageFromID(long id) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getMessageID() == id) {
                index = i;

            }
        }
        return list.get(index);
    }

    public void saveNewMessageFile() throws FileNotFoundException {

        File file = new File("mailData/mailData.txt");
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                for (Message m : list) {
                    bw.write(m.toFileString());
                    bw.newLine();
                }
            }
            System.out.println("File " + file.getName() + " saved");
        } catch (IOException e) {
        }
    }

    public void loadNewMessageFile() throws FileNotFoundException {
        File file = new File("mailData/mailData.txt");
        String SplitBy = ";";
        if (!file.exists()) {
            System.out.println(file + " not found. Creating file.");
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write("1000;dummy@theFarm.org;dummy@theFarm.org;Welcome;1;1;2016;High;This is a dummy message since there was no mailData.txt file. Delete this when possible.");
                }
                System.out.println("File successfully created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (Scanner in = new Scanner(file)) {
            ZonedDateTime date;
            long id;
            ArrayList <String> recipients;
            while (in.hasNextLine()) {
                //date = new int[3];
                recipients = new ArrayList<>();
                String line = in.nextLine();
                String[] messageDetails = line.split(SplitBy);
                
                int day = Integer.parseInt(messageDetails[4]);
                int month = Integer.parseInt(messageDetails[5]);
                int year = Integer.parseInt(messageDetails[6]);
                int hour = Integer.parseInt(messageDetails[7]);
                int min = Integer.parseInt(messageDetails[8]);
                date = ZonedDateTime.of(year, month, day, hour, min, 0, 0, ZoneId.of("Europe/Dublin"));
                String[] recipient = messageDetails[2].split("::");
                recipients.addAll(Arrays.asList(recipient));
                
                Message m = new Message(messageDetails[1],recipients, messageDetails[3], date, messageDetails[9], messageDetails[10]);
                m.setDateSent(date);
                m.setMessageID(Long.parseLong(messageDetails[0]));
                list.add(m);
            }
            System.out.println("File " + file.getName() + " read");
            in.close();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.list);
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
        final messageStore other = (messageStore) obj;
        return Objects.equals(this.list, other.list);
    }

}
