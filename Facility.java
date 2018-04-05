import java.util.*;
import java.text.*;
import javax.swing.*;
public class Facility
{
	public int facilityId;
	public String facilityName;
	public double pricePerHour;
	public Date decommissionedUntilDate;
	public boolean availability;
	
	public Facility(int facilityId,String facilityName,double pricePerHour,Date decommissionedUntilDate,boolean availability)
	{
		facilityId++;
		this.facilityName=facilityName;
		this.pricePerHour=pricePerHour;
		this.decommissionedUntilDate=decommissionedUntilDate;
		this.availability=availability;
	}
	
	public Facility(int facilityId,String facilityName,double pricePerHour,boolean availability)
	{
		facilityId++;
		this.facilityName=facilityName;
		this.pricePerHour=pricePerHour;
		this.availability=availability;
	}
	
	
	public int getFacilityId()
	{
		return this.facilityId;
	}
	
	public String getFacilityName()
	{
		return this.facilityName;
	}
	
	public double getPricePerHour()
	{
		return this.pricePerHour;
	}
	
	public Date getDecommissionedUntilDate()
	{
		return this.decommissionedUntilDate;
	}
	
	public boolean getAvailability()
	{
		return this.availability;
	}
	
	public void setFacilityId(int facilityId)
	{
		this.facilityId=facilityId;
	}
	
	public void setFacilityName(String facilityName)
	{
		this.facilityName=facilityName;
	}
	
	public void setPricePerHour(double pricePerHour)
	{
		this.pricePerHour=pricePerHour;
	}
	
	public void setDecommissionedUntilDate(String decommissionedUntilDate)
	{
		try
		{
			SimpleDateFormat aFormatter=new SimpleDateFormat("dd/MM/yyyy");
			this.decommissionedUntilDate=aFormatter.parse(decommissionedUntilDate);
		}
		catch(ParseException e)
		{
			JOptionPane.showMessageDialog(null,"Error when parsing data");
		}
	}
	
	public void setAvailability(boolean availability)
	{
		this.availability=availability;
	}
	
	public String facilityToString()
	{
		String info=""+facilityId+","+facilityName+","+pricePerHour+","+decommissionedUntilDate;
		return info;
	}
	
}
