import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
public class Booking
{
	public int bookingId;
	public int facilityId;
	public int userId;
	public LocalDate bookingDate;
	public int bookingSlot;
	public boolean paymentStatus;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	//FOR RESTORING - MAY ADD FURTHER CONSTRUCTORS
	public Booking(int bookingId, int facilityId, int userId, LocalDate bookingDate, int bookingSlot, boolean paymentStatus)
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
	
	public LocalDate getBookingDate()
	{
		return bookingDate;
	}
	
	public int getBookingSlot()
	{
		return bookingSlot;
	}
	
	public boolean getPaymentStatus()
	{
		return paymentStatus;
	}
	
	public String bookingToString()
	{
		String temp = bookingDate.format(formatter);
		String info = bookingId + "," + facilityId + "," + userId + "," + temp + "," + bookingSlot + "," + paymentStatus;
		return info;
	}

	public void setBookingDate(String aDate)
	{
		this.bookingDate = LocalDate.parse(aDate);
	}
	
	public void setBookingDate(LocalDate aDate)
	{
		this.bookingDate = aDate;
	}
}
