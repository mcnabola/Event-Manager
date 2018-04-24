	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/	
public class User
{
	public int userId;
	public String email;
	public String password;
	public int userType; //1 = admin, 2 = user
	 /**
	   *Constructor method for user.
	   *Gets the parameters and assign them to the variables of user.
	   *Input -Int of user Id,String of user's email ,String of user's password, int of usertype
	   *Output -No output
	   *
	   **/	
	public User(int userId, String email, String password, int userType) //For restoring users
	{
		this.userId 	= userId;
		this.email		= email;
		this.password 	= password;
		this.userType	= userType;
	}
	 /**
	   *Gets the parameters and assign them to the variables of user.
	   *Calls the generatepassword method to create password.
	   *Automatically assigns usertype to 2.
	   *Input -Int user Id and String email
	   *Output -No output
	   *
	   **/		
	public User(int userId, String email) //Basic for adding new user
	{
		this.userId 	= userId;
		this.email 		= email;
		this.password	= generatePassword();
		this.userType 	= 2;
	}
	
	 /**
	   *Method to generate a random String password for user.
	   *Input -None
	   *Output -returns the genrated password as a string
	   *
	   **/		
	public static String generatePassword() //Basic alpha-numeric password generator
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
	   *Get method to return userid of user
	   *Input -No input
	   *Output -returns int UserId of user
	   *
	   **/	
	public int getUserId()
	{
		return userId;
	}
	 /**
	   *Get method to return Email of user
	   *Input -No input
	   *Output -returns String Email of user
	   *
	   **/		
	public String getEmail()
	{
		return email;
	}
	 /**
	   *Get method to return Password of user
	   *Input -No input
	   *Output -returns String Password of user
	   *
	   **/	
	public String getPassword()
	{
		return password;
	}
	 /**
	   *Get method to return Usertype of user
	   *Input -No input
	   *Output -returns int Uertype of user
	   *
	   **/		
	public int getUserType()
	{
		return userType;
	}
	 /**
	   *Get method to return User info to String
	   *Input -No input
	   *Output -returns a String of the user's info
	   *
	   **/		
	public String userToString()
	{
		String info	= userId + "," + email + "," + password + "," + userType;
		return info;
	}
}
