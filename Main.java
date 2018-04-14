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
	private static int currentID;
 	private static int userType;
	private static ArrayList<User> users=new ArrayList<User>();
	private static ArrayList<Facility> facilities=new ArrayList<Facility>();
	private static ArrayList<Booking> bookings=new ArrayList<Booking>();
 	
	
	
	public static void main(String [] args)
	{
	String email=menuBox("Enter Username");
			String password=menuBox("Enter Password");
			boolean testing=loginMethod(email,password);
			if (testing&&userType==1)  
			{
				adminMenu();
			
			}		
				else if (testing&&userType==2)
				{
					userMenu();
					
				}
				else System.exit(0);
				
	}


	public static void restore() //TESTING***** ON START LOADS FROM FILE TO ARRAYLISTS 
	{
		Scanner in;
		String[] fileElements;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try
		{

			in = new Scanner(new BufferedReader(new FileReader(userFileName)));
			while(in.hasNext())
			{
				fileElements 			= in.nextLine().split(",");
				int	a 					= Integer.parseInt(fileElements[0]);
				String b				= fileElements[1];
				String c				= fileElements[2];
				int d 					= Integer.parseInt(fileElements[3]); 			
				User aUser 	= new User(a, b, c, d);
				users.add(aUser);
			}
			in = new Scanner(new BufferedReader(new FileReader(facilityFileName)));
			while(in.hasNext())
			{
				Facility aFacility;
				int e;
				String f;
				double g;
				fileElements = in.nextLine().split(",");
				System.out.println(fileElements.length);
				if (fileElements.length == 5) //IF DECOM DATE PROVIDED
				{
					e			 = Integer.parseInt(fileElements[0]);
					f 			 = fileElements[1];
					g		   	 = Double.parseDouble(fileElements[2]);
					String temp1 = fileElements[3];
					LocalDate h	 = LocalDate.parse(temp1, formatter);
					aFacility 	 = new Facility(e, f, g, h);
					facilities.add(aFacility);
				}
				else
				{
					e = Integer.parseInt(fileElements[0]);
					f = fileElements[1];
					g = Double.parseDouble(fileElements[2]);
					aFacility = new Facility(e, f, g);
					facilities.add(aFacility);
				}
			}
			in = new Scanner(new BufferedReader(new FileReader(bookingFileName)));
			while(in.hasNext())
			{
				fileElements			= in.nextLine().split(",");
				int i 					= Integer.parseInt(fileElements[0]);
				int j 					= Integer.parseInt(fileElements[1]);
				int k 					= Integer.parseInt(fileElements[2]);
				String temp2			= fileElements[3];
				LocalDate l				= LocalDate.parse(temp2);
				int m					= Integer.parseInt(fileElements[4]);
				boolean n				= Boolean.parseBoolean(fileElements[5]);
				Booking aBooking		= new Booking(i, j, k, l, m, n);
				bookings.add(aBooking);
			}
			in.close();
		}
		catch(Exception e)
		{}
	}	
	
  
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
	
	public static int menuBoxInt(String options)
	{
		String input="";
		int inputAsInt=0;
		try
		{
			input=JOptionPane.showInputDialog(null,options);
			inputAsInt=Integer.parseInt(input);
			return inputAsInt;
		}
		catch(NumberFormatException e)
		{
		JOptionPane.showMessageDialog(null,"Error:Input is not a number entered");
		return menuBoxInt(options);
		}
	}
	
		public static void createNewUser()
	{
		String email=menuBox("Please enter an email:");
		int userId=users.size()+1;
		User newUser=new User(userId,email);
		users.add(newUser);
		String info=(newUser.userToString());
		writeFile(info,userFileName);
	}
	
	
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
		public static void removeStringFromFile(String fileName, String str, int pos)throws IOException
	{
		String tempFileName = "temp.txt";
		File scanFile = new File(fileName);
		File tempFile = new File(tempFileName);
		String[] fileElements;
		BufferedReader br = new BufferedReader(new FileReader(scanFile));
		
		String currentLine = "";
		try
		{
			while((currentLine = br.readLine()) != null)
			{
				fileElements = currentLine.split(",");
				if (!(fileElements[2].equals(str)))
					writeFile(currentLine, tempFileName);
			}
			br.close();
			 if(!(scanFile.delete()))
				System.out.println("File deleted unsuccessfully");
			if(!(tempFile.renameTo(scanFile)))
				System.out.println("File renamed unsuccessfully");
		}
		catch(IOException e)
		{}
	}
	
	/**
	  * Checks if user email is already registered by searching in the user file
	  * Input: Takes a user email string
	  * Output: Returns a true boolean if email is found, returns false otherwise
	  **/
	public static boolean nameExists(String email)
	{
		File userFile = new File(userFileName);
		boolean found = false;
		Scanner in;
		String[] fileElements;
		try
		{
			in = new Scanner(userFile);
			while(in.hasNext())
			{
				fileElements = in.nextLine().split(",");
				if (fileElements[1].equals(email))
					found = true;
			}
		}
		catch(Exception e)
		{}
		return found;
	}
	
	/**
	  * Checks if an inputted string is a valid email address by comparing it to
	  * a simple email pattern
	  * Input: Takes an email string
	  * Output: Returns a true boolean value if the email matches the pattern and 
	  * returns false if it does not
	  **/
	public static boolean validEmail(String email)
	{
		boolean isValid = false; 
		String pattern  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$");
		
		if (email.matches(pattern))
			isValid = true;
		
		return isValid;
	}
	
	public static boolean loginMethod(String username, String password)
	{
		File userFile = new File(userFileName);
		Scanner in;
		String[] fileElements;
		boolean found = false;
		int loginAttempts = 3;
		try
		{
			if (userFile.exists())
			{
				while (!found)
				{
					in = new Scanner(userFile);
					while (in.hasNext())
					{
						fileElements = in.nextLine().split(",");
						if (username.equals(fileElements[1]) && password.equals(fileElements[2]))
						{
							found = true;
							currentID = Integer.parseInt(fileElements[0]);
							userType = Integer.parseInt(fileElements[3]);
						}
					}
					in.close();
					if (!found)
					{
						loginAttempts--; 
						if (loginAttempts == 0)
						{
							JOptionPane.showMessageDialog(null, "No attempts remaining.");
							break;
						}
						 JOptionPane.showMessageDialog(null, "Incorrect login details.\n" + loginAttempts + " attempt(s) remaining.");
						username = JOptionPane.showInputDialog(null, "Enter username");
						password = JOptionPane.showInputDialog(null,"Enter password");
					}
					else
						JOptionPane.showMessageDialog(null, "Successfully logged in as " + username);	
				}
			}
		}
		catch(Exception e)
		{}
		return found;
	} 
	
		public static void createNewFacility()
	{
		String [] options={"True","False"};
		String facilityName=menuBox("Please enter a facilityName:");
		double pricePerHour=menuBoxInt("please enter a price per hour:");
		String date=menuBox("Please enter a date:");
		int facilityId=facilities.size()+1;
		int whichChoice=optionBoxs(options,"Select availability");
		boolean availability;
		
		if(whichChoice==0)
			availability=true;
		else
			availability=false;
		
		Facility newFacility=new Facility(facilityId,facilityName,pricePerHour,availability);
		newFacility.setDecommissionedUntilDate(date);
		facilities.add(newFacility);
		String info=newFacility.facilityToString();
		writeFile(info,facilityFileName);
	}
	
	public static void removeFacility()
	{
		String [] listOfFacilities=new String[facilities.size()];
		int positionInArrayList=0;
		for(int i=0;i<listOfFacilities.length;i++)
		{
			listOfFacilities[i]=facilities.get(i).getFacilityName();
		}
		String selection=dropDown(listOfFacilities,"Please select a facility to remove:");
		for(int i=0;i<listOfFacilities.length;i++)
		{
			if(facilities.get(i).getFacilityName().equals(selection))
				positionInArrayList=i;
		}
		boolean availability=facilities.get(positionInArrayList).getAvailability();
		if(!(availability))
			outputBoxs("This facility has a booking, cannot delete.");
		else
			facilities.remove(positionInArrayList);
	}
	
	
	public static String dropDown(String[] options, String dialogText)
	{
		String selection = "";
		if (options.length != 0)
		{
			selection = (String)JOptionPane.showInputDialog(null, dialogText, "Input", 1, null, options, options[0]);
		}
		return selection;
	}

	
	public static void outputBoxs(String output)
	{
	     JOptionPane.showMessageDialog(null, output);	
	}
	
	public static void adminMenu()
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
						case 1: //Facility Availability
						break;
						case 2:	//View Facility Bookings		 
						break;
						case 3: removeFacility();
						break;
						case 4: //Decommission Facility
						break;
						case 5: //Recommission Facility
						break;
						case 6: //Make Booking
						break;
					}
                break;
				case 2: //Record Payments;
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
			    case 0: //View Bookings
		        break;
			    case 1: accountStatement(currentID,2);
                break;				
			}
		}	
	}

	public static void viewBookings()
	{
		ArrayList<Booking> personalBookings=new ArrayList<Booking>();
		ArrayList<String> info=new ArrayList<String>();
		String singleBooking;
		String facilityName;
		for(int i=0;i<bookings.size();i++)
		{
			if(bookings.get(i).getUserId()==currentID)
				personalBookings.add(bookings.get(i));
		}
		for(int i=0;i<personalBookings.size();i++)// have to get the facility name
		{
			System.out.print(personalBookings.get(i).bookingToString());
			
		}
	}
		
			
			

	/////=====//// havent tested - but 0 errors when compiled 
	public static String accountStatement(int userId, int userType)
	{
	    // Want to return a String output that contains the amount due by each user - this output is depending on whether a admin or a user calls the method  
	    int amountDue = 0;
	    String statement = ( userId + "    " + amountDue );
            for (int i = 0 ; i < bookings.size(); i++ )
	    {
                if (bookings.get(i).getUserId() == userId)
		{
	            int facilityID = bookings.get(i).getFacilityId();
		    for (int y = 0; i < facilities.size();y++)
		    {
	                if (facilities.get(y).getFacilityId() == facilityID)
			{
			    if (userType == 1)
			    {
			        if (bookings.get(i).getPaymentStatus() == false)
				{
				    amountDue += facilities.get(y).getPricePerHour();
                                    statement = userId + "    " + amountDue;									
				}
			    }
			    else
			    {
			        boolean ff =  (bookings.get(i).getPaymentStatus() == false ); // opposite atm
      				if (ff)
				{
				    amountDue += facilities.get(y).getPricePerHour();
				}
				statement += ( facilities.get(y).getFacilityName() + " " + facilities.get(y).getPricePerHour()  + "  Paid Status: " + ff );				    
			    }
		       }
                   }
		  }				
		 } 
	      statement += ("Amount Due:     " + amountDue);
	
		
		/*  IF UserType 1 
		Church Room   €100     Paid
 		Tea Room       €25   Unpaid
		Room 3         €12   Unpaid
		
		Amount Due:            €37
		
		========
		
		IF UserType 2
		User Id: 1   €56
		  - change ints to doubles*/
	    return statement;
	}
	/* -- not finished and havent tested
	public static void viewAvailabilityFacility()
	{
	//array for dropdown of the facilities
        String[] facilitiesName = String[facilities.size()];
	 for (int i = 0; i < facilities.size();i++)
	 {
		 facilitiesName[i] = facilities.get(i).getFacilityName();
	 }
	 String choice = dropDown(facilitiesName, "Choose a facility to view availability for.");
		 
		 // need the corressponding facility id or index value in the arraylist
	 int indexName;
	 for (int u = 0; u< facilitiesName.length;u++)
	  {
		 if (facilities[i].equals(choice)
		 {
		 indexName = i;
		 }
	 }
         
         // date1 and date2 
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 String d1 = menuBox("Enter the first date you want to view the dates between.\nIn the format dd/MM/yyyy");
		 String d1 = menuBox("Enter the second date to view the availability between.\nIn the format dd/MM/yyyy");
		 Date date1 = formatter.parse(d1);
		 Date date2 = formatter.parse(d2);   // add try catch while loop to check that the date was enterd in the correct format and to try again 
		 
		 // check that the second date is after the first one 
		 if (date2.after(date1))
		 {
			 // loop through the dates inclusive so its a loop from date1 up to and including date2
			 // store each date in a array -> dropDown -- the user chooses to see a day from the drop down and
			 // and then it shows the nine time slots for that date
			 
			 // while loop until user chooses to leave - allows the user to view the bookings for multiple days one after each other
			 
			 // once a date is chosen from a dropdown - load the string back into a dateformat
			 // look through bookings with that date - add to boolean array position 0-9 || How add to a string
			 // then outputBoxs the string and the index values from the array 
			 
			 
		 }
	} */
		//NOT TESTED
	//NOT TESTED
	//NOT TESTED
	public static void viewBookingsForAFacility() //check what slots are free or booked for a certain date
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
	 //LocalDate today=LocalDate.now();
	 String date=menuBox("Enter a date search:");
	 LocalDate secondDate=LocalDate.parse(date);
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
	 String result="The current slots on the "+date+"are booked:"+"\n";
	 for(int i=0;i<slotNumberForBookingsOfDate.size();i++)
	 {
		 result+="slot "+slotNumberForBookingsOfDate.get(i)+"\n";
	 }
	 outputBoxs(result);
	 }
	}
}
