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
 	
	
	
	public static void main(String [] args)
	{
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
	  * Checks if an inputted string is a valid email address by comparing it to
	  * a simple email pattern
	  * Input: Takes an email string
	  * Output: Returns a true boolean value if the email matches the pattern and 
	  * returns false if it does not
	  **/                                                               // ---- can "," be accepted - other values "."
	public static boolean validEmail(String email)
	{
		boolean isValid = false; 
		String pattern  = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$");
		
		if (email.matches(pattern))
			isValid = true;
		
		return isValid;
	}
	
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
					if (users.get(0).getEmail().equalsIgnoreCase(email) && users.get(i).getPassword().equals(password))
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
			
			double pricePerHour 	= menuBoxInt("please enter a price per hour:");
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
			removeStringFromFile(facilityFileName,selection,1);
		}
		catch(Exception e)
		{}
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
			    case 1: accountStatement(currentUserId,2);
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
			if(bookings.get(i).getUserId()==currentUserId)
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
 
	// viewFacility() - method made - still currently refactoring/making presentable
	
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
	
	public static void decommissionFacility()throws IOException
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
			facilities.get(i).setDecommissionedUntil(decommissionedToDate);
			removeStringFromFile(facilityFileName,choice,1);
			writeFile(facilities.get(i).toString(),facilityFileName);
		 }
	 }
	 }
	 else
	 {
		 outputBoxs("There is a booking for this Facility, cannot decommission");
	 }
	 
	}
	
	public static void recommissionFacility()throws IOException
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
		 if (facilities.get(i).getFacilityName().equals(choice)&&facilities.get(i).getAvailability())
		 {
			facilities.get(i).setDecommissionedUntil(LocalDate.now());
			finished=true;
			removeStringFromFile(facilityFileName,choice,1);
			writeFile(facilities.get(i).toString(),facilityFileName);
		 }
	 }
	 if(!finished)
	 {
		 outputBoxs("This facility is not decomissioned");
	 }
	}
	
	/*public static void makeBooking()
	{
		int [] slots={1,2,3,4,5,6,7,8,9};
		// check if a booking already made
		arraylist{2,5,7,3}
		 arraylist - searchBookings()
		
	        
	}
	*/
}
