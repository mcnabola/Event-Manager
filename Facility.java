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
	   *Input -Int facilityid,String of facilityName,double of pricePerHour
	   *Output -No output
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
	   *Input -Int facilityid,String of facilityName,double of pricePerHour,LocalDate of decomission date of facility
	   *Output -No output -
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
	   *Input -Int facilityid,String of facilityName,double of pricePerHour,String of decomission date of facility
	   *Output -No output -
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
	   *Input -none
	   *Output -Returns int facility Id of facility
	   *
	   **/	
	public int getFacilityId()
	{
		return facilityId;
	}
	 /**
	   *Method to Get FacilityName of facility
	   *Input -none
	   *Output -Returns String FacilityName of facility
	   *
	   **/	
	public String getFacilityName()
	{
		return facilityName;
	}
	 /**
	   *Method to Price per hour of facility
	   *Input -noe
	   *Output -Returns double of price per hour of facility -
	   *
	   **/	
	public double getPricePerHour()
	{
		return pricePerHour;
	}
	 /**
	   *Method to Get decommission date of facility
	   *Input -none 
	   *Output -Returns LocalDate decommission date of facility
	   *
	   **/		
	public LocalDate getDecommissionedUntil()
	{
		return decommissionedUntil;
	}	
	 /**
	   *Method to Get String of facility information
	   *Input -none
	   *Output -returns String of facility information
	   *
	   **/		
	public String facilityToString()
	{
		String info = "";

		if (decommissionedUntil != null)
		{
			String temp = decommissionedUntil.format(formatter);
			
			info	= facilityId + "," + facilityName + "," + pricePerHour + "," + temp + "," + available;
		}
		else if(decommissionedUntil.isBefore(LocalDate.now()))
			info = facilityId + "," + facilityName + "," + pricePerHour +"," + available;
		else
			info 	= facilityId + "," + facilityName + "," + pricePerHour + "," + available; //Considering changing to include date=null
		return info;
	}
	 /**
	   *Method to check if facility is available or decommissioned
	   *Input -none
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
	   *Input -String of date to decommision until
	   *Output -None
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
	   *Input -LocalDate of date to decommision until
	   *Output -None
	   *
	   **/		
	public void setDecommissionedUntil(LocalDate aDate)
	{
		this.decommissionedUntil = aDate;
		this.available = getAvailability();
	}
	
	
}
