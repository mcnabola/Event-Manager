import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
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
	//createNewUser();
	//createNewUser();
	}


  public static String generatePassword()
	{
		boolean isValidPassword=false;
		int passwordLength=(int)((Math.random()*3)+8);
		int positionOfCharacter;
		String password="";
		String pattern="(?=.*?\\d)(?=.*?[a-zA-Z])(?=.*?[^\\w]).{8,}";
		String possibleCharacters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiklmnopqrstuvwxyz1234567890!£$%^&*()_+=@;.,#~/?<>:'-";
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
		User newUser=new User(email,password,userType);
		int userId=newUser.getUserId();
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
}
