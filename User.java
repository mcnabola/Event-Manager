public class User
{
	public static int userId;
	public static String email;
	public static String password;
	public static int userType;
	
	public User(String email,String password,int userType)
	{
		userId++;
		this.email=email;
		this.password=password;
		this.userType=userType;
	}

	public int getUserId()
	{
		return this.userId;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public int getUserType()
	{
		return this.userType;
	}
	
	public void setUserId(int userId)
	{
		this.userId=userId;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}
	
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public void setUserType(int userType)
	{
		this.userType=userType;
	}
}
