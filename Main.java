import java.io.*;
import javax.swing.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
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
 	
	
	 /**
	  * General Description of the method - leave the space between description and tags
	  * @ tag nameOFVariable Description of the variable
	  *
	  * @param facilityId facilityId is a int that represents the unique value for that facility
	  * @param optionType Option Type is a string that represesents a "option" 
          * @return           Description of the return value
	  *
	  *
	  **/		
	public static void main(String [] args)
	{
		restore();
		System.out.println(users.size());
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
	   *Input -
	   *Output -
	   *
	   **/	
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
				lineFromFile 			= in.nextLine();
				fileElements 			= lineFromFile.split(",");
				int facilityId 			= Integer.parseInt(fileElements[0]);
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
					aBooking = new Booking(bookingId, facilityId, userId, bookingDate, bookingSlot, paid);
				bookings.add(aBooking);
			}
			in.close();
		}
		}
		catch(Exception e)
		{}
	}
	
	 /**
	   *A JOptionPane menu which allows the user to enter a word, digits or characters which are returned as a string.
	   *Input -Passed a string of what it is going to be displayed on the option box
	   *Output - whatever the user enters is returned as a string
	   *
	   **/	  
	public static String menuBox(String options)
	{
		String input="";
		try
		{
			input=JOptionPane.showInputDialog(null,options);
			return input;
		}
		catch(Exception e)
		{
		JOptionPane.showMessageDialog(null,"Error: no String entered");
		return menuBox(options);
		}
	}
	 /**
	   *A JOptionPane menu which allows the user to enter digits which are returned as a double.
	   *Input -Passed a string of what it is going to be displayed on the option box
	   *Output - whatever the user enters is returned as a double
	   *
	   **/	  
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
		JOptionPane.showMessageDialog(null,"Error:Input is not a number entered");
		return menuBoxDouble(options);
		}
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
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
	   *input - passed a string of what the user wants to write to file and a filename of where the input is printed to.
	   *output - the input is is wrote to file specified
	   *
	   **/		
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
	   *Input -passed an array which are the options and also a string of what you want the JOptionPane to say
	   *Output - returns a int which is the index in the array which the user selected
	   *
	   **/	
	public static int optionBoxs(String[] options,String whatYouWantItToSay)
	{
        int result = JOptionPane.showOptionDialog(null, whatYouWantItToSay, "League Manager", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return result;
	}
		/**
	  * Searches a file for a given string in a given position and regenerates the file without the line
	  * Copies every line from the file except the specified line and writes them to a temp file
	  * The original file is then deleted and the temp file is renamed to the name of the original file
	  * Input: Takes the filename, the string item to search for, and its position in a line
	  **/
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
	  * Input: Takes an email string
	  * Output: Returns a true boolean value if the email matches the pattern and 
	  * returns false if it does not
	  **/                                                               // ---- can "," be accepted - other values "."
	public static boolean validEmail(String email)
	{
		boolean isValid = false; 
		String pattern  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+[.]([a-zA-Z]{2,})");
		
		if (email.matches(pattern))
			isValid = true;
		
		return isValid;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
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
						JOptionPane.showMessageDialog(null, "No attempts remaining");
						break;
					}
					JOptionPane.showMessageDialog(null, "Incorrect login details.\n" + loginAttempts + " attempts remaining:");
					email	 = JOptionPane.showInputDialog(null, "Enter email");
					password = JOptionPane.showInputDialog(null, "Enter password");
				}
				else
					JOptionPane.showMessageDialog(null, "Successfully logged in as " + email);
			}
		}
		catch(Exception e)
		{}
		return found;
	}
	 /**
	   *checks whether a string passed into the method is a valid date in the form dd/mm/yyyy
	   *Input -The method is passed a string which is checked upon whether or not to be in the correct format or not
	   *Output - the method reutrns a boolean value whether the date is valid or not.
	   *
	   **/		
		public static boolean isValidDate(String date)throws IOException
	{
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate test = LocalDate.parse(date, formatter);
			System.out.println(" TRUE" + test);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public static void createNewFacility()
	{
		Facility aFacility;
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
				aFacility = new Facility(facilityId, facilityName, pricePerHour, date);
			}
			else
				aFacility = new Facility(facilityId, facilityName, pricePerHour);
			
			facilities.add(aFacility);
			String info = aFacility.facilityToString();
			System.out.println(info);
			writeFile(info,facilityFileName);
		}
		catch(Exception e)
		{System.out.print(e.getMessage());}
	}
	 /**
	   *removes a facility from the facility array and also removes it from the facility file. A facility cannot be removed if it has a booking.
	   *Input -The user is asked to choose a facility from a drop down in which they want to remove 
	   *Output -The facility is removed from the facility arraylist and from the facility file.
	   *
	   **/		
	public static void removeFacility()
	{
		try
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
		catch(Exception e)
		{}
	}
	
	 /**
	   *drop down menu in which the user can choose an option.
	   *Input -passed an array of options in which the user can select and also a sting of what the menu box displays
	   *Output -The choice in which the user selects is returned as a string
	   *
	   **/		
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
	   *Input -passed a string which is displayed to screen
	   *Output - none
	   *
	   **/		
	public static void outputBoxs(String output)
	{
	     JOptionPane.showMessageDialog(null, output);	
	}
	 /**
	   *Gui for the administrator to navigate through commands
	   *Input - admin selects an option which is then passed through switch statements to call the correct method
	   *Output - the method selected is called an executed 
	   *
	   **/	
	public static void adminMenu()
	{
		try
		{
		String [] initialOptions = { "Register User", "Facility Menu", "Record Payments", "View Account Statements" };
		String [] subOptions	 = {"Add Facility","View Facility Availability", "View Facility Bookings", "Remove Facility", "Decommission Facility", "Recommission Facility","Make Booking"};
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
					}
                break;
				case 2: recordPayments();
				break;
				case 3: String statementOutput = ""; 
                                        for (int i = 0 ; i < users.size();i++)
                                        { int userid = users.get(i).getUserId();
				          statementOutput+= accountStatement(userid,1)+"\n";}						
				          outputBoxs(statementOutput); 
				break;
			}
		}
		} 
		catch(Exception E)
		{}
	}
	 /**
	   *Gui for the administrator to navigate through commands
	   *Input - admin selects an option which is then passed through switch statements to call the correct method
	   *Output - the method selected is called an executed 
	   *
	   **/	
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
			    case 1: outputBoxs(accountStatement(currentUserId,2));
                break;				
			}
		}	
	}
	 /**
	   *Allows the current user to view their own booking
	   *Input - Uses the global variable of the currentUserId to distinguish bookings.
	   *Output - The users bookings are displayed to screen
	   *
	   **/	
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
			    out+=(personalBookings.get(i).bookingToString()+"\n");	
		    }
		}
		else
			out = "There are currently no bookings at this time.";
		outputBoxs(out);
	}
	
	
		// also should add the euro sign beside the money and the users email as opposed to their id number
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/	
	public static String accountStatement(int userId, int userType)
	{
	    // Want to return a String output that contains the amount due by each user - this output is depending on whether a admin or a user calls the method  
	    double amountDue = 0.0;
		String userEmail = "";
		for (int j = 0;j<users.size();j++)
		{
			if (users.get(j).getUserId() == userId)
				userEmail = users.get(j).getEmail();
		}
	    String statement = ("User ID: "+userId + "Email: " + userEmail + "    Amount Due: " + amountDue+"\n");
            for (int i = 0 ; i < bookings.size(); i++ )
	         {
                 if (bookings.get(i).getUserId() == userId)
		         {
	             int facilityID = bookings.get(i).getFacilityId();
		         for (int y = 0; i < facilities.size();y++)
		         {
	                if (facilities.get(y).getFacilityId() == facilityID)
			        {
			        if (userType == 1)  // whats returned to a admin
			        {
			            if (bookings.get(i).getPaymentStatus() == false)
				        {
				        amountDue += facilities.get(y).getPricePerHour(); 
						amountDue = currency.getSymbol() + amountDue;/// currency.getSymbol();
                                        statement = "User ID: "+userId + "Email: " + userEmail + "    Amount Due: " + amountDue+"\n"; /// returned to normal user !!!???!!!!									
				        }
			         }
			         else  /// user type 2 returned to a ordinary user
			         {
			             boolean ff =  (bookings.get(i).getPaymentStatus() == false ); // opposite atm
      				     if (ff)
				         {
				             amountDue += facilities.get(y).getPricePerHour();
				         }
						 amountDue = currency.getSymbol() + amountDue;
				     statement += ( facilities.get(y).getFacilityName() + " " + facilities.get(y).getPricePerHour()  + "  Paid Status: " + ff +"\n");				    
			     }
		         }
                 }
		         }				
		     } 
	      statement += ("\nAmount Due:     " + amountDue);
	
		
		/*  IF UserType 2		
		
		User Id : 4 || JJ@CSIS.UL
		Church Room   €100     Paid
 		Tea Room       €25   Unpaid
		Room 3         €12   Unpaid
		
		Amount Due:            €37
		
		========
		
		IF UserType 1
		
		User Id: 4 || JJ@CSIS.UL	  Amnt Due €37		*/
	    return statement;
	}
 
	// viewFacility() - method made - still currently refactoring/making presentable
	 /**
	   *Shows a user which slots are booked for a certain facility on a certain date
	   *Input - The user is asked to choose a facility from a dropdown and also asked to input a date to view the bookings
	   *Output - The slots which are booked for the facility on the date are displayed to screen
	   *
	   **/	
	public static void viewFacility(int type)throws IOException //check what slots are free or booked for a certain date
	{
	if(facilities.size()==0)
	{
		outputBoxs("There is no facilities.");
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
	 boolean validDate=true;
	 String date=menuBox("Enter a date search:");
	 validDate=isValidDate(date);
	 if(validDate)
	 {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 LocalDate secondDate=LocalDate.parse(date,formatter);
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
		 outputBoxs("There is no bookings for this date.");
	 }
	 else
	 { 
	 String result="The current slots on the "+date+" are booked:"+"\n";
	 String avail = "Current free slots on " + date + " are available\n";
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
	else
	{
		outputBoxs("Incorrect date format:Please enter a date in the form dd/MM/yyyy");
	}
	}
	}
	 /**
	   *The user has the ability to decommission a facility that is not decommissioned already
	   *Input -the user is asked to choose a facility to recommissioned  and enter a date for the facility to be decommissioned
	   *Output - The facility is decommissioned if it has no bookings and already isnt decommissioned and the availability of the entry in the file is changed.
	   *
	   **/	
	public static void decommissionFacility()throws IOException
	{
	 	if(facilities.size()==0)
	{
		outputBoxs("There is no facilities.");
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
	   *Input -the user is asked to choose a facility to recommission 
	   *Output - The facility is recommissioned if it was previously decommissioned and the availability of the entry in the file is changed.
	   *
	   **/	
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
		 if (facilities.get(i).getFacilityName().equals(choice)&&(!(facilities.get(i).getAvailability())))
		 {
			facilities.get(i).setDecommissionedUntil(LocalDate.now());
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
	   *Input - passed no arguments but the user is requested to choose a facility and enter a date to make the booking for and also choose the slot.
	   *Output - adds the booking to the booking array list and also writes the information to the file
	   *
	   **/		
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
			String [] dropDownOfUsers= new String[users.size()];
			for(int i=0;i<users.size();i++)
			{
				dropDownOfUsers[i]=""+users.get(i).getUserId();
			}
			int userId=Integer.parseInt(dropDown(dropDownOfUsers,"Please select a userId to make the booking for"));
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
	
	
	
	
	
	// this method is tested -- however changing to better more descriptive variable names is something i will change in a bit.
	public static void facilityViewing()
	{
	   if (facilities.size() != 0)
            {
            String[] initialUserOptions = {"Availability","Bookings"};
            int userOption = optionBoxs(initialUserOptions, "Between two dates, view the Availability or Bookings of a selected Facility?"); 
		
	     //fill array for dropdown of the facilities
         String[] facilitiesName = new String[facilities.size()];
	     for (int i = 0; i < facilities.size();i++)
	     {
		    facilitiesName[i] = facilities.get(i).getFacilityName();
	     }
	     String choice = dropDown(facilitiesName, "Choose a facility to view availability for."); 
		 // need the corressponding facility id or index value in the arraylist
	     int localFacilityId=0; // need a catch to make sure facilities != 0 earlier up
	     for (int j = 0; j< facilitiesName.length;j++)
	     {
		     if (facilities.get(j).equals(choice))
		     {
		         localFacilityId = j;
		     }
	     }
         
                 // getting date1 and date2 and checking
		 //  DateTimeFormatter formatter = new DateTimeFormatter("dd/MM/yyyy"); 
		 String d1 = menuBox("Enter the first date you want to view the dates between.\nIn the format dd/MM/yyyy");
		 String d2 = menuBox("Enter the second date to view the availability between.\nIn the format dd/MM/yyyy");
		 try{
		 boolean check = isValidDate(d1);
		 while(check == false)
		 {
		     String date = menuBox("Please enter a valid date for the first date again:\nFormat(DD/MM/YYYY)");
			 check = isValidDate(date);
		 }
		
		 check = isValidDate(d2);
		 while(check == false)
		 {
		     String date = menuBox("Please enter a valid date for the second date again:\nFormat(DD/MM/YYYY)");
			 check = isValidDate(d2);
		 }
		 }
		 catch(Exception e){}
		 
		 // need to check is the first date before the second one and how many iterations of my loop to store in the array
		     
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                 LocalDate date = LocalDate.parse(d1, formatter);
		 LocalDate date2 = LocalDate.parse(d2, formatter);
		 
		 int dateDifference = date.compareTo(date2); 
		 /// compare to -- different way of using it 
		 int ff = Math.abs(dateDifference); 
		 
		 String[] names = new String[ff];
		 // looping over the two dates to get the options for the user
		 LocalDate temp = date;
		 String output;
		 int count = 0;
		 while (temp.isBefore(date2))
		 {
		     temp = temp.plusDays(1);
		     output = temp.format(formatter);
			 names[count] = output;
			 count++;
		 }
		 
		 // user chooses date from the dropDown
		 String dateChoice = dropDown(names, "Choose a date to view");
                 LocalDate date777 = LocalDate.parse(dateChoice,formatter);
 
		 // now we need to view all the availabilities//bookings on the date xx/xx/xxxx
		  // this is where I pass to seans and I modified code.
		 viewBookingsForAFacility(date777, userOption, localFacilityId);
		 }
		 else
		 {
			 outputBoxs("There are no facilities made at this time.");
		 }
	 }
	 
	
	
	
	
	 public static void viewBookingsForAFacility(LocalDate date, int option, int localFacilityId) // note my current code has facilityId
	 {		 LocalDate secondDate=date;
		 String bookingsOut="For the date xx/zz/yyyy there are", availableOut = "For the date xx/zz/yyyy there are"; 
	     boolean available=true;
	     ArrayList<Integer> slotNumberForBookingsOfDate=new ArrayList<Integer>();
	     for(int i=0;i<bookings.size();i++)
	     {
		     if(bookings.get(i).getFacilityId()==localFacilityId)
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
		      bookingsOut = "For the date xx/xx/xxx there are 0 bookings";
			  availableOut = "For the date xx/xx/xxxx 09:00\n10:00\n11:00\12:00" ;
	     }
		 else
		 {
	        for (int i = 1 ; i< 10;i++)
		     {
                if (slotNumberForBookingsOfDate.contains(i))
                            {
				    bookingsOut+= "Slot " + i +"\n";
			    }
		else
				    availableOut+= "Slot " + i +  "\n";
	         }
		 }
		 if (option == 0)
			 outputBoxs(availableOut);
		 else
			 outputBoxs(bookingsOut);	 
	 }

	/**
	 * This method returns a the time value that a 'slot', a integer value from 1 to 9. Which represents a time from 09:00 to 17:00
	 * @param slot Integer value from 1 to 9 that is representative of a time value
	 * @return out A string with the time value that represents the inputted slot.
	 **/
    public static String slotTime(int slot)
	{
		// slot is a value 1 to 9 representing a time value
		String[] times = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00"};
		boolean found = false;
		String out="";
		for (int i=0; i < times.length && !found; i++)
		{
			if(slot == (i+1))
			{
				out = times[i];
				found = true;
			}
		}
		return out;
	}	
	
	
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
