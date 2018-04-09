public class User
{
	public int userId;
	public String email;
	public String password;
	public int userType; //1 = admin, 2 = user

	public User(int userId, String email, String password, int userType) //For restoring users
	{
		this.userId 	= userId;
		this.email		= email;
		this.password 	= password;
		this.userType	= userType;
	}
	
	public User(int userId, String email) //Basic for adding new user
	{
		this.userId 	= userId;
		this.email 		= email;
		this.password	= generatePassword();
		this.userType 	= 2;
	}
	
	
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

	public int getUserId()
	{
		return userId;
	}
	
	public String getEmail()
	{
		return email;
	}

	public String getPassword()
	{
		return password;
	}
	
	public int getUserType()
	{
		return userType;
	}
	
public String userToString()
	{
		String info	= userId + "," + email + "," + password + "," + userType;
		return info;
	}
}
