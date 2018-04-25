import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
	 /**
	   *Creating a Booking class with object varialbes
	   *Input -none
	   *Output -none
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
	   *Input -Int booking id, int facility id, int user id, localDate of booking, int of booking slot, boolean of whether the user has paid or not
	   *Output -None
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
	   *Input -Int booking id, int facility id, int user id, String of bookingdate, int of booking slot, boolean of whether the user has paid or not
	   *Output -None
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
	   *Input -None
	   *Output -Returns int of the BookingId
	   *
	   **/		
	public int getBookingId()
	{
		return bookingId;
	}
	 /**
	   *Get method for FacilityId of the booking
	   *Input -None
	   *Output -Returns int of the FacilityId
	   *
	   **/		
	public int getFacilityId()
	{
		return facilityId;
	}
	 /**
	   *Get method for UserId of the booking made
	   *Input -None
	   *Output -Returns int of the UserId that made the booking
	   *
	   **/		
	public int getUserId()
	{
		return userId;
	}
	 /**
	   *Get method for the booking date of the booking made
	   *Input -None
	   *Output -Returns LocalDate of the day the booking is for
	   *
	   **/		
	public LocalDate getBookingDate()
	{
		return bookingDate;
	}
	 /**
	   *Get method for the slot for which the booking is made
	   *Input -None
	   *Output -Returns int of the booking slot the booking is made for on that date
	   *
	   **/		
	public int getBookingSlot()
	{
		return bookingSlot;
	}
	 /**
	   *Get method for whether the user has paid for their booking or not
	   *Input -None
	   *Output -Returns Boolean of PaymentStatus true if they have paid for booking
	   *
	   **/		
	public boolean getPaymentStatus()
	{
		return paymentStatus;
	}
	 /**
	   *Mehtod to convert all Booking info made to one String
	   *Input -None
	   *Output -reurns String info,which contains all booking variables seperated by a ","
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
	   *Input -String of date the user wishes to book for
	   *Output -None
	   *
	   **/	
	public void setBookingDate(String aDate)
	{
		this.bookingDate = LocalDate.parse(aDate);
	}
	 /**
	   *Set Method to give a booking a date
	   *Input -LocalDate of date the user wishes to book for
	   *Output -None
	   *
	   **/		
	public void setBookingDate(LocalDate aDate)
	{
		this.bookingDate = aDate;
	}
	/**
	   *Set Method to give a booking a payment status
	   *Input -Boolean of wheter the user has paid or not for the booking
	   *Output -None
	   *
	   **/	
	public void setPaymentStatus(boolean paymentStatus)
	{
	       this.paymentStatus=paymentStatus;
	}
}
