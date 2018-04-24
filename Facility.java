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
	
	//IF USER DOEST WANT IT DECOMISSIONED WHEN CREATING FACILITY CALL THIS CONSTRUCTOR
	/**
	   *
	   *Input -
	   *Output -
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
	   *
	   *Input -
	   *Output -
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
	   *
	   *Input -
	   *Output -
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
	public String getFacilityName()
	{
		return facilityName;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/	
	public double getPricePerHour()
	{
		return pricePerHour;
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public LocalDate getDecommissionedUntil()
	{
		return decommissionedUntil;
	}	
	 /**
	   *
	   *Input -
	   *Output -
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
	   *
	   *Input -
	   *Output -
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
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public void setDecommissionedUntil(String aDate)//SET WITH A STRING
	{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			this.decommissionedUntil=LocalDate.parse(aDate,formatter);
			this.available		 = getAvailability();
	}
	 /**
	   *
	   *Input -
	   *Output -
	   *
	   **/		
	public void setDecommissionedUntil(LocalDate aDate)
	{
		this.decommissionedUntil = aDate;
		this.available = getAvailability();
	}
	
	
}
