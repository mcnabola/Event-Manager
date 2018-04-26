/**
 * Class created for describing a User Object 
 */	
public class User
{
	public int userId;
	public String email;
	public String password;
	public int userType; //1 = admin, 2 = user
	 
	/**
	 *Constructor method for restoring users from the text file and to be loaded into the arrayList.
	 *Gets the parameters and assign them to the variables of the user object. 
	 *
	 *@param userId is the Id of user
	 *@param email is the email of user
	 *@param password is the password of user
	 *@param userType is the type of user-1=admin,2=user
	 */	
	public User(int userId, String email, String password, int userType) 
	{
		this.userId 	= userId;
		this.email		= email;
		this.password 	= password;
		this.userType	= userType;
	}
	/**
	 * This is the basic method used for craeting a user while the program is operation.
	 *Calls the generatepassword method to create password.
	 *Automatically assigns usertype to 2.
	 *
	 *@param userId Id of user
	 *@param email is the email of user
	 *
	 */
	public User(int userId, String email) //For adding new user
	{
		this.userId 	= userId;
		this.email 		= email;
		this.password	= generatePassword();
		this.userType 	= 2;
	}
	
	/**
	 *Method to generate a random alpha-numeric password generator used in the creation of a user. 
	 *
	 *@return    Returns the generated password as a string.
	 *
	 */		
	public static String generatePassword() 
	{
		String password = "";
		String alpha    = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		int passLength  = 8;
		int index 		= 0;
		for (int i=0;i<passLength;i++)
		{
			index 		= (int)(Math.random()* alpha.length());
			password	+= alpha.substring(index, index+1);
		}
		return password;
	}
	/**
	  *Get method to return userid of user. 
	  * 
	  *@return userId returns the users id number.
	  *
	  */	
	public int getUserId()
	{
		return userId;
	}
	/**
	  *Get method to return Email of user
	  *  
	  *@return email returns a String of the users email address.
	  *
	  */		
	public String getEmail()
	{
		return email;
	}
	/**
	  *Get method to return Password of user. 
	  *
	  *@return password String Password of user
	  *
	  */	
	public String getPassword()
	{
		return password;
	}
	/**
	 *Get method to return the Usertype of user, Type 1 or 2 depending on whether the user is a Admin or not
	 *
	 *@return userType returns a int value of the users admin status type.
	 *
	 */		
	public int getUserType()
	{
		return userType;
	}
	/**
	 *Get method to return User info to String
	 * 
	 *@return info String containing all of the users info in one string seperated by commas.
	 *
	 */		
	public String userToString()
	{
		String info	= userId + "," + email + "," + password + "," + userType;
		return info;
	}
}
