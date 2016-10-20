
import java.io.Serializable;


public class Rent implements Serializable {
    private int rentalNumber;
    private String dateRent;
    private double pricePerDay;
    private int customerNumber;
    private int vehicleNumber;
    
    public Rent()
    {
        rentalNumber = 0;
        this.dateRent = "";
        pricePerDay = 0;
        customerNumber = 0;
        vehicleNumber = 0;
    }
    public Rent(int no, String dateRent,double price,int custNo, int vehNumber)
    {
        rentalNumber = no;
        this.dateRent = dateRent;
        pricePerDay = price;
        customerNumber = custNo;
        vehicleNumber = vehNumber;
    }
    public Rent(int custNo, int vehNumber)
    {
        customerNumber = custNo;
        vehicleNumber = vehNumber;        
    }
    public int getRentalNumber()
    {
        return this.rentalNumber;
    }
    public String getDateRent()
    {
        return this.dateRent;
    }
    public double getPricePerDay()
    {
        return this.pricePerDay;
    }
    public int getCustomerNumber()
    {
        return this.customerNumber;
    }
    public int getVehicleNumber()
    {
        return this.vehicleNumber;
    }
}
