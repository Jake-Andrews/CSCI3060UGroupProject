# CSCI3060UGroupProject
How to use: 

Clone the project into a folder. 

Open CSCI3060UGroupProject in vscode (I'm sure it will work in another editor, but vscode was used).

Open a terminal and navigate to the bnb folder. 

Command: mvn install 

Command: mvn clean compile assembly:single (Similar commands will work, this is what I used)

Command: mvn clean test

The jar far has been created, navigate to the target folder. 

Command: java -jar bnb-1.0-SNAPSHOT-jar-with-dependencies.jar availablerentalsfile.txt useraccounts.txt



A basic run of the program: 

Welcome, please login to use the program. 

To exit type exit, To start type login: (user types "login")

Please enter a valid username: (user gives a valid username)

You have been logged in!

Please enter a command!

For a list of commands type 'help': (user types search)

Please enter the city where the unit you are renting is: 

(user types Whitby)

Pease enter a valid price to rent the unit at (max is 999.99):

(user types 900)

Please enter the number of bedrooms (max is 9):

(user types 1)

rentID: 113142f7

Username: USER

City: Whitby

numberOfBedrooms: 2

rentalPricePerNight: 300.00

rentalFlag: false

numberOfNightsRemaining: 0


Daily transaction file string: 04_USER___________AA_113142f7_Whitby____________________2_300.00_00

Please enter a command!

For a list of commands type 'help': (user types logout)

Daily transaction file string: 00_USER___________AA____________________________________0_0.00___00

Printing Daily Transactions:You have been successfully logged out!

Welcome, please login to use the program.

To exit type exit, To start type login: (user types exit)

Thank you for using our program!
