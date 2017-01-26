This is a simple text based Employee mailing system.
This application Stores Information about employees and the messages they send.
Any Employee can login using thier employee id and password and can then send and recieve emails.
An admin can add, update and delete all employee info excluding id which is unique and automaticly generated.
All iinformation is saved to two text files, one for employee info and the other for mail.

You can log in as any Employee using there ID and password, for simplicity all paswords are set to Cheese by default.(would usually be some combination of date and userName)
There are administrators and users.
For simplicity all Agents are admins and all contractors are users.

admin login: ID=4 , password=Cheese.
user Login: ID=3, password=Cheese.

An admin can add edit and delete employees as well as view all mail. they can also do everything a user can
A user has restricted acsess they cannot add edit or delete employees, they can only see mail that involves them.
You will be presented with a list of options to choose from to help navigate the application.


*Known Issues*
-A non admin can see employee wages when viewing single employee.
-Date time seconds are not recorded when mailData file saves and seconds for viewing messages are displayed as 0.
-zoned date time is not used for employee birthday, Instead an array of 3 ints is used(zdt is used for everthing else).
-If file contains no data aplication will not load(file must be deleted or have data entered to fix issue).


*Changes for another version*
-The main as well as mailstore and employeestore are too long they should be broken down into more classes
-user should be able to search inbox and outbox
-users should be able to save mail list for future use
-incorperate a timeline so you can keep track of when an employee started and how much they are paid in different months etc.
-only certain employees can be admins not all agents and no contractors


Functinality
*****************
1) Create and add a new message to the messageStore.
2) Print all message details from a list of messages.
3) Return the details of a message selected by message ID.
4) Return a list of all messages sent from a user-defined email address.
5) Return a list of all messages that contain a user-defined substring in the message
subject or message body. (i.e. search for a keyword)
6) Return a list of all messages sent to a user-defined email address.
7) Return a list of all messages with a user-defined priority.
8) Return a list of all messages sent on a user-defined date. (day/month/year)
9) Return a list of all messages sorted by date.
10)Return a list of all messages sorted by message ID.
11)Return a list of all messages sent between two user-defined dates (inclusive).
12)Return a list of all messages sent in the last 10 days.
13)Create and add a new employee to the system. The employeeId must be unique
and must be automatically generated. The email address must also be unique.
14)Return a list of all employees.
15) Print details of all employees supplied in a list.
16)Return a list of employees selected based on a user-defined name.
17) Edit, delete, and print the details of any employee.
18) Login facility (login using employee ID and password)
19) Return a list of all messages sent by a user-defined email address, which were
sent between two user-defined dates (inclusive).
20) Load all employee and message details from a text file when the program
starts. Save all data to a text file when the program closes.
21) Create a number of Agent and Contractor employees.
22) Return a list of all employees (both Agent and Contractors)
23) Return a list of employees who are Agents (but not Contractors)
24) Return the total salary amount for all employees for this month.