import java.util.*;
import java.text.*;
public class Facility
{
	public int facilityId;
	public String facilityName;
	public double pricePerHour;
	public Date decommissionedUntil;
	public boolean available;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public Facility(int facilityId, String facilityName, double pricePerHour)
	{
		this.facilityId 		 = facilityId;
		this.facilityName		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.decommissionedUntil = decommissionedUntil;
		this.available			 = getAvailability();
	}
	//IF USER WANTS IT DECOMISSIONED WHEN CREATING FACILITY ENTER THIS CONSTRUCTOR
	public Facility(int facilityId, String facilityName, double pricePerHour, Date decommissionedUntil)
	{
		this.facilityId 		 = facilityId;
		this.facilityName 		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.decommissionedUntil = decommissionedUntil;
		this.available			 = getAvailability();
	}
	//IF USER DOEST WANT IT DECOMISSIONED WHEN CREATING FACILITY CALL THIS CONSTRUCTOR
	public Facility(int facilityId, String facilityName, double pricePerHour, boolean available)
	{
		this.facilityId 		 = facilityId;
		this.facilityName 		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.decommissionedUntil = null;
		this.available			 = true;
	}
	
	public int getFacilityId()
	{
		return facilityId;
	}
	
	public String getFacilityName()
	{
		return facilityName;
	}
	
	public double getPricePerHour()
	{
		return pricePerHour;
	}
	
	public Date getDecommissionedUntil()
	{
		return decommissionedUntil;
	}	
	
	public String facilityToString()
	{
		String temp = formatter.format(decommissionedUntil);
		String info	= facilityId + "," + facilityName + "," + pricePerHour + "," + temp + "," + available;
		return info;
	}
	
	public boolean getAvailability()
	{
		Date decommissioned = decommissionedUntil;
		boolean available = false;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date current = new Date();
		if (current.after(decommissionedUntil))
			available = true;
		return available;	
	}
	
	public void setDecommissionedUntil(Date decommissionedUntil)
	{
		this.decommissionedUntil = decommissionedUntil;
		this.available		 = getAvailability();
	}
}
