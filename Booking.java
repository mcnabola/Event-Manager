import java.util.*;
import java.text.*;
public class Booking
{
	public int bookingId;
	public int facilityId;
	public int userId;
	public Date bookingDate;
	public int bookingSlot;
	public char paymentStatus;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public Booking(int bookingId, int facilityId, int userId, Date bookingDate, int bookingSlot, char paymentStatus)
	{
		this.bookingId 		= bookingId;
		this.facilityId 	= facilityId;
		this.userId 		= userId;
		this.bookingDate	= bookingDate;
		this.bookingSlot 	= bookingSlot;
		this.paymentStatus	= paymentStatus;
	}
	
	public int getBookingId()
	{
		return bookingId;
	}
	
	public int getFacilityId()
	{
		return facilityId;
	}
	
	public int getUserId()
	{
		return userId;
	}
	
	public Date getBookingDate()
	{
		return bookingDate;
	}
	
	public int getBookingSlot()
	{
		return bookingSlot;
	}
	
	public char getPaymentStatus()
	{
		return paymentStatus;
	}
	
	public String bookingToString()
	{
		String temp = formatter.format(bookingDate);
		String info = bookingId + "," + facilityId + "," + userId + "," + temp + "," + bookingSlot + "," + paymentStatus;
		return info;
	}

}
