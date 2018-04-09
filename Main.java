import java.io.*;
import javax.swing.*;
import java.util.*;
public class main
{
	final static String userFileName="Users.txt";
	final static String facilityFileName="Facilities.txt";
	final static String bookingFileName="Bookings.txt";
	private static int currentID;
 	private static int userType;
	private static ArrayList<User> users;
	private static ArrayList<Facility> facilities;
	private static ArrayList<Booking> bookings;
 	
	
	
	public static void main(String [] args)
	{
	String [] Options={"Log in","Create Account"};
		int y=optionBoxs(Options,"Would you like to log in or create an account?");
		switch(y)
		{
			case 0: String email=menuBox("Enter Username");
			String password=menuBox("Enter Password");
			boolean testing=loginMethod(email,password);
			if (testing&&userType==1)  
			{
				adminMenu();
				break;
			}		
				else if (testing&&userType==2)
				{
					userMenu();
					break;
				}
				else break;
				
				case 1:createNewUser();
				break;
		}
	}


	public static void restore() //TESTING***** ON START LOADS FROM FILE TO ARRAYLISTS 
	{
		Scanner in;
		String[] fileElements;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			File userFileName 		= new File(userFile);
			File facilityFileName	= new File(facilityFile);
			File bookingFileName 	= new File(bookingFile);
			
			in = new Scanner(new BufferedReader(new FileReader(userFile)));
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
			in = new Scanner(new BufferedReader(new FileReader(facilityFile)));
			while(in.hasNext())
			{
				int e;
				String f;
				double g;
				fileElements 		    = in.nextLine().split(",");
				if (fileElements.length == 3)
				{
					e					= Integer.parseInt(fileElements[0]);
					f 					= fileElements[1];
					g					= Double.parseDouble(fileElements[2]);
					Facility aFacility 	= new Facility(e, f, g);
					facilities.add(aFacility);
				}
				else
				{
					e					= Integer.parseInt(fileElements[0]);
					f 					= fileElements[1];
					g					= Double.parseDouble(fileElements[2]);
					String temp1		= fileElements[3];
					Date h				= formatter.parse(temp1);
					Facility bFacility 	= new Facility(e, f, g, h);
					facilities.add(bFacility);
				}
			}
			in = new Scanner(new BufferedReader(new FileReader(bookingFile)));
			while(in.hasNext())
			{
				fileElements			= in.nextLine().split(",");
				int i 					= Integer.parseInt(fileElements[0]);
				int j 					= Integer.parseInt(fileElements[1]);
				int k 					= Integer.parseInt(fileElements[2]);
				String temp2			= fileElements[3];
				Date l					= formatter.parse(temp2);
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
	
  public static String generatePassword()
	{
		boolean isValidPassword=false;
		int passwordLength=(int)((Math.random()*3)+8);
		int positionOfCharacter;
		String password="";
		String pattern="(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?[^\\w]).{8,}";
		String possibleCharacters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiklmnopqrstuvwxyz1234567890!£$%^&*()_+=@;.#~/?<>:'-";
		while(!isValidPassword)
		{
			for(int i=0;i<passwordLength;i++)
			{
				positionOfCharacter=(int)(Math.random()*possibleCharacters.length()-1);
				password=password+possibleCharacters.substring(positionOfCharacter,positionOfCharacter+1);
			}
			if(password.matches(pattern))
				isValidPassword=true;
			else
				password="";
		}
		return password;
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
		String password=generatePassword();
		int userType=2;
		int userId=users.size()+1;
		User newUser=new User(userId,email,password,userType);
		users.add(newUser);
		String info=userId+","+email+","+password+","+userType;
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
	public static void removeLineFromFile(String fileName, String itemInLineToDel, int pos)
	{
		try 
		{
		        String[] fileElements;
			String line = "";
			File inFile = new File(fileName);
			if (!inFile.isFile()) 
			{
				System.out.println("Parameter is not an existing file");
				return;
			}
			File tempFile 	  = new File("temp.txt");
			BufferedReader br = new BufferedReader(new FileReader(inFile));
			PrintWriter pw 	  = new PrintWriter(new FileWriter(tempFile));
			while ((line = br.readLine()) != null) 
			{
				fileElements = line.split(","); 
				if (!fileElements[pos].equals(itemInLineToDel)) 
				{
				pw.println(line);
				pw.flush();
				}
			}
			pw.close();
			br.close();
			//Delete the original file
			if (!inFile.delete()) 
			{
			System.out.println("Could not delete file");
			return;
			} 
			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile)) 
			{
				System.out.println("Could not rename file");
				return;
			}
       } 
		catch (FileNotFoundException ex) 
		{
          ex.printStackTrace();
        } 
		catch (IOException ex) 
		{
         ex.printStackTrace();
        }
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
		while(main && x==0||x==1||x==2||x==3)  // && not null 
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
				case 3: //View Account Statements;
				break;
			}
		}
	}
	
	public static void userMenu()
	{
		String [] initialOptions = { "View Bookings", "View Account Statement"};
	    boolean main = true;
		int x = 0;
		while (main && x==0||x==1||x==2||x==3)  // && not null 
		{	
		    x = optionBoxs(initialOptions,"Choose an option");
		    int y = 0;
		    switch (x)
		    {
			    case 0: //View Bookings
		        break;
			    case 1: //View Account Statement
                break;				
			}
		}	
	}
	
	/*public static void viewBookings()
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
			
			
		}
	}*/
	
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
			        if (bookings.get(i).getPaymentStatus() == 'N')
				{
				    amountDue += facilities.get(y).getPricePerHour();
                                    statement = userId + "    " + amountDue;									
				}
			    }
			    else
			    {
			        boolean ff =  (bookings.get(i).getPaymentStatus() == 'N'); // opposite atm
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
}
