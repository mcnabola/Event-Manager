import java.io.*;
import javax.swing.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
/**
 *Public Class Main: This is the Driver of the program
 *
 *This is a Event Manager for a charitable organisation that has to manage facilites and bookings by certain users.
 */
public class main
{
	final static String userFileName="Users.txt";
	final static String facilityFileName="Facilities.txt";
	final static String bookingFileName="Bookings.txt";
	private static int currentUserId;
 	private static int currentUserType;
	private static ArrayList<User> users=new ArrayList<User>();
	private static ArrayList<Facility> facilities=new ArrayList<Facility>();
	private static ArrayList<Booking> bookings=new ArrayList<Booking>();
		
	public static void main(String [] args)
	{
		restore();
		String email=menuBox("Enter Email");
			String password=menuBox("Enter Password");
			boolean testing=loginMethod(email,password);
			if (testing&&currentUserType==1)  
			{
				adminMenu();
			
			}		
				else if (testing&&currentUserType==2)
				{
					userMenu();
					
				}
				else System.exit(0);	
	}	 
	
	/**
	 *
	 *A method which is called when the application opens.
	 *Creates objects based on pre-existing files in the system.
	 *Adds each object into the appropriate Arraylist.
	 *No params or return.
	 */	
	public static void restore()
	{
		try
		{
		String filename = userFileName;
		Scanner in;
		String[] fileElements;
		String lineFromFile = "";
		File aFile;
		User aUser;
		Facility aFacility;
		Booking aBooking;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		aFile = new File(filename);
		if (aFile.exists())
		{ 	
			in = new Scanner(aFile);
			while (in.hasNext())
			{
				lineFromFile = in.nextLine();
				fileElements = lineFromFile.split(",");
				int userId 	 = Integer.parseInt(fileElements[0]);
				String email = fileElements[1];
				String pass  = fileElements[2];
				int userType = Integer.parseInt(fileElements[3]);
				aUser = new User(userId, email, pass, userType);
				users.add(aUser);
			}
			in.close();
		}
		filename = facilityFileName;
		aFile = new File(filename);
		if (aFile.exists())
		{
			in = new Scanner(aFile);
			while (in.hasNext())
			{
				lineFromFile 		= in.nextLine();
				fileElements 		= lineFromFile.split(",");
				int facilityId 		= Integer.parseInt(fileElements[0]);
				String facilityName 	= fileElements[1];
				Double pricePerHour  	= Double.parseDouble(fileElements[2]);
				if (fileElements.length == 4)
					aFacility = new Facility(facilityId, facilityName, pricePerHour);
				else
				{
					String decomDate = fileElements[3];
					LocalDate temp = LocalDate.parse(decomDate, formatter);
					if (temp.isBefore(LocalDate.now()))
					{
						removeLine(filename, fileElements[1], 1);
						aFacility = new Facility(facilityId, facilityName, pricePerHour);
						writeFile(aFacility.facilityToString(), filename);
					}
					aFacility = new Facility(facilityId, facilityName, pricePerHour, decomDate);
				}
				facilities.add(aFacility);
			}
			in.close();
		}
		
		
		filename = bookingFileName;
		aFile = new File(filename);
		if (aFile.exists())
		{
			in = new Scanner(aFile);
			while (in.hasNext())
			{
				lineFromFile 		= in.nextLine();
				fileElements 		= lineFromFile.split(",");
				int bookingId  		= Integer.parseInt(fileElements[0]);
				int facilityId 		= Integer.parseInt(fileElements[1]);
				int userId 	   		= Integer.parseInt(fileElements[2]);
				String bookingDate 	= fileElements[3];
				int bookingSlot     = Integer.parseInt(fileElements[4]);
				boolean paid 		= Boolean.parseBoolean(fileElements[5]);
				
				LocalDate temp2 = LocalDate.parse(bookingDate, formatter);
				if (temp2.isBefore(LocalDate.now()))
					removeLine(filename, fileElements[0], 0);
				else
				{	
					aBooking = new Booking(bookingId, facilityId, userId, bookingDate, bookingSlot, paid);
					bookings.add(aBooking);
				}
			}
			in.close();
		}
		}
		catch(Exception e)
		{}
	}
	
	/**
	 *A JOptionPane menu which allows the user to enter a word, digits or characters which are returned as a string.
	 *
	 *@param options Enters a string to prompt a response from the user.   
	 *@return   Whatever the user enters in the JOptionPane is returned as a string
	 *
	 */	  
	public static String menuBox(String options)
	{
		String input=JOptionPane.showInputDialog(null,options);;
			if(!(input != null))
			{
				int check = JOptionPane.showConfirmDialog (null, "Do you want to exit?","Menu",JOptionPane.YES_NO_OPTION);
				if (check == JOptionPane.YES_OPTION)
					System.exit(0);
				else
					input=menuBox(options);
			}
			else if(input.equals(""))
			{
				outputBoxs("Error: no String entered");
				input = menuBox(options);
			}
		return input;
	}

	/**
	  *A JOptionPane menu which allows the user to enter digits which are returned as a double.
	  *
	  *@param options Type is a string that represesents a the type of info you want from the user. 
	  *@return   Whatever the user enters is returned as a double
	  *
	  */	  
	public static double menuBoxDouble(String options)
	{
		String input="";
		double inputAsDouble=0;
		try
		{
			input=JOptionPane.showInputDialog(null,options);
			inputAsDouble=Double.parseDouble(input);
			return inputAsDouble;
		}
		catch(NumberFormatException e)
		{
		outputBoxs("Error: Input is not a number entered");
		return menuBoxDouble(options);
		}
	}
	/**
	  *Method thats called from main.
	  *Administrator is presented with a JOptionPaneBox to create a user and enter their email.
	  *Writes the info of new user to Users.txt file and added to appropriate arraylist
	  *No params or return
	  *
	  */		
	public static void createNewUser()
	{
		boolean found 	= true;
		String email	= menuBox("Please enter an email:");
		while (!validEmail(email) || found)
		{
			found = false;
			if(!validEmail(email))
				email = menuBox("Please enter a valid email:");
			for (int i=0;i<users.size();i++)
			{
				if (users.get(i).getEmail().equals(email))
					found = true;
			}
			if (found)
				email = menuBox("Email already registered.Please enter another email:");
		}	
		
		int userId	 =users.size()+1;
		User newUser =new User(userId,email);
		users.add(newUser);
		String info  =(newUser.userToString());
		writeFile(info,userFileName);
	}

	/**
	 *Writes the input given to the file specified
	 *
	 *@param input Input of string type of what the user wants to write to file.
	 *@param fileName Filename of where the input is to be written to file.
	 *
	 */		
	public static void writeFile(String input, String fileName) 
    {
		try
	    {
            FileWriter aFileWriter = new FileWriter(fileName,true);
            PrintWriter out = new PrintWriter(aFileWriter);
	    	out.print(input);
            out.println();
            out.close();
            aFileWriter.close();		
	    }
	    catch(Exception e)
		{}
    }
	
	/**
	 *A JOptionPane drop down menu which returns the index in the array of which option the user selects
	 *
	 *@param options Array of String is passed as options for the user can choose in a Button styled menu.
	 *@param whatYouWantItToSay String of what you want the JOptionPane to print to prompt user selection
	 *@return  returns a int which is the index in the array which the user selected
	 *
	 */	
	public static int optionBoxs(String[] options,String whatYouWantItToSay)
	{
        int result = JOptionPane.showOptionDialog(null, whatYouWantItToSay, "League Manager", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return result;
	}
	/**
	 * Searches a file for a given string in a given position and regenerates the file without the line
	 * Copies every line from the file except the specified line and writes them to a temp file
	 * The original file is then deleted and the temp file is renamed to the name of the original file
	 *
	 *@param filename File name of the selcted file to remove a line from
	 *@param str String you wish to locate in the file that you wish to delete.
	 *@param pos Position in the file line you wish to search for String str
	 *@throws IOException Removing a line from a file.
	 */
	public static void removeLine(String filename, String str, int pos)throws IOException
	{  
		File aFile 	  = new File(filename);
		String lineFromFile = "";
		String fileOutput = "";
		Scanner in;
		FileWriter out;
		String[] fileElements;
		if (aFile.exists())
		{
			in = new Scanner(aFile);
			while (in.hasNext())
			{
				lineFromFile = in.nextLine();
				fileElements = lineFromFile.split(",");
	 			if (!fileElements[pos].equals(str))
				{
					fileOutput += lineFromFile + "\n";
				}
			}
			in.close();
			out = new FileWriter(filename);
			out.close();
		}
	}
	
	/**
	 * Checks if an inputted string is a valid email address by comparing it to
	 * a simple email pattern
	 * 
	 * @param email String of the user email
	 * @return   Returns a true boolean value if the email matches the pattern and 
	 * returns false if it does not
	 */ 
	public static boolean validEmail(String email)
	{
		boolean isValid = false; 
		String pattern  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+[.]([a-zA-Z]{2,})");
		
		if (email.matches(pattern))
			isValid = true;
		
		return isValid;
	}
	/**
	 * Simple login method that scans user arrayList for given parameters. User is given 3 attempts
	 * to login before the program is closed.
	 *
	 *@param email searches the users arraylist and calls the .getEmail() method
	 *        and compares it to the given email.
	 *@param password If the given email belongs to a user in the arraylist, the getPassword()
	 *		method is called on that user and is compared to the given password.
	 *@return Returns a true boolean if both params match. Else returns false and exits the program.
	 *
	 */		
	public static boolean loginMethod(String email, String password) 
	{
		boolean found = false;
		int loginAttempts = 3;
		try
		{
			while(!found)
			{
				for (int i=0;i<users.size();i++)
				{
					if (users.get(i).getEmail().equalsIgnoreCase(email) && users.get(i).getPassword().equals(password))
					{
						found = true;
						currentUserId 	= users.get(i).getUserId();
						currentUserType = users.get(i).getUserType();
					}	
				}
				if (!found)
				{
					loginAttempts--;
					if (loginAttempts == 0)
					{
						outputBoxs("No attempts remaining");
						break;
					}
					outputBoxs("Incorrect login details.\n" + loginAttempts + " attempts remaining:");
					email	 = menuBox("Enter email");
					password = menuBox("Enter password");
				}
				else
					outputBoxs("Successfully logged in as " + email);
			}
		}
		catch(Exception e)
		{}
		return found;
	}
	/**
	 *checks whether a string passed into the method is a valid date in the form dd/mm/yyyy
	 *  
	 *@param date checker to see whether or not date is in the correct format or not
	 *@return  Method reutrns a boolean value whether the date is valid or not.
	 *@throws IOException Try & Catch
	 */		
	public static boolean isValidDate(String date)throws IOException
	{ 
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate test = LocalDate.parse(date, formatter);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	/**
	 *Method for administrator to create a new facility
	 *
	 *Asks user to create and name facility, checks if the facility already exists.
	 *User then inputs price per hour and date it's decomissioned till, if any.
	 *Then adds the new facility to facilities arraylist.
	 *  
	 */		
	public static void createNewFacility()
	{
		String date;
		int facilityId;
		int temp = 0;
		boolean found = true;
		boolean check = false;
		try
		{
			for (int i=0;i<facilities.size();i++)
			{
				if (facilities.get(i).getFacilityId() > temp)
					temp = facilities.get(i).getFacilityId();
			}
			facilityId = temp + 1;
			
			String facilityName = menuBox("Please enter a facilityName:");
			while (found)
			{
				found = false;
				for (int j=0;j<facilities.size();j++)
				{
					if (facilities.get(j).getFacilityName().equals(facilityName))
						found = true;
				}
				if (found)
					facilityName = menuBox("Facility already exists.\nPlease enter another facility name:");
			}	
			
			double pricePerHour 	= menuBoxDouble("please enter a price per hour:");
			Facility aFacility = new Facility(facilityId, facilityName, pricePerHour);
			int decommissionChoice 	= JOptionPane.showConfirmDialog (null, "Do you want to decommission this facility?","Facility",JOptionPane.YES_NO_OPTION);
			
			
			if (decommissionChoice == JOptionPane.YES_OPTION)
			{
				date = menuBox("Please enter a date:");
				check = isValidDate(date);
				while(check == false)
				{
					date = menuBox("Please enter a valid date:\nFormat(DD/MM/YYYY)");
					check = isValidDate(date);
				}
				aFacility.setDecommissionedUntil(date);
			}
			facilities.add(aFacility);
			String info = aFacility.facilityToString();
			writeFile(info,facilityFileName);
		}
		catch(Exception e)
		{System.out.println(e.getMessage());}
	}
	/**
	 *Removes a facility from the facility array and also removes it from the facility file. A facility cannot be removed if it has a booking.
	 *
	 */		
	public static void removeFacility()
	{
		try
		{
			if(facilities.size()==0)
			{
				outputBoxs("There are no facilites to remove.");
			}
			else
			{
		String [] listOfFacilities=new String[facilities.size()];
		int facilityId=0;
		int positionInArrayList=0;
		for(int i=0;i<listOfFacilities.length;i++)
		{
			listOfFacilities[i]=facilities.get(i).getFacilityName();
		}
		String selection=dropDown(listOfFacilities,"Please select a facility to remove:");
		for(int i=0;i<listOfFacilities.length;i++)
		{
			if(facilities.get(i).getFacilityName().equals(selection))
			{
				facilityId=facilities.get(i).getFacilityId();
				positionInArrayList=i;
			}
		}
		boolean availability=true;
		for(int i=0;i<bookings.size();i++)
		{
			if(bookings.get(i).getFacilityId()==facilityId)
				availability=false;
		}
		if(!(availability))
			outputBoxs("This facility has a booking, cannot delete.");
		else
			facilities.remove(positionInArrayList);
			removeLine(facilityFileName,selection,1);
		}
		}
		catch(Exception e)
		{}
	}
	
	/**
	 *Drop down menu in which the user can choose an option.
	 *
	 *@param options An array titled options is the list of options the user can choose from in the dropdown.
	 *@param dialogText This is a string that contains the text to be displayed to the user before picking from the dropdown.
	 *@return   The choice in which the user selects is returned as a string
	 *
	 */
	public static String dropDown(String[] options, String dialogText)
	{
		String selection = "";
		if (options.length != 0)
		{
			selection = (String)JOptionPane.showInputDialog(null, dialogText, "Input", 1, null, options, options[0]);
		}
		return selection;
	}

	/**
	 *Displays a message in a JOptionPane gui
	 *
	 *@param output String contains the message to be displayed
	 *
	 */		
	public static void outputBoxs(String output)
	{
	     JOptionPane.showMessageDialog(null, output);	
	}
	/**
	 *Gui for the administrator to navigate through commands
	 *admin selects an option which is then passed through switch statements to call the correct method
	 *
	 */	
	public static void adminMenu()
	{
		try
		{
		String [] initialOptions = { "Register User", "Facility Menu", "Record Payments", "View Account Statements" };
		String [] subOptions	 = {"Add Facility","View Facility Availability", "View Facility Bookings", "Remove Facility", "Decommission Facility", "Recommission Facility","Make Booking","Return to main menu"};
	        boolean main = true;
		int x = 0;
		while(main && x==0||x==1||x==2||x==3)   
		{	
		    x = optionBoxs(initialOptions,"Choose an option");
		    int y = 0;
		    int z = 0;
		
		    switch (x)
		    {
			    case 0: createNewUser();
		        break;
			    case 1: y = optionBoxs(subOptions,"Choose an option");
					switch (y)
					{
						case 0: createNewFacility();
						break;
						case 1: viewFacility(0);
						break;
						case 2:	viewFacility(1);		 
						break;
						case 3: removeFacility();
						break;
						case 4: decommissionFacility();
						break;
						case 5: recommissionFacility();
						break;
						case 6: makeBooking();
						break;
						case 7: 
						break;
					}
                break;
				case 2: recordPayments();
				break;
				case 3: String statementOutput = ""; 
                                        for (int i = 0 ; i < users.size();i++)
                                        { int userid = users.get(i).getUserId();
				          statementOutput+= accountStatement(userid)+"\n";
					} outputBoxs(statementOutput);						
				break;
			}
		}
		} 
		catch(Exception E)
		{}
	}
	/**
	 *Gui for the User to navigate through commands. User selects an option which is then passed through switch statements to call the correct method
	 *
	 */	
	public static void userMenu()
	{
		String [] initialOptions = { "View Bookings", "View Account Statement"};
	    boolean main = true;
		int x = 0;
		while (main && x==0||x==1||x==2||x==3)  
		{	
		    x = optionBoxs(initialOptions,"Choose an option");
		    int y = 0;
		    switch (x)
		    {
			    case 0: viewBookings();
		        break;
			    case 1: outputBoxs(accountStatement(currentUserId));
                break;				
			}
		}	
	}
	/**
	 *Allows the current user to view their own booking. Uses the global variable of the currentUserId to distinguish bookings.
	 *The users bookings are displayed to a output box.
	 *
	 */	
	public static void viewBookings()
	{
		ArrayList<Booking> personalBookings=new ArrayList<Booking>();
		ArrayList<String> info=new ArrayList<String>();
		String singleBooking;
		String facilityName;
		for(int i=0;i<bookings.size();i++)
		{
			if(bookings.get(i).getUserId()==currentUserId)
				personalBookings.add(bookings.get(i));
		}
		String out="";
		if (personalBookings.size() != 0)
		{
		    for(int i=0;i<personalBookings.size();i++)// have to get the facility name
		    {
                    String[] elements = personalBookings.get(i).bookingToString().split(",");
out+=("Booking ID: "+elements[0]+"   Facility ID: "+elements[1]+"   Booking Date: "+elements[3]+"   Slot: "+elements[4]+"   Paid Status: " +elements[5]+"\n");	
		    }
		}
		else
			out = "There are currently no bookings at this time.";
		outputBoxs(out);
	}
	
	/**  
	 * Method that takes in a users id and returns the amount owed by that user.
	 *
	 *@param userId this is the ID number of the user 
	 *@return The collected data of a users Id, their email address and the total amount due for that user.
	 **/	
	 public static String accountStatement(int userId) 
	 {
	    double amountDue = 0.0;
		String userEmail = "";
		Currency curr = Currency.getInstance("EUR");						 
 		String symbol = curr.getSymbol();
		for (int j = 0;j<users.size();j++)
		{
			if (users.get(j).getUserId() == userId)
				userEmail = users.get(j).getEmail();
		}
        
		if (bookings.size() != 0)
		{
			for (int i = 0;i<bookings.size();i++)
			{
				if (bookings.get(i).getUserId() == userId)
				{
					int facilityId = bookings.get(i).getFacilityId();
					boolean payment = bookings.get(i).getPaymentStatus();
					if (!payment)
					{
						for (int j = 0;j<facilities.size();j++)
						{
							if (facilities.get(j).getFacilityId() == facilityId)
							{
								amountDue += facilities.get(j).getPricePerHour();
							}
						}
					}
				}
			}
		}   
          String statement = "User ID: "+userId + "    Email: " + userEmail + "    Amount Due: "+symbol+ ""+ amountDue;									
	    return statement;
     }
	
 
	
	/**
	 *Shows a user which slots are booked for a certain facility on a certain date
	 *The user is asked to choose a facility from a dropdown and also asked to input a date to view the bookings
	 *The slots which are booked for the facility on the date are displayed to screen
	 *
	 *@param type This is a int 0 or 1 and depending on where this method is called in the adminMenu() 0 or 1 will be passed through which will display availabilies of a facility or the bookings.
	 *@throws IOException Writing to Facilities file.
	 */	
	public static void viewFacility(int type)throws IOException 
	{ 
		if(facilities.size()==0)
		{
			outputBoxs("There are no facilities.");
		}
		else
		{
			String[] facilitiesName = new String[facilities.size()];
			for (int i = 0; i < facilities.size();i++)
			{
				facilitiesName[i] = facilities.get(i).getFacilityName();
			}
			String choice = dropDown(facilitiesName, "Choose a facility to view availability for.");
			int facilityId=0;
			for (int i = 0; i< facilities.size();i++)
			{
				if (facilities.get(i).getFacilityName().equals(choice))
				{
					facilityId=facilities.get(i).getFacilityId();
				}
			}
			String d1 = menuBox("Enter the first date you want to view the dates between.\nIn the format dd/MM/yyyy");
			String d2 = menuBox("Enter the second date to view the availability between.\nIn the format dd/MM/yyyy");
			try{
				boolean check = isValidDate(d1);
				while(check == false)
				{
					d1 = menuBox("Please enter a valid date for the first date again:\nFormat(DD/MM/YYYY)");
					check = isValidDate(d1);
				}
		
				check = isValidDate(d2);
				while(check == false)
				{
					d2 = menuBox("Please enter a valid date for the second date again:\nFormat(DD/MM/YYYY)");
					check = isValidDate(d2);
				}
			}
			catch(Exception e){}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate date = LocalDate.parse(d1, formatter);
			date = date.minusDays(1);
			LocalDate date2 = LocalDate.parse(d2, formatter);
			
			int firstDate=date.getYear();
			int secondDates=date2.getYear();
			LocalDate secondDate = LocalDate.now();
			if(firstDate==secondDates)
			{
			 
				firstDate=date.getDayOfYear();
				secondDates=date2.getDayOfYear();
				int difference= secondDates-firstDate;
			 
		        // want to limit to 30 days/30 items to drop down otherwise this affects the drop down reliability.    
				if (difference<=30&&difference>-1)
				{
					String[] names = new String[difference];
					LocalDate temp = date;
					String output;
					int count = 0;
					for(int i=0;i<difference;i++){
						temp = temp.plusDays(1);
						output = temp.format(formatter);
						names[count] = output;
						count++;
					}
					// user chooses date from the dropDown
					String dateChoice = dropDown(names, "Choose a date to view");
					secondDate = LocalDate.parse(dateChoice,formatter);
				}
				else{// reason:: JOptionPane pane cant handle displaying dates in a drop down over a very large period - so its been arbitrarily limited
                     outputBoxs("This feature only allows you to view two dates within the duration of a month.");
					 return;
				}	
		     }
			 
		     else{	 
					outputBoxs("You can only search between dates in the same year.");
					return;
		     }
			 

			boolean available=true;
			ArrayList<Integer> slotNumberForBookingsOfDate=new ArrayList<Integer>();
			for(int i=0;i<bookings.size();i++)
			{
				if(bookings.get(i).getFacilityId()==facilityId)
				{
					if(bookings.get(i).getBookingDate().equals(secondDate))
					{
						available=false;
						slotNumberForBookingsOfDate.add(bookings.get(i).getBookingSlot());
					}
				}
			}
			if(available==true)
			{
				outputBoxs("There are no bookings for this date.");
			}
			else
			{ 
				date = date.plusDays(1);
				String temp3 = date.format(formatter);
				String result="The current slots on the "+temp3+" are booked:"+"\n";
				String avail = "Current free slots on " + temp3 + " are available\n";
				for (int i = 1 ; i< 10;i++)
				{
					if (slotNumberForBookingsOfDate.contains(i))
					{
						result+=("Slot "+i+"\n");
					}
					else{
						avail+=("Slot "+i+"\n");
					}
				}	 
				if (type == 0)
					outputBoxs(avail);
				else
					outputBoxs(result);
			}
		}
	}
	
	/**
	 *The user has the ability to decommission a facility that is not decommissioned already
	 *The user is asked to choose a facility to decommission and enter a date for the facility to be decommissioned
	 *The facility is decommissioned if it has no bookings and already isn't decommissioned. Then the availability of the entry in the file is changed.
	 *  
	 *@throws IOException Writing to Facilities File.
	 */	
	public static void decommissionFacility()throws IOException
	{ 
	 	if(facilities.size()==0)
	{
		outputBoxs("There are no facilities.");
	}
	else
	{
	 String[] facilitiesName = new String[facilities.size()];
	 int facilityId=0;
	 boolean available=true;
	 for (int i = 0; i < facilities.size();i++)
	 {
		 facilitiesName[i] = facilities.get(i).getFacilityName();
	 }
	 String choice = dropDown(facilitiesName, "Choose a facility to decommission:");
	 for (int i = 0; i< facilities.size();i++)
	  {
		 if (facilities.get(i).getFacilityName().equals(choice))
		 {
		 facilityId=facilities.get(i).getFacilityId();
		 }
	 }
	 for(int i=0;i<bookings.size();i++)
	 {
		 if(bookings.get(i).getFacilityId()==facilityId)
			 available=false;
	 }
	 if(available)
	 {
	 for (int i = 0; i< facilities.size();i++)
	  {
		 if (facilities.get(i).getFacilityName().equals(choice))
		 {
		 	String decommissionedToDate=menuBox("Please enter a date in the form DD/MM/YYYY:");
			boolean validDate=isValidDate(decommissionedToDate);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 		LocalDate dDate=LocalDate.parse(decommissionedToDate,formatter);
			if(dDate.isBefore(LocalDate.now()))
			 {
				 outputBoxs("This date is in the past, cannot decommission");
			 }
			 else
			 {
			if(validDate)
			{
			facilities.get(i).setDecommissionedUntil(decommissionedToDate);
			removeLine(facilityFileName,choice,1);
			writeFile(facilities.get(i).facilityToString(),facilityFileName);
			}
			else
			{
				outputBoxs("Incorrect date format:Please enter a date in the form dd/MM/yyyy");
			}
			 }
		 }
	 }
	 }
	 else
	 {
		 outputBoxs("There is a booking for this Facility, cannot decommission");
	 }
	 
	}
	}
	/**
	 *The user has the ability to recommission a facility that has previously been decommissioned
	 *The user is asked to choose a facility to recommission 
	 *The facility is recommissioned if it was previously decommissioned. Then the availability of the entry in the file is changed.
	 *  
	 *@throws IOException Writing to Facilities file.
	 */	
	public static void recommissionFacility()throws IOException
	{
	if(facilities.size()==0)
	{
		outputBoxs("There is no facilities.");
	}
	else
	{
	 String[] facilitiesName = new String[facilities.size()];
	 int facilityId=0;
	 boolean finished=false;
	 for (int i = 0; i < facilities.size();i++)
	 {
		 facilitiesName[i] = facilities.get(i).getFacilityName();
	 }
	 String choice = dropDown(facilitiesName, "Choose a facility to recommission.");
	 for (int i = 0; i< facilities.size();i++)
	  {
		 if (facilities.get(i).getFacilityName().equals(choice)&&(facilities.get(i).getAvailability()==false))
		 {
			facilities.get(i).setDecommissionedUntil("10/10/1900");
			finished=true;
			removeLine(facilityFileName,choice,1);
			writeFile(facilities.get(i).facilityToString(),facilityFileName);
		 }
	 }
	 if(!finished)
	 {
		 outputBoxs("This facility is not decommissioned");
	 }
	}
	}
	/**
	 *It allows a user to make a booking for a certain facility for a certain date.
	 *User is requested to choose a facility and enter a date to make the booking for and also choose the slot.
	 *Adds the booking to the booking array list and also writes the information to the file
	 *
	 *@throws IOException Writing to Bookings file
	 */		
	public static void makeBooking()throws IOException
	{ 
	if(facilities.size()==0)
	{
		outputBoxs("There is no facilities.");
	}
	else
	{
	ArrayList<Integer> slots=new ArrayList<Integer>();
	int slotNumber=0;
	int bookingId=bookings.size()+1;
	String[] facilitiesName = new String[facilities.size()];
	 for (int i = 0; i < facilities.size();i++)
	 {
		 facilitiesName[i] = facilities.get(i).getFacilityName();
	 }
	 String choice = dropDown(facilitiesName, "Choose a facility to make a booking for");
	 int facilityId=0;
	 for (int i = 0; i< facilities.size();i++)
	  {
		 if (facilities.get(i).getFacilityName().equals(choice))
		 {
		 facilityId=facilities.get(i).getFacilityId();
		 }
	 }
	 boolean validDate=true;
	 String date=menuBox("Enter a date to make a booking:");
	 validDate=isValidDate(date);
	 if(validDate)
	 {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 LocalDate secondDate=LocalDate.parse(date,formatter);
	 if(secondDate.isBefore(LocalDate.now()))
	 {
		 outputBoxs("This date is in the past, cannot make a booking.");
	 }
	 else
	 {
	 ArrayList<Integer> slotNumberForBookingsOfDate=new ArrayList<Integer>();
	 for(int i=0;i<bookings.size();i++)
	 {
		if(bookings.get(i).getFacilityId()==facilityId)
		{
			if(bookings.get(i).getBookingDate().equals(secondDate))
			{
				slotNumberForBookingsOfDate.add(bookings.get(i).getBookingSlot());
			}
		}
	 }
		String slotAsString="";
		String [] dropDownListOfSlots=new String[9-slotNumberForBookingsOfDate.size()];
		int counter=0;
		boolean payment=false;
		if(slotNumberForBookingsOfDate.size()==0)
		{
			for(int i=1;i<10;i++)
			{
				dropDownListOfSlots[counter]=""+i;
				counter++;
			}
			slotAsString=dropDown(dropDownListOfSlots,"The following slots are available please select one");
			slotNumber=Integer.parseInt(slotAsString);
			String [] dropDownOfUsersEmail= new String[users.size()];
			for(int i=0;i<users.size();i++)
			{
				dropDownOfUsersEmail[i]=users.get(i).getEmail();
			}
			String email=dropDown(dropDownOfUsersEmail,"Please select an email to make the booking for");
			int userId=0;
			for(int i=0;i<users.size();i++)
			{
				if(users.get(i).getEmail().equals(email))
				{
					userId=users.get(i).getUserId();
				}
			}
			int check = JOptionPane.showConfirmDialog (null, "Is this booking paid for?","Booking",JOptionPane.YES_NO_OPTION);
			if (check == JOptionPane.YES_OPTION)
				payment = true;
			Booking newBooking= new Booking(bookingId,facilityId,userId,secondDate,slotNumber,payment);
			writeFile(newBooking.bookingToString(),bookingFileName);
			bookings.add(newBooking);
		}
		
		else if(slotNumberForBookingsOfDate.size()==9)
		{
			outputBoxs("There are no slots available on this date");
		}
		else
		{
			for(int i=1;i<10;i++)
			{
				slots.add(i);
			}
			for(int i=0;i<slotNumberForBookingsOfDate.size();i++)
			{
				slots.remove(slotNumberForBookingsOfDate.get(i));
			}
			for(int i=0;i<slots.size();i++)
			{
				dropDownListOfSlots[counter]=""+slots.get(i);
				counter++;
			}
			slotAsString=dropDown(dropDownListOfSlots,"The following slots are available please select one");
			slotNumber=Integer.parseInt(slotAsString);
			String [] dropDownOfUsersEmail= new String[users.size()];
			for(int i=0;i<users.size();i++)
			{
				dropDownOfUsersEmail[i]=users.get(i).getEmail();
			}
			String email=dropDown(dropDownOfUsersEmail,"Please select an email to make the booking for");
			int userId=0;
			for(int i=0;i<users.size();i++)
			{
				if(users.get(i).getEmail().equals(email))
				{
					userId=users.get(i).getUserId();
				}
			}
			int check = JOptionPane.showConfirmDialog (null, "Is this booking paid for?","Booking",JOptionPane.YES_NO_OPTION);
			if (check == JOptionPane.YES_OPTION)
				payment = true;
			Booking newBooking= new Booking(bookingId,facilityId,userId,secondDate,slotNumber,payment);
			writeFile(newBooking.bookingToString(),bookingFileName);
			bookings.add(newBooking);
		}
	}
	 }
	else
	{
		outputBoxs("Incorrect date format:Please enter a date in the form dd/MM/yyyy");
	}
	}
	}
	
	/**
	 *Method which records the payments the user makes.
	 *Administrator is presented with a JOptionPane dropdown menu of bookings that have not been paid for.
	 *Once they select the booking to pay for, it changes the boolean pay to true in the bookings array.
	 *Rewrites the file accordingly with the new payement information.
	 *
	 *@throws IOException Writing to Bookings File
	**/
	public static void recordPayments()throws IOException
	{ 
		if(bookings.size()==0)
		{
			outputBoxs("There is no bookings to record payments for.");
		}
		else
		{
			ArrayList<Integer> bookingsNotPaid=new ArrayList<Integer>();
			for(int i=0;i<bookings.size();i++)
			{
				if(bookings.get(i).getPaymentStatus()==false)
				{
					bookingsNotPaid.add(bookings.get(i).getBookingId());
				}
			}
			if(bookingsNotPaid.size()==0)
			{
				outputBoxs("Every booking has been paid for.");
			}
			else
			{
				String [] dropDownBookings=new String[bookingsNotPaid.size()];
				for(int i=0;i<bookingsNotPaid.size();i++)
				{
					dropDownBookings[i]=""+bookingsNotPaid.get(i);
				}
				String choice=dropDown(dropDownBookings,"Please select a bookingId to record payment for:");
				int specificChoice=Integer.parseInt(choice);
				for(int i=0;i<bookings.size();i++)
				{
					if(bookings.get(i).getBookingId()==specificChoice)
					{
						boolean setTrue=true;
						removeLine(bookingFileName,choice,0);
						bookings.get(i).setPaymentStatus(setTrue);
						writeFile(bookings.get(i).bookingToString(),bookingFileName);
					}
				}
			}
		}
	}
}
