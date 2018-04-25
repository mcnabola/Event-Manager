import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
	 /**
	   public Class Booking,variables for making objects of booking type
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
	   *Construcutor method to create Booking object
	   
	   @param-Int bookingId is the Id of the booking
	   @param-Int facility is the Id of the facility for which the booking is made for
	   @param-Int UserId is the Id of the user who made the booking
	   @param-LocalDate bookingDate is the date of when the booking is made for
	   @param-Int bookingSlot is the position of the day/time the booking is made for
	   @param-Boolean paymentStatus is the boolean of whether the user has paid for this booking or not
	   
	   *@return-None
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
	/**
	   *Construcutor method to create Booking object
	   
	   @param-Int bookingId is the Id of the booking
	   @param-Int facility is the Id of the facility for which the booking is made for
	   @param-Int UserId is the Id of the user who made the booking
	   @param-String bookingDate is the date of when the booking is made for
	   @param-Int bookingSlot is the position of the day/time the booking is made for
	   @param-Boolean paymentStatus is the boolean of whether the user has paid for this booking or not
	   
	   *@return-None
	   *
	   **/	
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
	   
	   *@param -None
	   
	   *@return -Returns int of the BookingId
	   *
	   **/		
	public int getBookingId()
	{
		return bookingId;
	}
	 /**
	   *Get method for FacilityId of the booking
	   
	   *@param -None
	   
	   *@return -Returns int of the FacilityId
	   *
	   **/		
	public int getFacilityId()
	{
		return facilityId;
	}
	 /**
	   *Get method for UserId of the booking made
	   
	   *@param -None
	   
	   *@retutn -Returns int of the UserId that made the booking
	   *
	   **/		
	public int getUserId()
	{
		return userId;
	}
	 /**
	   *Get method for the booking date of the booking made
	   
	   *@param -None
	   
	   *@return -Returns LocalDate of the day the booking is for
	   *
	   **/		
	public LocalDate getBookingDate()
	{
		return bookingDate;
	}
	 /**
	   *Get method for the slot for which the booking is made
	   
	   *@param -None
	   
	   *@return -Returns int of the booking slot the booking is made for on that date
	   *
	   **/		
	public int getBookingSlot()
	{
		return bookingSlot;
	}
	 /**
	   *Get method for whether the user has paid for their booking or not
	   
	   *@param -None
	   
	   *return -Returns Boolean of PaymentStatus true if they have paid for booking
	   *
	   **/		
	public boolean getPaymentStatus()
	{
		return paymentStatus;
	}
	 /**
	   *Mehtod to convert all Booking info made to one String
	   
	   *@param -None
	   
	   *@return -reurns String info,which contains all booking variables seperated by a ","
	   *
	   **/		
	public String bookingToString()
	{
		String temp = bookingDate.format(formatter);
		String info = bookingId + "," + facilityId + "," + userId + "," + temp + "," + bookingSlot + "," + paymentStatus;
		return info;
	}
	 /**
	   *Set Method to give a booking a date
	   
	   *@param -String aDate is the date the user wishes to book for
	   
	   *@return -None
	   *
	   **/	
	public void setBookingDate(String aDate)
	{
		this.bookingDate = LocalDate.parse(aDate);
	}
	 /**
	   *Set Method to give a booking a date
	   
	   *@param-LocalDate aDate is the date the user wishes to book for
	   
	   *@return -None
	   *
	   **/		
	public void setBookingDate(LocalDate aDate)
	{
		this.bookingDate = aDate;
	}
	/**
	   *Set Method to give a booking a payment status
	   
	   *@param-Boolean paymentStatus of whether the user has paid for the booking or not
	   
	   *@return-none
	   *
	   **/	
	public void setPaymentStatus(boolean paymentStatus)
	{
	       this.paymentStatus=paymentStatus;
	}
}
