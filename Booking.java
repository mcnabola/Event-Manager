import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
/**
 *Public Class for Bookings
 *
 */	
public class Booking
{
	public int bookingId;
	public int facilityId;
	public int userId;
	public LocalDate bookingDate;
	public int bookingSlot;
	public boolean paymentStatus;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 *Construcutor method to create Booking object
	 * 
	 *@param bookingId Input is the Id of the booking
	 *@param facilityId Input is the Id of the facility for which the booking is made for
	 *@param userId Input is the Id of the user who made the booking
	 *@param bookingDate Inputs is the date of when the booking is made for in the LocalDate type.
	 *@param bookingSlot Input is the position of the day/time the booking is made for
	 *@param paymentStatus Inputs the boolean of whether the user has paid for this booking or not
	 *
	 */	
	   public Booking(int bookingId, int facilityId, int userId, LocalDate bookingDate, int bookingSlot, boolean paymentStatus)
	{
		this.bookingId 		= bookingId;
		this.facilityId 	= facilityId;
		this.userId 		= userId;
		this.bookingDate	= bookingDate;
		this.bookingSlot 	= bookingSlot;
		this.paymentStatus	= paymentStatus;
	}
	/**
	 *Construcutor method to create Booking object
	 *  
	 *@param bookingId is the Id of the booking
	 *@param facility is the Id of the facility for which the booking is made for
	 *@param UserId is the Id of the user who made the booking
	 *@param bookingDate is the date of when the booking is made for
	 *@param bookingSlot is the position of the day/time the booking is made for
	 *@param paymentStatus is the boolean of whether the user has paid for this booking or not
	 *
	 */	
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
	 *Get method for BookingId of the booking made
	 *    
	 *@return getBookingId Returns the booking id of a booking object.
	 *
	 */		
	public int getBookingId()
	{
		return bookingId;
	}
	/**
	 *Get method for FacilityId of the booking
	 * 	   
	 *@return facilityId Returns a int of the facilities id number.
	 *
	  */		
	public int getFacilityId()
	{
		return facilityId;
	}
	/**
	 *Get method for UserId of the booking made
	 *  
	 *@retutn userID Returns a int of the UserId that is made for the booking
	 *
	 */		
	public int getUserId()
	{
		return userId;
	}
	/**
	 *Get method for the booking date of the booking made
	 *  
	 *@return bookingDate Returns a LocalDate of the day the booking is made for.
	 *
	 */		
	public LocalDate getBookingDate()
	{
		return bookingDate;
	}
	/**
	 *Get method for the slot for which the booking is made
	 *
	 *  
	 *@return bookingSlot The booking is made for on that date
	 *
	 */		
	public int getBookingSlot()
	{
		return bookingSlot;
	}
	/**
	 *Get method for whether the user has paid for their booking or not
	 *    
	 *return paymentStatus Boolean return based on payment status.
	 *
	 */		
	public boolean getPaymentStatus()
	{
		return paymentStatus;
	}
	/**
	 *Mehtod to convert all Booking info made to one String
	 *
	 *@return info Contains all booking variables seperated by a ","
	 *
	 */		
	public String bookingToString()
	{
		String temp = bookingDate.format(formatter);
		String info = bookingId + "," + facilityId + "," + userId + "," + temp + "," + bookingSlot + "," + paymentStatus;
		return info;
	}
	/**
	 *Set Method to give a booking a date, alternative to the LocalDate
	 *
	 *@param aDate Date entered as a String is the date the user wishes to book for
	 *
	 *
	 */	
	public void setBookingDate(String aDate)
	{
		this.bookingDate = LocalDate.parse(aDate);
	}
	/**
	 *Set Method to give a booking a date
	 *
	 *@param aDate date the user wishes to book for
	 *
	 *
	 */		
	public void setBookingDate(LocalDate aDate)
	{
		this.bookingDate = aDate;
	}
	/**
	 *Set Method to give a booking a payment status
	 *
	 *@param paymentStatus Boolean of whether the user has paid for the booking or not
	 *
	 *
	 */	
	public void setPaymentStatus(boolean paymentStatus)
	{
	       this.paymentStatus=paymentStatus;
	}
}
