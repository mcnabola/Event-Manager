import java.util.*;
import java.text.*;
import javax.swing.*;
public class Booking
{
	private static int bookingId;
	private static int facilityId;
	private static int userId;
	private static Date date;
	private static int slotNumber; // e.g 9am - 10am is slot 1.
	private static char paymentStatus;
	
	public Booking(int bookingId,int facilityId,int userId,Date date,int slotNumber,char paymentStatus)
	{
	bookingId++;
	this.facilityId=facilityId;
	this.userId=userId;
	this.date=date;
	this.slotNumber=slotNumber;
	this.paymentStatus=paymentStatus;
	}
	
	public int getBookingId()
	{
		return this.bookingId;
	}
	
	public int getFacilityId()
	{
		return this.facilityId;
	}
	
	public int getUserId()
	{
		return this.userId;
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public int getSlotNumber()
	{
		return this.slotNumber;
	}
	
	public char getPaymentStatus()
	{
		return this.paymentStatus;
	}
	
	public void setBookingId(int bookingId)
	{
		this.bookingId=bookingId;
	}
	
	public void setFacilityId(int facilityId)
	{
		this.facilityId=facilityId;
	}
	
	public void setUserId(int userId)
	{
		this.userId=userId;
	}
	
	public void setDate(String date)
	{
		try
		{
			SimpleDateFormat aFormatter=new SimpleDateFormat("dd/MM/yyyy");
			this.date=aFormatter.parse(date);
		}
		catch(ParseException e)
		{
			JOptionPane.showMessageDialog(null,"Error when parsing data");
		}
	}
	
	public void setSlotNumber(int slotNumber)
	{
		this.slotNumber=slotNumber;
	}
	
	public void setPaymentStatus(char paymentStatus)
	{
		this.paymentStatus=paymentStatus;
	}
	
}