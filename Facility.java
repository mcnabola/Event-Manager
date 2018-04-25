import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
	 /**
	   *public class Facility,variables for creating object of facility type
	   *
	   *
	   **/	
public class Facility
{
	public int facilityId;
	public String facilityName;
	public double pricePerHour;
	public LocalDate decommissionedUntil;
	public boolean available;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//IF USER DOESN'T WANT IT DECOMISSIONED WHEN CREATING FACILITY CALL THIS CONSTRUCTOR
	/**
	   *Constructor method if the user does not want to decomission the facility
	   
	   @param-Int facilityId is the Id of the facility
	   @param-String facilityName is the name of the facility
	   @param-Double pricePerHour is the price of facility to book per time slot
	   
	   *@return-None
	   *
	   **/	
	public Facility(int facilityId, String facilityName, double pricePerHour)
	{
		this.facilityId 		 = facilityId;
		this.facilityName		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.available			 = true;
	}
	//IF USER WANTS IT DECOMISSIONED WHEN CREATING FACILITY ENTER THIS CONSTRUCTOR
	 /**
	   *Constructor method if the user wants to decomission the facility
	   
	    @param-Int facilityId is the Id of the facility
	    @param-String facilityName is the name of the facility
	    @param-Double pricePerHour is the price of facility to book per time slot
	    @param-LocalDate decommissionedUntil is the date the facility is decommissioned to.
	  
	   
	   *@return-None
	   *
	   **/	
	public Facility(int facilityId, String facilityName, double pricePerHour, LocalDate decommissionedUntil)
	{ 		
		this.facilityId 		 = facilityId;
		this.facilityName 		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.decommissionedUntil = decommissionedUntil;
		this.available			 = getAvailability();
	}
	//IF USER WANTS IT DECOMISSIONED WHEN CREATING FACILITY BUT DATE IS STRING
	 /**
	   *Constructor method if the user wants to decomission the facility and enters date as string
	   
	    @param-Int facilityId is the Id of the facility
	    @param-String facilityName is the name of the facility
	    @param-Double pricePerHour is the price of facility to book per time slot
	    @param-String decommissionedUntil is the date the facility is decommissioned to.
	    
	    @return-None
	   *
	   **/	
	public Facility(int facilityId, String facilityName, double pricePerHour, String decommissionedUntilDate)
	{
		this.facilityId 		 = facilityId;
		this.facilityName 		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		LocalDate aDate 		 = LocalDate.parse(decommissionedUntilDate, formatter);
		this.decommissionedUntil = aDate;
		this.available			 = getAvailability();
	}
	 /**
	   *Method to Get Facility ID of facility
	   
	   *@param-None
	   
	   *@return-Returns int facility Id of facility
	   *
	   **/	
	public int getFacilityId()
	{
		return facilityId;
	}
	 /**
	   *Method to Get FacilityName of facility
	   
	   *@param-None
	   
	   *@return -Returns String FacilityName of facility
	   *
	   **/	
	public String getFacilityName()
	{
		return facilityName;
	}
	 /**
	   *Method to Price per hour of facility
	   
	   *@param-None
	   
	   *@return-Returns double of price per hour of facility -
	   *
	   **/	
	public double getPricePerHour()
	{
		return pricePerHour;
	}
	 /**
	   *Method to Get decommission date of facility
	   
	   *@param-None
	   
	   *@return -Returns LocalDate decommission date of facility
	   *
	   **/		
	public LocalDate getDecommissionedUntil()
	{
		return decommissionedUntil;
	}	
	 /**
	   *Method to Get String of facility information
	   
	   *@param-None
	   
	   *@return-returns String of facility information
	   *
	   **/		
	public String facilityToString()
	{
		String info = "";

		if (decommissionedUntil != null)
		{
			if (decommissionedUntil.isBefore(LocalDate.now()))
				info 	= facilityId + "," + facilityName + "," + pricePerHour + "," + available;
			else
			{
				String temp = decommissionedUntil.format(formatter);
				info = facilityId + "," + facilityName + "," + pricePerHour + "," + temp + "," + available;
			}
		}
		
		else
			info 	= facilityId + "," + facilityName + "," + pricePerHour + "," + available;
		
		return info;
	}
	 /**
	   *Method to check if facility is available or decommissioned
	   
	   *@param-None
	   
	   *Output -returns boolean based on availability
	   *
	   **/		
	public boolean getAvailability()
	{
		LocalDate decommissioned = decommissionedUntil;
		LocalDate today 		 = LocalDate.now();
		boolean available = false;
		if (today.isAfter(decommissionedUntil))
			available = true;
		return available;	
	}
	 /**
	   *Set method to assign a decommission date to a facility object
	   
	   *@param-String of date to decommision until
	   
	   *@return-None
	   *
	   **/		
	public void setDecommissionedUntil(String aDate)//SET WITH A STRING
	{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			this.decommissionedUntil=LocalDate.parse(aDate,formatter);
			this.available		 = getAvailability();
	}
	 /**
	   *Set method to assign a decommission date to a facility object
	   
	   *@param -LocalDate aDate of date to decommision until
	   
	   *@return-None
	   *
	   **/		
	public void setDecommissionedUntil(LocalDate aDate)
	{
		this.decommissionedUntil = aDate;
		this.available = getAvailability();
	}
	
	
}