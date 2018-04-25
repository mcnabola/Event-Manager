import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
/**
 * Public class for a Facility
 *
 */	
public class Facility
{
	public int facilityId;
	public String facilityName;
	public double pricePerHour;
	public LocalDate decommissionedUntil;
	public boolean available;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	/**
	 *Constructor method if the user "does not want" to decomission the facility, this constructor is used.
	 * 
	 *@param facilityId Inputs the Id of the facility
	 *@param facilityName Inputs the name of the facility
	 *@param pricePerHour Inputs the price of the facility to book per time slot. 
	 *
	 */		
	public Facility(int facilityId, String facilityName, double pricePerHour)
	{
		this.facilityId 		 = facilityId;
		this.facilityName		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.available			 = true;
	}
	/**
	 *Constructor method if the user "wants to" decomission the facility, this constructor is used.
	 * 
	 *@param facilityId Inputs the Id number of the facility
	 *@param facilityName Inputs the name of the facility
	 *@param pricePerHour Inputs the price of the facility to book per a time slot
	 *@param decommissionedUntil Inputs a date and decommissions the facility until that date.
	 *
	 *@return-None
	 *
	 */	
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
	 *
	 *@param facilityId  Inputs the Id number of the facility.
	 *@param facilityName Input of the name of the facility.
	 *@param pricePerHour Input of the price of the facility to book per time slot.
	 *@param decommissionedUntilDate decommissionedUntil is the date the facility is decommissioned to.
	 *
	 */	
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
	 *Method to Get Facility ID of facility.
	 *  
	 *@return facilityId Returns a int of the ID number of the facility.
	 *
	 */	
	public int getFacilityId()
	{
		return facilityId;
	}
	/**
	 *Method to return the Name of a facility.
	 *  
	 *@return facilityName A String returning the name of a facility.
	 *
	 */	
	public String getFacilityName()
	{
		return facilityName;
	}
	/**
	 *Method to Price per hour of facility
	 *  
	 *@return pricePerHour Returns a double value of the price per hour of the facility. -
	 *
	 */	
	public double getPricePerHour()
	{
		return pricePerHour;
	}
	/**
	 *Method to get the decommissioned date for a facility.
	 *   
	 *@return decommissionedUntil Returns a LocalDate of the date of decomission for a facility
	 *
	 */		
	public LocalDate getDecommissionedUntil()
	{
		return decommissionedUntil;
	}	
	/**
	 *Method to get a String containing all of the facilities information available.
	 *    
	 *@return-returns String of facility information
	 *
	 */		
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
	 *Method to check if a facility is available or decommissioned.  
	 *
	 *return    Returning a boolean based on the availability of the facility.
	 *
	 */		
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
	 *Set method to assign a decommission date to a facility object, this is an alternative to setting it with a LocalDate
	 *  
	 *@param aDate String of a date that you want to decommision a facility until. 
	 *
	 */		
	public void setDecommissionedUntil(String aDate)//SET WITH A STRING
	{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			this.decommissionedUntil=LocalDate.parse(aDate,formatter);
			this.available		 = getAvailability();
	}
	/**
	 *Set method to assign a decommission date to a facility object.
	 * 
	 *@param aDate This is a date that you want to decommision the facility until. 
	 *
	 */		
	public void setDecommissionedUntil(LocalDate aDate)
	{
		this.decommissionedUntil = aDate;
		this.available = getAvailability();
	}
	
	
}
