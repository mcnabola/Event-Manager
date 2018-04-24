import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/	
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
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/	
	   public Booking(int bookingId, int facilityId, int userId, LocalDate bookingDate, int bookingSlot, boolean paymentStatus)
	{
		this.bookingId 		= bookingId;
		this.facilityId 	= facilityId;
		this.userId 		= userId;
		this.bookingDate	= bookingDate;
		this.bookingSlot 	= bookingSlot;
		this.paymentStatus	= paymentStatus;
	}
	
	   public Booking(int bookingId, int facilityId, int userId, String bookingDate, int bookingSlot, boolean paymentStatus)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.bookingId 		= bookingId;
		this.facilityId 	= facilityId;
		this.userId 		= userId;
		this.bookingDate	= LocalDate.parse(bookingDate, formatter);
		this.bookingSlot 	= bookingSlot;
		this.paymentStatus	= paymentStatus;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public int getBookingId()
	{
		return bookingId;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public int getFacilityId()
	{
		return facilityId;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public int getUserId()
	{
		return userId;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public LocalDate getBookingDate()
	{
		return bookingDate;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public int getBookingSlot()
	{
		return bookingSlot;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public boolean getPaymentStatus()
	{
		return paymentStatus;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public String bookingToString()
	{
		String temp = bookingDate.format(formatter);
		String info = bookingId + "," + facilityId + "," + userId + "," + temp + "," + bookingSlot + "," + paymentStatus;
		return info;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/	
	public void setBookingDate(String aDate)
	{
		this.bookingDate = LocalDate.parse(aDate);
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public void setBookingDate(LocalDate aDate)
	{
		this.bookingDate = aDate;
	}
	
	public void setPaymentStatus(boolean paymentStatus)
	{
	       this.paymentStatus=paymentStatus;
	}
}
